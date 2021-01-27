package com.amar.sample.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.sample.ActivityFromTambahProduk;
import com.amar.sample.R;
import com.amar.sample.activity.ActivityBuatStruk;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.model.ModelProduk;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;


public class AdapterProdukTambah extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int HEADER = 90;

    private Activity activity;
    private List<ModelDaftarProduk> listMerchant;

    public AdapterProdukTambah(Activity activity, List<ModelDaftarProduk> listMerchant){
        this.activity = activity;
        this.listMerchant = listMerchant;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEADER;
        }
        else{
            return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == HEADER){
            return new HeaderViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_adapter_tambah, viewGroup, false));
        }
        else{
            return new MerchantPopulerViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_adapter_barang_struk, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof MerchantPopulerViewHolder){
            MerchantPopulerViewHolder holder = (MerchantPopulerViewHolder) viewHolder ;

            final ModelDaftarProduk item = listMerchant.get(i - 1);

            //holder.txt_satuan.setText(item.getItem6());
            holder.txt_jumlah.setText(item.getItem4()+" "+ item.getItem6());
            holder.nama_produk.setText(item.getItem3());
            Integer harga = Integer.parseInt(item.getItem5());
            holder.txt_harga.setText(NumberFormat.getCurrencyInstance().format(harga));


            if (item.getItem8().toLowerCase().trim().equals("0")){
                holder.ln_bg.setBackgroundColor(activity.getResources().getColor(R.color.grey_text));
            }

            ActivityBuatStruk.totalBRG(item.getItem9());

            holder.img_hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivityBuatStruk)activity).HapusBarang(item.getItem1(), 2);
                }
            });


            /*final Gson gson = new Gson();
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(activity, DetailActivityBarang.class);
                    i.putExtra(Constant.EXTRA_BARANG, gson.toJson(item));
                    activity.startActivity(i);

                }
            });*/

        } else if (viewHolder instanceof HeaderViewHolder){
            HeaderViewHolder head = (HeaderViewHolder) viewHolder ;


            head.btn_tambah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ActivityBuatStruk)activity).InputBarangRecomend();
                }
            });
            head.btn_tambah.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listMerchant.size() + 1;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{

        ImageView img_header;
        TextView LihatSemua;
        CardView btn_tambah;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
           // img_header = itemView.findViewById(R.id.iv_cardview);
           // LihatSemua = itemView.findViewById(R.id.lihat_semua);
            btn_tambah = itemView.findViewById(R.id.cr_btn_tambah);
        }
    }


    class MerchantPopulerViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_satuan, txt_jumlah, nama_produk, txt_harga;
        private CardView cr_item;
        private ImageView img_hapus;
        private LinearLayout ln_bg;

        MerchantPopulerViewHolder(@NonNull View itemView) {
            super(itemView);
            //txt_satuan = (TextView) itemView.findViewById(R.id.txt_satuan);
            txt_jumlah = (TextView) itemView.findViewById(R.id.txt_jumlah);
            txt_harga = (TextView) itemView.findViewById(R.id.txt_harga);
            nama_produk = (TextView) itemView.findViewById(R.id.txt_nama_produk);
            img_hapus = (ImageView) itemView.findViewById(R.id.hapus);
            ln_bg = (LinearLayout) itemView.findViewById(R.id.head);
        }
    }
}
