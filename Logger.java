import static java.lang.System.out;

/**
 * Mecanismo de log básico da aplicação.
 */
public class Logger {

    private Class<?> lookupClass;

    private Logger(Class<?> lookupClass) {
        this.lookupClass = lookupClass;
    }

    public static Logger getLogger(Class<?> lookupClass) {
        return new Logger(lookupClass);
    }

    public void info(Object message) {
        out.println("[" + lookupClass.getName() + "] " + message);
    }

    public void info(String format, Object... arguments) {
        out.println("[" + lookupClass.getName() + "] " + format(format, arguments));
    }

    private String format(String format, Object... arguments) {
        return String.format(format.replace("{}", "%s"), arguments);
    }

}
