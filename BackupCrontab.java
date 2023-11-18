import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.auto.service.AutoService;

/**
 * Rotina de backup dos pacotes instalados com DNF (Fedora).
 */
@AutoService(Backup.class)
public class BackupCrontab extends BackupAbstract {

    @Override
    public void doBackup() throws BackupException {
        log.info("Iniciando");

        backup("crontab -l", "crontab_with_command");

        String username = getContext().getUsername();

        Path source = Paths.get("/var/spool/cron/" + username);
        String outputFilename = "crontab_user_" + username;
        copyWithSudo(source, outputFilename);
    }

    @Override
    protected void doRestore() throws BackupException {
        // TODO: implementar
    }

}
