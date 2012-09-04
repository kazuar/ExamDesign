package org.examdesign.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;

@Entity(name="question")
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String questionText;
	
	@ManyToOne
	@JoinColumn
	private Category category;
	
	@ManyToOne
	@JoinColumn
	private Difficulty difficulty;
	
	@ManyToOne
	@JoinColumn
	private QuestionType questionType;

	@JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.question", orphanRemoval=true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<QuestionAnswers> answers;
	
	public Question() {}
	
	public Question(Long id) {
		this.id = id;
	}
	
	public Question(String questionText) {
		this.questionText = questionText;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getQuestionText() {
		return questionText;
	}
	
	public Category getCategory() {
		return category;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public QuestionType getQuestionType() {
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
	
	public void setCategory(Category category) {
		this.category = category;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	
	public void setAnswers(List<QuestionAnswers> answers) {
		this.answers = answers;
	}
	
	public void setUpdatedAnswer(List<QuestionAnswers> answers) {
		this.answers.clear();
		this.answers.addAll(answers);
	}
}
