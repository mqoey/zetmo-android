package com.electricity.monitoring.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.electricity.monitoring.model.Appliance;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "zetmodb";
    private static final String TABLE_NAME = "appliances";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String CONDITION_COL = "condition";
    private static final String DESCRIPTION_COL = "description";
    private static final String YEARS_COL = "years";
    private static final String CONSUMPTION_COL = "consumption";
    private static final String IMAGE_COL = "image";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + CONDITION_COL + " TEXT,"
                + YEARS_COL + " TEXT,"
                + CONSUMPTION_COL + " TEXT,"
                + IMAGE_COL + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addAppliance(String applianceName, String applianceDescription, String applianceCondition, String applianceYears, String applianceConsumption, String applianceImage) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME_COL, applianceName);
        contentValues.put(DESCRIPTION_COL, applianceDescription);
        contentValues.put(CONDITION_COL, applianceCondition);
        contentValues.put(IMAGE_COL, applianceImage);
        contentValues.put(CONSUMPTION_COL, applianceConsumption);
        contentValues.put(YEARS_COL, applianceYears);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<Appliance> getAppliances() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<Appliance> applianceArrayList = new ArrayList<>();

        if (cursorAppliances.moveToFirst()) {
            do {
                applianceArrayList.add(new Appliance(
                        cursorAppliances.getString(0),
                        cursorAppliances.getString(1),
                        cursorAppliances.getString(2),
                        cursorAppliances.getString(3),
                        cursorAppliances.getString(4),
                        cursorAppliances.getString(5),
                        cursorAppliances.getString(6)));
            } while (cursorAppliances.moveToNext());
        }
        cursorAppliances.close();
        return applianceArrayList;
    }

    public ArrayList<Appliance> getAppliancesByID(String appliance_ID) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL + " = " + appliance_ID, null);

        ArrayList<Appliance> applianceArrayList = new ArrayList<>();

        if (cursorAppliances.moveToFirst()) {
            do {
                applianceArrayList.add(new Appliance(
                        cursorAppliances.getString(0),
                        cursorAppliances.getString(1),
                        cursorAppliances.getString(2),
                        cursorAppliances.getString(3),
                        cursorAppliances.getString(4),
                        cursorAppliances.getString(5),
                        cursorAppliances.getString(6)));
            } while (cursorAppliances.moveToNext());
        }
        cursorAppliances.close();
        return applianceArrayList;
    }

    public void deleteAppliance(String applianceID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID_COL + "=?",new String[]{applianceID});
        sqLiteDatabase.close();
    }
}