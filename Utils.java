import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Utilitários.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    public static final String PATH_SEPARATOR_LINUX = "/";
    public static final String PATH_SEPARATOR_WINDOWS = "\\";

    /**
     * Copia um arquivo usando sudo.
     *
     * @param source o arquivo a ser copiado
     * @param target path de destino, incluindo o nome do arquivo
     * @throws IOException se um erro de I/O ocorrer
     * @throws InterruptedException se alguma outra thread interromper esta enquanto aguarda a execução do comando
     * @throws NonZeroExitCodeException se o comando encerrar com exit code >= 1
     */
    public static void copyWithSudo(Path source, Path target) throws IOException, InterruptedException, NonZeroExitCodeException {
        String command = "sudo cp -a " + source + " " + target;
        Utils.exec(command);
    }

    /**
     * Executa um comando.
     * @param command comando para ser executado
     * @throws IOException se um erro de I/O ocorrer
     * @throws InterruptedException se alguma outra thread interromper esta enquanto aguarda a execução do comando
     * @throws NonZeroExitCodeException se o comando encerrar com exit code >= 1
     * @see #exec(String, Path)
     */
    public static void exec(@NonNull String command) throws IOException, InterruptedException, NonZeroExitCodeException {
        exec(command, null);
    }

    /**
     * Executa um comando, permitindo direcionar o output para um arquivo.
     * @param command comando para ser executado
     * @param output (opcional) arquivo que deve receber o output do comando
     * @throws IOException se um erro de I/O ocorrer
     * @throws InterruptedException se alguma outra thread interromper esta enquanto aguarda a execução do comando
     * @throws NonZeroExitCodeException se o comando encerrar com exit code >= 1
     * @see #exec(String)
     */
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

    /**
     * Formata uma string de acordo com o formato especificado, substituindo marcadores '{}' pelos argumentos fornecidos.
     *
     * @param format a string de formato que contém marcadores '{}' a serem substituídos pelos argumentos
     * @param arguments os argumentos a serem inseridos na string de formato. Eles serão substituídos pelos marcadores '{}' na ordem em que aparecem
     * @return a string formatada
     */
    public static String format(String format, Object... arguments) {
        return String.format(format.replace("{}", "%s"), arguments);
    }

    /**
     * Verifica se a string fornecida contém apenas o nome do arquivo (sem caminho).
     *
     * @param filename a string a ser verificada
     * @throws java.lang.IllegalStateException se a string contiver um separador de caminho para Linux ou Windows, indicando a presença de um caminho
     */
    public static void assertOnlyFilename(String filename) {
        if (filename.contains(PATH_SEPARATOR_LINUX) || filename.contains(PATH_SEPARATOR_WINDOWS)) {
            throw new IllegalStateException("Permitido apenas o nome do arquivo, sem path");
        }
    }

}
