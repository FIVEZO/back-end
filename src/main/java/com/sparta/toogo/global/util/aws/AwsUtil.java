package com.sparta.toogo.global.util.aws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwsUtil {
    @GetMapping("/health")
    public ResponseEntity<Void> checkHealth() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}