package org.examdesign.service;

import java.util.List;

import org.examdesign.domain.Difficulty;
import org.examdesign.repository.DifficultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DifficultyService {
	
	@Autowired
	private DifficultyRepository difficultyRepository;
	
	public Boolean create(Difficulty difficulty) {
		
		Difficulty savedDifficulty = difficultyRepository.save(difficulty);
		if (savedDifficulty == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean update(Difficulty difficulty) {
		
		Difficulty existingDifficulty = difficultyRepository.findOne(difficulty.getId());
		
		if (existingDifficulty == null) {
			return false;
		}
		
		existingDifficulty.setDifficultyName(difficulty.getDifficultyName());
		
		Difficulty savedDifficulty = difficultyRepository.save(existingDifficulty);
		if (savedDifficulty == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean delete(Difficulty difficulty) {
		
		Difficulty existingDifficulty = difficultyRepository.findOne(difficulty.getId());
		
		if (existingDifficulty == null) {
			return false;
		}
		
		difficultyRepository.delete(existingDifficulty);
		
		Difficulty deletedDifficulty = difficultyRepository.findOne(difficulty.getId());
		if (deletedDifficulty != null) {
			return false;
		}
		
		return true;
	}
	
	public String list() {
		List<Difficulty> difficulties = difficultyRepository.findAll();
		String difficultiesList = "";
		
		for (Difficulty difficulty : difficulties) {
			difficultiesList += "" + difficulty.getId() + ":" + difficulty.getDifficultyName();
			difficultiesList += (difficulties.indexOf(difficulty) < (difficulties.size() - 1) ? ";" : "");
		}
		
		return difficultiesList;
	}
}
