package com.certificator.openssl.structure;

import com.certificator.support.OSSupport;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Named
public class CertificateIssuingFileStructureCreator implements OSSupport {
  String certificatesRoot;
  private final TemplateEngine rawTemplateEngine;

  public CertificateIssuingFileStructureCreator(@Value("${certificates.root}") String certificatesRoot, TemplateEngine rawTemplateEngine) {
    this.certificatesRoot = certificatesRoot;
    this.rawTemplateEngine = rawTemplateEngine;
  }

  public CertificateIssuingFileStructure createFileStructure(String caCertificateCN) {
    CertificateIssuingFileStructure fileStructure = new CertificateIssuingFileStructure(certificatesRoot, caCertificateCN);

    try {
      Files.createDirectories(fileStructure.getCaRoot());
      Files.createDirectories(fileStructure.getCaPath());
      Files.createDirectories(fileStructure.getCertsPath());
      Files.createDirectories(fileStructure.getCrlPath());
      Files.createDirectories(fileStructure.getIssuingPath());

      Files.writeString(fileStructure.getSerialTxt(), "1000");
      Files.writeString(fileStructure.getIndexTxt(), "");

      Files.writeString(fileStructure.getOpensslConfig(), createOpensslConfiguration(caCertificateCN));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return fileStructure;
  }

  public CertificateIssuingFileStructure createIssuingFileStructure(String caCertificateCN, String certificateCN) {
    CertificateIssuingFileStructure fileStructure = new CertificateIssuingFileStructure(certificatesRoot, caCertificateCN);

    try {
      Files.createDirectories(fileStructure.getIssuingCertificatePath(certificateCN));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return fileStructure;
  }

  private String createOpensslConfiguration(String caCN) {
    Map<String, Object> variables = Map.of("certificatesRoot", certificatesRoot, "certificationAuthorityCN", caCN);
    return rawTemplateEngine.process("openssl_template.config", new Context(null, variables));
  }
}