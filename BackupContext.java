import java.nio.file.Path;

import lombok.Getter;

/**
 * Contexto referente a rotina de backup.
 */
public class BackupContext {

    @Getter
    private final Path backupDir;

    private BackupContext(Path backupDir) {
        this.backupDir = backupDir;
    }

    public static BackupContext of(Path backupRootDir) {
        String id = MachineIdentifier.getIdentifier();
        return new BackupContext(backupRootDir.resolve(id));
    }

}
