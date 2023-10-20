import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.google.auto.service.AutoService;

/**
 * Rotina de backup do histórico do ZSH.
 */
@AutoService(Backup.class)
public class ZshHistoryBackup extends BackupAbstract {

    @Override
    public void process() throws BackupException {
        log.info("Iniciando");

        String fileName = ".zsh_history";
        Path source = getContext().getUserHome().resolve(fileName);
        Path target = getBackupContext().getBackupDir().resolve(fileName);

        log.info("Backup de '{}' para '{}'", source, target);

        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            log.info("Backupo feito");
        } catch (IOException e) {
            throw BackupException.of(e, "Falha ao copiar de '{}' para '{}'", source, target);
        }
    }
}
