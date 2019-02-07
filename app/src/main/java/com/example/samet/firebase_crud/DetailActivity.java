package com.example.samet.firebase_crud;

/**
 * Created by samet on 22.12.2017.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.samet.firebase_crud.Adapters.OrderAdapter;
import com.example.samet.firebase_crud.model.Order;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private DatabaseReference databaseOrders;
    private TextView customerName;
    private EditText editText, editTextPrice;
    private FloatingActionButton floatingActionButton;
    private OrderAdapter orderAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    List<Order> orderList;

    @Override
    protected void onStart() {
        super.onStart();
        databaseOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);
                    orderList.add(order);
                }
                recyclerView.setHasFixedSize(true);
                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(DetailActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);

                orderAdapter = new OrderAdapter(DetailActivity.this, orderList);
                recyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("Hata");
        builder.setMessage("Lütfen Sipariş/Fiyat Giriniz!");

        String positiveText = "TAMAM";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        databaseOrders = FirebaseDatabase.getInstance().getReference("orders").child(intent.getStringExtra(MainActivity.CUSTOMER_ID));

        customerName = (TextView)findViewById(R.id.customerName);
        customerName.setText(intent.getStringExtra(MainActivity.CUSTOMER_NAME));

        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.addOrders);
        editText = (EditText)findViewById(R.id.edittextName);
        editTextPrice = (EditText)findViewById(R.id.edittextprice);

        orderList = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().length()>0 && editTextPrice.getText().length()>0){

                    String id  = databaseOrders.push().getKey();
                    Order order = new Order(id, editText.getText().toString(),Integer.parseInt(editTextPrice.getText().toString()));
                    databaseOrders.child(id).setValue(order);
                    Toast.makeText(getApplicationContext(),"Yeni Sipariş Eklendi",Toast.LENGTH_SHORT).show();
                    editText.setText("");
                    editTextPrice.setText("");

                }else{

                    showAlertDialog();
                }
            }
        });

    }
}
