import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Como o objeto `BackupContext` deve ser construído.
 */
@Singleton
public class BackupContextProvider implements Provider<BackupContext> {

    @Inject
    private BackupCommand backupCommand;

    @Override
    public BackupContext get() {
        return BackupContext.of(backupCommand.getBackupRootDir());
    }

}
