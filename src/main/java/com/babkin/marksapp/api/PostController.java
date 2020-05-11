package com.babkin.marksapp.api;

import com.babkin.marksapp.Mark;
import com.babkin.marksapp.Student;
import com.babkin.marksapp.Subject;
import com.babkin.marksapp.data.MarkRepository;
import com.babkin.marksapp.data.StudentRepository;
import com.babkin.marksapp.data.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.ManyToMany;
import java.util.List;

@Slf4j
@Controller
public class PostController {
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private MarkRepository markRepository;

    @Autowired
    public PostController(StudentRepository studentRepository, SubjectRepository subjectRepository, MarkRepository markRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.markRepository = markRepository;
    }

    @ModelAttribute(name = "student")
    public Student student() {
        return new Student();
    }

    @ModelAttribute(name = "subject")
    public Subject subject() {
        return new Subject();
    }

    @ModelAttribute(name = "mark")
    public Mark mark() {
        return new Mark();
    }

    @PostMapping(value = "/saveStudent")
    public String saveStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        log.info("save student");
        return "redirect:/";
    }

    @PostMapping(value = "/saveSubject")
    public String saveSubject(@ModelAttribute Subject subject) {
        subjectRepository.save(subject);
        log.info("save subject");
        return "redirect:/";
    }

    @PostMapping("/saveMark")
    public String saveMark(@ModelAttribute Mark mark) {
        markRepository.save(mark);
        log.info("save mark");
        return "redirect:/";
    }

    @PostMapping("/students/{studentId}/edit")
    public String editStudent(@ModelAttribute Student student,
                              @PathVariable Long studentId) {
        student.setId(studentId);
        studentRepository.save(student);
        return "redirect:/";
    }

    @PostMapping("/subjects/{subjectId}/edit")
    public String editStudent(@ModelAttribute Subject subject,
                              @PathVariable Long subjectId) {
        subject.setId(subjectId);
        subjectRepository.save(subject);
        return "redirect:/";
    }

    @PostMapping("/marks/{markId}/edit")
    public String editMark(@ModelAttribute Mark mark,
                              @PathVariable Long markId) {
        mark.setId(markId);
        markRepository.save(mark);
        return "redirect:/";
    }

    @PostMapping("/students/{studentId}/delete")
    public String deleteStudent(@ModelAttribute Student student,
                                @PathVariable Long studentId) {
        deleteAllMarks(markRepository.findAllByStudentId(studentId));
        studentRepository.deleteById(studentId);
        return "redirect:/";
    }


    @PostMapping("/subjects/{subjectId}/delete")
    public String deleteSubject(@ModelAttribute Subject subject,
                                @PathVariable Long subjectId) {
        deleteAllMarks(markRepository.findAllBySubjectId(subjectId));
        subjectRepository.deleteById(subjectId);
        return "redirect:/";
    }

    @PostMapping("marks/{markId}/delete")
    public String deleteMark(@ModelAttribute Mark mark,
                             @PathVariable Long markId) {
        markRepository.deleteById(markId);
        return "redirect:/";
    }

    private void deleteAllMarks(List<Mark> marksForDeletion) {
        for (Mark m : marksForDeletion) {
            markRepository.delete(m);
        }
    }

}
