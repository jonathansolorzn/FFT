import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Lista.Lista;

class DSP {

    DSP(Lista amplitud, Lista frecuencias) {

        final XYChart graficoAmplitud = new XYChartBuilder()
                .width(480).height(320)
                .title("Forma de Onda (no escalada)")
                .xAxisTitle("tiempo").yAxisTitle("amplitud").build();

        List<List<Double>> amplitudXY = amplitud.listar();

        graficoAmplitud.getStyler().setLegendPosition(Styler.LegendPosition.InsideN);
        XYSeries serie = graficoAmplitud.addSeries("a(t)", amplitudXY.get(0), amplitudXY.get(1));
        serie.setMarker(SeriesMarkers.NONE);



        final XYChart graficoFrecuencias = new XYChartBuilder()
                .width(480).height(320)
                .title("Frecuencias")
                .xAxisTitle("frecuencia").yAxisTitle("dB").build();

        List<List<Double>> frecuenciasXY = frecuencias.listar();

        graficoFrecuencias.getStyler().setLegendPosition(Styler.LegendPosition.InsideN);
        serie = graficoFrecuencias.addSeries("f(t)", frecuenciasXY.get(0), frecuenciasXY.get(1));
        serie.setMarker(SeriesMarkers.NONE);

        // Programa un trabajo para el hilo de envío de eventos:
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
