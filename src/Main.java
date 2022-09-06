import effect.Effect;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    // Often times we create functions, and we'd like to handle the success and failure cases
    // e.g perform I/O operations such as logging, interacting with databases, servers or other services.
    // We often end up writing massive boilerplate for null checking / data availability.
    // Effect data structure can be useful in order to reduce the boilerplate and write more compact code
    // which is based on composition and laziness.

    public static void main(String[] args) {

        // Effect is a lazy data structure which represents the description of computation from Integer to List<Data>.
        // It may fail with any concrete instance in the Throwable hierarchy.
        Effect<Integer, List<Data>> description =
                Effect.fromFunction((Integer num) -> num * 5)
                        .compose(Effect.fromFunction(Object::toString))
                        .compose(Effect.fromFunction(txt -> txt.concat(txt)))
                        .compose(Effect.fromFunction(Data::new))
                        .compose(Effect.fromFunction(data -> Stream
                                .iterate(0, i -> i + 1)
                                .limit(10)
                                .map(num -> new Data(data.inner + num))
                                .collect(Collectors.toList())))
                        .onSuccess(num -> System.out.println("mapped num to Data successfully: " + num)) // consumer callback if the pipeline will be successful (it's optional)
                        .onFailure(err -> System.out.println("Failed due to: " + err)); // consumer callback if the pipeline will be unsuccessful (it's optional)

        // Calling executeWithArg(T arg) starts executing the pipeline of composed functions.
        // Since the pipeline may fail it returns Optional<R> for indicating the possible absence of value.
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


