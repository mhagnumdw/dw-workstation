import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Context.class).toProvider(ContextProvider.class);
        bind(BackupContext.class).toProvider(BackupContextProvider.class);
    }

}
