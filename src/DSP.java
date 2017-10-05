import org.knowm.xchart.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DSP extends JFrame {

    DSP(Datos amplitud, Datos frecuencias) {

        List<XYChart> graficas = new ArrayList<>();

        //Amplitud
        XYChart gAmplitud = QuickChart.getChart("Amplitud", "X", "Y", "y(x)", amplitud.x, amplitud.y);
        XYChart gFrecuencia = QuickChart.getChart("Freciencias", "X", "Y", "y(x)", frecuencias.x, frecuencias.y);

        graficas.add(gAmplitud);
        graficas.add(gFrecuencia);

        new SwingWrapper<>(graficas).displayChartMatrix();
    }

}
