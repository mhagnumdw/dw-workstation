/**
 * Representa uma rotina de backup. Exemplo: backup da configuração do
 * powerlevel10k, backup do histórico de comandos do ZSH, backup das conexões de rede etc.
 */
public interface Backup {

    void process();

    // TODO: o ideal é que esse método não exista e que o context seja recebido via
    // construtor
    void setContext(Context context);

}
