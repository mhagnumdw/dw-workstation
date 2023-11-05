import com.google.auto.service.AutoService;

/**
 * Rotina de backup dos pacotes instalados com DNF (Fedora).
 */
@AutoService(Backup.class)
public class BackupDNFPackages extends BackupAbstract {

    @Override
    public void backup() throws BackupException {
        log.info("Iniciando");

        backup("dnf list installed", "dnf_list_installed");

        backup("dnf history userinstalled", "dnf_history_userinstalled");
    }

}
