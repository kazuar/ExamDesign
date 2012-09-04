package org.examdesign.util;

import java.util.ArrayList;
import java.util.List;

import org.examdesign.domain.Answer;
import org.examdesign.response.AnswerDto;
import org.springframework.data.domain.Page;

public class AnswerMapper {
	
	public static AnswerDto map(Answer answer) {
		AnswerDto dto = new AnswerDto();
		dto.setId(answer.getId());
		dto.setAnswerText(answer.getAnswerText());
		return dto;
	}
	
	public static List<AnswerDto> map(Page<Answer> answers) {
		
		List<AnswerDto> dtos = new ArrayList<AnswerDto>();
		for (Answer answer : answers) {
			dtos.add(map(answer));
		}
		
		return dtos;
	}
}
