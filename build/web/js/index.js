function buscarPontoDeArticulacao(){
    if(vertices.length>0){
        const URL_TO_FETCH='AnalisarGrafo?acao=pontodearticulacao';
        var formData = new FormData();
        var tipo=document.getElementById("select-tipo").value;

        formData.append('vertices',vertices);
        formData.append('tipo',tipo)

        fetch(URL_TO_FETCH,{method:'post', body: formData}).then(function(response)
        {
            response.text().then(function(result)  //response é um promisse
            {




            });
        }).catch (function(err) {console.error(err);});
    }
}
function coloracao(){
    if(vertices.length>0){
        var resposta=document.getElementById("div-resposta");
        const URL_TO_FETCH='AnalisarGrafo?acao=coloracaofila';
        var obj_fila;
        var obj_tabelas;

        listarVertices();

        var formData = new FormData();
        var tipo=document.getElementById("select-tipo").value;

        formData.append('vertices',vertices);
        formData.append('tipo',tipo)
        fetch(URL_TO_FETCH,{method:'post', body: formData}).then(function(response)
        {
            response.text().then(function(result)  //response é um promisse
            {

                obj_fila=JSON.parse(result);
                fetch('AnalisarGrafo?acao=coloracaotabelas',{method:'post', body: formData}).then(function(response)
                {

                    response.text().then(function(result)  //response é um promisse
                    {

                        obj_tabelas=JSON.parse(result);
                        var resp="";




                        for(let i=0;i<obj_fila.length;i++){
                            if(i<obj_tabelas.length){
                                var tabela="";
                                tabela+="<table class='table-coloracao'><thead><tr>"
                                tabela+="<th>Vértice</th>"
                                for(let i=1;i<vet.length+1;i++){
                                    tabela+="<th>"+i+"</th>";
                                }
                                tabela+="</tr></thead>";
                                tabela+="<tbody>";

                                for(let x=0;x<obj_tabelas.length;x++){

                                    tabela+="<tr>"
                                    tabela+="<td>"+vet[x]+"</td>"
                                    for(let t=0;t<obj_tabelas[i][x].length;t++){
                                        if(obj_tabelas[i][x][t]!==-1)
                                            tabela+="<td>"+obj_tabelas[i][x][t]+"</td>"
                                        else
                                            tabela+="<td>x</td>"
                                    }
                                    tabela+="</tr>";
                                }
                                tabela+="</tbody></table>";

                                resp+="<div class='div-bloco'>"+
                                            "<div class='div-fila'>"+
                                                obj_fila[i]+

                                            "</div>"+  
                                            "<div class='div-tabela'>"+
                                                tabela+
                                            "</div>"+
                                      "</div>";
                            }
                        }
                        resposta.innerHTML=resp

                    });
                }).catch (function(err) {console.error(err);});


            });
        }).catch (function(err) {console.error(err);});
    }
    
}

var vertices=[];
var vet=[];
function verticeNaLista(v){
    let i=0;
    while(i<vet.length && v!==vet[i]){
        i++;
    }
    return i<vet.length;
}
function listarVertices(){
    vet=[];
    let aux;
    for(let i=0;i<vertices.length;i++){
        for(let j=1;j<vertices[i].length;j++){
            if(!verticeNaLista(parseInt(vertices[i][j])) && parseInt(vertices[i][j])!==-1)
                vet.push(parseInt(vertices[i][j]));
        }
    }
    for(let i=0;i<vet.length-1;i++){
        for(let j=i+1;j<vet.length;j++){
            if(vet[i]>vet[j]){
                aux=vet[i];
                vet[i]=vet[j]
                vet[j]=aux
            }
        }
    }
 
   
}
function addVertice(){
    var v1=document.getElementById("v1").value;
    var v2=document.getElementById("v2").value;
    var msg=document.getElementById("msg-erro")
    var Vcod;
    if(v1.length===0 || v2.length===0){
        
        msg.innerHTML="<p>Dado inválido</p>"
    }
    else{
        msg.innerHTML="";
        var Vcod=0;
            if(vertices.length>0)
                Vcod=vertices[vertices.length-1][0]+1;
        vertices.push([Vcod,v1,v2]);
        document.getElementById("corpoTabela").innerHTML+="<tr>"+
            "<td>"+vertices[vertices.length-1][1]+"</td>"+
            "<td>"+vertices[vertices.length-1][2]+"</td>"+

            "<td>"+
            "<button type='button' onClick='removerVertice("+vertices[vertices.length-1][0]+")'>Excluir</button>"+
            "</td>"+
            "</tr>";
    }
}
function removerVertice(codV){
    
    let i=0;
  
    while(i<vertices.length && vertices[i][0]!==codV){
        i++;
    }
    
    vertices=vertices.filter(vertices=>vertices[0]!==codV);
    
    MostrarVertices();
}
function MostrarVertices(){
    document.getElementById("corpoTabela").innerHTML="";
    for(var i=0;i<vertices.length;i++){
        document.getElementById("corpoTabela").innerHTML+="<tr>"+
        "<td>"+vertices[i][1]+"</td>"+
        "<td>"+vertices[i][2]+"</td>"+
        "<td>"+
        "<button type='button' class='btn btn-outline-danger' onClick='removerVertice("+vertices[i][0]+")'>Excluir</button>"+
        "</td>"+
        "</tr>";
    }
            
}


