package com.caffeine.gwanghwamun.controller;

import com.caffeine.gwanghwamun.domain.ExampleEntity;
import com.caffeine.gwanghwamun.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;

    @GetMapping("/test")
    public ExampleEntity test() {
        ExampleEntity entity = new ExampleEntity("test content");
        ExampleEntity saved = testRepository.save(entity);

        System.out.println("Created By: " + saved.getCreatedBy());
        System.out.println("Created Date: " + saved.getCreatedDate());
        System.out.println("Last Modified By: " + saved.getLastModifiedBy());
        System.out.println("Last Modified Date: " + saved.getLastModifiedDate());

        return saved;
    }
}
