package org.examdesign.controller;

import java.util.List;
import java.util.Map;

import org.examdesign.domain.Answer;
import org.examdesign.domain.Exam;
import org.examdesign.domain.Question;
import org.examdesign.domain.QuestionAnswers;
import org.examdesign.repository.CategoryRepository;
import org.examdesign.repository.DifficultyRepository;
import org.examdesign.repository.QuestionRepository;
import org.examdesign.repository.QuestionTypeRepository;
import org.examdesign.response.JqgridResponse;
import org.examdesign.response.QuestionDto;
import org.examdesign.response.StatusResponse;
import org.examdesign.service.CategoryService;
import org.examdesign.service.DifficultyService;
import org.examdesign.service.QuestionAnswersService;
import org.examdesign.service.QuestionService;
import org.examdesign.service.QuestionTypeService;
import org.examdesign.util.JqgridFilter;
import org.examdesign.util.JqgridObjectMapper;
import org.examdesign.util.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired 
	private QuestionService questionService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private DifficultyRepository difficultyRepository;
	
	@Autowired
	private QuestionTypeRepository questionTypeRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private DifficultyService difficultyService;
	
	@Autowired
	private QuestionTypeService questionTypeService;
	
	@RequestMapping
	public String getQuestionsPage() {
		return "questions";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<QuestionDto> records(
    		@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sidx,
    		@RequestParam(value="sord", required=false) String sord) {

		Pageable pageRequest = new PageRequest(page-1, rows);
		
		if (search == true) {
			return getFilteredRecords(filters, pageRequest);			
		} 
			
		Page<Question> questions = questionRepository.findAll(pageRequest);
		List<QuestionDto> questionDtos = QuestionMapper.map(questions);
		
		JqgridResponse<QuestionDto> response = new JqgridResponse<QuestionDto>();
		response.setRows(questionDtos);
		response.setRecords(Long.valueOf(questions.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(questions.getTotalPages()).toString());
		response.setPage(Integer.valueOf(questions.getNumber()+1).toString());
		
		response.addAdditionalData("categories", categoryService.list());
		response.addAdditionalData("difficulties", difficultyService.list());
		response.addAdditionalData("questiontypes", questionTypeService.list());
		
		return response;
	}
	
	/**
	 * Helper method for returning filtered records
	 */
	public JqgridResponse<QuestionDto> getFilteredRecords(String filters, Pageable pageRequest) {
		String qQuestionText = null;
		Long qCategory = null;
		Long qDifficulty = null;
		Long qQuestionType = null;
		
		JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters);
		for (JqgridFilter.Rule rule: jqgridFilter.getRules()) {
			if (rule.getField().equals("questionText"))
				qQuestionText = rule.getData();
			else if (rule.getField().equals("category"))
				qCategory = Long.valueOf(rule.getData());
			else if (rule.getField().equals("difficulty"))
				qDifficulty = Long.valueOf(rule.getData());
			else if (rule.getField().equals("questionType"))
				qQuestionType = Long.valueOf(rule.getData());
		}
		
		Page<Question> questions = null;
		if (qQuestionText != null) 
			questions = questionRepository.findByQuestionTextLike("%" + qQuestionText + "%", pageRequest);
		if (qCategory != null) 
			questions = questionRepository.findByCategory(qCategory, pageRequest);
		if (qDifficulty != null) 
			questions = questionRepository.findByDifficulty(qDifficulty, pageRequest);
		if (qQuestionType != null) 
			questions = questionRepository.findByQuestionType(qQuestionType, pageRequest);
		
		List<QuestionDto> questionDtos = QuestionMapper.map(questions);
		JqgridResponse<QuestionDto> response = new JqgridResponse<QuestionDto>();
		response.setRows(questionDtos);
		response.setRecords(Long.valueOf(questions.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(questions.getTotalPages()).toString());
		response.setPage(Integer.valueOf(questions.getNumber()+1).toString());
		
		response.addAdditionalData("categories", categoryService.list());
		response.addAdditionalData("difficulties", difficultyService.list());
		response.addAdditionalData("questiontypes", questionTypeService.list());
		
		return response;
	}
	
	@RequestMapping(value="/get", produces="application/json")
	public @ResponseBody QuestionDto get(@RequestBody QuestionDto question) {
		return QuestionMapper.map(questionRepository.findOne(question.getId()));
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addQuesiton(Map<String, Object> map) {
		
		map.put("question", new Question());
		map.put("difficultyList", difficultyRepository.findAll());
		map.put("categoryList", categoryRepository.findAll());
		map.put("questionTypeList", questionTypeRepository.findAll());
		//map.put("answerList", answerService.listEntities());
		map.put("action", "create");
		map.put("answer", new Answer());
		return "/newquestion";
	}
	
	@RequestMapping(value = "/createQuestion", method = RequestMethod.POST)
	public String createQuestion(@Validated @ModelAttribute("question") Question question, BindingResult result) {

		if (result.hasErrors()) {
			return "/newquestion";
		}
			
		questionService.create(question);
		
		return "redirect:/questions";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editQuestion(@RequestParam Long id, Map<String, Object> map) {
		
		Question existingQuestion = questionRepository.findOne(id);
		if (existingQuestion == null) {
			return "redirect:/questions";
		}
		
		map.put("question", existingQuestion);
		map.put("difficultyList", difficultyRepository.findAll());
		map.put("categoryList", categoryRepository.findAll());
		map.put("questionTypeList", questionTypeRepository.findAll());
		map.put("action", "update");
		
		return "/newquestion";
	}
	
	@RequestMapping(value = "/updateQuestion", method = RequestMethod.POST)
	public String updateQuestion(@ModelAttribute("question") Question question, BindingResult result) {

		if (result.hasErrors()) {
			return "/newquestion";
		}
			
		questionService.update(question);
		
		return "redirect:/questions";
	}

	/*
	@RequestMapping(value="/update", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse update(
			@RequestParam String username,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam Integer role) {
		
		User existingUser = new User(username, firstName, lastName, new Role(role));
		Boolean result = service.update(existingUser);
		return new StatusResponse(result);
	}
	*/
	
	@RequestMapping(value="/delete", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse delete(
			@RequestParam Long id) {
		
		Question existingQuestion = new Question(id);
		Boolean result = true;
		StatusResponse statusResponse = new StatusResponse();
		
		try {
			result = questionService.deleteQuestionAnswers(existingQuestion);
			
			result = questionService.delete(existingQuestion);
		} catch (DataIntegrityViolationException e) {
			result = false;
			statusResponse.setMessage("Question is being used in an Exam");
			
		} catch (Exception e) {
			result = false;
			statusResponse.setMessage(e.getMessage());
		}
		
		statusResponse.setSuccess(result);
		return statusResponse;
	}
}
