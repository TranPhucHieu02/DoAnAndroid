package com.example.doan.Adaptor;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.Truyens;
import com.example.doan.R;
import com.example.doan.TTTruyenActivity;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    private Context context;
    private List<Truyens>listTruyen;
    private DocGia docGia;

    public PhotoAdapter(Context context, List<Truyens> listTruyen,DocGia docGia) {
        this.context = context;
        this.listTruyen = listTruyen;
        this.docGia = docGia;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_image,container,false);
        ImageView imageView= view.findViewById(R.id.imageView);
        TextView tenTruyen = view.findViewById(R.id.lbTenTruyen);
        Truyens truyen = listTruyen.get(position);
        DocGia docGias =docGia;
        if(truyen!=null){

            Glide.with(context)
                    .load(truyen.getImageUrl())
                    .into(imageView);
            tenTruyen.setText(truyen.getTenTruyen());

        }
        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TTTruyenActivity.class);
                intent.putExtra("Truyen",truyen);
                intent.putExtra("docgia",docGias);
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        if(listTruyen!=null){
            return listTruyen.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
