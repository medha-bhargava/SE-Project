package com.muj.se.codeerrordetection.controller;

import com.muj.se.codeerrordetection.dto.Code;
import com.muj.se.codeerrordetection.dto.Error;
import com.muj.se.codeerrordetection.service.CodeProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CodeSubmissionController {

    private final CodeProcessingService codeProcessingService;

    @Autowired
    public CodeSubmissionController(CodeProcessingService codeProcessingService) {
        this.codeProcessingService = codeProcessingService;
    }

    @PostMapping("/detect-error")
    public ResponseEntity<Error> identifyAndDisplayErrors(@RequestBody Code code) {
        Error errorList = codeProcessingService.sendForErrorAnalysis(code);
        return new ResponseEntity<>(errorList, HttpStatus.OK);
    }
}
