import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Implementação comum das rotinas de backup. Normalmente rotinas de backup
 * devem herdar dessa classe ao invés de implementar a interface Backup.
 */
public abstract class BackupAbstract implements Backup {

    protected final Logger log = Logger.getLogger(getClass());

    @Setter
    @Getter(AccessLevel.PROTECTED)
    private Context context;

    @Override
    public BackupContext getBackupContext() {
        return context.getBackupContext();
    }

}
