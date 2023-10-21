import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

/**
 * Rotina de backup dos pacotes instalados com DNF (Fedora).
 */
@AutoService(Backup.class)
public class DNFPackagesBackup extends BackupAbstract {

    @Override
    public void process() throws BackupException {
        log.info("Iniciando");

        Path target = getBackupContext().getBackupDir().resolve("dnf_list_installed");
        log.info("O backup será salvo em '{}'", target);

        String command = "dnf list installed";

        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        // processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(target.toFile());

        try {
            Process process = processBuilder.start();
            String stderrLines = process.errorReader().lines().collect(Collectors.joining(System.lineSeparator()));
            int exitCode = process.waitFor();
            if (exitCode > 0) {
                throw BackupException.of("Comando '{}' encerrou com exit code '{}', motivo: {}", command, exitCode, stderrLines);
            }
            log.info("Backup feito");
        } catch (IOException e) {
            throw BackupException.of(e, "Falha ao executar '{}'", command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw BackupException.of(e, "Falha ao executar (operação interrompida) '{}'", command);
        }
    }
}
