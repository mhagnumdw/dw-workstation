import java.lang.invoke.MethodHandles;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "backup", description = "Executa o backup")
public class BackupCommand implements Callable<Integer> {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @ParentCommand
    private BackupRestore parentCommand; // picocli injects reference to parent command

    @Override
    public Integer call() throws Exception {
        log.info("Iniciando...");

        ServiceLoader<Backup> backupProviders = ServiceLoader.load(Backup.class);
        for (Backup backup : backupProviders) {
            backup.setContext(parentCommand.getContext());
            backup.process();
        }

        return 0;
    }

}
