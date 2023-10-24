import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

import com.google.auto.service.AutoService;

/**
 * Rotina de backup dos pacotes instalados com DNF (Fedora).
 */
@AutoService(Backup.class)
public class CrontabBackup extends BackupAbstract {

    @Override
    public void process() throws BackupException {
        log.info("Iniciando");

        backup("crontab -l", "crontab_with_command");

        // TODO: nome do usuário corrente (está fixo mhagnumdw) - bom vir do contexto
        copy("/var/spool/cron/mhagnumdw", "crontab_user_mhagnumdw");
    }

    private void copyWithSudo(String sourcePath, String outputFilename) throws BackupException {
        // TODO: daquiii: reusando o método backup(String command, String outputFilename)
        // Ver dw.java como exemplo
    }

    private void copy(String sourcePath, String outputFilename) throws BackupException {
        Path source = Path.of(sourcePath);
        Path target = getBackupContext().getBackupDir().resolve(outputFilename);

        log.info("Backup de '{}' para '{}'", source, target);

        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            log.info("Backup feito");
        } catch (IOException e) {
            throw BackupException.of(e, "Falha ao copiar de '{}' para '{}'", source, target);
        }
    }

    private void backup(String command, String outputFilename) throws BackupException {
        Path target = getBackupContext().getBackupDir().resolve(outputFilename);
        log.info("O resultado do comando '{}' será salvo em '{}'", command, target);

        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
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
