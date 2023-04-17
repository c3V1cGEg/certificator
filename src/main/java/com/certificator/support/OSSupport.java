package com.certificator.support;

public interface OSSupport {
  boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

  default boolean isWindows() {
    return isWindows;
  }

  default String fsSeparator() {
    return isWindows() ? "\\" : "/";
  }
}