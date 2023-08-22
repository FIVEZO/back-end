package com.sparta.toogo.domain.home.controller;

import com.sparta.toogo.domain.home.dto.HomeCountryCountDto;
import com.sparta.toogo.domain.home.dto.HomeResponseDto;
import com.sparta.toogo.domain.home.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "홈으로 이동")
    @GetMapping("/homepost")
    public List<HomeResponseDto> getHome() {
        return homeService.getHome();
    }

    @GetMapping("/count")
    public HomeCountryCountDto getCountryCount() {
        return homeService.getCountryCount();
    }
}