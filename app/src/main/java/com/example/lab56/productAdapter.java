package com.example.lab56;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class productAdapter extends RecyclerView.Adapter<productAdapter.myViewHolder>
{
    Context mContext;
    ArrayList<product> productArrayList;

//    private IClickListener mIClickListener;

//    public ProductAdapter(Home_Fragment home_fragment, ArrayList<Product> productArrayList) {
//    }

//    public interface IClickListener{
//        void onClickUpdateItem(Product product);
//        void onClickDeleteItem(Product product);
//    }


    public productAdapter(Context mContext, ArrayList<product> productArrayList) {
        this.mContext = mContext;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public productAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_product,parent,false);

        return new productAdapter.myViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull productAdapter.myViewHolder holder, int position) {

        product product=productArrayList.get(position);

        holder.txtName.setText(product.getTenProduct());
//        holder.txtSoLuong.setText(product.getSoLuong());

        Glide.with(mContext)
                .load(product.getAnhProduct())
                .into(holder.imgProduct);
//        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mIClickListener.onClickUpdateItem(product);
//            }
//        });
//        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mIClickListener.onClickDeleteItem(product);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView txtName,txtGia,txtSoLuong;
        ImageView imgUpdate;
        ImageView imgDelete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgHinh);
            txtName=itemView.findViewById(R.id.textName);
//            txtSoLuong=itemView.findViewById(R.id.txtSoLuong);
//            imgDelete=itemView.findViewById(R.id.imgDelete);
//            imgUpdate=itemView.findViewById(R.id.imgUpdate);
        }
    }
}
