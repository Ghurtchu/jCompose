package effect;

import java.util.function.Consumer;

public interface FallibleConsumer<T, R> {
    Effect<T, R> onFailure(Consumer<Throwable> consumer);
}


