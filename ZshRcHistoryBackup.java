import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.google.auto.service.AutoService;

/**
 * Rotina de backup do {@code ~/.zshrc}.
 */
@AutoService(Backup.class)
public class ZshRcHistoryBackup extends BackupAbstract {

    @Override
    public void process() throws BackupException {
        log.info("Iniciando");

        String fileName = ".zshrc";
        Path source = getContext().getUserHome().resolve(fileName);
        Path target = getBackupContext().getBackupDir().resolve(fileName);

        log.info("Backup de '{}' para '{}'", source, target);

        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING /*, StandardCopyOption.COPY_ATTRIBUTES*/);
            log.info("Backup feito");
        } catch (IOException e) {
            throw BackupException.of(e, "Falha ao copiar de '{}' para '{}'", source, target);
        }
    }
}
