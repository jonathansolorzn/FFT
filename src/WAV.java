import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import Lista.Lista;

/**
 * https://stackoverflow.com/a/39425678/3809894
 */

public class WAV {
    private byte[] data;      // wav bytes
    private Clip clip;
    private boolean puedeSonar;

    WAV(File file) throws UnsupportedAudioFileException, IOException {

        if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        long framesCount = audioInputStream.getFrameLength();

        AudioFormat audioFormat = audioInputStream.getFormat();

        long dataLength = framesCount * audioFormat.getSampleSizeInBits() * audioFormat.getChannels() / 8;
        data = new byte[(int) dataLength];
        audioInputStream.read(data);

        AudioInputStream audioInputStreamForPlay = AudioSystem.getAudioInputStream(file);
        try {
            clip = AudioSystem.getClip();
            clip.open(audioInputStreamForPlay);
            clip.setFramePosition(0);
            puedeSonar = true;
        } catch (LineUnavailableException e) {
            puedeSonar = false;
            System.out.println("I can play only 8bit and 16bit music.");
        }
    }

    public boolean puedeSonar() {
        return puedeSonar;
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }


    /**
     * Amplitud sin escala
     * @return
     */
    Lista getAmplitud() {

        Lista datos = new Lista();

        for (int audioByte = 0, i = 0; audioByte < data.length; i++) {
            // Conversion de byte a sample
            double low = (double) data[audioByte];
            audioByte++;
            double high = (double) data[audioByte];
            audioByte++;
            double sample = (double) ((int) high << 8) + ((int) low & 0x00ff);

            double xi = i + 1;
            double yi = sample;

            datos.insertarFin(xi, yi);
        }

        return datos;
    }

    /**
     * Dominio de frecuencias usando FFT
     * @return
     */
    Lista getFreciencias() {

        Lista datos = new Lista();
        int dominio = potenciaDe2Anterior(data.length);

        Complex[] in = new Complex[dominio];
        for (int i = 0; i < dominio; i++) in[i] = new Complex(data[i], 0);
        Complex[] out = FFT.fft(in);

        for (int i = 0; i < out.length; i++) {

            double xi = (double) i;
            double yi = out[i].magnitud();

            datos.insertarFin(xi, yi);
        }

        return datos;
    }

    private int potenciaDe2Anterior(int x) {
        if (x == 0) return 0;

        x--;
        x |= (x >> 1);
        x |= (x >> 2);
        x |= (x >> 4);
        x |= (x >> 8);
        x |= (x >> 16);
        return x - (x >> 1);
    }

}