import java.nio.file.Path;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor(staticName = "of")
public class Context {

    @NonNull
    @Getter
    private Path userHome;

    @NonNull
    @Getter
    private String username;

    @Getter
    @Setter // TODO: é ideal que essa setagem só ocorra uma única vez
    private BackupContext backupContext;

}
