/**
 * https://en.wikipedia.org/wiki/Cooley–Tukey_FFT_algorithm
 */

class FFT {

    /**
     * Calculo FFT de x [], suponiendo que su longitud es una potencia de 2
     *
     * @param x - Array de objetos de tipo Complejo
     * @return
     */
    static Complex[] fft(Complex[] x) {//Aka ditfft2
        int N = x.length;

        if (N == 1) return new Complex[]{x[0]};//Caso base

        if (N % 2 != 0) {// Radix 2 Cooley-Tukey FFT
            throw new RuntimeException("N no es potencia de 2");
        }

        int mitad = N / 2;
        Complex[] muestra = new Complex[mitad];

        // FFT de terminos pares
        for (int k = 0; k < mitad; k++) {
            muestra[k] = x[2 * k];
        }
        Complex[] X2s = fft(muestra);//pares

        // FFT de terminos impares
        for (int k = 0; k < mitad; k++) {
            muestra[k] = x[2 * k + 1];
        }
        Complex[] Xs_2s = fft(muestra);//impares

        // Combinar
        Complex[] y = new Complex[N];
        for (int k = 0; k < mitad; k++) {
            double kth = -2 * Math.PI * k / N; //−2πi k/N
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = X2s[k].mas(wk.por(Xs_2s[k]));
            y[k + mitad] = X2s[k].menos(wk.por(Xs_2s[k]));
        }
        return y;
    }
}
