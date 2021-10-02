
package util;

import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class PontoDeArticulacao {
    
    private int n, id;
    private boolean solved;
    private int[] menor, vertice_possivel,no;
    private boolean[] visited, isArticulationPoint;
    private List<Vertice> grafo,grafo2;

    public PontoDeArticulacao() {
    }
  
 
  

    public boolean[] procurarPontosDeArticulação() {
        int posTo,i=0;
        boolean condicao;
        //crio a árvore geradora
        grafo2 = criarGrafo(n);
        
        id = 0;
        menor = new int[n];
        vertice_possivel = new int[n]; 
        visited = new boolean[n];
        isArticulationPoint = new boolean[n];
        
        //percorro o grafo, usando a busca em profundidade
        dfs(grafo.get(0));
        adicionarAsFolhas();
        arestaPontilhada();
        posOrdem(grafo2.get(0),grafo2.get(0));
        System.out.println("----------------Grafo 2-------------------------------");
        for(i=0;i<grafo2.size();i++){
            System.out.println("Vertice: "+grafo2.get(i).getNum()+" ID: "+grafo2.get(i).getId()+" "+grafo2.get(i).getList());
            System.out.println("Vertice Possivel: "+vertice_possivel[i]);
            System.out.println("Menor: "+menor[i]);
            System.out.println("\n");
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("------------Verificando Ponto---------------");
        if(grafo2.get(0).getList().size()==1){//se a raiz tem só um filho , não preciso testa-la
            i=1;
        }
        for(;i<n;i++){
            System.out.println("Vertice: "+grafo2.get(i).getNum());
            for(Integer to: grafo2.get(i).getList()){//percorro toda a lista de vertice ligadas a ele
                if(to!=-1 && grafo2.get(i).getId()<grafo2.get(buscarPosicaoDoVertice(to)).getId()){
                    posTo=buscarPosicaoDoVertice(to);
                    condicao=false;
                    if(grafo2.get(i).getId()<=menor[posTo]){//se o menor de posTo é maior que o preNum de i, é um ponto de articulação
                        isArticulationPoint[i]=true;
                        condicao=true;
                    }
                    System.out.println("PreNum("+grafo2.get(i).getNum()+")="+grafo2.get(i).getId()+
                            "   Menor("+grafo2.get(posTo).getNum()+")="+menor[posTo]+"  "+condicao);
                    
                }
            }
        }
        return isArticulationPoint;
       
    }
    
    //percorrer o grafo usando a busca em profundidade 
    private void dfs(Vertice at){
        int posTo,posAt;
        List<Integer> l;
        //coloque o vertice at foi visitado
        visited[buscarPosicaoDoVertice(at.getNum())]=true;
        
        //esse id é a ordem de visita
        at.setId(++id);
        //procuro a posição do vertice at na lista de vertice
        posAt=buscarPosicaoDoVertice(at.getNum());
        
        //percorro a lista do vertice at, que são os vertices com quem ele se liga
        for(Integer to: at.getList()){ 
            if(!visited[buscarPosicaoDoVertice(to)]){//se o vertice ainda não foi visitado
                //adiciono na arvore geradora a ligação do at para o to
                addEdge(grafo2,at.getNum(),to);
                //busco na lista de vertices a posição do to
                posTo=buscarPosicaoDoVertice(to);
                //pego o vertice na posicao do porTo
                l=grafo.get(posTo).getList();
                int pos=0;
                //procuro se no vertice de posTo, existe uma ligação para o vertice at
                while(pos<l.size() && l.get(pos)!=at.getNum())
                    pos++;
                if(pos<l.size()){//existe uma ligação, assim coloco a aresta na arvore geradora (provavelmente significa que não é im digrafo)
                    addEdge(grafo2,to,at.getNum());
                     
                }
                //agora vai para o vertice do to
                posTo=buscarPosicaoDoVertice(to);
                dfs(grafo.get(posTo)); 
  
            }
           
        }
        
    }
    private void adicionarAsFolhas(){
        List<Integer> folhas=new ArrayList<>(n);
   
        int j;
        for(int i=0;i<n;i++){//percorrendo toda a lista de vertices
            j=0;
            while(j<grafo2.size() && grafo.get(i).getNum()!=grafo2.get(j).getNum())
                j++;
            if(j==grafo2.size()){
                folhas.add(grafo.get(i).getNum());
            }
        }
        
        for(int i=0;i<folhas.size();i++)
            addEdge(grafo2,folhas.get(i),-1);//-1 para representar que a ligação é null
        
        Ordem();
    }
    private void Ordem(){//coloco a ordem de visita
        for(int i=0;i<n;i++){
            grafo2.get(i).setId(grafo.get(i).getId());
        }
    }
    //vou fazer para colocar o valor menor em todos os vertices
    private void posOrdem(Vertice root,Vertice at){
        int posTo,posAt,auxMenor;
        posAt=buscarPosicaoDoVertice(at.getNum());
        if(at.getList().size()>0){//vejo se a lista de vertices que estão ligados a ele, tem alguem
            for(Integer to:at.getList()){//percorro a lista de vertices com que ele está ligado
                posTo=buscarPosicaoDoVertice(to);//procuro a posição do vertice de to
                if(grafo2.get(posAt).getId()>grafo2.get(posTo).getId()){//se a ordem de visita do vertice posAt é maior que o de posTo, significa que estou na folha
                    
                    auxMenor=menor[posAt];
               
                    if(vertice_possivel[posAt]==0)//se não existe arestas pontilhadas o menor é a ordem de visita
                        menor[posAt]=at.getId();
                    else//se existe a aresta pontilhada, vejo qual é menor a ordem de visita ou a do vertice da aresta pontilhada
                        menor[posAt]=Math.min(at.getId(), vertice_possivel[posAt]);
                    
                    if(auxMenor!=0)
                        menor[posAt]=Math.min(auxMenor,menor[posAt]);
                    System.out.println("----------No if---------------");

                    System.out.println("Vertice At="+grafo2.get(posAt).getNum()+" Menor="+menor[posAt]);
                    System.out.println("-----------------------------");
                }     
                else{



                    posOrdem(root,grafo2.get(posTo));
                    
                    auxMenor=menor[posAt];
                    System.out.println("menor = "+menor[posAt]);
                    if(vertice_possivel[posAt]==0){//se não existe arestas pontilhadas, pego o menor da ordem de visita ou o menor do filho
                        menor[posAt]=Math.min(at.getId(), menor[posTo]);

                    }
                    else{//se existe a aresta pontilhada, primeiro vejo a ordem de visita e a aresta pontilhada, depois vejo em relação ao menor do filho

                        menor[posAt]=Math.min(grafo2.get(posAt).getId(),vertice_possivel[posAt]);
                        menor[posAt]=Math.min(menor[posAt],menor[posTo]);

                    }
                    if(auxMenor>0){
                        menor[posAt]=Math.min(auxMenor, menor[posAt]);
                    }

                  
                    System.out.println("Vertice To="+grafo2.get(posTo).getNum()+" Menor="+menor[posTo]);
                    System.out.println("Vertice At="+grafo2.get(posAt).getNum()+" Menor="+menor[posAt]);
                    System.out.println("******************");



                }

            }
        }
        else{//se ele não ir para ninguem, entao o valor menor dele é decidido
            if(vertice_possivel[posAt]==0)
                menor[posAt]=at.getId();
            else
                menor[posAt]=Math.min(at.getId(), vertice_possivel[posAt]);
        }
        
    }
    
    //para colocar o valor dos vertices que existiam no grafo original, mas não existe na arvore geradora
    private void arestaPontilhada(){
        int j,posTo;
        //percorro em todos os vertices da lista de vertices
        for(int i=0;i<n;i++){
            for(Integer to:grafo.get(i).getList()){//percorro a lista de vertices ligados ao vertice( na posição i) 
                j=0;
                while(j<grafo2.get(i).getList().size() && grafo2.get(i).getList().get(j)!=to)//vou percorrendo a lista dos vertices ligados até encontrar o vertice to
                    j++;
                if(j==grafo2.get(i).getList().size()){//não encontrei a aresta na arvore geradora
              
                    posTo=buscarPosicaoDoVertice(to);//busco a posição do vertice
                    if(vertice_possivel[i]==0){//é a primeira aresta pontilhada
                        
                        vertice_possivel[i]=grafo2.get(posTo).getId();
                    }
                    else//preciso ver qual é a que possui o menor valor da orde de visita
                        if(vertice_possivel[i]>grafo2.get(posTo).getId())
                            vertice_possivel[i]=grafo2.get(posTo).getId();
                    
                    if(vertice_possivel[posTo]==0)
                        vertice_possivel[posTo]=grafo2.get(i).getId();
                    else{
                        if(vertice_possivel[posTo]>grafo2.get(i).getId())
                            vertice_possivel[posTo]=grafo2.get(i).getId();
                    }
                }
            }
        }
    }
  
    
    private int buscarPosicaoDoVertice(int num){
        int i=0;
        while(i<n && grafo.get(i).getNum()<num)
            i++;
        return i;
    }


  //inicioalizar grafos
    public static List<Vertice> criarGrafo(int n) {
      List<Vertice> graph = new ArrayList<>(n);
      for (int i = 0; i < n; i++) 
          graph.add(new Vertice());
      return graph;
    }

  //adicionar aresta
    public void addEdge(List<Vertice> grafo, int from, int to) {
        int i=0,j;
        Vertice v;
        while(i<n && grafo.get(i).getNum()<from && grafo.get(i).getNum()!=-1){
            i++;
        }
        if(grafo.get(i).getNum()==from){

            grafo.get(i).getList().add(to);
            grafo.get(i).getList().sort(Comparator.naturalOrder());
        }
        else
            if(grafo.get(i).getNum()==-1){
                grafo.get(i).setNum(from);
                grafo.get(i).getList().add(to);
            }
            else{
                j=0;
                while(j<n && grafo.get(j).getNum()!=-1)
                    j++;
                for(int x=j;x>i;x--){
                    v=new Vertice();
                    v.setNum(grafo.get(x-1).getNum());
                    v.setList(grafo.get(x-1).getList());
                    grafo.set(x, v);
                }
                grafo.get(i).setNum(from);
                grafo.get(i).setList(new ArrayList());
                grafo.get(i).getList().add(to);
            }
        
        i=0;
        while(i<n && grafo.get(i).getNum()<to && grafo.get(i).getNum()!=-1){
            i++;
        }
       
        if(grafo.get(i).getNum()==-1){
          
            grafo.get(i).setNum(to);
        }
        else{
            
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

  

  

   
    public void encontrarPontoDeArticulacao(List<List<Integer>> lista,int qtdeVertice){
        n = qtdeVertice;
        //crio o grafo
        grafo = criarGrafo(n);
        //adiciona as arestas
        for(int i=0;i<lista.size();i++){
            addEdge(grafo, lista.get(i).get(0), lista.get(i).get(1));
        }
        procurarPontosDeArticulação();
       
        
        System.out.println("Pontos de Articulação: ");
        //percorrendo a lista de boolean, para dizer quais são os vertices de ponto de articulação
        for(int i=0;i<n;i++){
            if(isArticulationPoint[i])
            System.out.println(""+grafo2.get(i).getNum());
        }
    }

  // Tests a graph with 3 nodes in a line: A - B - C
  // Only node 'B' should be an articulation point.
}
