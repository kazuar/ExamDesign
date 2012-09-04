package org.examdesign.util;

import java.util.ArrayList;
import java.util.List;

import org.examdesign.domain.QuestionType;
import org.examdesign.response.QuestionTypeDto;
import org.springframework.data.domain.Page;

public class QuestionTypeMapper {
	
	public static QuestionTypeDto map(QuestionType questionType) {
		QuestionTypeDto dto = new QuestionTypeDto();
		dto.setId(questionType.getId());
		dto.setQuestionTypeName(questionType.getQuestionTypeName());
		return dto;
	}
	
	public static List<QuestionTypeDto> map(Page<QuestionType> categories) {
		
		List<QuestionTypeDto> dtos = new ArrayList<QuestionTypeDto>();
		for (QuestionType questionType : categories) {
			dtos.add(map(questionType));
		}
		
		return dtos;
	}
}
