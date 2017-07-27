package com.example.android.inventory;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by manmohan on 23/6/17.
 */

public class InventoryContract {
    private InventoryContract() {}

    public static final class InventoryEntry implements BaseColumns {
        public static final String CONTENT_AUTHORITY = "com.example.android.inventory";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH = "inventory";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String TABLE_NAME = "inventory";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_IMAGE = "image";

        public static final boolean isValidPrice(int price) {
            if (price < 0) return false;
            return true;
        }

        public static final boolean isValidQuantity(int quantity) {
            if(quantity < 0) return false;
            return true;
        }
    }
}
