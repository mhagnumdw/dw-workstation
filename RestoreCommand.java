import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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

/**
 * Comando de restore no CLI. Executa todas as rotinas de restauração do backup.
 */
@Singleton
@Command(name = "restore", description = "Executa a restauração do backup")
public class RestoreCommand implements Callable<Integer> {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Getter
    @Option(names = "--backup-root-dir", required = true, description = "Directory where the backup is saved")
    private Path backupRootDir;

    @Inject
    private Provider<BackupContext> backupContextProvider; // Lazy inject with Provider<>

    // @Inject
    // private Injector injector;

    @Override
    public Integer call() throws Exception {
        log.info("Iniciando...");
        log.info("Diretório raiz de backup: {}", backupRootDir);

        // TODO: daquiii: erro ao executar:
        // ./DwWorkstation.java restore --backup-root-dir=/home/mhagnumdw/Dropbox/backup/teste-dw-backup
        // Pois realmente não é possível injetar BackupCommand em BackupContextProvider
        // já que no momento está sendo executado RestoreCommand
        // Talvez uma boa opção seja ter apenas o BackupCommand com
        // duas opções: --executar --restaurar
        // ./DwWorkstation.java backup --executar --backup-root-dir=/xpto/xpto
        // ./DwWorkstation.java backup --restaurar --backup-root-dir=/xpto/xpto
        BackupContext backupContext = backupContextProvider.get();

        if (Files.notExists(backupContext.getBackupDir())) {
            throw new NoSuchFileException(backupContext.getBackupDir().toString());
        }

        // ServiceLoader<Backup> backupProviders = ServiceLoader.load(Backup.class);
        // for (Backup backup : backupProviders) {
        //     injector.injectMembers(backup);
        //     try {
        //         backup.process(); // executa a rotina de backup
        //     } catch (BackupException e) {
        //         // TODO: e se não vier BackupException, o que fazer?
        //         // TODO: decidir melhor o que fazer com o erro:
        //         // parar o fluxo?
        //         // guardar o erro, pular pra próxima execução e resumir todos os erros no final?
        //         log.error(e);
        //         return 1; // abortando
        //     }
        // }

        return 0;
    }

}
