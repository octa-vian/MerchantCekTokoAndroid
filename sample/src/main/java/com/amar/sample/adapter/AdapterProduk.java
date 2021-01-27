package com.amar.sample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.sample.R;
import com.amar.sample.model.ModelProduk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProduk extends RecyclerView.Adapter<AdapterProduk.AdapterPdodukViewHolder> {


    private ArrayList<ModelProduk> dataList;

    public AdapterProduk(ArrayList<ModelProduk> dataList) {
        this.dataList = dataList;
    }

    @Override
    public AdapterPdodukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_adapter_produk, parent, false);
        return new AdapterPdodukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPdodukViewHolder holder, int position) {
        holder.txtNama.setText(dataList.get(position).getNama());
        holder.txtNpm.setText(dataList.get(position).getNpm());
        holder.txtNoHp.setText(dataList.get(position).getNohp());
        Picasso.get().load(dataList.get(position).getGambar()).into(holder.img_produk);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AdapterPdodukViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtNpm, txtNoHp;
        private ImageView img_produk;

        public AdapterPdodukViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama_brg);
            txtNpm = (TextView) itemView.findViewById(R.id.txt_harga);
            txtNoHp = (TextView) itemView.findViewById(R.id.status);
            img_produk = (ImageView) itemView.findViewById(R.id.iv_cardview);

        }
    }
}

