package com.xenon.presenter.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class HomeController {

    @GetMapping("status")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok(Map.of("status","application is up and running"));
    }
}
