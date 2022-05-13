package com.liveguard.repository;

import com.liveguard.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {

    Page<Question> findAllByChipVersionId(Long chipVersionId, Pageable pageable);

    boolean existsById(Long id);
}
