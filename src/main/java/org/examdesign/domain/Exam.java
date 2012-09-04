package org.examdesign.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.IndexColumn;

@Entity(name="exam")
public class Exam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String examName;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="examquestions",
		joinColumns={@JoinColumn(name="id_exam", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="id_question", referencedColumnName="id")})
	@IndexColumn(name="INDEX_COL")
	private List<Question> questions;

	public Exam() {}
	
	public Exam(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public String getExamName() {
		return examName;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public void setUpdatedQuestions(List<Question> questions) {
		this.questions.clear();
		this.questions.addAll(questions);
	}
}
