import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.google.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Implementação comum das rotinas de backup. Normalmente rotinas de backup
 * devem herdar dessa classe ao invés de implementar a interface Backup.
 */
public abstract class BackupAbstract implements Backup {

    protected final Logger log = Logger.getLogger(getClass());

    @Inject
    @Getter(AccessLevel.PROTECTED)
    private Context context;

    @Inject
    @Getter(AccessLevel.PROTECTED)
    private BackupContext backupContext;

    // TODO: nem precisaria do segundo parâmetro, pode assumir o nome do arquivo do source
    /**
     * Copia um arquivo.
     *
     * @param source o arquivo a ser copiado
     * @param outputFilename o nome do arquivo de destino (apenas o nome do arquivo, sem path, o local será resolvido de acordo com o contexto do backup)
     * @throws BackupException se ocorrer uma falha ao copiar o arquivo
     */
    protected void copy(Path source, String outputFilename) throws BackupException {
        Utils.assertOnlyFilename(outputFilename);
        Path target = getBackupContext().getBackupDir().resolve(outputFilename);
        log.info("Backup de '{}' para '{}'", source, target);
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING/*, StandardCopyOption.COPY_ATTRIBUTES*/);
            log.info("Backup feito");
        } catch (IOException e) {
            throw BackupException.of(e, "Falha ao executar cópia de '{}' para '{}'", source, target);
        }

    }

    // TODO: nem precisaria do segundo parâmetro, pode assumir o nome do arquivo do source
    /**
     * Copia um arquivo para o diretório de backup usando sudo.
     *
     * @param source o arquivo a ser copiado
     * @param outputFilename o nome do arquivo de destino (apenas o nome do arquivo, sem path, o local será resolvido de acordo com o contexto do backup)
     * @throws BackupException se ocorrer uma falha ao copiar o arquivo
     */
    protected void copyWithSudo(Path source, String outputFilename) throws BackupException {
        Utils.assertOnlyFilename(outputFilename);
        Path target = getBackupContext().getBackupDir().resolve(outputFilename);
        try {
            log.info("Backup de '{}' para '{}'", source, target);
            Utils.copyWithSudo(source, target);
            log.info("Backup feito");
        } catch (IOException | NonZeroExitCodeException e) {
            throw BackupException.of(e, "Falha ao executar cópia com sudo de '{}' para '{}'", source, target);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw BackupException.of(e, "Falha ao executar cópia com sudo de '{}' para '{}' (operação interrompida)", source, target);
        }
    }

    /**
     * Guarda o stdout de um comando.
     * @param command comando que deve ser executado
     * @param outputFilename o nome do arquivo de destino (apenas o nome do arquivo, sem path, o local será resolvido de acordo com o contexto do backup)
     * @throws BackupException se ocorrer uma falha ao executar
     */
    protected void backup(String command, String outputFilename) throws BackupException {
        Path target = getBackupContext().getBackupDir().resolve(outputFilename);
        log.info("O resultado do comando '{}' será salvo em '{}'", command, target);
        try {
            Utils.exec(command, target);
            log.info("Backup feito");
        } catch (IOException | NonZeroExitCodeException e) {
            throw BackupException.of(e, "Falha ao executar '{}'", command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw BackupException.of(e, "Falha ao executar (operação interrompida) '{}'", command);
        }
    }

}
