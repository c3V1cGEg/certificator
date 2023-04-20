package com.certificator.web;

import com.certificator.openssl.CertificateDataLoader;
import com.certificator.openssl.CertificateParameters;
import com.certificator.openssl.CertificateCreator;
import com.certificator.openssl.structure.CaEnumerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("usr")
public class UserCertificateCreationController {
  private final CertificateCreator certificateCreator;
  private final CaEnumerator caEnumerator;
  private final CertificateDataLoader certificateDataLoader;

  public UserCertificateCreationController(CertificateCreator certificateCreator, CaEnumerator caEnumerator, CertificateDataLoader certificateDataLoader) {
    this.certificateCreator = certificateCreator;
    this.caEnumerator = caEnumerator;
    this.certificateDataLoader = certificateDataLoader;
  }

  @GetMapping(value = { "", "/" })
  public ModelAndView index(@RequestParam(required = false) String caCN) {
    CertificateParameters caParameters = certificateDataLoader.getCAParameters(caCN);

    Map<String, Object> vars = new HashMap<>();
    vars.put("casList", caEnumerator.listCAs());
    vars.put("caParams", caParameters);
    vars.put("templateName", "usr_crt_view");
    return new ModelAndView("page", vars);
  }

  @PostMapping(value = "/generate")
  public ModelAndView generate(CertificateParameters cert) {
    certificateCreator.createCACertificate(cert);
    return new ModelAndView("redirect:/usr");
  }
}