
package util;

import java.util.ArrayList;
import java.util.List;


public class Vertice {
    private int num;
    private List<Integer> list;
    private int id;

    public Vertice(int num) {
        this.num = num;
        this.list=new ArrayList();
    
    }
    public Vertice() {
        this.num = -1;
        this.list=new ArrayList();
       
    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
    public int getGrau(){
        return list.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
