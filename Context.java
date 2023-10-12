import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class Context {

    @NonNull
    @Getter
    private String userHome;

}
