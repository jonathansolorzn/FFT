/**
 * http://introcs.cs.princeton.edu/java/97data/FFT.java.html
 */

public class TRF {
    // compute the FFT of x[], assuming its length is a power of 2
    public static Komplex[] trf(Komplex[] x) {
        int N = x.length;

        // base case
        if (N == 1) return new Komplex[]{x[0]};

        // radix 2 Cooley-Tukey FFT
        if (N%2 != 0) {
            throw new RuntimeException("N no es potencia de 2");
        }

        // trf of even terms
        Komplex[] even = new Komplex[N / 2];
        for (int k = 0; k < N / 2; k++) {
            even[k] = x[2 * k];
        }
        Komplex[] q = trf(even);

        // trf of odd terms
        Komplex[] odd = even;  // reuse the array
        for (int k = 0; k < N / 2; k++) {
            odd[k] = x[2 * k + 1];
        }
        Komplex[] r = trf(odd);

        // combine
        Komplex[] y = new Komplex[N];
        for (int k = 0; k < N / 2; k++) {
            double kth = -2 * k * Math.PI / N;
            Komplex wk = new Komplex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].mas(wk.por(r[k]));
            y[k + N / 2] = q[k].menos(wk.por(r[k]));
        }
        return y;
    }


    // compute the inverse FFT of x[], assuming its length is a power of 2
    public static Komplex[] itrf(Komplex[] x) {
        int N = x.length;
        Komplex[] y = new Komplex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugado();
        }

        // compute forward FFT
        y = trf(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugado();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].por(1.0 / N);
        }

        return y;

    }

    // compute the circular convolution of x and y
    public static Komplex[] cconvolve(Komplex[] x, Komplex[] y) {

        // should probably pad x and y with 0s so that they have same length
        // and are powers of 2
        if (x.length != y.length) {
            throw new RuntimeException("Dimensions don't agree");
        }

        int N = x.length;

        // compute FFT of each sequence
        Komplex[] a = trf(x);
        Komplex[] b = trf(y);

        // point-wise multiply
        Komplex[] c = new Komplex[N];
        for (int i = 0; i < N; i++) {
            c[i] = a[i].por(b[i]);
        }

        // compute inverse FFT
        return itrf(c);
    }


    // compute the linear convolution of x and y
    static Komplex[] convolve(Komplex[] x, Komplex[] y) {
        Komplex ZERO = new Komplex(0, 0);

        Komplex[] a = new Komplex[2 * x.length];
        System.arraycopy(x, 0, a, 0, x.length);
        for (int i = x.length; i < 2 * x.length; i++) a[i] = ZERO;

        Komplex[] b = new Komplex[2 * y.length];
        System.arraycopy(y, 0, b, 0, y.length);
        for (int i = y.length; i < 2 * y.length; i++) b[i] = ZERO;

        return cconvolve(a, b);
    }

    // display an array of Complex numbers to standard output
    static void show(Komplex[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (Komplex aX : x) {
            System.out.println(aX);
        }
        System.out.println();
    }
}
