import java.util.Arrays;
import java.util.concurrent.Callable;

public class Summarizer implements Callable<Integer> {
    private final int[] numbers;

    public Summarizer(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public Integer call() {
        return Arrays.stream(numbers)
                .sum();
    }
}
