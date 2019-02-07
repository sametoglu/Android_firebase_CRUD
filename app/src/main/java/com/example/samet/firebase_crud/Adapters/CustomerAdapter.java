package com.example.samet.firebase_crud.Adapters;

/**
 * Created by samet on 22.12.2017.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samet.firebase_crud.model.Customer;
import com.example.samet.firebase_crud.MainActivity;
import com.example.samet.firebase_crud.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.samet.firebase_crud.ItemClickListener;
import com.example.samet.firebase_crud.R;
import com.example.samet.firebase_crud.model.Customer;

import java.util.List;

/**
 * Created by alper on 23/04/2017.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    List<Customer> customerList;
    Context context;
    private ItemClickListener clickListener;

    public CustomerAdapter(Context context, List<Customer> customerList) {

        this.customerList = customerList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView customerName;
        public TextView customerAddress;
        public ImageView customerPhoto;
        public ImageView optionMenu;


        public ViewHolder(View view) {
            super(view);

            customerName = (TextView)view.findViewById(R.id.customerName);
            customerAddress = (TextView)view.findViewById(R.id.customerAddress);
            customerPhoto = (ImageView)view.findViewById(R.id.customer_photo);
            optionMenu = (ImageView)view.findViewById(R.id.optionMenu);
            view.setTag(view);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customeradapter_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.customerName.setText(customerList.get(position).getCustomerName());
        holder.customerAddress.setText(customerList.get(position).getCustomerAddress());
        holder.customerPhoto.setImageResource(R.drawable.user);
        holder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(context, holder.optionMenu);
                popup.inflate(R.menu.menu_items);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                //update customer
                                updateCustomer(customerList.get(position));
                                break;
                            case R.id.delete:
                                //delete customer
                                deleteCustomer(customerList.get(position).getCustomerId());
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

    }

    private Customer updateCustomer;
    private void updateCustomer(Customer customer) {

        updateCustomer = customer;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerUpdate = (Spinner) dialogView.findViewById(R.id.spinnerUpdate);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateCustomer);

        editTextName.setText(updateCustomer.getCustomerName());

        dialogBuilder.setTitle("Müşteri Düzenleme");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextName.getText().length()>0){

                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("customers").child(updateCustomer.getCustomerId());
                    Customer customer = new Customer(updateCustomer.getCustomerId(),editTextName.getText().toString(),spinnerUpdate.getSelectedItem().toString());
                    dR.setValue(customer);
                    b.dismiss();
                    Toast.makeText(context, "Müşteri Güncellendi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteCustomer(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("customers").child(id);
        dR.removeValue();
        DatabaseReference drOrders = FirebaseDatabase.getInstance().getReference("orders").child(id);
        drOrders.removeValue();
        Toast.makeText(context ,"Müşteri Silindi", Toast.LENGTH_SHORT).show();
    }

    public void setClickListener(MainActivity itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }
}
