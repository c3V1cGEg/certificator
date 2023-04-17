package com.certificator.openssl;

import com.certificator.openssl.structure.CAFileStructureCreator;
import com.certificator.process.StreamGobbler;
import jakarta.inject.Named;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Named
public class CertificateCreator {
  CAFileStructureCreator caFileStructureCreator;

  public CertificateCreator(CAFileStructureCreator caFileStructureCreator) {
    this.caFileStructureCreator = caFileStructureCreator;

    caFileStructureCreator.createFileStructure("testCA");
  }

  public void createCACertificate() throws Exception {
    ProcessBuilder builder = new ProcessBuilder();
    builder.command("cmd.exe", "/c", "dir");
    builder.directory(new File("C:/"));
    Process process = builder.start();

    StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<?> future = executorService.submit(streamGobbler);
    int exitCode = process.waitFor();
    assert exitCode == 0;
    future.get();
  }
}