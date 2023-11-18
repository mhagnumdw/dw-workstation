/**
 * Representa uma rotina de backup/restore. Exemplo: backup da configuração do
 * powerlevel10k, backup do histórico de comandos do ZSH, backup das conexões de rede etc.
 */
public interface Backup {

    void backup() throws BackupException;

    void restore() throws BackupException;

}
