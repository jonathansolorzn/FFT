import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static java.nio.ByteBuffer.*;

/**
 * https://stackoverflow.com/a/39425678/3809894
 */

public class WaveFile {
    private final int NOT_SPECIFIED = AudioSystem.NOT_SPECIFIED; // -1

    private int sampleSize = NOT_SPECIFIED;
    private long framesCount = NOT_SPECIFIED;
    private byte[] data;      // wav bytes
    private AudioFormat audioFormat;
    private Clip clip;
    private boolean canPlay;

    public WaveFile(File file) throws UnsupportedAudioFileException, IOException {
        if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());

        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        audioFormat = ais.getFormat();
        framesCount = ais.getFrameLength();
        sampleSize = audioFormat.getSampleSizeInBits() / 8;

        long dataLength = framesCount * audioFormat.getSampleSizeInBits() * audioFormat.getChannels() / 8;
        data = new byte[(int) dataLength];
        ais.read(data);

        AudioInputStream aisForPlay = AudioSystem.getAudioInputStream(file);
        try {
            clip = AudioSystem.getClip();
            clip.open(aisForPlay);
            clip.setFramePosition(0);
            canPlay = true;
        } catch (LineUnavailableException e) {
            canPlay = false;
            System.out.println("I can play only 8bit and 16bit music.");
        }
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }


    public int getSampleSize() {
        return sampleSize;
    }


    public Datos getUnscaledAmplitude() {
        double[] x = new double[data.length / 2];
        double[] y = new double[data.length / 2];

        for (int audioByte = 0, i = 0; audioByte < data.length; i++) {
            // Do the byte to sample conversion.
            double low = (double) data[audioByte];
            audioByte++;
            double high = (double) data[audioByte];
            audioByte++;
            double sample = (double) ((int) high << 8) + ((int) low & 0x00ff);

            x[i] = i + 1;
            y[i] = sample;
        }

        return new Datos(x, y);
    }

    public Datos getFreciencias() {

        int length = potenciaDe2Anterior(data.length);

        Komplex[] in = new Komplex[length];
        for (int i = 0; i < length; i++) in[i] = new Komplex(data[i], 0);
        Komplex[] out = TRF.trf(in);

        double[] x = new double[out.length];
        double[] y = new double[out.length];

        for (int i = 0; i < out.length; i++) {
            x[i] = (double) i;
            y[i] = out[i].magnitud();
        }

        return new Datos(x, y);
    }

    private int potenciaDe2Anterior(int x) {
        if (x == 0)
            return 0;

        x--;
        x |= (x >> 1);
        x |= (x >> 2);
        x |= (x >> 4);
        x |= (x >> 8);
        x |= (x >> 16);
        return x - (x >> 1);
    }

}