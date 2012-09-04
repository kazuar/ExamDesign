package org.examdesign.util;

import java.util.ArrayList;
import java.util.List;

import org.examdesign.domain.Difficulty;
import org.examdesign.response.DifficultyDto;
import org.springframework.data.domain.Page;

public class DifficultyMapper {
	
	public static DifficultyDto map(Difficulty difficulty) {
		DifficultyDto dto = new DifficultyDto();
		dto.setId(difficulty.getId());
		dto.setDifficultyName(difficulty.getDifficultyName());
		return dto;
	}
	
	public static List<DifficultyDto> map(Page<Difficulty> categories) {
		
		List<DifficultyDto> dtos = new ArrayList<DifficultyDto>();
		for (Difficulty difficulty : categories) {
			dtos.add(map(difficulty));
		}
		
		return dtos;
	}
}
