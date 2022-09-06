package effect;

import java.util.Optional;

public interface UnsafeExecutable<T, R> {
    Optional<R> executeWithArg(T arg);
}


