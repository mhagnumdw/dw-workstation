import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
