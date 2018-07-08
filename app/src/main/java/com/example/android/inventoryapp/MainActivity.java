package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.android.inventoryapp.data.InventoryDbHelper;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PRODUCT_LOADER = 0;
    ProductCursorAdapter productCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        //Find the ListView and populate it
        ListView productListView = (ListView) findViewById(R.id.list);

        //Find and set empty view on the ListView
        View emptyView = findViewById(R.id.empty_view);
        productListView.setEmptyView(emptyView);

        //Setup and Adapter to create a list item for each row of the product data in the Cursor
        productCursorAdapter = new ProductCursorAdapter(this, null);
        productListView.setAdapter(productCursorAdapter);

        //Setup the item click listener
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Create new intent to go to EditorActivity
                Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);

                //form the current URI
                Uri currentProductUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);

                //set the URI on the data field of the intent
                intent.setData(currentProductUri);

                //Launch the EditorActivity to display the data
                startActivity(intent);
            }
        });

        //kick off the loader
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }


    private void insertData(){
        //Insert data into Inventory Database

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, "MLS");
        values.put(InventoryEntry.COLUMN_CATEGORY, 7);
        values.put(InventoryEntry.COLUMN_PRICE, "250.90");
        values.put(InventoryEntry.COLUMN_QUANTITY, 10);
        values.put(InventoryEntry.COLUMN_SUPPLIER_NAME, "Alpha");
        values.put(InventoryEntry.COLUMN_SUPPLIER_PHONE, "2721082283");

        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);

    }

    private void deleteAllData(){
        int rowsDeleted = getContentResolver().delete(InventoryEntry.CONTENT_URI, null, null);
        Toast.makeText(this, getString(R.string.delete_all), Toast.LENGTH_SHORT).show();
    }

    /**
     * prompt the user to confirm that they want to delete
     */
    private void showDeleteConfirmationDialog() {
        //create an AlertDialog.Builder and set the message and the click listeners
        //for the positive and negative buttons on the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteAllData();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        //create and show the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertData();
                return true;
            case R.id.action_delete_all_entrie:
                showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //define a projection
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_CATEGORY,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_QUANTITY,
                InventoryEntry.COLUMN_SUPPLIER_NAME
        };
        //this loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                InventoryEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //Update ProductCursorAdapter with this new cursor containing updated data
        productCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Callback called when the data needs to be deleted
        productCursorAdapter.swapCursor(null);

    }

    public void saleProduct(long productId, int quantity) {

        // Decrement item quantity
        if (quantity >= 1) {
            quantity--;
            // Construct new uri and content values
            Uri updateUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, productId);
            ContentValues values = new ContentValues();
            values.put(InventoryEntry.COLUMN_QUANTITY, quantity);
            int rowsUpdated = getContentResolver().update(
                    updateUri,
                    values,
                    null,
                    null);
            if (rowsUpdated == 1) {
                Toast.makeText(this, R.string.sale_successful, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.sale_failed, Toast.LENGTH_SHORT).show();
            }

        } else {
            //  Out of stock
            Toast.makeText(this, R.string.out_of_stock, Toast.LENGTH_LONG).show();
        }
    }


}