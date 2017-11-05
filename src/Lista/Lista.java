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

    // Metodo para insertar un nodo al final de una lista
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

    // Metodo para listar el contenido de los nodos
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
