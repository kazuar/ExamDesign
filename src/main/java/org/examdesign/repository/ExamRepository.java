package org.examdesign.repository;

import org.examdesign.domain.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
	
	Exam findByExamName(String examName);
	
	Page<Exam> findByExamNameLike(String examName, Pageable pageable);
}
