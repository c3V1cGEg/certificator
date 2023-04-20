package com.certificator.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class DefaultController {
  @GetMapping(value = "")
  public ModelAndView index() {
    Map<String, Object> vars = new HashMap<>();
    vars.put("templateName", "main");
    return new ModelAndView("page", vars);
  }
}