package lab5.sequence;

public class ExponentialSequence extends Sequence {

    public ExponentialSequence(int n, double a1, double d) {
        super(n, a1, d);
    }

    private static double binPow(double val, int n) {
        if (n == 0)
            return 1;
        if ((n & 1) == 0) {
            double tmp = binPow(val * val, n >> 1);
            return tmp * tmp;
        }
        return binPow(val, n - 1) * val;
    }

    @Override
    public double getElement(int j) {
        if (j <= 0)
            throw new IllegalArgumentException("no such element in Exponential Sequence");
        return a1 * binPow(d, j - 1);
    }
}
