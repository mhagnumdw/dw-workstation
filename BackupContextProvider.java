import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class BackupContextProvider implements Provider<BackupContext> {

    @Inject
    private BackupCommand backupCommand;

    @Override
    public BackupContext get() {
        return BackupContext.of(backupCommand.getBackupRootDir());
    }

}
