
package util;

import java.util.ArrayList;
import java.util.List;


public class RetornoColoracao {
    private List<List<Integer>> listaFila;
    private List<List<List<Integer>>> tabelas;
    private int n;;
    private int id=0;
    public RetornoColoracao(int n) {
        listaFila=new ArrayList<>(n);
        for(int i=0;i<n;i++)
            listaFila.add(new ArrayList<>());
        tabelas = new ArrayList<>(n);
       
        this.n=n;
        this.id=0;
    }

    public List<List<List<Integer>>> getTabelas() {
        return tabelas;
    }

    public void setTabelas(List<List<List<Integer>>> tabelas) {
        this.tabelas = tabelas;
    }
    
    
    
    public List<List<Integer>> getListaFila() {
        return listaFila;
    }

    public void setListaFila(List<List<Integer>> listaFila) {
        this.listaFila = listaFila;
    }
    public void exibirListaFila(){
        for(int i=0;i<n;i++){
            System.out.println(i+" "+listaFila.get(i));
        }
    }
    public void inserirLista(Fila f){
        NoFila nf=f.getInicio();
        
        while(nf!=null){
         
            listaFila.get(id).add(nf.getV().getNum());
            nf=nf.getProx();
        }
        id++;
    }
    public void inserirListaTabela(List<List<Integer>> tab){
        List<List<Integer>> tab2=new ArrayList<>(n);
        for(int i=0;i<tab.size();i++){
            tab2.add(new ArrayList<>(n));
         
            for(int j=0;j<tab.get(i).size();j++){
                
                tab2.get(i).add(tab.get(i).get(j));
            }
        }
        
     
        tabelas.add(tab2);
    }
    public void exibirTabelas(){
        for(int i=0;i<n;i++){
            System.out.println(i+" "+tabelas.get(i));
        }
    }


    public String toStringFila() {
        /*
        return "RetornoColoracao{" + "listaFila=" + listaFila + ", tabelas=" + tabelas + '}';*/
        return this.listaFila + "";
    }
    public String toStringTabela() {
        /*
        return "RetornoColoracao{" + "listaFila=" + listaFila + ", tabelas=" + tabelas + '}';*/
        return this.tabelas + "";
    }
    
    
    
}
