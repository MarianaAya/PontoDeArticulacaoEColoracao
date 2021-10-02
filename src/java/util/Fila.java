
package util;


public class Fila {
    private NoFila inicio;

    public Fila() {
        inicio=null;
    }
    public void enqueue(Vertice v){
        NoFila novo=new NoFila(v),aux,ant;
        if(inicio==null){
            inicio=novo;
         
        }
        else{
            if(inicio.getV().getGrau()<v.getGrau()){
                novo.setProx(inicio);
                inicio=novo;
            }
            else{
                aux=inicio;
                ant=inicio;
                while(aux.getProx()!=null && aux.getV().getGrau()>=v.getGrau()){
                    ant=aux;
                    aux=aux.getProx();
                }
                if(aux.getV().getGrau()<v.getGrau()){
                    ant.setProx(novo);
                    novo.setProx(aux);
               
                }
                else{
                    novo.setProx(aux.getProx());
                    aux.setProx(novo);
                }
            }
            
        }
    }
    public boolean procurarVertice(int v){
        NoFila aux=inicio;
        boolean achou=false;
        while(aux!=null && !achou){
            if(aux.getV().getNum()==v)
                achou=true;
            aux=aux.getProx();
        }
        return achou;
    }
    public boolean isEmpty(){
        return inicio==null;
    }
    public NoFila dequeue(){
        NoFila aux=null;
        if(inicio!=null){
            aux=inicio;
            inicio=inicio.getProx();
        }
        return aux;
    }
    public NoFila getInicio(){
        return inicio;
    }
}
