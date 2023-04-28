package com.certificator.openssl;

import com.certificator.openssl.structure.CertificateIssuingFileStructure;

public class CertificateCommands {
  String createKey = "openssl genrsa -out %s 2048";
  String createRequest = "openssl req -new -key %s -out %s -config %s -subj \"%s\" -batch";
  String signCertificate = "openssl ca -in %s -out %s -config %s -extensions %s %s -batch";
  String addExtension = "-addext \"subjectAltName = DNS:%s\"";
  String cerToPem = "openssl x509 -in %s -out %s";
  String createPKCS12 = "openssl pkcs12 -export -out %s -in %s -inkey %s -password pass:%s";

  private final CertificateIssuingFileStructure fs;
  private final CertificateParameters certParams;

  public CertificateCommands(CertificateIssuingFileStructure fs, CertificateParameters certParams) {
    this.fs = fs;
    this.certParams = certParams;
  }

  public String getCreateKey() {
    String keyPath = fs.getIssuingCertificatePrivateKeyPath(certParams.getCommonName()).toString();
    return createKey.formatted(keyPath);
  }

  public String getCreateRequest() {
    String keyPath = fs.getIssuingCertificatePrivateKeyPath(certParams.getCommonName()).toString();
    String reqPath = fs.getIssuingCertificateRequestPath(certParams.getCommonName()).toString();
    return createRequest.formatted(keyPath, reqPath, fs.getOpensslConfig().toString(), certParams.toDN());
  }

  public String getSignCertificate(String extensions) {
    String reqPath = fs.getIssuingCertificateRequestPath(certParams.getCommonName()).toString();
    String certPath = fs.getIssuingCertificateSignedCertificatePath(certParams.getCommonName()).toString();
    String domainName = certParams.getDomainName();
    String addSANExtension = domainName != null && !domainName.isBlank() ? addExtension.formatted(domainName) : "";
    return signCertificate.formatted(reqPath, certPath, fs.getOpensslConfig().toString(), extensions, addSANExtension);
  }

  public String getCerToPem() {
    String certPath = fs.getIssuingCertificateSignedCertificatePath(certParams.getCommonName()).toString();
    String pemPath = fs.getIssuingCertificatePemPath(certParams.getCommonName()).toString();
    return cerToPem.formatted(certPath, pemPath);
  }

  public String getCreatePKCS12() {
    String p12Path = fs.getIssuingCertificateP12Path(certParams.getCommonName()).toString();
    String certPath = fs.getIssuingCertificateSignedCertificatePath(certParams.getCommonName()).toString();
    String keyPath = fs.getIssuingCertificatePrivateKeyPath(certParams.getCommonName()).toString();
    return createPKCS12.formatted(p12Path, certPath, keyPath, certParams.getCommonName());
  }

  public static String test(String s) {
    return s + "null";
  }
}