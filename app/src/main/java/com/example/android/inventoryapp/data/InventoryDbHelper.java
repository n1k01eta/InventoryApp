package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 11/6/2018.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Store.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String NOT_NULL = " NOT NULL";
    private static final String COMMA_SEP = ", ";

    //Constructs the inventory table: CREATE TABLE inventory (_id INTEGER, name TEXT, price REAL,
    // quantity INTEGER, supplier TEXT, phone BLOB);
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + InventoryContract.InventoryEntry.TABLE_NAME + " (" +
                    InventoryContract.InventoryEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    InventoryContract.InventoryEntry.COLUMN_PRICE + REAL_TYPE + COMMA_SEP +
                    InventoryContract.InventoryEntry.COLUMN_QUANTITY + INTEGER_TYPE + COMMA_SEP +
                    InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME + TEXT_TYPE + COMMA_SEP +
                    InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE + TEXT_TYPE + ");";

    //Delete the table inventory if exists
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + InventoryContract.InventoryEntry.TABLE_NAME;

    public InventoryDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

}
