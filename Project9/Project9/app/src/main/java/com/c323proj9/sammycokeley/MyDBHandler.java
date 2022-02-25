package com.c323proj9.sammycokeley;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "purchaseDB.db";
    private static final String TABLE_PURCHASES = "purchases";
    private static final String COLUMN_EXPENSE = "_expense";
    private static final String COLUMN_MONEY_SPENT = "_money_spent";
    private static final String COLUMN_DATE = "_date";
    private static final String COLUMN_CATEGORY = "_category";
    private static final String COLUMN_IMAGE = "_image";

    public MyDBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PURCHASES_TABLE = "CREATE TABLE "+ TABLE_PURCHASES + "(" + COLUMN_EXPENSE + " TEXT,"
                + COLUMN_MONEY_SPENT + " TEXT," + COLUMN_DATE + " TEXT," + COLUMN_CATEGORY + " TEXT," + COLUMN_IMAGE + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_PURCHASES_TABLE);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASES);
        onCreate(sqLiteDatabase);
    }

    public void addPurchase(Purchase purchase) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSE, purchase.getExpense());
        values.put(COLUMN_MONEY_SPENT, purchase.getCost());
        values.put(COLUMN_DATE, purchase.getDate());
        values.put(COLUMN_CATEGORY, purchase.getCategory());
        values.put(COLUMN_IMAGE, purchase.getImage());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PURCHASES,null, values);
        db.close();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_PURCHASES, null);
        return res;
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASES);
        onCreate(db);
    }


}
