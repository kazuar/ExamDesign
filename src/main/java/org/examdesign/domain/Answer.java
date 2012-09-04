package org.examdesign.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="answer")
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String answerText;
	
	public Answer() {}
	
	public Answer(Long id) {
		this.id = id;
	}
	
	public Answer(String answerText) {
		this.answerText = answerText;
	}
	
	public Answer(Long id, String answerText) {
		this.id = id;
		this.answerText = answerText;
	}

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
