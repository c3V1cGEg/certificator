package com.certificator.openssl.structure;

import com.certificator.support.OSSupport;

import java.nio.file.Path;

public class CAFileStructure implements OSSupport {
  private final Path caRoot;
  private final Path caPath;
  private final Path certsPath;
  private final Path crlPath;
  private final Path issuingPath;
  private final Path serialTxt;
  private final Path indexTxt;
  private final Path opensslConfig;
  private final Path privateKey;
  private final Path caPEM;
  private final Path caCer;
  private final Path caP12;
  private final Path crl;

  public CAFileStructure(String basePath, String caCN) {
    String fixedBasePath = basePath.endsWith(fsSeparator()) ? basePath : basePath + fsSeparator();
    this.caRoot = Path.of(fixedBasePath, caCN);
    this.caPath = Path.of(fixedBasePath, caCN, "ca");
    this.certsPath = Path.of(fixedBasePath, caCN, "certs");
    this.crlPath = Path.of(fixedBasePath, caCN, "crl");
    this.issuingPath = Path.of(fixedBasePath, caCN, "issuing");
    this.serialTxt = Path.of(fixedBasePath, caCN, "ca", "serial.txt");
    this.indexTxt = Path.of(fixedBasePath, caCN, "ca", "index.txt");
    this.opensslConfig = Path.of(fixedBasePath, caCN, "ca", "openssl.config");

    this.privateKey = Path.of(fixedBasePath, caCN, "ca", "private.key");
    this.caPEM = Path.of(fixedBasePath, caCN, "ca", "ca.pem");
    this.caCer = Path.of(fixedBasePath, caCN, "ca", "ca.cer");
    this.caP12 = Path.of(fixedBasePath, caCN, "ca", "ca.p12");
    this.crl = Path.of(fixedBasePath, caCN, "crl", "ca.crl");
  }

  public Path getCaRoot() {
    return caRoot;
  }

  public Path getCaPath() {
    return caPath;
  }

  public Path getCertsPath() {
    return certsPath;
  }

  public Path getCrlPath() {
    return crlPath;
  }

  public Path getIssuingPath() {
    return issuingPath;
  }

  public Path getSerialTxt() {
    return serialTxt;
  }

  public Path getIndexTxt() {
    return indexTxt;
  }

  public Path getOpensslConfig() {
    return opensslConfig;
  }

  public Path getPrivateKey() {
    return privateKey;
  }

  public Path getCaPEM() {
    return caPEM;
  }

  public Path getCaCer() {
    return caCer;
  }

  public Path getCaP12() {
    return caP12;
  }

  public Path getCrl() {
    return crl;
  }
}