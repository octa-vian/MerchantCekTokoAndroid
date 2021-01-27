package com.amar.sample.util;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Server {

    //Header Request
    public final static Map<String, String> HEADER_AUTH = new HashMap<String, String>(){{
            put("Auth-Key", "gmedia_kartikars");
            put("Client-Service", "front_end_client");
        }
    };

    //Token heaader dengan enkripsi
    public static Map<String, String> getTokenHeader(Context context){
        Map<String, String> header = new HashMap<>();
        header.put("Auth-Key", "gmedia_kartikars");
        header.put("Client-Service", "front_end_client");
        header.put("User-id", AppSharedPreferences.getUid(context));
        return header;
    }

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

   //Customer
   public static String Idcus = "";
   public static String flag_promo = "flag_promo";

   //type OTP
    public static final String Register = "register";
    public static final String ResetPass = "reset_pass";
    public static final String ResetPin = "reset_pin";


    public static final String TAG = "";


    //EXTRA
    public static final String EXTRA_BARANG = "barang";
    public static final String EXTRA_NOBUKTI = "nobukti";
    public static final String EXTRA_NILAI_PIUTANG = "nilaipiutang";


    //URL
    //private static final String baseURL = "http://gmedia.bz/kartikars/api/reseller/";
    private static final String baseURL = "http://gmedia.bz/cektoko/api/";

    public static final String URL_LOGIN = baseURL + "authentication/login_merchant";
    public static final String URL_VIEW_PROFILE = baseURL + "profile/profile_merchant";
    public static final String URL_NEW_PESANAN = baseURL + "transaksi/new_order";
    public static final String URL_DETAIL_PESANAN = baseURL + "transaksi/detail_transaksi";
    public static final String URL_DETAIL_BARANG_PESANAN = baseURL + "transaksi/detail_pesanan_baru";
    public static final String URL_BARANG_RECOMENDASI = baseURL + "transaksi/product_recomended_transaksi";
    public static final String URL_HAPUS_TRANSAKSI = baseURL + "transaksi/hapus_product_transaksi";
    public static final String URL_TAMBAH_PRODUK_RECOMEND = baseURL + "transaksi/add_rekomendasi_product_transaksi";
    public static final String URL_SATUAN_SPINNER = baseURL + "master/satuan";
    public static final String URL_KONFIRMASI_PEMBELI = baseURL + "transaksi/confirmation_order";
    public static final String URL_KIRIM = baseURL + "transaksi/process_new_order";
    public static final String URL_TOLAK_TRANSAKSI = baseURL + "transaksi/tolak_transaksi";
    public static final String URL_SIAP_KIRIM_PESANAN = baseURL + "transaksi/order_ready_toship";
    public static final String URL_KIRIM_PESANAN = baseURL + "transaksi/send_order";
    public static final String URL_LIST_DALAM_PENGIRIMAN = baseURL + "transaksi/delivery_orders";
    public static final String URL_LIST_ORDER_COMPLATE = baseURL + "transaksi/completed_order";
    public static final String URL_DETAIL_BARANG_BUAT_STRUK = baseURL + "transaksi/daftar_struk_produk";
    public static final String URL_UPDATE_PRODUCT_TRANSAKSi = baseURL + "transaksi/update_product_transaksi";






 // http://gmedia.bz/kartika/api/reseller/product/
 // filter_product?start=0&limit=12&keyword=eter&sort_by=terlaris&category=4&merk=2002&stock_status=null
 //inimas Fan yg bawah

    /*public static String getPathfromDrawable(int res_int){
        return Uri.parse("android.resource://"+ R.class.getPackage().getName()+"/" + res_int).toString();*/

}
