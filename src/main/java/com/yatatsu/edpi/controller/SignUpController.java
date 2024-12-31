package com.yatatsu.edpi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SignUpController {
    
    @GetMapping("/signup")
    public ModelAndView SingUpUser(ModelAndView mav) {
        mav.setViewName("signup");
        return mav;
    }
    
}
