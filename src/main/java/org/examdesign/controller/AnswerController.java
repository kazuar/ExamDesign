package org.examdesign.controller;

import java.util.List;

import org.examdesign.domain.Answer;
import org.examdesign.repository.AnswerRepository;
import org.examdesign.response.AnswerDto;
import org.examdesign.response.JqgridResponse;
import org.examdesign.response.StatusResponse;
import org.examdesign.service.AnswerService;
import org.examdesign.util.AnswerMapper;
import org.examdesign.util.JqgridFilter;
import org.examdesign.util.JqgridObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/answers")
public class AnswerController {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private AnswerService answerService;
	
	@RequestMapping
	public String getAnswersPage() {
		return "answers";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<AnswerDto> records(
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
		
		Page<Answer> answers = answerRepository.findAll(pageRequest);
		List<AnswerDto> answerDtos = AnswerMapper.map(answers);
		
		JqgridResponse<AnswerDto> response = new JqgridResponse<AnswerDto>();
		response.setRows(answerDtos);
		response.setRecords(Long.valueOf(answers.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(answers.getTotalPages()).toString());
		response.setPage(Integer.valueOf(answers.getNumber()+1).toString());
		
		return response;
	}
	
	/**
	 * Helper method for returning filtered records
	 */
	public JqgridResponse<AnswerDto> getFilteredRecords(String filters, Pageable pageRequest) {
		String qAnswerText = null;
		
		JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters);
		for (JqgridFilter.Rule rule: jqgridFilter.getRules()) {
			if (rule.getField().equals("answerText"))
				qAnswerText = rule.getData();
		}
		
		Page<Answer> answers = null;
		if (qAnswerText != null) 
			answers = answerRepository.findByAnswerTextLike("%"+qAnswerText+"%", pageRequest);
		
		List<AnswerDto> answerDtos = AnswerMapper.map(answers);
		JqgridResponse<AnswerDto> response = new JqgridResponse<AnswerDto>();
		response.setRows(answerDtos);
		response.setRecords(Long.valueOf(answers.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(answers.getTotalPages()).toString());
		response.setPage(Integer.valueOf(answers.getNumber()+1).toString());
		return response;
	}
	
	@RequestMapping(value="/get", produces="application/json")
	public @ResponseBody AnswerDto get(@RequestBody AnswerDto answer) {
		return AnswerMapper.map(answerRepository.findOne(answer.getId()));
	}

	@RequestMapping(value="/create", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse create(
			@RequestParam(required=true) String answerText) {
		
		Answer newAnswer = new Answer(answerText);
		Boolean result = answerService.create(newAnswer);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/createAndReturn", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse createAndReturn(
			@RequestParam(required=true) String answerText) {
		
		Answer newAnswer = new Answer(answerText);
		Boolean result = true;
		Answer savedAnswer = null;
		StatusResponse statusResponse = new StatusResponse();
		
		savedAnswer = answerService.createAndReturn(newAnswer);
		if (savedAnswer != null) {
			result = true;
			statusResponse.addAnswer(newAnswer);
		}
		
		statusResponse.setSuccess(result);
		
		return statusResponse;
	}
	
	@RequestMapping(value="/update", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse update(
			@RequestParam(required=true) Long id,
			@RequestParam(required=true) String answerText) {
		
		Answer existingAnswer = new Answer(id, answerText);
		Boolean result = answerService.update(existingAnswer);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/delete", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse delete(
			@RequestParam Long id) {
		
		Answer existingAnswer = new Answer(id);
		Boolean result = true;
		StatusResponse statusResponse = new StatusResponse();
		try {
			
			result = answerService.delete(existingAnswer);
			
		} catch (DataIntegrityViolationException e) {
			result = false;
			statusResponse.setMessage("Answer is being used in a question");
			
		} catch (Exception e) {
			result = false;
			statusResponse.setMessage(e.getMessage());
		}
		
		statusResponse.setSuccess(result);
		return statusResponse;
	}
}
