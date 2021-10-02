
package util;


public class NoFila {
    private Vertice v;
    private NoFila prox;

    public NoFila(Vertice v, NoFila prox) {
        this.v = v;
        this.prox = prox;
    }

    public NoFila(Vertice v) {
        this.v=v;
        this.prox=null;
    }

    public Vertice getV() {
        return v;
    }

    public void setV(Vertice v) {
        this.v = v;
    }

    public NoFila getProx() {
        return prox;
    }

    public void setProx(NoFila prox) {
        this.prox = prox;
    }
}
