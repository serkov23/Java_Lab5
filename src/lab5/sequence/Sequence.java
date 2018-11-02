package lab5.sequence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Sequence {
    protected int n;
    protected double a1, d;
    public Sequence(int n, double a1, double d) {
        if(n<0)
            throw new IllegalArgumentException("invalid n");
        this.n = n;
        this.a1 = a1;
        this.d = d;
    }

    public void setN(int n) {
        this.n = n;
    }

    public abstract double getElement(int j);

    public double sum() {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += getElement(i + 1);
        }
        return sum;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            res.append(getElement(i + 1)).append(' ');
        }
        return res.toString();
    }

    public void saveToFile(File file) throws IOException {
        var fileWriter=new FileWriter(file);
        fileWriter.write(toString());
        fileWriter.close();
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public void setD(double d) {
        this.d = d;
    }
}
