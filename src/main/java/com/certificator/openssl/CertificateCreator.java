package com.certificator.openssl;

import com.certificator.openssl.structure.CertificateIssuingFileStructure;
import com.certificator.openssl.structure.CertificateIssuingFileStructureCreator;
import com.certificator.process.ProcessCall;
import jakarta.inject.Named;

@Named
public class CertificateCreator {
  CertificateIssuingFileStructureCreator certificateIssuingFileStructureCreator;

  public CertificateCreator(CertificateIssuingFileStructureCreator certificateIssuingFileStructureCreator) {
    this.certificateIssuingFileStructureCreator = certificateIssuingFileStructureCreator;
  }

  public void createCACertificate(CertificateParameters certParams) {
    CertificateIssuingFileStructure fs = certificateIssuingFileStructureCreator.createFileStructure(certParams.getCommonName());
    CACertificateCommands commands = new CACertificateCommands(fs, certParams);

    ProcessCall p = new ProcessCall(fs.getIssuingPath());
    p.execute(commands.getCreateKey());
    p.execute(commands.getCreateRequest());
    p.execute(commands.getCreateCer());
    p.execute(commands.getCreatePKCS12());
    p.execute(commands.getCreateCrl());
  }

  public void createCertificateWithSelectedExtensions(CertificateParameters certParams) {
    CertificateIssuingFileStructure fs = certificateIssuingFileStructureCreator.createIssuingFileStructure(certParams.getIssuerCN(), certParams.getCommonName());
    CertificateCommands commands = new CertificateCommands(fs, certParams);

    ProcessCall p = new ProcessCall(fs.getIssuingPath());
    p.execute(commands.getCreateKey());
    p.execute(commands.getCreateRequest());
    p.execute(commands.getSignCertificate("usr_cert"));
    p.execute(commands.getCerToPem());
    p.execute(commands.getCreatePKCS12());
  }
}