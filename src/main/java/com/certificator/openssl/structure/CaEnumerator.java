package com.certificator.openssl.structure;

import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Named
public class CaEnumerator {
  private final String certificatesRoot;

  public CaEnumerator(@Value("${certificates.root}") String certificatesRoot) {
    this.certificatesRoot = certificatesRoot;
  }

  public List<String> listCAs() {
    try (Stream<Path> paths = Files.list(Path.of(certificatesRoot))) {
      return paths.filter(Files::isDirectory).map(Path::getFileName).map(Path::toString).toList();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}