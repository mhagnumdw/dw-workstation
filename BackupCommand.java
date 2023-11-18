import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import lombok.Getter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

enum BackupOpeartion {
    backup, restore;
}

/**
 * Comando de backup no CLI. Executa todas as rotinas de backup.
 */
@Singleton
@Command(name = "backup", description = "Executa o backup")
public class BackupCommand implements Callable<Integer> {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Getter
    @Option(names = "--backup-root-dir", required = true, description = "Directory where the backup is saved")
    private Path backupRootDir;

    // @Getter
    @Option(names = "--operation", required = true, description = "Valid values: ${COMPLETION-CANDIDATES}")
    private BackupOpeartion operation;

    // @Inject
    // private Provider<BackupContext> backupContextProvider; // Lazy inject with Provider<>

    @Inject
    private Injector injector;

    @Override
    public Integer call() throws Exception {
        log.info("Iniciando...");
        log.info("Diretório raiz de backup: {}", backupRootDir);
        log.info("Operação: {}", operation);

        // BackupContext backupContext = backupContextProvider.get();

        // TODO: talvez esse if-else seja melhor ficar na classe abstrada de backup
        // como um pre-processamento
        // if (Files.exists(backupContext.getBackupDir())) {
        //     log.info("O diretório '{}' já existe", backupContext.getBackupDir());
        // } else {
        //     log.info("O diretório '{}' não existe, criando", backupContext.getBackupDir());
        //     Files.createDirectory(backupContext.getBackupDir());
        // }

        ServiceLoader<Backup> backupProviders = ServiceLoader.load(Backup.class);
        for (Backup backup : backupProviders) {
            injector.injectMembers(backup);
            try {
                if (operation.equals(BackupOpeartion.backup)) {
                    backup.backup(); // executa a rotina de backup
                } else if (operation.equals(BackupOpeartion.restore)) {
                    backup.restore();
                } else {
                    throw BackupException.of("Operação não suportada: {}", operation);
                }
            } catch (BackupException e) {
                // TODO: e se não vier BackupException, o que fazer?
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
