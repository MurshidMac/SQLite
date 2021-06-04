package com.example.dreamcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.example.dreamcrud.config.DBConfig;
import com.example.dreamcrud.db.model.CustomerModel;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    // make a reference to buttons and all other layouts
    // since its a basic implementation I would be using only a sqlite implemetation
    // would rather go to a room database in future implementations

    @BindView(R.id.et_name)
    EditText  etName;
    @BindView(R.id.et_age)
    EditText etAge;

    @BindView(R.id.sw_active)
    Switch sw_ActiveCustomer;

    @BindView(R.id.lv_customerList)
    ListView lv_CustomerList;

    DBConfig database;
    CustomerModel customerModel;
    List<CustomerModel> customerModelList;
    ArrayAdapter customerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAdapter();
    }
    @OnClick(R.id.btn_add)
    public void add() {
        try {
            int age = Integer.parseInt(etAge.getText().toString());
             customerModel = new CustomerModel(1, etName.getText().toString(), age
                    , sw_ActiveCustomer.isChecked());
            Log.i("Value of Customer Model", customerModel.toString());
        }catch (Exception e){
            Log.e("Input Mismatch", "Error creating a customer "+e.getMessage());
            customerModel = new CustomerModel(-1, "error", 0, false);
        }
        database = new DBConfig(this);
        boolean success = database.create(customerModel);
        Log.i("Customer Insert", "STATUS INSERT "+success);
    }
    @OnClick(R.id.btn_viewAll)
    public void viewAll() {
         database = new DBConfig(this);
         customerModelList=  database.getEveryone();
         Log.i("LIST OF CUSTOMERS",customerModelList.toString());
         customerListAdapter = new ArrayAdapter<CustomerModel>(this, android.R.layout.simple_list_item_1,customerModelList);
        lv_CustomerList.setAdapter(customerListAdapter);
    }
    private void initAdapter(){
        database = new DBConfig(this);
        customerModelList=  database.getEveryone();
        customerListAdapter= new ArrayAdapter<CustomerModel>(this, android.R.layout.simple_list_item_1,customerModelList);
        lv_CustomerList.setAdapter(customerListAdapter);
    }

    @OnItemClick(R.id.lv_customerList)
    public void customerClicked(@NotNull android.widget.AdapterView<?>
            parent, android.view.View view, int position, long id){
            CustomerModel clickedmodel = (CustomerModel)parent.getItemAtPosition(position);
            Log.i("CUSTOMER DELETED", clickedmodel.toString());
            database.delete(clickedmodel);
            initAdapter();
    }


}