package com.amar.sample.adapter;

import android.app.Activity;
import android.support.v4.media.RatingCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amar.sample.R;
import com.amar.sample.activity.ActivityPreviewStruk;
import com.amar.sample.model.ModelDaftarProduk;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterDaftarRatingToko extends RecyclerView.Adapter<AdapterDaftarRatingToko.AdapterPdodukViewHolder> {


    private ArrayList<ModelDaftarProduk> dataList;
    private Activity activity;

    public AdapterDaftarRatingToko(Activity activity, ArrayList<ModelDaftarProduk> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @Override
    public AdapterPdodukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_adapter_dafter_rating, parent, false);
        return new AdapterPdodukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterPdodukViewHolder holder, final int position) {
        final ModelDaftarProduk item = dataList.get(position);

        holder.txt_no_pemesanan.setText(dataList.get(position).getItem1());
        holder.txt_nama_pembeli.setText(dataList.get(position).getItem2());


        float rating, rating2, rating3;
        rating = 2;
        rating2 = 1;
        rating3 = 2;

        //Rating 1
        holder.ratingBar.setRating(rating);
        holder.nila_toko.setText(String.valueOf(rating));
        holder.ratingBar.setEnabled(false);

        //Rating 2
        holder.ratingHarga.setRating(rating2);
        holder.nilai_harga.setText(String.valueOf(rating2));
        holder.ratingHarga.setEnabled(false);

        //Rating 3
        holder.ratingPelayanan.setRating(rating3);
        holder.nilai_pelayanan.setText(String.valueOf(rating3));
        holder.ratingPelayanan.setEnabled(false);


        /*holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating = 3;
                holder.ratingBar.setRating(rating);
                holder.nila_toko.setText(String.valueOf(rating));
            }
        });*/

    }



    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class AdapterPdodukViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_no_pemesanan, txt_nama_pembeli;
        private CardView cr_item;
        private LinearLayout ln_bg;
        private AppCompatRatingBar ratingBar, ratingHarga, ratingPelayanan;
        private TextView nila_toko, nilai_harga, nilai_pelayanan;

        public AdapterPdodukViewHolder(View itemView) {
            super(itemView);
            txt_no_pemesanan = (TextView) itemView.findViewById(R.id.no_pemesanan);
            txt_nama_pembeli = (TextView) itemView.findViewById(R.id.nama_pembeli);
            nila_toko = (TextView) itemView.findViewById(R.id.nl_rating_toko);
            nilai_harga = (TextView) itemView.findViewById(R.id.txt_Rharga);
            nilai_pelayanan = (TextView) itemView.findViewById(R.id.txt_Rpenilaian);
            ratingBar = (AppCompatRatingBar) itemView.findViewById(R.id.penilaian);
            ratingHarga = (AppCompatRatingBar) itemView.findViewById(R.id.penilaian_harga);
            ratingPelayanan = (AppCompatRatingBar) itemView.findViewById(R.id.penilaian_pelayanan);

           // ln_bg = (LinearLayout) itemView.findViewById(R.id.head);

        }
    }
}

