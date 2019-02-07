package com.example.samet.firebase_crud;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.samet.firebase_crud.Adapters.CustomerAdapter;
import com.example.samet.firebase_crud.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    public static final String CUSTOMER_NAME = "com.example.samet.firebase_crud.customername";
    public static final String CUSTOMER_ID = "com.example.samet.firebase_crud.customerid";
    private Spinner spinner;
    private EditText editText;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference databaseReferenceCustomers;
    private List<Customer> customers;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomerAdapter customerAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        databaseReferenceCustomers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                customers.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Customer customer = postSnapshot.getValue(Customer.class);
                    customers.add(customer);
                }


                mRecyclerView.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);

                customerAdapter = new CustomerAdapter(MainActivity.this, customers);
                customerAdapter.setClickListener(MainActivity.this);
                mRecyclerView.setAdapter(customerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReferenceCustomers = FirebaseDatabase.getInstance().getReference("customers");

        spinner = (Spinner)findViewById(R.id.spinnerArray);
        editText = (EditText)findViewById(R.id.edittextName);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.addCustomer);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        customers = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomer();
            }
        });


    }

    private void addCustomer() {

        if(editText.getText().length()>0){

            String id = databaseReferenceCustomers.push().getKey();
            Customer customer = new Customer(id,editText.getText().toString(),spinner.getSelectedItem().toString());
            databaseReferenceCustomers.child(id).setValue(customer);
            editText.setText("");
            Toast.makeText(getApplicationContext(),"Yeni Müşteri Eklendi",Toast.LENGTH_SHORT).show();

        }else {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Hata");
        builder.setMessage("Lütfen Müşteri Adını Giriniz!");

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
    public void onClick(View view, int position) {
        Customer customer = customers.get(position);

        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(CUSTOMER_ID, customer.getCustomerId());
        intent.putExtra(CUSTOMER_NAME, customer.getCustomerName());
        startActivity(intent);
    }
}