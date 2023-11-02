/**
 * Representa uma rotina de backup. Exemplo: backup da configuração do
 * powerlevel10k, backup do histórico de comandos do ZSH, backup das conexões de rede etc.
 *
 * @see Restore
 */
public interface Backup {

    void process() throws BackupException;

}
