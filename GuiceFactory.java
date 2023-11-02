import java.lang.invoke.MethodHandles;

import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

public class GuiceFactory implements IFactory {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private final Injector injector = Guice.createInjector(new GuiceModule());

    @Override
    public <K> K create(Class<K> aClass) throws Exception {
        try {
            log.info("Criando: {}", aClass.getName());
            K instance = injector.getInstance(aClass);
            log.info("Criado: {}", aClass.getName());
            return instance;
        } catch (ConfigurationException ex) { // no implementation found in Guice configuration
            log.error("Usando a factory default para criar: {}", aClass.getName());
            return CommandLine.defaultFactory().create(aClass); // fallback if missing
        }
    }

}
