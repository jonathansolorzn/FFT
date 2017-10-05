import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class DSP {

    DSP(DatosXY amplitud, DatosXY frecuencias) {

        final XYChart graficoAmplitud = new XYChartBuilder()
                .width(480).height(320)
                .title("Forma de Onda (no escalada)")
                .xAxisTitle("tiempo").yAxisTitle("amplitud").build();

        graficoAmplitud.getStyler().setLegendPosition(Styler.LegendPosition.InsideN);
        XYSeries serie = graficoAmplitud.addSeries("a(t)", amplitud.x, amplitud.y);
        serie.setMarker(SeriesMarkers.NONE);



        final XYChart graficoFrecuencias = new XYChartBuilder()
                .width(480).height(320)
                .title("Frecuencias")
                .xAxisTitle("frecuencia").yAxisTitle("dB").build();

        graficoFrecuencias.getStyler().setLegendPosition(Styler.LegendPosition.InsideN);
        serie = graficoFrecuencias.addSeries("f(t)", frecuencias.x, frecuencias.y);
        serie.setMarker(SeriesMarkers.NONE);

        // Programe un trabajo para el hilo de envío de eventos:
        // Crear y mostrar la GUI de esta aplicación.
        javax.swing.SwingUtilities.invokeLater(() -> {

            // Crear el formulario.
            JFrame frame = new JFrame("Procesamiento de Senales Digitales (FFT)");
            frame.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Amplitud
            JPanel panelAmplitud = new XChartPanel<>(graficoAmplitud);
            frame.add(panelAmplitud, BorderLayout.BEFORE_FIRST_LINE);

            // Frecuencia
            JPanel panelFrecuencia = new XChartPanel<>(graficoFrecuencias);
            frame.add(panelFrecuencia, BorderLayout.AFTER_LAST_LINE);

            // Mostrar formulario.
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }

}
