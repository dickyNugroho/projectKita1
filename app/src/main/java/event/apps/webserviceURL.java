package event.apps;

/**
 * Created by rejak on 3/22/2017.
 */

public class webserviceURL {
    public static final String baseurl = "http://192.168.43.78/event_api/";
    public static final String get_login = baseurl+"Login/get_login_id";
    public static final String register = baseurl +"Login/create_client";
    public  static final String listevent = baseurl +"makeEvent/all_event";
    public  static final String listeventkat = baseurl +"makeEvent/all_event_kat";
    public static final String makeEvent = baseurl + "makeEvent/create_event";
    public static final String pesan = baseurl + "tiket/create_tiket";
    public static final String listfav = baseurl + "Favorit/listfav";
    public static final String P_image = baseurl + "assets/";
    public static final String img = baseurl + "assets/Event_img/";
    public static final String favorit =baseurl + "Favorit/create_favorit";
    public static final String listtiket =baseurl +"tiket/listtiket";
    public static final String delfav =baseurl +"Favorit/delete_fav";
    public static final String showProfile = baseurl + "Client/all_client/";
    public static final String editProfile=baseurl +"Client/change_profile";
    public static final String caridata = baseurl + "makeEvent/search_key";
    public static final String userevent = baseurl + "makeEvent/get_event_user/";
    public static final String kategori = baseurl + "Kategori/all_kategori";
    public static final String carikat = baseurl + "makeEvent/search_key_kat";
    public static final String jumdaf = baseurl + "makeEvent/edit_event";


}
