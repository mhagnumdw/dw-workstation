import java.io.IOException;
import java.nio.file.Path;

import com.google.auto.service.AutoService;

/**
 * Rotina de backup dos pacotes instalados com DNF (Fedora).
 */
@AutoService(Backup.class)
public class BackupCrontab extends BackupAbstract {

    @Override
    public void process() throws BackupException {
        log.info("Iniciando");

        backup("crontab -l", "crontab_with_command");

        String username = getContext().getUsername();
        String sourcePath = "/var/spool/cron/" + username;
        String outputFilename = "crontab_user_" + username;
        copyWithSudo(sourcePath, outputFilename);
    }

    private void copyWithSudo(String sourcePath, String outputFilename) throws BackupException {
        Path target = getBackupContext().getBackupDir().resolve(outputFilename);
        String command = "sudo cp -a " + sourcePath + " " + target.toString();
        try {
            Utils.exec(command);
        } catch (IOException | NonZeroExitCodeException e) {
            throw BackupException.of(e, "Falha ao executar '{}'", command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw BackupException.of(e, "Falha ao executar (operação interrompida) '{}'", command);
        }
    }

    private void backup(String command, String outputFilename) throws BackupException {
        Path target = getBackupContext().getBackupDir().resolve(outputFilename);
        log.info("O resultado do comando '{}' será salvo em '{}'", command, target);

        try {
            Utils.exec(command, target);
            log.info("Backup feito");
        } catch (IOException | NonZeroExitCodeException e) {
            throw BackupException.of(e, "Falha ao executar '{}'", command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw BackupException.of(e, "Falha ao executar (operação interrompida) '{}'", command);
        }
    }

}
