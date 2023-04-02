package com.cdtn.kltn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    @GetMapping("/showAll")
    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public String get() {
        return "category";
    }
}
