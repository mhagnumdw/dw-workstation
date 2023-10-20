
public class BackupException extends Exception {

    private BackupException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BackupException of(Throwable cause, String format, Object... arguments) {
        return new BackupException(format(format, arguments), cause);
    }

    // TODO: esse mesmo método existe em Logger.java
    private static String format(String format, Object... arguments) {
        return String.format(format.replace("{}", "%s"), arguments);
    }
}
