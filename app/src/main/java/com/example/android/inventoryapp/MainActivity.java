package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.android.inventoryapp.data.InventoryDbHelper;

public class MainActivity extends AppCompatActivity {

    public static InventoryDbHelper inventoryDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inventoryDbHelper = new InventoryDbHelper(this);
        insertData();
        displayDataBaseInfo();
    }

    private void insertData(){
        //Insert data into Inventory Database
        SQLiteDatabase db = inventoryDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, "Book");
        values.put(InventoryEntry.COLUMN_PRICE, 5.99);
        values.put(InventoryEntry.COLUMN_QUANTITY, 10);
        values.put(InventoryEntry.COLUMN_SUPPLIER_NAME, "Alpha");
        values.put(InventoryEntry.COLUMN_SUPPLIER_PHONE, "2721082283");

        long newRowID =db.insert(InventoryEntry.TABLE_NAME, null, values);

        if (newRowID == -1) {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data saved with row ID: " + newRowID, Toast.LENGTH_SHORT).show();
        }

    }

    private Cursor query(){

        // Create and/or open a database to read from it
        SQLiteDatabase db = inventoryDbHelper.getReadableDatabase();

        String[] project = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_QUANTITY,
                InventoryEntry.COLUMN_SUPPLIER_NAME,
                InventoryEntry.COLUMN_SUPPLIER_PHONE
        };

        return db.query(
                InventoryEntry.TABLE_NAME,
                project,
                null, null, null, null, null
        );

    }

    private void displayDataBaseInfo(){
        //Select the Text View to display data from the Inventory DataBase
        TextView displayView = (TextView) findViewById(R.id.text_view);
        Cursor dbCursor = query();

        try {

            displayView.setText(InventoryEntry._ID + "\t" +
                    InventoryEntry.COLUMN_PRODUCT_NAME + "\t" + InventoryEntry.COLUMN_PRICE + "\t" +
                    InventoryEntry.COLUMN_QUANTITY + "\t" + InventoryEntry.COLUMN_SUPPLIER_NAME + "\t"
                    + InventoryEntry.COLUMN_SUPPLIER_PHONE + "\n");

            // Retrieve the index of each column
            int idColumnIndex = dbCursor.getColumnIndex(InventoryEntry._ID);
            int nameColumnIndex = dbCursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = dbCursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
            int quantityColumnIndex = dbCursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);
            int supplierColumnIndex = dbCursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_NAME);
            int phoneColumnIndex = dbCursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER_PHONE);

            //iterate through all the returned rows in the dbCursor
            while (dbCursor.moveToNext()) {
                //Use that index to extract the values from the row
                int currentID = dbCursor.getInt(idColumnIndex);
                String currentName = dbCursor.getString(nameColumnIndex);
                Double currentPrice = dbCursor.getDouble(priceColumnIndex);
                int currentQuantity = dbCursor.getInt(quantityColumnIndex);
                String currentSupplier = dbCursor.getString(supplierColumnIndex);
                String currentPhone = dbCursor.getString(phoneColumnIndex);

                // Display the values from each column of the current row in the dbCursor in the TextView
                displayView.append("\n" + currentID + "\t" + currentName + "\t" + currentPrice + "\t" +
                        currentQuantity + "\t" + currentSupplier + "\t" + currentPhone);
            }
        } finally {
            displayView.append("\n" + "Number of rows in pets database table: " + dbCursor.getCount());
            dbCursor.close();
        }

    }
}
