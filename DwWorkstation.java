///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+
//DEPS info.picocli:picocli:4.6.3
//DEPS info.picocli:picocli-codegen:4.6.3 // necessário apenas para gerar código nativo
//DEPS com.google.auto.service:auto-service:1.1.1
//DEPS org.projectlombok:lombok:1.18.30
//SOURCES *.java

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import lombok.Getter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.ScopeType;

/**
 * Comando principal pra ser executado no CLI, exemplo: ./DwWorkstation.java --help
 */
@Command(
    name = "DwWorkstation",
    mixinStandardHelpOptions = true,
    version = "DwWorkstation 0.2",
    description = "DwWorkstation made with jbang",
    scope = ScopeType.INHERIT, // https://picocli.info/#_inherited_command_attributes
    subcommands = {
        BackupCommand.class
    }
)
class DwWorkstation implements Callable<Integer> {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Option(names = "--user-home", description = "User home dir (default: ${DEFAULT-VALUE})")
    private Path userHome = Path.of(System.getProperty("user.home"));

    @Getter
    private Context context;

    public static void main(String... args) {
        DwWorkstation app = new DwWorkstation();
        int exitCode = new CommandLine(app)
            .setExecutionStrategy(app::executionStrategy)
            .execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        log.info("Iniciando...");
        return 0;
    }

    // https://picocli.info/#_initialization_before_execution
    // A reference to this method can be used as a custom execution strategy
    // that first calls the init() method,
    // and then delegates to the default execution strategy.
    private int executionStrategy(ParseResult parseResult) {
        init(); // custom initialization to be done before executing any command or subcommand
        return new CommandLine.RunLast().execute(parseResult); // default execution strategy
    }

    private void init() {
        log.info("init");
        this.context = Context.of(userHome);
    }

}