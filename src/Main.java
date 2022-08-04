import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    private static final int NUMBERS_COUNT = 10;
    private static final int ORIGIN = 0;
    private static final int BOUND = 1000;
    private static final int SHOP_COUNT = 3;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        LongAdder sum = new LongAdder();
        List<Summarizer> threads = new ArrayList<>(SHOP_COUNT);
        for (int i = 0; i < SHOP_COUNT; i++) {
            threads.add(new Summarizer(getNumbers(ORIGIN, BOUND, NUMBERS_COUNT)));
        }
        List<Future<Integer>> results = executorService.invokeAll(threads);
        results
                .parallelStream()
                .forEach(result -> {
                    try {
                        sum.add(result.get());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
        executorService.shutdown();
        System.out.println("Сумма выручки по " + SHOP_COUNT + " магазинам = " + sum + " рублей.");
    }

    public static int[] getNumbers(int origin, int bound, int numbersCount) {
        return  Arrays.stream(new int[numbersCount])
                .parallel()
                .map(num -> num = new Random().nextInt(origin, bound))
                .toArray();
    }
}