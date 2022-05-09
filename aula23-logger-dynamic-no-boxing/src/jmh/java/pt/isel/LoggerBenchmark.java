package pt.isel;

import org.openjdk.jmh.annotations.Benchmark;

public class LoggerBenchmark {

    static final SavingsAccount acc = new SavingsAccount(1500, 1.25);
    static final LoggerReflect loggerReflect = new LoggerReflect(new EmptyPrinter(), LoggerKind.FUNCTIONS);
    static final LoggerDynamic loggerDynamic = new LoggerDynamic(new EmptyPrinter(), LoggerKind.FUNCTIONS);

    @Benchmark
    public void benchLogSavingsAccountViaReflection() {
        loggerReflect.log(acc);
    }
    @Benchmark
    public void benchLogSavingsAccountViaDynamic() {
        loggerDynamic.log(acc);
    }
}
