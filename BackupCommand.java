import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import lombok.Getter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Comando de backup no CLI.
 */
@Singleton
@Command(name = "backup", description = "Executa o backup")
public class BackupCommand implements Callable<Integer> {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Getter
    @Option(names = "--backup-root-dir", required = true, description = "Directory where the backup is saved")
    private Path backupRootDir;

    @Inject
    private Injector injector;

    @Override
    public Integer call() throws Exception {
        log.info("Iniciando...");
        log.info("Diretório raiz de backup: {}", backupRootDir);

        BackupContext backupContext = injector.getInstance(BackupContext.class);

        if (Files.exists(backupContext.getBackupDir())) {
            log.info("O diretório '{}' já existe", backupContext.getBackupDir());
        } else {
            log.info("O diretório '{}' não existe, criando", backupContext.getBackupDir());
            Files.createDirectory(backupContext.getBackupDir());
        }

        ServiceLoader<Backup> backupProviders = ServiceLoader.load(Backup.class);
        for (Backup backup : backupProviders) {
            injector.injectMembers(backup);
            try {
                backup.process(); // executa a rotina de backup
            } catch (BackupException e) {
                // TODO: decidir melhor o que fazer com o erro:
                // parar o fluxo?
                // guardar o erro, pular pra próxima execução e resumir todos os erros no final?
                log.error(e);
                return 1; // abortando
            }
        }

        return 0;
    }

}
