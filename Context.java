import java.nio.file.Path;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class Context {

    @NonNull
    @Getter
    private Path userHome;

    @NonNull
    @Getter
    private String username;

}
