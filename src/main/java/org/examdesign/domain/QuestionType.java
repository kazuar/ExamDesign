package org.examdesign.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="questiontype")
public class QuestionType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String questionTypeName;
	
	public QuestionType() {}
	
	public QuestionType(Long id) {
		this.id = id;
	}
	
	public QuestionType(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}
	
	public QuestionType(Long id, String questionTypeName) {
		this.id = id;
		this.questionTypeName = questionTypeName;
	}

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
