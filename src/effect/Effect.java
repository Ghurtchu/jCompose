package effect;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Effect<T, R> implements InfallibleConsumer<T, R>, FallibleConsumer<T, R>, UnsafeExecutable<T, R>, Composable<T, R> {

    private final Function<T, R> function;

    private Consumer<R>         onSuccessConsumer;
    private Consumer<Throwable> onFailureConsumer;

    private Effect(Function<T, R> function) {
        this.function = function;
    }

    public static <T, R> Effect<T, R> fromFunction(Function<T, R> function) {
        return new Effect<>(function);
    }

    @Override
    public Optional<R> executeWithArg(T arg) {
        Optional<R> maybeResult = Optional.empty();
        try {
            maybeResult = Optional.of(function.apply(arg));
            onSuccessConsumer.accept(maybeResult.get());
        } catch (Throwable e) {
            onFailureConsumer.accept(e);
        }
        return maybeResult;
    }

    @Override
    public Effect<T, R> onFailure(Consumer<Throwable> consumer) {
        this.onFailureConsumer = consumer;
        return this;
    }

    @Override
    public Effect<T, R> onSuccess(Consumer<R> consumer) {
        this.onSuccessConsumer = consumer;
        return this;
    }

    @Override
    public <V> Effect<T, V> compose(Effect<R, V> that) {
        return new Effect<>(function.andThen(that.function));
    }

}




