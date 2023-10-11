import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.auto.service.AutoService;

@AutoService(Backup.class)
public class ZshHistoryBackup extends BackupAbstract {

    @Override
    public void process() {
        log.info("Iniciando");

        Path source = Path.of(getContext().getUserHome(), ".zsh_history");

        // TODO: daquiiiiiiii

        // Path target = Path.of("~/.zsh_history");

        // Files.copy(source, target);
        log.info(source);
    }
}
