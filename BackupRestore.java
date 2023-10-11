///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+
//DEPS info.picocli:picocli:4.6.3
//DEPS info.picocli:picocli-codegen:4.6.3
//DEPS com.google.auto.service:auto-service:1.1.1
//DEPS org.projectlombok:lombok:1.18.30
//SOURCES *.java

import static java.lang.System.out;

import java.lang.invoke.MethodHandles;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "BackupRestore", mixinStandardHelpOptions = true, version = "BackupRestore 0.2",
        description = "BackupRestore made with jbang")
class BackupRestore implements Callable<Integer> {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    // @Parameters(index = "0", description = "User home dir", defaultValue = "World!")
    @Option(names = "--user-home", description = "User home dir (default: ${DEFAULT-VALUE})")
    private String userHome = System.getProperty("user.home");

    public static void main(String... args) {
        int exitCode = new CommandLine(new BackupRestore()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        log.info("Iniciando...");

        Context context = new Context();
        context.setUserHome(userHome);

        ServiceLoader<Backup> backupProviders = ServiceLoader.load(Backup.class);
        for (Backup backup : backupProviders) {
            backup.setContext(context);
            backup.process();
        }

        return 0;
    }
}
