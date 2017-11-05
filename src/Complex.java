/**
 * http://introcs.cs.princeton.edu/java/32class/Complex.java.html
 */
public class Complex {
    private final double real;
    private final double imaginario;

    Complex(double r, double i) {
        real = r;
        imaginario = i;
    }

    Complex mas(Complex b) {
        return new Complex(real + b.real, imaginario + b.imaginario);
    }

    Complex menos(Complex b) {
        return new Complex(real - b.real, imaginario - b.imaginario);
    }

    Complex por(Complex b) {
        double r = real * b.real - imaginario * b.imaginario;
        double i = real * b.imaginario + imaginario * b.real;
        return new Complex(r, i);
    }

    double magnitud(){
        return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginario, 2));
    }

    @Override
    public String toString() {
        return String.format("(%f,%f)", real, imaginario);
    }
}
