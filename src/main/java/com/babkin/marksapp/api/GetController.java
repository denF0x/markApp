package com.babkin.marksapp.api;

import com.babkin.marksapp.Mark;
import com.babkin.marksapp.Student;
import com.babkin.marksapp.Subject;
import com.babkin.marksapp.data.MarkRepository;
import com.babkin.marksapp.data.StudentRepository;
import com.babkin.marksapp.data.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class GetController {
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private MarkRepository markRepository;

    private static final int STUDENTS_ROW_PER_PAGE = 10;
    private static final int SUBJECTS_ROW_PER_PAGE = 10;

    @Autowired
    public GetController(StudentRepository studentRepository, SubjectRepository subjectRepository, MarkRepository markRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.markRepository = markRepository;
    }

    @GetMapping(value = "/")
    public String showAll(Model model,
                          @RequestParam(value = "stPage", defaultValue = "1") int stPageNumber,
                          @RequestParam(value = "suPage", defaultValue = "1") int suPageNumber,
                          @RequestParam(value = "subject", defaultValue = "0") Long markBySubjId,
                          @RequestParam(value = "student", defaultValue = "0") Long markByStudId) {
        Pageable stPageable = PageRequest.of(stPageNumber - 1, STUDENTS_ROW_PER_PAGE);
        List<Student> students = studentRepository.findAll(stPageable);
        long studentCount = studentRepository.count();
        boolean stHasPrev = stPageNumber > 1;
        boolean stHasNext = (stPageNumber * STUDENTS_ROW_PER_PAGE) < studentCount;
        model.addAttribute("students", students);
        model.addAttribute("stHasPrev", stHasPrev);
        model.addAttribute("stPrev", stPageNumber - 1);
        model.addAttribute("stHasNext", stHasNext);
        model.addAttribute("stNext", stPageNumber + 1);
        model.addAttribute("stPageNumber", stPageNumber);


        Pageable suPageable = PageRequest.of(suPageNumber - 1, SUBJECTS_ROW_PER_PAGE);
        List<Subject> subjects = subjectRepository.findAll(suPageable);
        long subjectsCount = subjectRepository.count();
        boolean suHasPrev = suPageNumber > 1;
        boolean suHasNext = (suPageNumber * SUBJECTS_ROW_PER_PAGE) < subjectsCount;
        model.addAttribute("subjects", subjects);
        model.addAttribute("suHasPrev", suHasPrev);
        model.addAttribute("suPrev", suPageNumber - 1);
        model.addAttribute("suHasNext", suHasNext);
        model.addAttribute("suNext", suPageNumber + 1);
        model.addAttribute("suPageNumber", suPageNumber);


        List<Student> markStudents = new ArrayList<>();
        studentRepository.findAll().forEach(i -> markStudents.add(i));
        model.addAttribute("markStudents", markStudents);
        List<Subject> markSubjects = new ArrayList<>();
        subjectRepository.findAll().forEach(i -> markSubjects.add(i));
        model.addAttribute("markSubjects", markSubjects);


        List<Mark> markListBySubject = markRepository.findAllBySubjectId(markBySubjId);
        model.addAttribute("markListBySubject", markListBySubject);

        List<Mark> markListByStudent = markRepository.findAllByStudentId(markByStudId);
        model.addAttribute("markListByStudent", markListByStudent);


        log.info("populate");
        return "home";
    }

    @ModelAttribute(name = "mark")
    public Mark mark() {
        return new Mark();
    }

    @GetMapping(value = "/students/{studentId}/edit")
    public String showSingleStudent(Model model, @PathVariable Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = optionalStudent.orElseGet(Student::new);
        model.addAttribute("student", student);
        return "student";
    }

    @GetMapping(value = "/subjects/{subjectId}/edit")
    public String showSingleSubject(Model model, @PathVariable Long subjectId) {
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        Subject subject = optionalSubject.orElseGet(Subject::new);
        model.addAttribute("subject", subject);
        return "subject";
    }

    @GetMapping(value = "/marks/{markId}/edit")
    public String showSingleMark(Model model, @PathVariable Long markId) {
        Optional<Mark> optionalMark = markRepository.findById(markId);
        Mark mark = optionalMark.orElseGet(Mark::new);
        model.addAttribute("mark", mark);
        return "mark";
    }

}
