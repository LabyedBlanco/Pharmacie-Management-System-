package example.Services;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class vente {

    private int idv;
    private float prix;
    private Date date;
    private int idcl;
    private int idca;
    private int idu;
    private String methodepaymentv;
    private int qua;
    private String med;
    private String categ;
    private float totprix;

    public vente(){

    }
    public vente(int idv,float prix,Date date,int idcl,int idca,int idu,String methodepaymentv,int qua,String categ,String med,int idprod){
        this.date=date;
        this.idv=idv;
        this.idcl=idcl;
        this.idca=idca;
        this.idu=idu;
        this.methodepaymentv=methodepaymentv;
        this.qua=qua;
        this.categ=categ;
        this.med=med;
        this.prix=prix;

    }

    public void setdate(Date date){this.date=date;}
    public void setidcl(int idcl){this.idcl=idcl;}
    public void setidca(int idca){this.idca=idca;}
    public void setidu(int idu){this.idu=idu;}
    public void setmethod(String method){this.methodepaymentv=method;}
    public void setcateg(String categ){this.categ=categ;}
    public void setmed(String med){this.med=med;}
    public void setqua(int qua){this.qua=qua;}
    public void setprix(float prix){this.prix=prix;}


    public int getidv(){return this.idv;}
    public void setidv(int idv){this.idv=idv;}

    public float getprix(){return this.prix;}
    public Date getdate(){return this.date;}
    public int getidcl(){return this.idcl;}
    public int getidca(){return this.idca;}
    public int getidu(){return this.idu;}
    public String getMethod(){return this.methodepaymentv;}
    public String getcateg(){return this.categ;}
    public String getmed(){return this.med;}
    public int getqua(){return this.qua;}
    public float gettotal(){return this.totprix;}



    public void calculerprix(){
        this.totprix=prix*qua;
    }
}
