package com.amar.sample.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.sample.R;
import com.amar.sample.activity.ActivityBuatStruk;
import com.amar.sample.activity.ActivityPreviewStruk;
import com.amar.sample.model.ModelDaftarProduk;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterDaftarBarangStrukPreview extends RecyclerView.Adapter<AdapterDaftarBarangStrukPreview.AdapterPdodukViewHolder> {


    private ArrayList<ModelDaftarProduk> dataList;
    private Activity activity;

    public AdapterDaftarBarangStrukPreview(Activity activity, ArrayList<ModelDaftarProduk> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @Override
    public AdapterPdodukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_adapter_preview_struk, parent, false);
        return new AdapterPdodukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPdodukViewHolder holder, final int position) {
        final ModelDaftarProduk item = dataList.get(position);


        holder.txt_satuan.setText(dataList.get(position).getItem6());
        holder.txt_ket.setText(dataList.get(position).getItem9());
        holder.nama_produk.setText(dataList.get(position).getItem3());
        holder.txt_jumlah.setText(dataList.get(position).getItem4());
        Integer harga = Integer.parseInt(dataList.get(position).getItem5());
        holder.txt_harga.setText(NumberFormat.getCurrencyInstance().format(harga));

        /*if(itemSelected.getItem11().toLowerCase().trim().equals("available")  || itemSelected.getItem11().toLowerCase().trim().equals("tersedia")){
            templateViewHolder.txt_status.setTextColor(activity.getResources().getColor(R.color.color_tersedia));
        }*/

        if (item.getItem8().toLowerCase().trim().equals("0")){
            holder.ln_bg.setBackgroundColor(activity.getResources().getColor(R.color.grey_text));
        }

        //Action Activity
        ActivityPreviewStruk.totalBRG(dataList.get(position).getItem10());

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
        private TextView txt_satuan, txt_ket, nama_produk, txt_harga, txt_jumlah;
        private CardView cr_item;
        private LinearLayout ln_bg;

        public AdapterPdodukViewHolder(View itemView) {
            super(itemView);
            txt_satuan = (TextView) itemView.findViewById(R.id.txt_satuan);
            txt_ket = (TextView) itemView.findViewById(R.id.txt_ket);
            txt_harga = (TextView) itemView.findViewById(R.id.txt_harga);
            nama_produk = (TextView) itemView.findViewById(R.id.txt_nama_produk);
            txt_jumlah = (TextView) itemView.findViewById(R.id.txt_jumlah);
            ln_bg = (LinearLayout) itemView.findViewById(R.id.head);

        }
    }
}

