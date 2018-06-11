package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by user on 11/6/2018.
 */

public class InventoryContract {
    private InventoryContract(){}

    public static final class InventoryEntry implements BaseColumns{
        public static final String TABLE_NAME = "inventory";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier";
        public static final String COLUMN_SUPPLIER_PHONE = "phone";

    }
}
