/**
 * Representa uma rotina de restauração de algo que foi feito backup. Exemplo: restaurar
 * a configuração do powerlevel10k, do histórico de comandos do ZSH, das conexões de rede etc.
 *
 * @see Backup
 */
public interface Restore {

    void process();

}
