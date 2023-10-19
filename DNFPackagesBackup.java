import com.google.auto.service.AutoService;

/**
 * Rotina de backup dos pacotes instalados com DNF (Fedora).
 */
@AutoService(Backup.class)
public class DNFPackagesBackup extends BackupAbstract {

    @Override
    public void process() {
        log.info("Iniciando");
    }
}
