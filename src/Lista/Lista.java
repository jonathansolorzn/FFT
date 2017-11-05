package Lista;

import java.util.ArrayList;
import java.util.List;

public class Lista {

    Nodo inicio;
    Nodo fin;

    public Lista() {
        inicio = null;
        fin = null;
    }

    public void insertarFin(double x, double y) {
        Nodo nuevo = new Nodo(x, y, null);

        if (inicio == null) {
            fin = nuevo;
            inicio = fin;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }

    public double[] extraerInicio() {
        double x = inicio.getX();
        double y = inicio.getY();

        inicio = inicio.getSiguiente();
        if (inicio == null){
            fin = null;
            return null;
        }

        return new double[]{x, y};
    }

    public List<List<Double>> listar(){

        Nodo temp = inicio;
        List<List<Double>> xy = new ArrayList<>();
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        while(temp != null){
            x.add(temp.getX());
            y.add(temp.getY());
            temp = temp.siguiente;
        }
        xy.add(x);
        xy.add(y);

        return xy;
    }

}
