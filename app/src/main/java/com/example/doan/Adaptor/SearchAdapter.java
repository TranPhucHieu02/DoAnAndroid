package com.example.doan.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan.Model.DocGia;
import com.example.doan.Model.Truyens;
import com.example.doan.R;
import com.example.doan.SearchActivity;
import com.example.doan.TTTruyenActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {

    private Context context;
    private List<Truyens> listSearch;
    private List<Truyens> listSearchOld;
    private DocGia docGia;




    public class SearchViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgTruyen;
        private TextView tenTruyen;
        private TextView tacGia;
        private CardView cardItem;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTruyen = itemView.findViewById(R.id.imageTruyen);
            tenTruyen = itemView.findViewById(R.id.textTenTruyen);
            tacGia = itemView.findViewById(R.id.textTacGia);
            cardItem = itemView.findViewById(R.id.cardItem);


        }
    }
    //==========================================================================
    public boolean check(String s1,String s2){
        int t=0,u=0;
        String A1[] = s1.split(" ");
        String A2[] = s2.split(" ");
        for(int i = 0; i<A1.length;i++){
            for(int j=u; j<A2.length;j++){
                if(A1[i].equalsIgnoreCase(A2[j]) == true){
                    t++;
                    u=j+1;
                    break;
                }
            }
        }
        if(t>=(A1.length)*0.6){
            return true;
        }
        return false;
    }
    //==========================================================================

    public SearchAdapter(Context context, List<Truyens> listSearch,DocGia docGia) {
        this.context = context;
        this.listSearch = listSearch;
        this.listSearchOld =listSearch;
        this.docGia = docGia;

    }

    public void setData(List<Truyens> list){
        this.listSearch =list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        return new SearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Truyens truyen =listSearch.get(position);
        DocGia docGias =docGia;
        if(truyen == null){
            return;
        }
        Glide.with(context)
                .load(truyen.getImageUrl())
                .into(holder.imgTruyen);
        holder.tenTruyen.setText(truyen.getTenTruyen());
        holder.tacGia.setText(truyen.getTacGia());
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TTTruyenActivity.class);
                intent.putExtra("Truyen",truyen);
                intent.putExtra("docgia", docGias);
                context.startActivity(intent);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(listSearch != null){
            return listSearch.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String stringSearch = charSequence.toString();
                if(stringSearch.isEmpty()){
                    listSearch = listSearchOld;
                }else{
                    List<Truyens> newListTruyen = new ArrayList<>();
                    for(Truyens truyen:listSearchOld){
                        if(check(stringSearch,truyen.getTenTruyen())==true){
//                        if(truyen.getTenTruyen().toLowerCase().contains(stringSearch.toLowerCase())){
                            newListTruyen.add(truyen);
                        }
                    }
                    listSearch =newListTruyen;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=listSearch;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listSearch = (List<Truyens>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
