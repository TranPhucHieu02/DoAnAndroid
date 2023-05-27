package com.example.doan.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.Truyens;
import com.example.doan.R;
import com.example.doan.TTTruyenActivity;

import java.util.List;

public class DSTruyenAdapter extends RecyclerView.Adapter<DSTruyenAdapter.TruyenViewHolder> {

    private Context context;
    private List<Truyens> listTruyen;
    private DocGia docGia;

    public class TruyenViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgTruyen;
        private TextView tenTruyen;
        private CardView cardItem;
        public TruyenViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTruyen = itemView.findViewById(R.id.imageTruyen);
            tenTruyen = itemView.findViewById(R.id.textTenTruyen);
            cardItem = itemView.findViewById(R.id.cardItem);

        }
    }

    public DSTruyenAdapter(Context context, List<Truyens> listTruyen,DocGia docGia) {
        this.context = context;
        this.listTruyen = listTruyen;
        this.docGia = docGia;
    }

    public void setData(List<Truyens> list){
        this.listTruyen =list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TruyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new TruyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruyenViewHolder holder, int position) {
        Truyens truyen =listTruyen.get(position);
        DocGia docGias =docGia;
        if(truyen== null){
            return;
        }
        Glide.with(context)
                .load(truyen.getImageUrl())
                .into(holder.imgTruyen);
        holder.tenTruyen.setText(truyen.getTenTruyen());

        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, TTTruyenActivity.class);
               intent.putExtra("Truyen", truyen);
               intent.putExtra("docgia", docGias);
               context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(listTruyen != null){
            return listTruyen.size();
        }
        return 0;
    }


}
