package org.amir.pollat.controller;

import org.amir.pollat.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

//@Controller
@RestController
public class OptionController {
    private OptionRepository optionRepository;

    @Autowired
    public OptionController(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }
}
