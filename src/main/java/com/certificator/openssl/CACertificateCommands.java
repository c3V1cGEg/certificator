package com.certificator.openssl;

import com.certificator.openssl.structure.CertificateIssuingFileStructure;

public class CACertificateCommands {
  String createKey = "openssl genrsa -out %s 2048";
  String createRequest = "openssl req -new -x509 -sha256 -key %s -out %s -config %s -extensions v3_ca -subj \"%s\" -batch";
  String createCer = "openssl x509 -in %s -out %s";
  String createPKCS12 = "openssl pkcs12 -export -out %s -in %s -inkey %s -password pass:%s";
  String createCrl = "openssl ca -config %s -gencrl -out %s";

  private final CertificateIssuingFileStructure fs;
  private final CertificateParameters certParams;

  public CACertificateCommands(CertificateIssuingFileStructure fs, CertificateParameters certParams) {
    this.fs = fs;
    this.certParams = certParams;
  }

  public String getCreateKey() {
    return createKey.formatted(fs.getPrivateKey().toString());
  }

  public String getCreateRequest() {
    return createRequest.formatted(fs.getPrivateKey().toString(), fs.getCaPEM().toString(), fs.getOpensslConfig().toString(), certParams.toDN());
  }

  public String getCreateCer() {
    return createCer.formatted(fs.getCaPEM(), fs.getCaCer());
  }

  public String getCreatePKCS12() {
    return createPKCS12.formatted(fs.getCaP12().toString(), fs.getCaCer().toString(), fs.getPrivateKey().toString(), certParams.getCommonName());
  }

  public String getCreateCrl() {
    return createCrl.formatted(fs.getOpensslConfig(), fs.getCrl());
  }
}