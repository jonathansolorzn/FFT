import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

public class Grabacion extends JFrame{
    private JButton grabarButton;
    private JPanel mainPanel;

    private AudioFileFormat.Type audioFileFormatType = AudioFileFormat.Type.WAVE;
    private AudioFormat audioFormat = new AudioFormat(8000.0F, 16, 1, true, false);
    private TargetDataLine targetDataLine;
    private File file = new File("Grabacion.wav");

    private Grabacion() {
        grabarButton.addActionListener(e -> {
            try {
                DataLine.Info dLI = new DataLine.Info(TargetDataLine.class, audioFormat);
                targetDataLine = (TargetDataLine) AudioSystem.getLine(dLI);
                new CapThread().start();
                System.out.println("Grabando durante 10s...");
                Thread.sleep(10000);
                targetDataLine.close();

                WAV grabacion = new WAV(file);
                new DSP(grabacion.getAmplitud(), grabacion.getFreciencias());

                Grabacion.this.dispose();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Grabacion");
        frame.setContentPane(new Grabacion().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,250);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class CapThread extends Thread {
        public void run() {
            try {
                targetDataLine.open(audioFormat);
                targetDataLine.start();
                AudioSystem.write(new AudioInputStream(targetDataLine), audioFileFormatType, file);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


