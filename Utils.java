import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    public static void exec(@NonNull String command) throws IOException, InterruptedException, NonZeroExitCodeException {
        exec(command, null);
    }

    public static void exec(@NonNull String command, Path output) throws IOException, InterruptedException, NonZeroExitCodeException {
        log.info("Comando que será executado: {}", command);
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        if (output != null) {
            processBuilder.redirectOutput(output.toFile());
        }
        Process process = processBuilder.start();
        String stderrLines = process.errorReader().lines().collect(Collectors.joining(System.lineSeparator()));
        int exitCode = process.waitFor();
        if (exitCode > 0) {
            String msg = format("Comando '{}' encerrou com exit code '{}', motivo: {}", command, exitCode, stderrLines);
            throw new NonZeroExitCodeException(msg, exitCode);
        }
    }

    public static String format(String format, Object... arguments) {
        return String.format(format.replace("{}", "%s"), arguments);
    }
}
