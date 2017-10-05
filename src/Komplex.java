/**
 * http://introcs.cs.princeton.edu/java/32class/Complex.java.html
 */
public class Komplex {
    private final double real;
    private final double im;

    Komplex() {
        this(0, 0);
    }

    Komplex(double r, double i) {
        real = r;
        im = i;
    }

    Komplex mas(Komplex b) {
        return new Komplex(real + b.real, im + b.im);
    }

    Komplex menos(Komplex b) {
        return new Komplex(real - b.real, im - b.im);
    }

    Komplex por(Komplex b) {
        double r = real * b.real - im * b.im;
        double i = real * b.im + im * b.real;
        return new Komplex(r, i);
    }

    Komplex por(double d) {
        return new Komplex(real * d, im * d);
    }

    public Komplex conjugado() {
        return new Komplex(real, -im);
    }

    public double magnitud(){
        return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.im, 2));
    }

    @Override
    public String toString() {
        return String.format("(%f,%f)", real, im);
    }
}
