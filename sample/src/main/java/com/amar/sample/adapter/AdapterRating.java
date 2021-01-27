package com.amar.sample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amar.sample.R;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.model.ModelProduk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterDaftarPesanan extends RecyclerView.Adapter<AdapterDaftarPesanan.AdapterPdodukViewHolder> {


    private ArrayList<ModelDaftarProduk> dataList;

    public AdapterDaftarPesanan(ArrayList<ModelDaftarProduk> dataList) {
        this.dataList = dataList;
    }

    @Override
    public AdapterPdodukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_adapter_daftar_pesanan, parent, false);
        return new AdapterPdodukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPdodukViewHolder holder, int position) {
        holder.txt_nopesanan.setText(dataList.get(position).getItem1());
        holder.txt_tglpesanan.setText(dataList.get(position).getItem2());
        holder.txt_status.setText(dataList.get(position).getItem3());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AdapterPdodukViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_nopesanan, txt_tglpesanan, txt_status;

        public AdapterPdodukViewHolder(View itemView) {
            super(itemView);
            txt_nopesanan = (TextView) itemView.findViewById(R.id.no_pesanan);
            txt_tglpesanan = (TextView) itemView.findViewById(R.id.tgl_pesanan);
            txt_status = (TextView) itemView.findViewById(R.id.status_pesanan);

        }
    }
}

