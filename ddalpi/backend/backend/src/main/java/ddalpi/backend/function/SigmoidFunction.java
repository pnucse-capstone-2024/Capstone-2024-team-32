package ddalpi.backend.function;

public class SigmoidFunction {
    private static final int X0 = 240;
    private static final double K = 0.03;
    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-K * (x - X0)));
    }
}
