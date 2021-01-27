package com.amar.sample.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.sample.R;
import com.amar.sample.activity.ActivityDetailPesananBaru;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.model.ModelProduk;
import com.amar.sample.util.Server;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterDaftarPesanan extends RecyclerView.Adapter<AdapterDaftarPesanan.AdapterPdodukViewHolder> {


    private ArrayList<ModelDaftarProduk> dataList;
    private Activity activity;

    public AdapterDaftarPesanan(Activity activity, ArrayList<ModelDaftarProduk> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @Override
    public AdapterPdodukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_adapter_daftar_pesanan, parent, false);
        return new AdapterPdodukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPdodukViewHolder holder, int position) {
        final ModelDaftarProduk item = dataList.get(position);


        holder.txt_nopesanan.setText(dataList.get(position).getItem2());
        holder.txt_tglpesanan.setText(dataList.get(position).getItem3());
        holder.jam_dari.setText(dataList.get(position).getItem4());
        holder.jam_sampai.setText(dataList.get(position).getItem5());

        final Gson gson = new Gson();
        holder.cr_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ActivityDetailPesananBaru.class);
                i.putExtra(Server.EXTRA_BARANG, gson.toJson(item));
                activity.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AdapterPdodukViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_nopesanan, txt_tglpesanan, jam_dari, jam_sampai;
        private CardView cr_item;

        public AdapterPdodukViewHolder(View itemView) {
            super(itemView);
            txt_nopesanan = (TextView) itemView.findViewById(R.id.no_pesanan);
            txt_tglpesanan = (TextView) itemView.findViewById(R.id.tgl_pesanan);
            jam_dari = (TextView) itemView.findViewById(R.id.jam_dari);
            jam_sampai = (TextView) itemView.findViewById(R.id.jam_sampai);
            cr_item = (CardView) itemView.findViewById(R.id.cr_item);

        }
    }
}

