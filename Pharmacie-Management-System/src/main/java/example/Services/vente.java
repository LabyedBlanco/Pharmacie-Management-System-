package example.Services;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class vente {

    private float prixv;
    private String date;
    private int idcl;
    private int idca;
    private int idu;
    private String methodepaymentv;
    private String categ;
    private String code;
    private String med;
    private int qua;

    public vente(){

    }
    public vente(float prixv,String date,int idcl,int idca,int idu,String methodepaymentv,String categ,String code,String med,int qua){
        this.prixv=prixv;
        this.date=date;
        this.idcl=idcl;
        this.idca=idca;
        this.idu=idu;
        this.methodepaymentv=methodepaymentv;
        this.categ=categ;
        this.code=code;
        this.med=med;
        this.qua=qua;
    }

    public void setprixv(float prixv){this.prixv=prixv;}
    public void setdate(String date){this.date=date;}
    public void setidcl(int idcl){this.idcl=idcl;}
    public void idca(int idca){this.idca=idca;}
    public void setidu(int idu){this.idu=idu;}
    public void setmethod(String method){this.methodepaymentv=method;}
    public void setcateg(String categ){this.categ=categ;}
    public void setcode(String code){this.code=code;}
    public void setmed(String med){this.med=med;}
    public void setqua(int qua){this.qua=qua;}


    public float getprixv(){return this.prixv;}
    public String getdate(){return this.date;}
    public int getidcl(){return this.idcl;}
    public int getidca(){return this.idca;}
    public int getidu(){return this.idu;}
    public String getMethod(){return this.methodepaymentv;}
    public String getcateg(){return this.categ;}
    public String getcode(){return this.code;}
    public String getmed(){return this.med;}
    public int getqua(){return this.qua;}
}
