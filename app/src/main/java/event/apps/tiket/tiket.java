package event.apps.tiket;

/**
 * Created by rejak on 4/8/2017.
 */

public class tiket {

    private String judul, subjudul, instansi, img, desk, id_usr,tanggal,code,nama;
    private int daftar, kuota, harga, id,verif;

    public tiket() {
    }

    public tiket(String Judul, String Subjudul, String Instansi, int Daftar, int Kuota, int Harga,
                 String Img, String desk, int id, String id_usr,int verif,String tanggal,String code,String nama) {
        this.judul = Judul;
        this.subjudul = Subjudul;
        this.instansi = Instansi;
        this.daftar = Daftar;
        this.kuota = Kuota;
        this.harga = Harga;
        this.img = Img;
        this.desk = desk;
        this.id = id;
        this.id_usr = id_usr;
        this.verif=verif;
        this.tanggal=tanggal;
        this.code=code;
        this.nama=nama;
    }

    public String getnama(){return nama;}

    public void setnama(String nama) { this.nama=nama;}

    public String gettgl() {
        return tanggal;
    }

    public void settgl(String tanggal) {
        this.tanggal = tanggal;
    }


    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getId_usr() {
        return id_usr;
    }

    public void setId_usr(String id_usr) {
        this.id_usr = id_usr;
    }

    public String getjudul() {
        return judul;
    }

    public void setjudul(String judul) {
        this.judul = judul;
    }

    public String getsubjudul() {
        return subjudul;
    }

    public void setsubjudul(String subjudul) {
        this.subjudul = subjudul;
    }

    public String getinstansi() {
        return instansi;
    }

    public void setinstansi(String instansi) {
        this.instansi = instansi;
    }

    public int getdaftar() {
        return daftar;
    }

    public void setdaftar(int daftar) {
        this.daftar = daftar;
    }

    public int getkuota() {
        return kuota;
    }

    public void setkuota(int kuota) {
        this.kuota = kuota;
    }

    public String getimg() {
        return img;
    }

    public void setimg(String img) {
        this.img = img;
    }

    public int getharga() {
        return harga;
    }

    public void setharga(int harga) {
        this.harga = harga;
    }

    public void setdesk(String desk) {
        this.desk = desk;
    }

    public String getdesk() {
        return desk;
    }

    public int getverif(){
        return verif;
    }
    public void setverif(int verif){
        this.verif=verif;
    }

    public String getqrcode(){return code;}
    public void setqrcode(String code){this.code=code;}


}