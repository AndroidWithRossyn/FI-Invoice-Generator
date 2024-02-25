package com.invoice.generator.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class MyDatabaseHelperItems extends SQLiteOpenHelper {
    private static final String COLUMN_ITEM_IN_STOCK = "itemInStock";
    private static final String COLUMN_ITEM_NAME = "itemName";
    private static final String COLUMN_ITEM_PIC = "itemPic";
    private static final String COLUMN_ITEM_PRICE = "itemPrice";
    private static final String DATABASE_NAME = "ItemList.db";
    private static final String TABLE_NAME = "myItemTable";
    private final Context context;
    String currencyString;
    SharedPreferences share2;

    public MyDatabaseHelperItems(Context context2) {
        super(context2, DATABASE_NAME, null, 1);
        this.context = context2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE myItemTable (idcolumn INTEGER PRIMARY KEY AUTOINCREMENT,itemPic BLOB,itemName TEXT,itemPrice TEXT,itemInStock TEXT );");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS myItemTable");
        onCreate(sQLiteDatabase);
    }

    public ArrayList<String> getAllNames() {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT itemName , itemPrice FROM myItemTable", null);
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("shopinfo", 0);
        this.share2 = sharedPreferences;
        if (sharedPreferences.contains(FirebaseAnalytics.Param.CURRENCY)) {
            String string = this.share2.getString(FirebaseAnalytics.Param.CURRENCY, "");
            this.currencyString = string;
            if (string.equals("No")) {
                this.currencyString = "";
            }
        } else {
            this.currencyString = "";
        }
        if (rawQuery.moveToFirst()) {
            do {
                arrayList.add(rawQuery.getString(0) + "\nPrice: " + rawQuery.getString(1) + " " + this.currencyString + " \n");
            } while (rawQuery.moveToNext());
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public void addMyItem(byte[] bArr, String str, String str2, String str3) throws SQLiteException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_PIC, bArr);
        contentValues.put(COLUMN_ITEM_NAME, str);
        contentValues.put(COLUMN_ITEM_PRICE, str2);
        contentValues.put(COLUMN_ITEM_IN_STOCK, str3);
        if (writableDatabase.insert(TABLE_NAME, null, contentValues) == -1) {
            Toast.makeText(this.context, "Failed to add", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        if (readableDatabase != null) {
            return readableDatabase.rawQuery(" SELECT * FROM myItemTable", null);
        }
        return null;
    }

    public void updateData(String str, byte[] bArr, String str2, String str3, String str4) throws SQLiteException {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_PIC, bArr);
        contentValues.put(COLUMN_ITEM_NAME, str2);
        contentValues.put(COLUMN_ITEM_PRICE, str3);
        contentValues.put(COLUMN_ITEM_IN_STOCK, str4);
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
