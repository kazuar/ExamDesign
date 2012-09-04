package org.examdesign.controller;

import java.util.List;

import org.examdesign.domain.Category;
import org.examdesign.repository.CategoryRepository;
import org.examdesign.response.CategoryDto;
import org.examdesign.response.JqgridResponse;
import org.examdesign.response.StatusResponse;
import org.examdesign.service.CategoryService;
import org.examdesign.util.CategoryMapper;
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
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping
	public String getCategorysPage() {
		return "categories";
	}
	
	@RequestMapping(value="/records", produces="application/json")
	public @ResponseBody JqgridResponse<CategoryDto> records(
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
		
		Page<Category> categorys = categoryRepository.findAll(pageRequest);
		List<CategoryDto> categoryDtos = CategoryMapper.map(categorys);
		
		JqgridResponse<CategoryDto> response = new JqgridResponse<CategoryDto>();
		response.setRows(categoryDtos);
		response.setRecords(Long.valueOf(categorys.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(categorys.getTotalPages()).toString());
		response.setPage(Integer.valueOf(categorys.getNumber()+1).toString());
		
		return response;
	}
	
	/**
	 * Helper method for returning filtered records
	 */
	public JqgridResponse<CategoryDto> getFilteredRecords(String filters, Pageable pageRequest) {
		String qCategoryText = null;
		
		JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters);
		for (JqgridFilter.Rule rule: jqgridFilter.getRules()) {
			if (rule.getField().equals("categoryName"))
				qCategoryText = rule.getData();
		}
		
		Page<Category> categories = null;
		if (qCategoryText != null) 
			categories = categoryRepository.findByCategoryNameLike("%" + qCategoryText + "%", pageRequest);
		
		List<CategoryDto> categoryDtos = CategoryMapper.map(categories);
		JqgridResponse<CategoryDto> response = new JqgridResponse<CategoryDto>();
		response.setRows(categoryDtos);
		response.setRecords(Long.valueOf(categories.getTotalElements()).toString());
		response.setTotal(Integer.valueOf(categories.getTotalPages()).toString());
		response.setPage(Integer.valueOf(categories.getNumber()+1).toString());
		return response;
	}
	
	@RequestMapping(value="/get", produces="application/json")
	public @ResponseBody CategoryDto get(@RequestBody CategoryDto category) {
		return CategoryMapper.map(categoryRepository.findOne(category.getId()));
	}

	@RequestMapping(value="/create", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse create(
			@RequestParam(required=true) String categoryName) {
		
		Category newCategory = new Category(categoryName);
		Boolean result = categoryService.create(newCategory);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/update", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse update(
			@RequestParam(required=true) Long id,
			@RequestParam(required=true) String categoryName) {
		
		Category existingCategory = new Category(id, categoryName);
		Boolean result = categoryService.update(existingCategory);
		return new StatusResponse(result);
	}
	
	@RequestMapping(value="/delete", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody StatusResponse delete(
			@RequestParam Long id) {
		
		Category existingCategory = new Category(id);
		Boolean result = true;
		StatusResponse statusResponse = new StatusResponse();
		
		try {
			
			result = categoryService.delete(existingCategory);
			
		} catch (DataIntegrityViolationException e) {
			result = false;
			statusResponse.setMessage("Category is being used in a question");
			
		} catch (Exception e) {
			result = false;
			statusResponse.setMessage(e.getMessage());
		}
		
		statusResponse.setSuccess(result);
		return statusResponse;
	}
}
