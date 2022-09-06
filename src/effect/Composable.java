package effect;

public interface Composable<T, R> {
   <V> Effect<T, V> compose(Effect<R, V> that);
}
