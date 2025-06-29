package com.starbuks.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/403")
    public String accesoDenegado() {
        return "error/403";
    }
}
