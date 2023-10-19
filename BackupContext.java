import java.nio.file.Path;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class BackupContext {

    @NonNull
    @Getter
    private Path backupDir;

}
