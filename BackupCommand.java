import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(name = "backup", description = "Executa o backup")
public class BackupCommand implements Callable<Integer> {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Option(names = "--backup-dir", required = true, description = "Directory where the backup is saved")
    private Path backupDir;

    @ParentCommand
    private BackupRestore parentCommand; // picocli injects reference to parent command

    @Override
    public Integer call() throws Exception {
        log.info("Iniciando..." + backupDir);

        BackupContext backupContext = BackupContext.of(backupDir);
        getContext().setBackupContext(backupContext);

        ServiceLoader<Backup> backupProviders = ServiceLoader.load(Backup.class);
        for (Backup backup : backupProviders) {
            backup.setContext(getContext());
            backup.process();
        }

        return 0;
    }

    private Context getContext() {
        return parentCommand.getContext();
    }

}
