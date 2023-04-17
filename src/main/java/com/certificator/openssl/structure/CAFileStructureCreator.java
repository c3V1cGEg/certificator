package com.certificator.openssl.structure;

import com.certificator.support.OSSupport;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Named
public class CAFileStructureCreator implements OSSupport {
  private final String certificatesRoot;
  private final TemplateEngine rawTemplateEngine;

  public CAFileStructureCreator(@Value("${certificates.root}") String certificatesRoot, TemplateEngine rawTemplateEngine) {
    this.certificatesRoot = certificatesRoot.endsWith(fsSeparator()) ? certificatesRoot : certificatesRoot + fsSeparator();
    this.rawTemplateEngine = rawTemplateEngine;
  }

  public void createFileStructure(String caCertificateCN) {
    String caRoot = certificatesRoot + caCertificateCN + fsSeparator();
    List<String> dirs = List.of(
        caRoot + "ca",
        caRoot + "certs",
        caRoot + "crl",
        caRoot + "issuing"
        );

    dirs.forEach(s -> {
      try {
        Files.createDirectories(Paths.get(s));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    try {
      Files.writeString(Paths.get(caRoot + "ca" + fsSeparator() + "serial.txt"), "1000");
      Files.writeString(Paths.get(caRoot + "ca" + fsSeparator() + "index.txt"), "");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    createOpensslConfiguration(caRoot, caCertificateCN);
  }

  private void createOpensslConfiguration(String caRoot, String caCN) {
    Map<String, Object> variables = Map.of("certificatesRoot", certificatesRoot, "certificationAuthorityCN", caCN);
    String config = rawTemplateEngine.process("openssl_template.config", new Context(null, variables));

    try {
      Files.writeString(Paths.get(caRoot + "ca" + fsSeparator() + "openssl.config"), config);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}