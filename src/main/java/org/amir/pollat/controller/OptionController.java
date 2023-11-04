package org.amir.pollat.controller;

import org.amir.pollat.repository.OptionRepository;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

//@Controller
@RestController
public class OptionController {
//    @Autowired
    @Inject
    private OptionRepository optionRepository;
}
