package com.example.samet.firebase_crud.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samet.firebase_crud.R;
import com.example.samet.firebase_crud.model.Order;

import java.util.List;

/**
 * Created by alper on 24/04/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Order> orderList;
    Context context;

    public OrderAdapter(Context context, List<Order> orderList) {

        this.orderList = orderList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView orderName;
        public TextView orderPrice;

        public ViewHolder(View view) {
            super(view);

            orderName = (TextView)view.findViewById(R.id.orderName);
            orderPrice = (TextView)view.findViewById(R.id.orderPrice);
            view.setTag(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.orderName.setText("Sipariş Adı: "+" "+orderList.get(position).getOrderName());
        holder.orderPrice.setText("Sipariş Fiyatı: "+" "+ String.valueOf(orderList.get(position).getOrderPrice()+" "+"TL"));
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderadapter_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
