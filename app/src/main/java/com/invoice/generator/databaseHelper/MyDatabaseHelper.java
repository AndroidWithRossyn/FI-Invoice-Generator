package com.invoice.generator.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String COLUMN_ADVANCE = "advance";
    private static final String COLUMN_CUSTOMER_ADDRESS = "customerAddress";
    private static final String COLUMN_CUSTOMER_NAME = "customerName";
    private static final String COLUMN_CUSTOMER_PHONE = "customerPhone";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_DELI_FEES = "deliFee";
    private static final String COLUMN_MARK = "completeMark";
    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_PAID = "paidStatus";
    private static final String COLUMN_PRODUCT_COUNT = "productCount";
    private static final String COLUMN_PRODUCT_NAME = "productName";
    private static final String COLUMN_PRODUCT_PRICE = "productPrice";
    private static final String DATABASE_NAME = "InvoiceList.db";
    private static final String TABLE_NAME = "myInvoiceTable";
    private static final String COLUMN_TOTAL_PRICE = "TotalPrice";
    private final Context context;

    public MyDatabaseHelper(Context context2) {
        super(context2, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE myInvoiceTable (idcolumn INTEGER PRIMARY KEY AUTOINCREMENT,productName TEXT,productCount TEXT,productPrice TEXT,id TEXT,advance TEXT,tax TEXT,discount TEXT,deliFee TEXT,customerName TEXT,customerAddress TEXT,customerPhone TEXT,date TEXT,completeMark TEXT,paidStatus TEXT,totalPrice TEXT);");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS myInvoiceTable");
        onCreate(sQLiteDatabase);
    }

    public void addOrder(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14,String price) throws SQLiteException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, str);
        contentValues.put(COLUMN_PRODUCT_COUNT, str2);
        contentValues.put(COLUMN_PRODUCT_PRICE, str3);
        contentValues.put(COLUMN_ORDER_ID, str4);
        contentValues.put(COLUMN_ADVANCE, str5);
        contentValues.put("tax", str6);
        contentValues.put("discount", str7);
        contentValues.put(COLUMN_DELI_FEES, str8);
        contentValues.put(COLUMN_CUSTOMER_NAME, str9);
        contentValues.put(COLUMN_CUSTOMER_ADDRESS, str10);
        contentValues.put(COLUMN_CUSTOMER_PHONE, str11);
        contentValues.put(COLUMN_DATE, str12);
        contentValues.put(COLUMN_MARK, str13);
        contentValues.put(COLUMN_PAID, str14);
        contentValues.put(COLUMN_TOTAL_PRICE, price);
        if (writableDatabase.insert(TABLE_NAME, (String) null, contentValues) == -1) {
            Toast.makeText(this.context, "Failed to add", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        if (readableDatabase != null) {
            return readableDatabase.rawQuery(" SELECT * FROM myInvoiceTable", (String[]) null);
        }
        return null;
    }

    public void updateData(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, String price) throws SQLiteException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, str2);
        contentValues.put(COLUMN_PRODUCT_COUNT, str3);
        contentValues.put(COLUMN_PRODUCT_PRICE, str4);
        contentValues.put(COLUMN_ORDER_ID, str5);
        contentValues.put(COLUMN_ADVANCE, str6);
        contentValues.put("tax", str7);
        contentValues.put("discount", str8);
        contentValues.put(COLUMN_DELI_FEES, str9);
        contentValues.put(COLUMN_CUSTOMER_NAME, str10);
        contentValues.put(COLUMN_CUSTOMER_ADDRESS, str11);
        contentValues.put(COLUMN_CUSTOMER_PHONE, str12);
        contentValues.put(COLUMN_DATE, str13);
        contentValues.put(COLUMN_MARK, str14);
        contentValues.put(COLUMN_PAID, str15);
        contentValues.put(COLUMN_TOTAL_PRICE, price);
        if (((long) writableDatabase.update(TABLE_NAME, contentValues, "idcolumn=?", new String[]{str})) == -1) {
            Toast.makeText(this.context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String str) {
        if (((long) getWritableDatabase().delete(TABLE_NAME, "idcolumn=?", new String[]{str})) == -1) {
            Toast.makeText(this.context, "Failed to delete!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
