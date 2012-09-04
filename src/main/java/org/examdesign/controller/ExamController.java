package org.examdesign.controller;

import java.util.List;
import java.util.Map;

import org.examdesign.domain.Exam;
import org.examdesign.repository.ExamRepository;
import org.examdesign.response.ExamDto;
import org.examdesign.response.JqgridResponse;
import org.examdesign.response.StatusResponse;
import org.examdesign.service.ExamService;
import org.examdesign.util.ExamMapper;
import org.examdesign.util.JqgridFilter;
import org.examdesign.util.JqgridObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/exams")
public class ExamController {
	
	@Autowired
	private ExamRepository examRepository;
	
	@Autowired 
	private ExamService examService;
	
	@RequestMapping
	public String getExamsPage() {
		return "exams";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<ExamDto> records(
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
			
		Page<Exam> exams = examRepository.findAll(pageRequest);
		List<ExamDto> examDtos = ExamMapper.map(exams);
		
		JqgridResponse<ExamDto> response = new JqgridResponse<ExamDto>();
		response.setRows(examDtos);
		response.setRecords(Long.valueOf(exams.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(exams.getTotalPages()).toString());
		response.setPage(Integer.valueOf(exams.getNumber()+1).toString());
		
		return response;
	}
	
	/**
	 * Helper method for returning filtered records
	 */
	public JqgridResponse<ExamDto> getFilteredRecords(String filters, Pageable pageRequest) {
		String qExamName = null;
		
		JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters);
		for (JqgridFilter.Rule rule: jqgridFilter.getRules()) {
			if (rule.getField().equals("examName"))
				qExamName = rule.getData();
		}
		
		Page<Exam> exams = null;
		if (qExamName != null) 
			exams = examRepository.findByExamNameLike("%" + qExamName + "%", pageRequest);
		
		List<ExamDto> examDtos = ExamMapper.map(exams);
		JqgridResponse<ExamDto> response = new JqgridResponse<ExamDto>();
		response.setRows(examDtos);
		response.setRecords(Long.valueOf(exams.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(exams.getTotalPages()).toString());
		response.setPage(Integer.valueOf(exams.getNumber()+1).toString());
		return response;
	}
	
	@RequestMapping(value="/get", produces="application/json")
	public @ResponseBody ExamDto get(@RequestBody ExamDto exam) {
		return ExamMapper.map(examRepository.findOne(exam.getId()));
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addExam(Map<String, Object> map) {
		
		map.put("exam", new Exam());
		map.put("action", "create");
		return "/newexam";
	}
	
	@RequestMapping(value = "/createExam", method = RequestMethod.POST)
	public String createExam(@ModelAttribute("exam") Exam exam, BindingResult result) {

		if (result.hasErrors()) {
			return "/add";
		}
			
		examService.create(exam);
		
		return "redirect:/exams";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editExam(@RequestParam Long id, Map<String, Object> map) {
		
		Exam existingExam = examRepository.findOne(id);
		if (existingExam == null) {
			return "redirect:/exams";
		}
		
		map.put("exam", existingExam);
		map.put("action", "update");
		
		return "/newexam";
	}
	
	@RequestMapping(value = "/updateExam", method = RequestMethod.POST)
	public String updateExam(@ModelAttribute("exam") Exam exam, BindingResult result) {

		if (result.hasErrors()) {
			return "/edit";
		}
			
		examService.update(exam);
		
		return "redirect:/exams";
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
		
		Exam existingExam = new Exam(id);
		Boolean result = examService.delete(existingExam);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/run", method=RequestMethod.GET)
	public String run (@RequestParam Long id, Map<String, Object> map) {

		Exam existingExam = examRepository.findOne(id);
		if (existingExam == null) {
			return "redirect:/exams";
		}
		
		map.put("exam", existingExam);
		
		return "/runexam";
	}
}
