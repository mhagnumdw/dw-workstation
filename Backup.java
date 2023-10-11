public interface Backup {

    void process();

    // TODO: o ideal é que esse método não exista e que o
    // context seja recebido via construtor
    void setContext(Context context);

}
