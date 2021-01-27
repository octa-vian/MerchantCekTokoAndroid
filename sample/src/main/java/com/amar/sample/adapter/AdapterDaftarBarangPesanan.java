package com.amar.sample.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.sample.R;
import com.amar.sample.activity.ActivityDetailPesananBaru;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.util.Server;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterDaftarBarangPesanan extends RecyclerView.Adapter<AdapterDaftarBarangPesanan.AdapterPdodukViewHolder> {


    private ArrayList<ModelDaftarProduk> dataList;
    private Activity activity;

    public AdapterDaftarBarangPesanan(Activity activity, ArrayList<ModelDaftarProduk> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @Override
    public AdapterPdodukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_adapter_barang, parent, false);
        return new AdapterPdodukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPdodukViewHolder holder, int position) {
        final ModelDaftarProduk item = dataList.get(position);


        holder.txt_satuan.setText(dataList.get(position).getItem4() + " " + dataList.get(position).getItem6());
        holder.txt_jenis.setText(dataList.get(position).getItem3());
        Integer harga = Integer.parseInt(dataList.get(position).getItem5());
        holder.txt_harga.setText(NumberFormat.getCurrencyInstance().format(harga));

        /*final Gson gson = new Gson();
        holder.cr_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ActivityDetailPesananBaru.class);
                i.putExtra(Server.EXTRA_BARANG, gson.toJson(item));
                activity.startActivity(i);
            }
        });*/
    }



    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AdapterPdodukViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_satuan, txt_jenis, txt_harga;
        private CardView cr_item;

        public AdapterPdodukViewHolder(View itemView) {
            super(itemView);
            txt_satuan = (TextView) itemView.findViewById(R.id.txt_satuan);
            txt_jenis = (TextView) itemView.findViewById(R.id.txt_jenis);
            txt_harga = (TextView) itemView.findViewById(R.id.txt_harga);

        }
    }
}

