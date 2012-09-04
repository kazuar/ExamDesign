package org.examdesign.controller;

import java.util.List;

import org.examdesign.domain.Difficulty;
import org.examdesign.repository.DifficultyRepository;
import org.examdesign.response.DifficultyDto;
import org.examdesign.response.JqgridResponse;
import org.examdesign.response.StatusResponse;
import org.examdesign.service.DifficultyService;
import org.examdesign.util.DifficultyMapper;
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
@RequestMapping("/difficulties")
public class DifficultyController {
	
	@Autowired
	private DifficultyRepository difficultyRepository;
	
	@Autowired
	private DifficultyService difficultyService;
	
	@RequestMapping
	public String getDifficultysPage() {
		return "difficulties";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<DifficultyDto> records(
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
		
		Page<Difficulty> difficultys = difficultyRepository.findAll(pageRequest);
		List<DifficultyDto> difficultyDtos = DifficultyMapper.map(difficultys);
		
		JqgridResponse<DifficultyDto> response = new JqgridResponse<DifficultyDto>();
		response.setRows(difficultyDtos);
		response.setRecords(Long.valueOf(difficultys.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(difficultys.getTotalPages()).toString());
		response.setPage(Integer.valueOf(difficultys.getNumber()+1).toString());
		
		return response;
	}
	
	/**
	 * Helper method for returning filtered records
	 */
	public JqgridResponse<DifficultyDto> getFilteredRecords(String filters, Pageable pageRequest) {
		String qDifficultyText = null;
		
		JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters);
		for (JqgridFilter.Rule rule: jqgridFilter.getRules()) {
			if (rule.getField().equals("difficultyName"))
				qDifficultyText = rule.getData();
		}
		
		Page<Difficulty> categories = null;
		if (qDifficultyText != null) 
			categories = difficultyRepository.findByDifficultyNameLike("%" + qDifficultyText + "%", pageRequest);
		
		List<DifficultyDto> difficultyDtos = DifficultyMapper.map(categories);
		JqgridResponse<DifficultyDto> response = new JqgridResponse<DifficultyDto>();
		response.setRows(difficultyDtos);
		response.setRecords(Long.valueOf(categories.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(categories.getTotalPages()).toString());
		response.setPage(Integer.valueOf(categories.getNumber()+1).toString());
		return response;
	}
	
	@RequestMapping(value="/get", produces="application/json")
	public @ResponseBody DifficultyDto get(@RequestBody DifficultyDto difficulty) {
		return DifficultyMapper.map(difficultyRepository.findOne(difficulty.getId()));
	}

	@RequestMapping(value="/create", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse create(
			@RequestParam(required=true) String difficultyName) {
		
		Difficulty newDifficulty = new Difficulty(difficultyName);
		Boolean result = difficultyService.create(newDifficulty);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/update", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse update(
			@RequestParam(required=true) Long id,
			@RequestParam(required=true) String difficultyName) {
		
		Difficulty existingDifficulty = new Difficulty(id, difficultyName);
		Boolean result = difficultyService.update(existingDifficulty);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/delete", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse delete(
			@RequestParam Long id) {
		
		Difficulty existingDifficulty = new Difficulty(id);
		
		Boolean result = true;
		StatusResponse statusResponse = new StatusResponse();
		
		try {
			
			result = difficultyService.delete(existingDifficulty);
			
		} catch (DataIntegrityViolationException e) {
			result = false;
			statusResponse.setMessage("Difficulty is being used in a question");
			
		} catch (Exception e) {
			result = false;
			statusResponse.setMessage(e.getMessage());
		}
		
		statusResponse.setSuccess(result);
		return statusResponse;
	}
}
