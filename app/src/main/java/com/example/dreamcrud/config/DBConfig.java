package com.example.dreamcrud.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dreamcrud.db.model.BaseModel;
import com.example.dreamcrud.db.model.CustomerModel;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DBConfig extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "customer.db";
    private static final int DATABASE_VERSION_NUMBER = 1;
    private static final String table_name = "CUSTOMER";
    private static final String column_name = "NAME";
    private static final String column_age = "AGE";
    private static final String column_active = "ACTIVE";
    private static final String column_id = "ID";

    // Extracting columns could be handy as well
    public DBConfig(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_NUMBER);
    }


    // when creating a local database in the SQL Lite implementation
    @Override
    public void onCreate(SQLiteDatabase db) {
        String statement = "CREATE TABLE IF NOT EXISTS " + table_name + "("+column_id+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                column_name+" TEXT NOT NULL, " +
                column_age+" TEXT NOT NULL, " +
                column_active+" INT NOT NULL " +
                ")";
        db.execSQL(statement);
    }
    // when upgrading or a typical migration. you should use this implementation
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean create(@NotNull CustomerModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column_name, model.getName());
        cv.put(column_age, model.getAge());
        cv.put(column_active, model.isActive());

        long value = db.insert(table_name,null,cv);
        if(value == -1){
            return false;
        }
        return true;
    }

    public List<CustomerModel> getEveryone(){
        List<CustomerModel> list = new ArrayList<>();

        String queryString = "SELECT * FROM "+ table_name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst() == true){
            // IF THERE ARE RESULTS LOOP
            do{
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                int dbCustomerStatus = cursor.getInt(3);
                boolean customerActiveStatus = false;
                if(dbCustomerStatus==1){
                    customerActiveStatus = true;
                }
                CustomerModel model = new CustomerModel(customerID,customerName,
                        customerAge,customerActiveStatus);
                list.add(model);
            }while(cursor.moveToNext());

        }else{
            return null;
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean delete(CustomerModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+ table_name+ " WHERE "+ column_id+ " = " + model.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }
}
