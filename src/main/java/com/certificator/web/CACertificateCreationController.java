package com.certificator.web;

import com.certificator.openssl.CertificateCreator;
import com.certificator.openssl.CertificateParameters;
import com.certificator.openssl.structure.CaEnumerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("ca")
public class CACertificateCreationController {
  private final CertificateCreator certificateCreator;
  private final CaEnumerator caEnumerator;

  public CACertificateCreationController(CertificateCreator certificateCreator, CaEnumerator caEnumerator) {
    this.certificateCreator = certificateCreator;
    this.caEnumerator = caEnumerator;
  }

  @GetMapping(value = {"", "/"})
  public ModelAndView index() {
    Map<String, Object> vars = new HashMap<>();
    vars.put("templateName", "ca_view");
    return new ModelAndView("page", vars);
  }

  @PostMapping(value = "/generate")
  public ModelAndView generate(CertificateParameters cert) {
    certificateCreator.createCACertificate(cert);
    return new ModelAndView("redirect:/ca");
  }
}