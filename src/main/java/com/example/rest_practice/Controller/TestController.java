package com.example.rest_practice.Controller;

import com.example.rest_practice.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {
    private final Repo repo;

    @Autowired
    public TestController(Repo repo) {
        this.repo = repo;
    }

    @GetMapping()
    public String test(){
        return repo.findAll().get(0).getDocumentInformation().getSeries().toString();
    }
}
