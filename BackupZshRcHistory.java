import java.nio.file.Path;

import com.google.auto.service.AutoService;

/**
 * Rotina de backup do {@code ~/.zshrc}.
 */
@AutoService(Backup.class)
public class BackupZshRcHistory extends BackupAbstract {

    @Override
    public void backup() throws BackupException {
        log.info("Iniciando");

        String fileName = ".zshrc";
        Path source = getContext().getUserHome().resolve(fileName);

        copy(source, fileName);
    }
}
