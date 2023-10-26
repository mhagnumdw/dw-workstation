import lombok.Getter;

public class NonZeroExitCodeException extends Exception {

    @Getter
    private final int exitCode;

    public NonZeroExitCodeException(String message, int exitCode) {
        super(Utils.format("{} (exit code = {})", message, exitCode));
        this.exitCode = exitCode;
    }

}
