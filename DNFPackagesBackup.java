import com.google.auto.service.AutoService;

@AutoService(Backup.class)
public class DNFPackagesBackup extends BackupAbstract {

    @Override
    public void process() {
        log.info("Iniciando");
    }
}
