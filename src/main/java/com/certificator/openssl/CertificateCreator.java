package com.certificator.openssl;

import com.certificator.openssl.structure.CAFileStructure;
import com.certificator.openssl.structure.CAFileStructureCreator;
import com.certificator.process.ProcessCall;
import com.certificator.process.ProcessCall.Result;
import jakarta.inject.Named;

@Named
public class CertificateCreator {
  CAFileStructureCreator caFileStructureCreator;

  public CertificateCreator(CAFileStructureCreator caFileStructureCreator) {
    this.caFileStructureCreator = caFileStructureCreator;
  }

  public void createCACertificate(CertificateParameters certParams) {
    CAFileStructure fs = caFileStructureCreator.createFileStructure(certParams.getCommonName());

    ProcessCall call = new ProcessCall();
    CACertificateCommands commands = new CACertificateCommands();

    Result result = call.execute(commands.getCreateKey(fs), fs.getCaPath());
    assert result.exitCode() == 0;

    result = call.execute(commands.getCreateRequest(fs, certParams.toCN()), fs.getCaPath());
    assert result.exitCode() == 0;

    result = call.execute(commands.getCreateCer(fs), fs.getCaPath());
    assert result.exitCode() == 0;

    result = call.execute(commands.getCreatePKCS12(fs, certParams.getCommonName()), fs.getCaPath());
    assert result.exitCode() == 0;

    result = call.execute(commands.getCreateCrl(fs), fs.getCaPath());
    assert result.exitCode() == 0;
  }
}