import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

/**
 * Comando de backup no CLI.
 */
@Command(name = "backup", description = "Executa o backup")
public class BackupCommand implements Callable<Integer> {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Option(names = "--backup-root-dir", required = true, description = "Directory where the backup is saved")
    private Path backupRootDir;

    @ParentCommand
    private DwWorkstation parentCommand; // picocli injects reference to parent command

    @Override
    public Integer call() throws Exception {
        log.info("Iniciando..." + backupRootDir);

        BackupContext backupContext = BackupContext.of(backupRootDir);
        getContext().setBackupContext(backupContext);

        if (Files.exists(backupContext.getBackupDir())) {
            log.info("O diretório '{}' já existe", backupContext.getBackupDir());
        } else {
            log.info("O diretório '{}' não existe, criando", backupContext.getBackupDir());
            Files.createDirectory(backupContext.getBackupDir());
        }

        ServiceLoader<Backup> backupProviders = ServiceLoader.load(Backup.class);
        for (Backup backup : backupProviders) {
            backup.setContext(getContext());
            try {
                backup.process(); // executa a rotina de backup
            } catch (BackupException e) {
                // TODO: decidir melhor o que fazer com o erro:
                // parar o fluxo?
                // guardar o erro, pular pra próxima execução e resumir todos os erros no final?
                log.error(e);
            }
        }

        return 0;
    }

    private Context getContext() {
        return parentCommand.getContext();
    }

}
