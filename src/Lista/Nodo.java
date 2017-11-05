package Lista;

public class Nodo {

    private double x;
    private double y;
    Nodo siguiente;

    public Nodo(double x, double y, Nodo siguiente) {
        this.x = x;
        this.y = y;
        this.siguiente = siguiente;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
