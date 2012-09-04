package org.examdesign.controller;

import java.util.List;

import org.examdesign.domain.QuestionType;
import org.examdesign.repository.QuestionTypeRepository;
import org.examdesign.response.JqgridResponse;
import org.examdesign.response.QuestionTypeDto;
import org.examdesign.response.StatusResponse;
import org.examdesign.service.QuestionTypeService;
import org.examdesign.util.JqgridFilter;
import org.examdesign.util.JqgridObjectMapper;
import org.examdesign.util.QuestionTypeMapper;
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
@RequestMapping("/questiontypes")
public class QuestionTypeController {
	
	@Autowired
	private QuestionTypeRepository questionTypeRepository;
	
	@Autowired
	private QuestionTypeService questionTypeService;
	
	@RequestMapping
	public String getQuestionTypesPage() {
		return "questiontypes";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<QuestionTypeDto> records(
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
		
		Page<QuestionType> questionTypes = questionTypeRepository.findAll(pageRequest);
		List<QuestionTypeDto> questionTypeDtos = QuestionTypeMapper.map(questionTypes);
		
		JqgridResponse<QuestionTypeDto> response = new JqgridResponse<QuestionTypeDto>();
		response.setRows(questionTypeDtos);
		response.setRecords(Long.valueOf(questionTypes.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(questionTypes.getTotalPages()).toString());
		response.setPage(Integer.valueOf(questionTypes.getNumber()+1).toString());
		
		return response;
	}
	
	/**
	 * Helper method for returning filtered records
	 */
	public JqgridResponse<QuestionTypeDto> getFilteredRecords(String filters, Pageable pageRequest) {
		String qQuestionTypeText = null;
		
		JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters);
		for (JqgridFilter.Rule rule: jqgridFilter.getRules()) {
			if (rule.getField().equals("questionTypeName"))
				qQuestionTypeText = rule.getData();
		}
		
		Page<QuestionType> categories = null;
		if (qQuestionTypeText != null) 
			categories = questionTypeRepository.findByQuestionTypeNameLike("%" + qQuestionTypeText + "%", pageRequest);
		
		List<QuestionTypeDto> questionTypeDtos = QuestionTypeMapper.map(categories);
		JqgridResponse<QuestionTypeDto> response = new JqgridResponse<QuestionTypeDto>();
		response.setRows(questionTypeDtos);
		response.setRecords(Long.valueOf(categories.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(categories.getTotalPages()).toString());
		response.setPage(Integer.valueOf(categories.getNumber()+1).toString());
		return response;
	}
	
	@RequestMapping(value="/get", produces="application/json")
	public @ResponseBody QuestionTypeDto get(@RequestBody QuestionTypeDto questionType) {
		return QuestionTypeMapper.map(questionTypeRepository.findOne(questionType.getId()));
	}

	@RequestMapping(value="/create", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse create(
			@RequestParam(required=true) String questionTypeName) {
		
		QuestionType newQuestionType = new QuestionType(questionTypeName);
		Boolean result = questionTypeService.create(newQuestionType);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/update", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse update(
			@RequestParam(required=true) Long id,
			@RequestParam(required=true) String questionTypeName) {
		
		QuestionType existingQuestionType = new QuestionType(id, questionTypeName);
		Boolean result = questionTypeService.update(existingQuestionType);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/delete", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse delete(
			@RequestParam Long id) {
		
		QuestionType existingQuestionType = new QuestionType(id);
		Boolean result = true;
		StatusResponse statusResponse = new StatusResponse();
		
		try {
			
			result = questionTypeService.delete(existingQuestionType);
			
		} catch (DataIntegrityViolationException e) {
			result = false;
			statusResponse.setMessage("Question Type is being used in a question");
			
		} catch (Exception e) {
			result = false;
			statusResponse.setMessage(e.getMessage());
		}
		
		statusResponse.setSuccess(result);
		return statusResponse;
	}
}
