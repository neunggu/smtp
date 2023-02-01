package com.company.smtp.controller;

import com.company.smtp.model.EmailDto;
import com.company.smtp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailRestCtrl {

    private final EmailService emailService;

    @PostMapping({"send"})
    public ResponseEntity send(@RequestBody EmailDto email) {
        emailService.sendEmailWithTemplate(email);
        return ResponseEntity.ok("send ok");
    }

}
