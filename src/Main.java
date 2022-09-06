import effect.Effect;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        // lazy description of computation from Integer to List<Data>
        Effect<Integer, List<Data>> description = Effect.fromFunction((Integer num) -> num / 0)
                .compose(Effect.fromFunction(Object::toString))
                .compose(Effect.fromFunction(txt -> txt.concat(txt)))
                .compose(Effect.fromFunction(Data::new))
                .compose(Effect.fromFunction(data -> Stream
                        .iterate(0, i -> i + 1)
                        .limit(10)
                        .map(num -> new Data(data.inner + num))
                        .collect(Collectors.toList())))
                .onSuccess(num -> System.out.println("mapped num to Data successfully: " + num))
                .onFailure(err -> System.out.println("Failed due to: " + err));

        // evaluation of computation
        Optional<List<Data>> maybeData = description.executeWithArg(new java.util.Random().nextInt(100));


    }

    private static class Data {

        private final String inner;

        private Data(String s) {
            this.inner = s;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "s='" + inner + '\'' +
                    '}';
        }
    }
}


