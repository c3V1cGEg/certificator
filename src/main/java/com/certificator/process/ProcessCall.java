package com.certificator.process;

import com.certificator.support.OSSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcessCall implements OSSupport {
  public record ProcessResult(int exitCode, List<String> log) { }
  Logger log = LoggerFactory.getLogger(ProcessCall.class);
  private final List<String> call = isWindows() ? List.of("cmd.exe", "/c") : List.of("/bin/sh", "-c");
  private final Path workingDir;

  public ProcessCall(Path workingDir) {
    this.workingDir = workingDir;
  }

  public void execute(String command) {
    log.info("Executing command: " + command);
    ProcessResult processResult = execute(command, workingDir);
    assert processResult.exitCode() == 0;
  }

  private ProcessResult execute(String command, Path workingDir) {
    try {
      ProcessBuilder builder = new ProcessBuilder();
      builder.command(call.get(0), call.get(1), command);
      builder.directory(workingDir.toFile());
      Process process = builder.start();
      List<String> output = new ArrayList<>();

      StreamConsumer streamConsumer = new StreamConsumer(process.getInputStream(), output::add);

      try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
        Future<?> future = executorService.submit(streamConsumer);
        int exitCode = process.waitFor();
        future.get();
        return new ProcessResult(exitCode, output);
      }
    } catch (Exception e) {
      log.error("Error during process call execution", e);
      throw new RuntimeException(e);
    }
  }
}