package event.apps.Home;

/**
 * Created by rejak on 4/10/2017.
 */

public class kategori {
    private String nama,icon,color;
    private int jumlah,id;

    public kategori(){}

    public kategori(int id,String nama,String icon,String color,int jumlah){
        this.nama=nama;
        this.icon=icon;
        this.color=color;
        this.jumlah=jumlah;
        this.id=id;
    }

    public int getid(){return id;}
    public void setid(int id){this.id=id;}

    public String getnama(){return nama;}
    public void setnama(String nama){this.nama=nama;}

    public String geticon(){return icon;}
    public void seticon(String icon){this.icon=icon;}

    public String getcolor(){return color;}
    public void setcolor(String color){this.color=color;}

    public int getjumlah(){return jumlah;}
    public void setjumlah(int jumlah){this.jumlah=jumlah;}

}
