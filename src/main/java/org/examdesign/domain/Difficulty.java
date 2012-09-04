package org.examdesign.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="difficulty")
public class Difficulty {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String difficultyName;
	
	public Difficulty() {}
	
	public Difficulty(Long id) {
		this.id = id;
	}
	
	public Difficulty(String difficultyName) {
		this.difficultyName = difficultyName;
	}
	
	public Difficulty(Long id, String difficultyName) {
		this.id = id;
		this.difficultyName = difficultyName;
	}

	public Long getId() {
		return id;
	}

	public String getDifficultyName() {
		return difficultyName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDifficultyName(String difficultyName) {
		this.difficultyName = difficultyName;
	}
}
