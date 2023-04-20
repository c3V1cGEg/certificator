package com.certificator.process;

import com.certificator.support.OSSupport;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcessCall implements OSSupport {
  public record Result (int exitCode, List<String> log){}
  List<String> call = isWindows() ? List.of("cmd.exe", "/c") : List.of("/bin/sh", "-c");

  public Result execute(String command, Path workingDir) {
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
        return new Result(exitCode, output);
      }
    } catch (Exception e) {
      //TODO: log
      throw new RuntimeException(e);
    }
  }
}