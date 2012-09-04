package org.examdesign.response;

import java.io.Serializable;

public class QuestionTypeDto implements Serializable {
	
	private Long id;
	private String questionTypeName;
	
	public Long getId() {
		return id;
	}
	
	public String getQuestionTypeName() {
		return questionTypeName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}
}
