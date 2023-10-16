import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.auto.service.AutoService;

@AutoService(Backup.class)
public class ZshHistoryBackup extends BackupAbstract {

    @Override
    public void process() {
        log.info("Iniciando");

        String fileName = ".zsh_history";
        Path source = getContext().getUserHome().resolve(fileName);
        Path target = getBackupContext().getBackupDir().resolve(fileName);

        log.info("Backup de '{}' para '{}'", source, target);

        // TODO: daquiiiiiiii
        // Files.copy(source, target);
    }
}
