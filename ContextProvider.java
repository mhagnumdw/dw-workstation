import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ContextProvider implements Provider<Context> {

    @Inject
    private DwWorkstation dwWorkstation;

    @Override
    public Context get() {
        String username = System.getProperty("user.name");
        return Context.of(dwWorkstation.getUserHome(), username);
    }

}
