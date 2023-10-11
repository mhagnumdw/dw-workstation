import static java.lang.System.out;

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

}
