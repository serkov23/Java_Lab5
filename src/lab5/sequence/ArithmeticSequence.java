package lab5.sequence;

public class ArithmeticSequence extends Sequence {
    public ArithmeticSequence(int n, double a1, double d) {
        super(n, a1, d);
    }

    @Override
    public double getElement(int j) {
        if (j <= 0)
            throw new IllegalArgumentException("no such element in Arithmetic Sequence");
        return a1 + (j - 1) * d;
    }
}
