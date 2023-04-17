package com.certificator.process;

import com.certificator.support.OSSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProcessCall implements OSSupport {
  public record Result (int exitCode, List<String> log){}
  String call = isWindows() ? "cmd.exe /c" : "/bin/sh -c";

  public Result execute(String command, File workingDir) throws Exception {
    ProcessBuilder builder = new ProcessBuilder();
    builder.command(call, command);
    builder.directory(workingDir);
    Process process = builder.start();
    List<String> output = new ArrayList<>();

    StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), output::add);

    try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
      Future<?> future = executorService.submit(streamGobbler);
      int exitCode = process.waitFor();
      future.get();
      return new Result(exitCode, output);
    }
  }
}