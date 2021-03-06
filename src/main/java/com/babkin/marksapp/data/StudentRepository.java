package com.babkin.marksapp.data;

import com.babkin.marksapp.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findAll(Pageable pageable);
}
