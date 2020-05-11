package com.babkin.marksapp.data;

import com.babkin.marksapp.Subject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    List<Subject> findAll(Pageable pageable);
}
