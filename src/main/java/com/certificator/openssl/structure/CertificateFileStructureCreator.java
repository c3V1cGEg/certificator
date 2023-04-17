package com.certificator.openssl.structure;

import com.certificator.support.OSSupport;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Named
public class CertificateFileStructureCreator implements OSSupport {
  private final String certificatesRoot;

  public CertificateFileStructureCreator(@Value("${certificates.root}") String certificatesRoot) {
    this.certificatesRoot = certificatesRoot.endsWith(fsSeparator()) ? certificatesRoot : certificatesRoot + fsSeparator();
  }

  public void createFileStructure(String caCertificateCN, String certificateCN) {
    String certRoot = certificatesRoot + caCertificateCN + fsSeparator() + "issuing" + fsSeparator() + certificateCN;

    try {
      Files.createDirectories(Paths.get(certRoot));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}