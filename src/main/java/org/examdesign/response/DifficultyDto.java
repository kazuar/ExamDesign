package org.examdesign.response;

import java.io.Serializable;

public class DifficultyDto implements Serializable {
	
	private Long id;
	private String difficultyName;
	
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
