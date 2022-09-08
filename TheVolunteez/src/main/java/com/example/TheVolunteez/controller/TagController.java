package com.example.TheVolunteez.controller;

import com.example.TheVolunteez.entity.Tag;
import com.example.TheVolunteez.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagRepository tagRepository;

    @PostConstruct
    public void init() {
        for (int i = 0; i < 20; i++) {
            Tag tag = new Tag("봉사" + i);
            tagRepository.save(tag);
        }
    }
}
