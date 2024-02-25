package com.invoice.generator.databaseHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MyDatabaseHelperCustomers extends SQLiteOpenHelper {
    private static final String COLUMN_CUSTOMER_ADDRESS = "customerAddress";
    private static final String COLUMN_CUSTOMER_NAME = "customerName";
    private static final String COLUMN_CUSTOMER_PHONE = "customerPhone";
    private static final String DATABASE_NAME = "CustomerList.db";
    private static final String TABLE_NAME = "myCustomersTable";
    private final Context context;

    public MyDatabaseHelperCustomers(Context context2) {
        super(context2, DATABASE_NAME, null, 1);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE myCustomersTable (idcolumn INTEGER PRIMARY KEY AUTOINCREMENT,customerName TEXT,customerAddress TEXT,customerPhone TEXT );");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS myCustomersTable");
        onCreate(sQLiteDatabase);
    }

    public ArrayList<String> getAllCustomers() {
        ArrayList<String> arrayList = new ArrayList<>();
        new ArrayList();
        new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT idcolumn , customerName FROM myCustomersTable", null);
        if (rawQuery.moveToFirst()) {
            do {
                arrayList.add(rawQuery.getString(1));
            } while (rawQuery.moveToNext());
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public void addMyItem(String str, String str2, String str3) throws SQLiteException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CUSTOMER_NAME, str);
        contentValues.put(COLUMN_CUSTOMER_ADDRESS, str2);
        contentValues.put(COLUMN_CUSTOMER_PHONE, str3);
        if (writableDatabase.insert(TABLE_NAME, null, contentValues) == -1) {
            Toast.makeText(this.context, "Failed to add", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        if (readableDatabase != null) {
            return readableDatabase.rawQuery(" SELECT * FROM myCustomersTable", null);
        }
        return null;
    }

    public Cursor fetchAllNames() {
        Cursor query = getWritableDatabase().query(TABLE_NAME, new String[]{"idcolumn as _id", COLUMN_CUSTOMER_NAME, COLUMN_CUSTOMER_ADDRESS, COLUMN_CUSTOMER_PHONE}, null, null, null, null, null);
        if (query != null) {
            try {
                query.moveToFirst();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return query;
    }

    @SuppressLint("Recycle")
    public void compareCustomerData(String str, TextView textView, String str2) throws SQLiteException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (writableDatabase.rawQuery("SELECT * FROM myCustomersTable WHERE customerName = '" + str2 + "' OR " + COLUMN_CUSTOMER_PHONE + " = '" + str + "'", null).moveToFirst()) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void updateData(String str, String str2, String str3, String str4) throws SQLiteException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CUSTOMER_NAME, str2);
        contentValues.put(COLUMN_CUSTOMER_ADDRESS, str3);
        contentValues.put(COLUMN_CUSTOMER_PHONE, str4);
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
