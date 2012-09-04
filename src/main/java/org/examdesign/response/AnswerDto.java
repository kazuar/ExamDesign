package org.examdesign.response;

import java.io.Serializable;

public class AnswerDto implements Serializable {
	
	private Long id;
	private String answerText;
	
	public Long getId() {
		return id;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
}
