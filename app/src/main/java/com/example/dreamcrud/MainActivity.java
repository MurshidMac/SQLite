package com.example.dreamcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.dreamcrud.db.model.CustomerModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    // make a reference to buttons and all other layouts

    @BindView(R.id.et_name)
    EditText  etName;
    @BindView(R.id.et_age)
    EditText etAge;

    @BindView(R.id.sw_active)
    Switch sw_ActiveCustomer;

    @BindView(R.id.lv_customerList)
    ListView lv_CustomerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_add)
    public void add() {
        try {
            int age = Integer.parseInt(etAge.getText().toString());
            CustomerModel customerModel = new CustomerModel(1, etName.getText().toString(), age
                    , sw_ActiveCustomer.isChecked());
            Log.i("Value of Customer Model", customerModel.toString());
        }catch (Exception e){
            Log.e("Input Mismatch", "Error creating a customer "+e.getMessage());
        }
    }
    @OnClick(R.id.btn_viewAll)
    public void viewAll() {

    }
}