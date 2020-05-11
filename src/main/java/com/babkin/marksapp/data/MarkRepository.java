package com.babkin.marksapp.data;

import com.babkin.marksapp.Mark;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MarkRepository extends CrudRepository<Mark, Long> {
     List<Mark> findAllBySubjectId(Long subject_id);
     List<Mark> findAllByStudentId(Long student_id);

     void deleteAllBySubjectId(Long subjectId);
}
