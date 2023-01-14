package org.amir.pollat.controller;

import org.amir.pollat.entity.Poll;
import org.amir.pollat.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

//@Controller
@RestController
public class OptionController {
//    @Autowired
    @Inject
    private OptionRepository optionRepository;
}
