package org.examdesign.response;

import java.io.Serializable;
import java.util.List;

import org.examdesign.domain.Answer;
import org.examdesign.domain.QuestionAnswers;

public class QuestionDto implements Serializable {
	
	private Long id;
	private String questionText;
	private Integer category;
	private Integer difficulty;
	private Integer questionType;
	private List<QuestionAnswers> answers;
	
	public Long getId() {
		return id;
	}
	public String getQuestionText() {
		return questionText;
	}
	public Integer getCategory() {
		return category;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public Integer getQuestionType() {
		return questionType;
	}
	public List<QuestionAnswers> getAnswers() {
		return answers;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	public void setAnswers(List<QuestionAnswers> answers) {
		this.answers = answers;
	}
}
