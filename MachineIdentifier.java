import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MachineIdentifier {

    private static final String UNKNOWN_SO = "unknown-so";
    private static final String UNKNOWN_HOST = "unknown-host";

    public static String getIdentifier() {
        String distribution = getLinuxDistribution();
        String hostname = getHostname();
        String username = System.getProperty("user.name");
        return distribution + "-" + hostname + "-" + username;
    }

    private static String getLinuxDistribution() {
        try {
            return Files.readAllLines(Paths.get("/etc/os-release"))
                .stream()
                .filter(line -> line.startsWith("ID="))
                .map(line -> line.replace("ID=", ""))
                .findFirst()
                .orElse(UNKNOWN_SO);
        } catch (IOException e) {
            return UNKNOWN_SO;
        }
    }

    private static String getHostname() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return UNKNOWN_HOST;
        }
    }
}
