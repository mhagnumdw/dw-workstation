///usr/bin/env curl -Ls https://sh.jbang.dev | bash -s - "$0" "$@" ; exit $?

// Esse é um exemplo que nem precisa o JBang instalado inicialmente!!!

import static java.lang.System.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class dw {

    public static void main(String... args) throws IOException, InterruptedException {
        out.println("Hello World");
        ProcessBuilder processBuilder = new ProcessBuilder(
            "sudo",
            // "bash",
            // "-c",
            "echo",
            "'casa'");
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String stdoutLines = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        String stderrLines = process.errorReader().lines().collect(Collectors.joining(System.lineSeparator()));

        int exitCode = process.waitFor();
        out.println("Exit Code: " + exitCode);
        out.println(stdoutLines);
        // out.println(stderrLines);
    }
}
