package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by user on 11/6/2018.
 */

public class InventoryContract {
    /** CONTENT_AUTHORITY is the entire content provider*/
    public static final String CONTENT_AUTHORITY = "com.example.android.inventory";
    /** Use CONTENT_AUTHORITY to create the base of all URI's which app will use
     * to contact the content provider*/
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "inventory";
    private InventoryContract(){}

    public static final class InventoryEntry implements BaseColumns{

        /** The content URI to access the inventory database in the provider*/
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        /** The MIME type of the {@link #CONTENT_URI} for a list of products*/
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String TABLE_NAME = "inventory";

        /** Unique ID number for the product
         *
         * Type INTEGER
         *
         * */
        public static final String _ID = BaseColumns._ID;

        /** Category of the product
         * The only possible values are CATEGORY_DESKTOP, CATEGORY_GAME, CATEGORY_HEADSET,
         * CATEGORY_KEYBOARD, CATEGORY_LAPTOP, CATEGORY_MOUSE, CATEGORY_PHONE,
         * CATEGORY_ROUTER, CATEGORY_SPEAKER, CATEGORY_TABLET, CATEGORY_WATCH and
         * CATEGORY_UNKNOWN
         *
         * Type INTEGER
         *
         * */
        public static final String COLUMN_CATEGORY = "category";

        /** Name of the the product
         *
         * Type TEXT
         *
         * */
        public static final String COLUMN_PRODUCT_NAME = "name";

        /** Price of the product
         *
         * Type REAL
         *
         * */
        public static final String COLUMN_PRICE = "price";

        /** Quantity of the product
         *
         * Type INTEGER
         *
         * */
        public static final String COLUMN_QUANTITY = "quantity";

        /** Supplier of the product
         *
         * Type TEXT
         *
         * */
        public static final String COLUMN_SUPPLIER_NAME = "supplier";

        /** Phone of the supplier
         *
         * Type TEXT
         *
         * */
        public static final String COLUMN_SUPPLIER_PHONE = "phone";

        public static final int CATEGORY_DESKTOP = 1;
        public static final int CATEGORY_GAME = 2;
        public static final int CATEGORY_HEADSET = 3;
        public static final int CATEGORY_KEYBOARD = 4;
        public static final int CATEGORY_LAPTOP = 5;
        public static final int CATEGORY_MOUSE = 6;
        public static final int CATEGORY_PHONE = 7;
        public static final int CATEGORY_ROUTER = 8;
        public static final int CATEGORY_SPEAKER = 9;
        public static final int CATEGORY_TABLET = 10;
        public static final int CATEGORY_WATCH = 11;
        public static final int CATEGORY_UNKNOWN = 0;

        public static boolean isValidCategory(int category){
            if (category == CATEGORY_DESKTOP || category == CATEGORY_GAME || category == CATEGORY_HEADSET
                    || category == CATEGORY_KEYBOARD || category == CATEGORY_LAPTOP || category == CATEGORY_MOUSE
                    || category == CATEGORY_PHONE || category == CATEGORY_ROUTER || category == CATEGORY_SPEAKER
                    || category == CATEGORY_TABLET || category == CATEGORY_WATCH || category == CATEGORY_UNKNOWN){
                return true;
            }
            return false;
        }

    }
}
