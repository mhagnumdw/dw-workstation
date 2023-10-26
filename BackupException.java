public class BackupException extends Exception {

    private BackupException(String message) {
        super(message);
    }

    private BackupException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BackupException of(String format, Object... arguments) {
        return new BackupException(Utils.format(format, arguments));
    }

    public static BackupException of(Throwable cause, String format, Object... arguments) {
        return new BackupException(Utils.format(format, arguments), cause);
    }

}
