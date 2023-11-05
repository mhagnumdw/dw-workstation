import java.nio.file.Path;

import com.google.auto.service.AutoService;

/**
 * Rotina de backup do histórico do ZSH.
 */
@AutoService(Backup.class)
public class BackupZshHistory extends BackupAbstract {

    @Override
    public void backup() throws BackupException {
        log.info("Iniciando");

        String fileName = ".zsh_history";
        Path source = getContext().getUserHome().resolve(fileName);

        copy(source, fileName);
    }
}
