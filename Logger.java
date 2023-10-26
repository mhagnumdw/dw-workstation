import static java.lang.System.out;

/**
 * Mecanismo de log básico da aplicação.
 */
public class Logger {

    private enum Level {
        INFO, ERROR
    }

    private Class<?> lookupClass;

    private Logger(Class<?> lookupClass) {
        this.lookupClass = lookupClass;
    }

    public static Logger getLogger(Class<?> lookupClass) {
        return new Logger(lookupClass);
    }

    public void info(Object message) {
        log(Level.INFO, message);
    }

    public void info(String format, Object... arguments) {
        log(Level.INFO, Utils.format(format, arguments));
    }

    public void error(Object message) {
        log(Level.ERROR, message);
    }

    public void error(Throwable e) {
        log(Level.ERROR, e);
    }

    public void error(String format, Object... arguments) {
        log(Level.ERROR, Utils.format(format, arguments));
    }

    private void log(Level level, Object message) {
        out.println("[" + level + "] [" + lookupClass.getName() + "] " + message);
    }

    private void log(Level level, Throwable e) {
        out.println("[" + level + "] [" + lookupClass.getName() + "] " + e.getMessage());
        e.printStackTrace();
    }

}
