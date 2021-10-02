
package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Coloracao {
    private List<Vertice> grafo;
    private int n;
    private List<List<Integer>> tabela;
    private List<List<List<Integer>>> tabelas;
    private RetornoColoracao ret;
    public Coloracao(){
        
    }
    //buscao qual é a posição do vertice na lista
    public int buscarPosicaoDoVertice(int num){
        int i=0;
        while(i<n && grafo.get(i).getNum()<num)
            i++;
        return i;
    }
    //é nessa função que vou fazer a Coloração
    public void ColoracaoMinima(){
        boolean[] visitado=new boolean[n];
        NoFila nf;
        //crio a fila que será usada na coloração
        Fila f=new Fila();
        Vertice v;
        int i=0,pos,pos2;
        //pego o vertice da primeira posição
        v=grafo.get(0);
        //procura qual é o vertice que tem grau maior
        while(i<n){
            if(v.getGrau()<grafo.get(i).getGrau())
                v=grafo.get(i);
            i++;
        }
        //coloco o vertice de maior gray na fila
        f.enqueue(v);
        
        
        while(!f.isEmpty()){
            
            //olho o primeiro vertice da fila
            nf=f.getInicio();
            //percorro toda  a lista de vertices que estão ligados a ele
            for(Integer nv:nf.getV().getList()){
                if(!visitado[buscarPosicaoDoVertice(nv)]){//se ele não tiver sido visitado
                    if(!f.procurarVertice(nv)){//se ele não estiver na fila
                        pos2=buscarPosicaoDoVertice(nv);//procuro o vertice na lista de vertices
                        f.enqueue(grafo.get(pos2));//coloco o vertice na fila
                    }
                }
            }
            ret.inserirLista(f);//coloco em uma lista de fila que será exibido no front
            nf=f.dequeue();//tiro o primeiro vertice
            pos=buscarPosicaoDoVertice(nf.getV().getNum());//busco a posição dele na lista
     
            visitado[pos]=true;//com a posição encontrada, coloco que ele foi visitado
            marcarTabela(nf.getV(),pos);//olho qual será a cor dele
            
            
        }
        
    }
    public void marcarTabela(Vertice v,int pos){
        int i=0,aux,posX,j;
        boolean achou=false;
 
        
       //primeiro vejo se não existe um cor já existente que ele pode ser atribuido
        while(i<tabela.get(pos).size() && !achou){
            if(tabela.get(pos).get(i)==0)//encontrei uma cor
                achou=true;
            else  
                i++;
        }
        if(!achou){//nao encontrei uma cor existente, assim crio uma nova
         
            tabela.get(pos).add(0);
        }
        tabela.get(pos).set(i, i+1);//coloco a cor para o vertice
        for(Integer ver: v.getList()){//percorro a lista dos vertices que ele está ligado
            if(v.getNum()!=ver){//se não tiver o mesmo rotulo
                posX=buscarPosicaoDoVertice(ver);//procuro a posição do vertice na lista de vertices
                j=0;achou=false;

                while(j<tabela.get(posX).size() && !achou){//procuro a posição onde irá ser colocado que esse vertice não pode ter a mesma cor
                    if(j==i){//encontrou a posição onde terá que ser colocado -1 para dizer que ele não pode receber a mesma cor
                        tabela.get(posX).set(j, -1);
                        achou=true;
                    }
                    else
                        j++;
                }
                if(!achou){//significa que a lista não é longa o suficiente para encontrar a escolhida do vertice v

                    while(j<=i){//vou adicionando na lista até chegar a posição i
                        tabela.get(posX).add(0);
                        j++;
                    }
                    tabela.get(posX).set(i, -1);//chego na posição i e coloco -1 para dizer que ele não pode receber a mesma cor que vertice v
                }
            }
         
            
        }
        ret.inserirListaTabela(tabela);
        
    }
    
    public List<Vertice> criarGrafo(int n){
        List<Vertice> grafo = new ArrayList<>(n);
        for (int i = 0; i < n; i++) 
            grafo.add(new Vertice());
      
        return grafo;
    }
    public List<List<Integer>> criarTabela(int n){
        List<List<Integer>> tab = new ArrayList<>(n);
        for(int i=0;i<n;i++)
            tab.add(new ArrayList<>(n));
        return tab;
    }
    
    public void addEdge(List<Vertice> grafo, int from, int to) {
        int i=0,j;
        Vertice v;
        //------------- Vertice at----------------
        //procuro se já coloquei o vertice na lista 
        while(i<n && grafo.get(i).getNum()<from && grafo.get(i).getNum()!=-1){
            i++;
        }
        
        
        if(grafo.get(i).getNum()==from){//o vertice já existe na lista
            //adiciono um novo vértice que ele está ligado
            grafo.get(i).getList().add(to);
            //ordeno as vertices, para deixar na ordem crescente
            grafo.get(i).getList().sort(Comparator.naturalOrder());
        }
        else//não encontrei o vertice na lista
            if(grafo.get(i).getNum()==-1){//esse vertice tem o maior rótulo, assim coloco no fim
                grafo.get(i).setNum(from);
                //if(to!=-1)
                //adiciono o vertice que ele se liga
                grafo.get(i).getList().add(to);
            }
            else{//a posição encontrada está entre dois vertices
                j=0;
                while(j<n && grafo.get(j).getNum()!=-1)
                    j++;
                //mudo de posição as vertices, para encaixar o vertice que quero no lugar correto
                for(int x=j;x>i;x--){
                    v=new Vertice();
                    v.setNum(grafo.get(x-1).getNum());
                    v.setList(grafo.get(x-1).getList());
                    grafo.set(x, v);
                }
                //coloco o vertice
                grafo.get(i).setNum(from);
                grafo.get(i).setList(new ArrayList());
                if(to!=-1)
                grafo.get(i).getList().add(to);
            }
        
        //--------------Vertice to--------------------------------
        i=0;
        //procuro se ele está na lista
        while(i<n && grafo.get(i).getNum()<to && grafo.get(i).getNum()!=-1){
            i++;
        }
        
        if(grafo.get(i).getNum()==-1){//ele possui o maior rótulo
          
            grafo.get(i).setNum(to);
        }
        else{//vai ser colocado no lugar dele ideal, assim preciso movimentar os outros vertices
            
            if(grafo.get(i).getNum()>to){
                int x=i;
                while(x<n && grafo.get(x).getNum()!=-1)
                    x++;
                if(x<n){
                    for(int t=x;t>i;t--){
                        v=new Vertice();
                        v.setNum(grafo.get(t-1).getNum());
                        v.setList(grafo.get(t-1).getList());
                        grafo.set(t, v);
                    }
                    grafo.get(i).setNum(to);
                    grafo.get(i).setList(new ArrayList());
                }
            }
        }

    }
    public List<List<List<Integer>>> criarListaTabelas(int n){
        List<List<List<Integer>>> tab = new ArrayList<>(n);
        return tab;
    }
    public void teste(List<List<Integer>> lista){
        //crio o grafo com as listas vazias
        grafo = criarGrafo(n);
        //crio a tabela
        tabela=criarTabela(n);
        //crio a lista de tabelas que será mandada para o front
        tabelas=criarListaTabelas(n);
        //crio a estrutura que será enviada para o front
        ret=new RetornoColoracao(n);
        //coloco todos os vertices no grafo
        for(int i=0;i<lista.size();i++){
            addEdge(grafo, lista.get(i).get(0), lista.get(i).get(1));
        }
 
        ColoracaoMinima();
    }
    //essa função vai ser chamada no servlet
    public RetornoColoracao getRetorno(List<List<Integer>> lista, int qtdeVertice){
        n=qtdeVertice;
        
        
        teste(lista);
        
        
        return ret;
    }
}
