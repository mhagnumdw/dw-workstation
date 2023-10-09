///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+
//DEPS info.picocli:picocli:4.6.3
//DEPS info.picocli:picocli-codegen:4.6.3
//SOURCES Backup.java
//SOURCES Restore.java

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "BackupRestore", mixinStandardHelpOptions = true, version = "BackupRestore 0.2",
        description = "BackupRestore made with jbang")
class BackupRestore implements Callable<Integer> {

    @Parameters(index = "0", description = "The greeting to print", defaultValue = "World!")
    private String greeting;

    public static void main(String... args) {
        int exitCode = new CommandLine(new BackupRestore()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        System.out.println("Hello " + greeting);
        new Backup().process();
        new Restore().process();
        return 0;
    }
}
