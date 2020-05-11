package com.babkin.marksapp;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = Student.class)
    private Student student;

    @OneToOne(targetEntity = Subject.class)
    private Subject subject;

    @NotNull
    @Digits(integer = 100, fraction = 0)
    private Integer value;

    @NotNull
    @Digits(integer = 31, fraction = 0)
    private Integer createdAt;

    public Mark() {
    }

    public Mark(Long id, Student student, Subject subject, Integer value, Integer createdAt) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.value = value;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }
}

