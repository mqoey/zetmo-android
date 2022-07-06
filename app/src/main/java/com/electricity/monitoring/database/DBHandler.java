package com.electricity.monitoring.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.electricity.monitoring.Constant;
import com.electricity.monitoring.model.Appliance;
import com.electricity.monitoring.model.Tarrif;
import com.electricity.monitoring.model.User;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "zetmodb";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String ADDRESS_COL = "address";
    private static final String PASSWORD_COL = "password";
    private static final String METER_NUMBER_COL = "meternumber";
    private static final String USER_ID_COL = "userID";
    private static final String TARRIF_ID_COL = "tarridID";
    private static final String EMAIL_COL = "email";
    private static final String STATUS_COL = "status";
    private static final String CONDITION_COL = "condition";
    private static final String DESCRIPTION_COL = "description";
    private static final String YEARS_COL = "years";
    private static final String CONSUMPTION_COL = "consumption";
    private static final String IMAGE_COL = "image";
    private static final String APPLIANCE_ID = "appliance_id";
    private static final String DATE_COL = "date";
    private static final String START_TIME_COL = "start_time";
    private static final String END_TIME_COL = "end_time";
    private static final String PRICE_COL = "price";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + Constant.TABLE_APPLIANCES + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + CONDITION_COL + " TEXT,"
                + YEARS_COL + " TEXT,"
                + CONSUMPTION_COL + " TEXT,"
                + IMAGE_COL + " TEXT)";

        String query_1 = "CREATE TABLE " + Constant.TABLE_TIME_TRACKING + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + APPLIANCE_ID + " TEXT,"
                + START_TIME_COL + " TEXT,"
                + END_TIME_COL + " TEXT,"
                + DATE_COL + " TEXT)";

        String query_2 = "CREATE TABLE " + Constant.TABLE_USERS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + ADDRESS_COL + " TEXT,"
                + PASSWORD_COL + " TEXT,"
                + METER_NUMBER_COL + " TEXT,"
                + STATUS_COL + " TEXT)";

        String query_3 = "CREATE TABLE " + Constant.TABLE_TARRIFS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TARRIF_ID_COL + " TEXT,"
                + PRICE_COL + " TEXT,"
                + DATE_COL + " TEXT)";

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query_1);
        sqLiteDatabase.execSQL(query_2);
        sqLiteDatabase.execSQL(query_3);
    }

    public void addAppliance(String applianceName, String applianceDescription, String applianceCondition, String applianceYears, String applianceConsumption, String applianceImage) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME_COL, applianceName);
        contentValues.put(DESCRIPTION_COL, applianceDescription);
        contentValues.put(CONDITION_COL, applianceCondition);
        contentValues.put(IMAGE_COL, applianceImage);
        contentValues.put(CONSUMPTION_COL, applianceConsumption);
        contentValues.put(YEARS_COL, applianceYears);

        sqLiteDatabase.insert(Constant.TABLE_APPLIANCES, null, contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<Appliance> getAppliances() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_APPLIANCES, null);

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

//        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_APPLIANCES + ID_COL + " =? " + appliance_ID, null);
Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_APPLIANCES + " WHERE " + "id" + "=?", new String[]{appliance_ID});
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

    public void deleteAppliance(String applianceID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Constant.TABLE_APPLIANCES, ID_COL + "=?", new String[]{applianceID});
        sqLiteDatabase.close();
    }

    public int updateAppliance(String applianceID, String applianceName, String applianceDescription, String applianceCondition, String applianceYears, String applianceConsumption, String applianceImage) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME_COL, applianceName);
        contentValues.put(DESCRIPTION_COL, applianceDescription);
        contentValues.put(CONDITION_COL, applianceCondition);
        contentValues.put(IMAGE_COL, applianceImage);
        contentValues.put(CONSUMPTION_COL, applianceConsumption);
        contentValues.put(YEARS_COL, applianceYears);

        Integer status = sqLiteDatabase.update(Constant.TABLE_APPLIANCES, contentValues, ID_COL + "=?", new String[]{applianceID});
        sqLiteDatabase.close();

        return status;
    }

    public void loginUser(String userID, String name, String email, String password, String address, String meternumber) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID_COL, userID);
        contentValues.put(NAME_COL, name);
        contentValues.put(EMAIL_COL, email);
        contentValues.put(PASSWORD_COL, password);
        contentValues.put(ADDRESS_COL, address);
        contentValues.put(METER_NUMBER_COL, meternumber);
        contentValues.put(STATUS_COL, "1");

        sqLiteDatabase.insert(Constant.TABLE_USERS, null, contentValues);
        sqLiteDatabase.close();
    }

    public void logoutUser() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL("delete from " + Constant.TABLE_USERS);
        sqLiteDatabase.close();
    }

    public String loggedInUser() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String user = "";

        Cursor cursorUser = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_USERS + " WHERE " + STATUS_COL + "=?", new String[]{"1"});
        if (cursorUser.moveToFirst()) {
            user = cursorUser.getString(3);
        }
        cursorUser.close();
        return user;
    }

    public boolean checkLogin() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT 1 FROM " + Constant.TABLE_USERS + " WHERE " + STATUS_COL + "=?", new String[]{"1"});
        boolean exist = cursor.moveToFirst();
        cursor.close();
        return exist;
    }

    public ArrayList<User> getUser() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursorUser = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_USERS, null);

        ArrayList<User> userArrayList = new ArrayList<>();

        if (cursorUser.moveToFirst()) {
            userArrayList.add(new User(
                    cursorUser.getString(0),
                    cursorUser.getString(2),
                    cursorUser.getString(2),
                    cursorUser.getString(2),
                    cursorUser.getString(3),
                    cursorUser.getString(4),
                    cursorUser.getString(6),
                    cursorUser.getString(5)));
        }
        cursorUser.close();
        return userArrayList;
    }

    public ArrayList<Tarrif> getTarrif() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursorTarrif = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_TARRIFS, null);

        ArrayList<Tarrif> tarrifArrayList = new ArrayList<>();

        if (cursorTarrif.moveToFirst()) {
            tarrifArrayList.add(new Tarrif(
                    cursorTarrif.getString(1),
                    cursorTarrif.getString(2),
                    cursorTarrif.getString(3)));
        }
        cursorTarrif.close();
        return tarrifArrayList;
    }

    public void fetchTarrifs(String date, String price, String tarrifID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TARRIF_ID_COL, tarrifID);
        contentValues.put(PRICE_COL, price);
        contentValues.put(DATE_COL, date);

        sqLiteDatabase.insert(Constant.TABLE_TARRIFS, null, contentValues);
        sqLiteDatabase.close();
    }

    public void startApplianceTimer(String applianceID, String date, String startTime){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(APPLIANCE_ID, applianceID);
        contentValues.put(START_TIME_COL, startTime);
        contentValues.put(END_TIME_COL, "pending");
        contentValues.put(DATE_COL, date);

        sqLiteDatabase.insert(Constant.TABLE_TIME_TRACKING, null, contentValues);
        sqLiteDatabase.close();
    }

    public void stopApplianceTimer(String applianceID, String date, String endTime){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(END_TIME_COL, endTime);

        sqLiteDatabase.update(Constant.TABLE_TIME_TRACKING, contentValues, APPLIANCE_ID + "=? AND " + DATE_COL + "=? AND " + END_TIME_COL + "=?", new String[]{applianceID, date, "pending"});
        sqLiteDatabase.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_APPLIANCES);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_TIME_TRACKING);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_USERS);
        onCreate(sqLiteDatabase);
    }
}