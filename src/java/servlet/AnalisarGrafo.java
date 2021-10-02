
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Coloracao;
import util.PontoDeArticulacao;
import util.RetornoColoracao;


@WebServlet(name = "AnalisarGrafo", urlPatterns = {"/AnalisarGrafo"})
public class AnalisarGrafo extends HttpServlet {

 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Coloracao c;
        RetornoColoracao r;
        String acao = request.getParameter("acao");
        
        
        String vertices=request.getParameter("vertices");
      
        String[] partes=vertices.split(",");
        //crio a lista para guardar os vertices
        List<List<Integer>> listaV=new ArrayList<>((int)partes.length/3);
        //pego qual é o tipo do grafo
        String tipo=request.getParameter("tipo");
        int tam=0;
        if(tipo.equals("grafo"))//significa que vai haver um espelhamento, assim preciso de duas vezes mais de tamanho
            tam=(int)(partes.length/3)*2;
        else
            tam=(int)partes.length/3;
        for(int i=0;i<tam;i++)//em cada posição de listaV vai ter uma aresta, que é uma lista de dois números de vértice
            listaV.add(new ArrayList<>((int)partes.length/3));
        
        int id=0;

        if(partes.length>1){//se existe vertices que veio do front
            for(int i=0;i<partes.length;i+=3){
                listaV.get(id).add(Integer.parseInt(partes[i+1]));
                listaV.get(id).add(Integer.parseInt(partes[i+2]));
                id++;
                if(tipo.equals("grafo")){//se não for digrafo, faz o espelhamento
                    listaV.get(id).add(Integer.parseInt(partes[i+2]));
                    listaV.get(id).add(Integer.parseInt(partes[i+1]));
                    id++;
                }
            }
        }
        
        //Esse código serve para contar a quantidade de vertices que existe
        int qtdev=0,x=0;
        List<Integer> list=new ArrayList<>(tam);

        for(int i=0;i<listaV.size();i++){
            for(int j=0;j<listaV.get(i).size();j++){
                x=0;
                while(x<list.size() && listaV.get(i).get(j)!=list.get(x))
                    x++;
                if(x==list.size()){
                    list.add(listaV.get(i).get(j));
                }
            }
            
        }
        
        switch (acao.toLowerCase()) {
            case "pontodearticulacao":
                            
                               PontoDeArticulacao p=new PontoDeArticulacao();
                               //mando a lista e a quantidade de vertices
                               p.encontrarPontoDeArticulacao(listaV,list.size());
                            
                    break;
            case "coloracaofila": 
                                c =new Coloracao();
                                //mando a lista e a quantidade de vertices
                                r=c.getRetorno(listaV,list.size());
                                
                              response.getWriter().print(r.toStringFila());
                    break;
            case "coloracaotabelas": c =new Coloracao();
                                //mando a lista e a quantidade de vertices
                                r=c.getRetorno(listaV,list.size());
                                
                                response.getWriter().print(r.toStringTabela());
                    break;
               
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
