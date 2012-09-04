package org.examdesign.util;

import java.util.ArrayList;
import java.util.List;

import org.examdesign.domain.Answer;
import org.examdesign.domain.Question;
import org.examdesign.response.AnswerDto;
import org.examdesign.response.QuestionDto;
import org.springframework.data.domain.Page;

public class QuestionMapper {
	
	public static QuestionDto map(Question question) {
		QuestionDto dto = new QuestionDto();
		dto.setId(question.getId());
		dto.setQuestionText(question.getQuestionText());
		dto.setCategory(question.getCategory().getId().intValue());
		dto.setDifficulty(question.getDifficulty().getId().intValue());
		dto.setQuestionType(question.getQuestionType().getId().intValue());
		dto.setAnswers(question.getAnswers());
		return dto;
	}
	
	public static List<QuestionDto> map(Page<Question> questions) {
		
		List<QuestionDto> dtos = new ArrayList<QuestionDto>();
		for (Question question : questions) {
			dtos.add(map(question));
		}
		
		return dtos;
	}
}
