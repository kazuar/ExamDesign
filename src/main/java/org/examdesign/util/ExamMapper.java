package org.examdesign.util;

import java.util.ArrayList;
import java.util.List;

import org.examdesign.domain.Exam;
import org.examdesign.response.ExamDto;
import org.springframework.data.domain.Page;

public class ExamMapper {
	
	public static ExamDto map(Exam exam) {
		ExamDto dto = new ExamDto();
		dto.setId(exam.getId());
		dto.setExamName(exam.getExamName());
		//dto.setQuestions(exam.getQuestions());
		return dto;
	}
	
	public static List<ExamDto> map(Page<Exam> exams) {
		
		List<ExamDto> dtos = new ArrayList<ExamDto>();
		for (Exam exam : exams) {
			dtos.add(map(exam));
		}
		
		return dtos;
	}
}
