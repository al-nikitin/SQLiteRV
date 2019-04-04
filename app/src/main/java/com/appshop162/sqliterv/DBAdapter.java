package com.appshop162.sqliterv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBAdapter {
    private static final String DATABASE_NAME = "sqlite.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "elements";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COLOR = "_color";
    public static final String COLUMN_TEXT = "_text";

    private String[] allColumns = { COLUMN_ID, COLUMN_COLOR, COLUMN_TEXT };
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_COLOR + " text not null, " +
            COLUMN_TEXT + " text not null" + ");";

    private SQLiteDatabase sqlDB;
    private Context context;
    private DBHelper dbHelper;

    public DBAdapter(Context context) {
        this.context = context;
    }

    public DBAdapter open() throws SQLException{
        dbHelper = new DBHelper(context);
        sqlDB = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ListElement createListElement() {
        ListElement listElement = new ListElement();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLOR, listElement.imageColor.name());
        values.put(COLUMN_TEXT, listElement.text);

        long insertID = sqlDB.insert(TABLE_NAME, null, values);
        listElement.id = insertID;
        Cursor cursor = sqlDB.query(TABLE_NAME, allColumns, COLUMN_ID + " = " + insertID, null, null, null, null);
        cursor.close();
        return listElement;
    }

    public void deleteListElement(long idToDelete) {
        sqlDB.delete(TABLE_NAME, COLUMN_ID + " = " + idToDelete, null);
    }

    public long updateListElement(long idToUpdate, ListElement.ImageColor imageColor, String text) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COLOR, imageColor.name());
        values.put(COLUMN_TEXT, text);

        return sqlDB.update(TABLE_NAME, values, COLUMN_ID + " = " + idToUpdate, null);
    }

    public ArrayList<ListElement> getAllListElements() {
        ArrayList<ListElement> elements = new ArrayList<>();
        Cursor cursor = sqlDB.query(TABLE_NAME, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            ListElement listElement = cursorToElement(cursor);
            elements.add(listElement);
        }

        cursor.close();
        return elements;
    }

    private ListElement cursorToElement(Cursor cursor) {
        ListElement listElement = new ListElement(ListElement.ImageColor.valueOf(cursor.getString(1)), cursor.getString(2), cursor.getInt(0));
        return listElement;
    }

    private static class DBHelper extends SQLiteOpenHelper {
        DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(CREATE_TABLE);
            onCreate(db);
        }
    }
}