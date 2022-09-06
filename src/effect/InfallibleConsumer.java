package effect;

import java.util.function.Consumer;

public interface InfallibleConsumer<T, R> {
    Effect<T, R> onSuccess(Consumer<R> consumer);
}


