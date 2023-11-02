import com.google.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Implementação comum das rotinas de backup. Normalmente rotinas de backup
 * devem herdar dessa classe ao invés de implementar a interface Backup.
 */
public abstract class BackupAbstract implements Backup {

    protected final Logger log = Logger.getLogger(getClass());

    @Inject
    @Getter(AccessLevel.PROTECTED)
    private Context context;

    @Inject
    @Getter(AccessLevel.PROTECTED)
    private BackupContext backupContext;

}
