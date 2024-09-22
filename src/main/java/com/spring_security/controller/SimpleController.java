package com.spring_security.controller;

import com.spring_security.model.Student;
import com.spring_security.model.User;
import com.spring_security.repo.UserRepository;
import com.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class SimpleController
{
    @Autowired
    private UserService service;

    private List<Student> studentList = new ArrayList<>(Arrays.asList(
            new Student("1", "Sohan"),
            new Student("2", "Rohit")
    ));

    @GetMapping
    public String hello(HttpServletRequest request) {
        return "Hello session: " + request.getSession().getId();
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentList;
    }

    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student student) {
        studentList.add(student);
        return student;
    }

    @PostMapping("/register-user")
    public void registerUser(@RequestBody User user){
        service.addUser(user);
    }

    @PostMapping("/login-user")
    public String login(@RequestBody User user){
        return service.verifyUser(user);
    }

    @GetMapping("/csrf-Token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
