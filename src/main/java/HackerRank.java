import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HackerRank<T> {

    public int countSheeps(Boolean[] arrayOfSheeps) {
        return (int)Arrays.stream(arrayOfSheeps).filter(aBoolean -> Boolean.TRUE == aBoolean).count();

    }

    class Tutorial {
        public int getWordsCount() {
            return 0;
        }
    }


    public static <R> Map<String, R> execute(List<String> tables, Function<String, R> query, R defaultValue) {
        Map<String, R> tableToResultMap = new ConcurrentHashMap<>();

        CompletableFuture<?>[] futures = tables.stream()
                .map(table -> CompletableFuture
                        .supplyAsync(() -> tableToResultMap.put(table, query.apply(table)))
                        .handle((r, throwable) -> {
                            if (throwable == null) {
                                return tableToResultMap.put(table, defaultValue);
                            } else {
                                return r;
                            }
                        }))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures);

        return tableToResultMap;
    }

    interface Try<T> {

        /**
         * Returns true if the original operation succeeded, otherwise returns false
         */
        boolean isSuccess();

        /**
         * Returns the resulting value if this is a Success, otherwise throws the original exception
         */
        T get() throws Throwable;

        /**
         * Returns the resulting value if this is a Success,
         * otherwise throws the original exception wrapped in a RuntimeException
         *
         * @throws RuntimeException that wraps the original exception
         */
        T getUnchecked();

        /**
         * Converts this to a non-empty Optional that wraps the resulting value if this is Success,
         * otherwise returns an empty Optional
         */
        Optional<T> toOptional();

        /**
         * Returns the given default value if this is a Failure,
         * otherwise returns the resulting value
         */
        T getOrElse(T defaultValue);

        /**
         * Returns the resulting value if it is a Success,
         * otherwise returns the result produced by the given supplier
         */
        T getOrElseSupply(Supplier<? extends T> supplier);

        /**
         * Returns the resulting value if this is a Success,
         * otherwise throws an exception produced by the exception supplier
         *
         * @throws Throwable produced by the exception supplier
         */
        <X extends Throwable> T getOrElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

        /**
         * Applies the given action to the resulting value if it is a Success, otherwise does nothing
         *
         * @return the current Try object
         * @throws E if the action throws an exception
         */
        <E extends Throwable> Try<T> onSuccess(ThrowableConsumer<T, E> action) throws E;

        /**
         * Applies the given action to the exception if it is a Failure, otherwise does nothing
         *
         * @return the current Try object
         * @throws E if the action throws an exception
         */
        <E extends Throwable> Try<T> onFailure(ThrowableConsumer<Throwable, E> action) throws E;

        /**
         * Converts this Success into a Failure (which holds NoSuchElementException)
         * if it is a Success and the predicate doesn't match for the value,
         * otherwise returns this Try (Success or Failure)
         */
    }

    @FunctionalInterface
    interface ThrowableOperation<T> {
        T execute() throws Throwable;
    }

    /**
     * Represents an action that may potentially fail with an exception
     */
    @FunctionalInterface
    interface ThrowableConsumer<T, E extends Throwable> {
        void accept(T t) throws E;
    }

    class Success<T> implements Try<T> {
        private final T value;

        Success(T value) {
            this.value = value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public T get() {
            return this.value;
        }

        @Override
        public T getUnchecked() {
            return this.value;
        }

        @Override
        public Optional<T> toOptional() {
            return Optional.of(this.value);
        }

        @Override
        public T getOrElse(T defaultValue) {
            return this.value;
        }

        @Override
        public T getOrElseSupply(Supplier<? extends T> supplier) {
            return this.value;
        }

        @Override
        public <X extends Throwable> T getOrElseThrow(Supplier<? extends X> exceptionSupplier) {
            return this.value;
        }

        @Override
        public <E extends Throwable> Try<T> onSuccess(ThrowableConsumer<T, E> action) throws E {
            action.accept(this.value);
            return this;
        }

        @Override
        public <E extends Throwable> Try<T> onFailure(ThrowableConsumer<Throwable, E> action) {
            return this;
        }

        @Override
        public String toString() {
            return "Success[" + value + "]";
        }
    }

    public static void main(String[] args) {

        boolean[] b = {true, true, true ,false};
        Stream<Boolean> s = IntStream.range(0, b.length).mapToObj(operand -> b[operand]);
        System.out.println( s.filter(aBoolean -> Boolean.TRUE == aBoolean).count() );
        Boolean[] bb = {};
        Arrays.stream(bb).filter(aBoolean -> Boolean.TRUE == aBoolean).count();

        System.out.println("*********************************");
        int i = 0;
        Object[] a = new Object[] {"hay", "junk", "hay", "hay", "moreJunk", "needle", "randomJunk"};
        List list = Arrays.stream(a).collect(Collectors.toList());
        System.out.println( list.indexOf("needle") );

        System.out.println(null + "1245");

        Client client = new Client();

        //changes

    }

}
