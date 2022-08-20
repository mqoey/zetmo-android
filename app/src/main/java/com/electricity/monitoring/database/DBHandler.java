package com.electricity.monitoring.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.electricity.monitoring.Constant;
import com.electricity.monitoring.model.Appliance;
import com.electricity.monitoring.model.ApplianceTime;
import com.electricity.monitoring.model.Neighbourhood;
import com.electricity.monitoring.model.Tarrif;
import com.electricity.monitoring.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private static final String DURATION_COL = "duration";
    private static final String IMAGE_COL = "image";
    private static final String APPLIANCE_ID = "appliance_id";
    private static final String THRESHOLD_ID = "threshold_id";
    private static final String DATE_COL = "date";
    private static final String START_TIME_COL = "start_time";
    private static final String END_TIME_COL = "end_time";
    private static final String PRICE_COL = "price";
    private static final String NEIGHBOURHOOD_COL = "neighbourhood";
    private static final String AREA_COL = "area";
    private static final String MUNICIPALITY_COL = "municipality";
    private static final String ENERGY_COL = "energy";
    private static final String FCM_TOKEN_COL = "fcm_token";

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
                + DATE_COL + " TEXT,"
                + DURATION_COL + " TEXT,"
                + CONSUMPTION_COL + " TEXT)";

        String query_2 = "CREATE TABLE " + Constant.TABLE_USERS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + ADDRESS_COL + " TEXT,"
                + PASSWORD_COL + " TEXT,"
                + METER_NUMBER_COL + " TEXT,"
                + FCM_TOKEN_COL + " TEXT,"
                + STATUS_COL + " TEXT)";

        String query_3 = "CREATE TABLE " + Constant.TABLE_TARRIFS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TARRIF_ID_COL + " TEXT,"
                + PRICE_COL + " TEXT,"
                + DATE_COL + " TEXT)";

        String query_4 = "CREATE TABLE " + Constant.TABLE_NEIGHBOURHOODS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NEIGHBOURHOOD_COL + " TEXT,"
                + AREA_COL + " TEXT,"
                + MUNICIPALITY_COL + " TEXT,"
                + STATUS_COL + " TEXT)";

        String query_5 = "CREATE TABLE " + Constant.TABLE_THRESHOLDS + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ENERGY_COL + " TEXT,"
                + STATUS_COL + " TEXT)";

        String query_6 = "CREATE TABLE " + Constant.TABLE_ALARM + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ENERGY_COL + " TEXT,"
                + STATUS_COL + " TEXT)";

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query_1);
        sqLiteDatabase.execSQL(query_2);
        sqLiteDatabase.execSQL(query_3);
        sqLiteDatabase.execSQL(query_4);
        sqLiteDatabase.execSQL(query_5);
        sqLiteDatabase.execSQL(query_6);
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

    public List<String> getAppliancesList() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_APPLIANCES, null);

        List<String> applianceList = new ArrayList<>();

        int length = cursor.getCount();
        int j = 0;
        for (int i = 0; i < length; i++) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                applianceList.add(j, name);
                j++;
            }
        }
        return applianceList;
    }

    public ArrayList<Appliance> getAppliancesByID(String appliance_ID) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
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

    public ArrayList<Appliance> getAppliancesByName(String appliance_name) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_APPLIANCES + " WHERE " + NAME_COL + "=?", new String[]{appliance_name});
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

    public ArrayList<Appliance> getApplianceSearch(String appliance_name) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_APPLIANCES + " WHERE " + NAME_COL + "LIKE" '%", new String[]{appliance_name} + "OR" + DESCRIPTION_COL + "=?", new String[]{appliance_name});
        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM appliances WHERE name LIKE '%" + appliance_name + "%' OR description LIKE '%" + appliance_name + "%' ORDER BY id DESC", null);
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

    public void loginUser(String userID, String name, String email, String password, String address, String meternumber, String fcm_token) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID_COL, userID);
        contentValues.put(NAME_COL, name);
        contentValues.put(EMAIL_COL, email);
        contentValues.put(PASSWORD_COL, password);
        contentValues.put(ADDRESS_COL, address);
        contentValues.put(METER_NUMBER_COL, meternumber);
        contentValues.put(FCM_TOKEN_COL, fcm_token);
        contentValues.put(STATUS_COL, "1");

        sqLiteDatabase.insert(Constant.TABLE_USERS, null, contentValues);
        sqLiteDatabase.close();
    }

    public void logoutUser() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL("delete from " + Constant.TABLE_USERS);
        sqLiteDatabase.close();
    }

    public String loggedInUserEmail() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String user = "";

        Cursor cursorUser = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_USERS + " WHERE " + STATUS_COL + "=?", new String[]{"1"});
        if (cursorUser.moveToFirst()) {
            user = cursorUser.getString(3);
        }
        cursorUser.close();
        return user;
    }

    public String loggedInUserID() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String user = "";

        Cursor cursorUser = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_USERS + " WHERE " + STATUS_COL + "=?", new String[]{"1"});
        if (cursorUser.moveToFirst()) {
            user = cursorUser.getString(1);
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

    public boolean checkNeighbourhood() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT 1 FROM " + Constant.TABLE_NEIGHBOURHOODS + " WHERE " + STATUS_COL + "=?", new String[]{"1"});
        boolean exist = cursor.moveToFirst();
        cursor.close();
        return exist;
    }

    public ArrayList<Neighbourhood> getNeighbourhood() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorUser = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_NEIGHBOURHOODS, null);
        ArrayList<Neighbourhood> userArrayList = new ArrayList<>();

        if (cursorUser.moveToFirst()) {
            userArrayList.add(new Neighbourhood(
                    cursorUser.getString(0),
                    cursorUser.getString(2),
                    cursorUser.getString(1),
                    cursorUser.getString(3)));
        }
        cursorUser.close();
        return userArrayList;
    }

    public void choseNeighbourhood(String neighbourhood, String area, String municipality) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NEIGHBOURHOOD_COL, neighbourhood);
        contentValues.put(AREA_COL, area);
        contentValues.put(MUNICIPALITY_COL, municipality);
        contentValues.put(STATUS_COL, "1");

        sqLiteDatabase.insert(Constant.TABLE_NEIGHBOURHOODS, null, contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<User> getUser() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursorUser = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_USERS, null);
        ArrayList<User> userArrayList = new ArrayList<>();

        if (cursorUser.moveToFirst()) {
            userArrayList.add(new User(
                    cursorUser.getString(0),
                    cursorUser.getString(1),
                    cursorUser.getString(2),
                    cursorUser.getString(2),
                    cursorUser.getString(2),
                    cursorUser.getString(3),
                    cursorUser.getString(4),
                    cursorUser.getString(6),
                    cursorUser.getString(5),
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

    public void startApplianceTimer(String applianceID, String date, String startTime) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(APPLIANCE_ID, applianceID);
        contentValues.put(START_TIME_COL, startTime);
        contentValues.put(END_TIME_COL, "pending");
        contentValues.put(DURATION_COL, "pending");
        contentValues.put(CONSUMPTION_COL, "pending");
        contentValues.put(DATE_COL, date);

        sqLiteDatabase.insert(Constant.TABLE_TIME_TRACKING, null, contentValues);
        sqLiteDatabase.close();
    }

    public void stopApplianceTimer(String applianceID, String date) {
        String start_time = "";
        String end_time = "";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_TIME_TRACKING + " WHERE " + APPLIANCE_ID + "=? AND " + DATE_COL + "=? AND " + END_TIME_COL + "=?", new String[]{applianceID, date, "pending"});
        Date endTime = null, startTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        if (cursor.moveToFirst()) {
            start_time = cursor.getString(2);
        }
        cursor.close();

        try {
            startTime = simpleDateFormat.parse(start_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Calendar calendar = Calendar.getInstance();
            end_time = simpleDateFormat.format(calendar.getTime());
            endTime = simpleDateFormat.parse(end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endTime.getTime() - startTime.getTime();
//        long difference = startTime.getTime();

        if (difference < 0) {
            Date dateMax = null;
            try {
                dateMax = simpleDateFormat.parse("24:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date dateMin = null;
            try {
                dateMin = simpleDateFormat.parse("00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            difference = (dateMax.getTime() - startTime.getTime()) + (endTime.getTime() - dateMin.getTime());
        }


        int hours = (int) (difference / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * hours)) / (1000 * 60);

        String duration = hours + "hrs " + min + "mins";

        ArrayList<Tarrif> tarrifArrayList;
        tarrifArrayList = getTarrif();
        String tarrif = tarrifArrayList.get(0).getPrice();

        double consumption = (difference / (1000 * 60 * 60)) * Double.parseDouble(tarrif);

        contentValues.put(END_TIME_COL, end_time);
        contentValues.put(DURATION_COL, duration);
        contentValues.put(CONSUMPTION_COL, consumption);

        sqLiteDatabase.update(Constant.TABLE_TIME_TRACKING, contentValues, APPLIANCE_ID + "=? AND " + DATE_COL + "=? AND " + END_TIME_COL + "=?", new String[]{applianceID, date, "pending"});
        sqLiteDatabase.close();
    }

    public ArrayList<ApplianceTime> getApplianceDate() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT DISTINCT " + DATE_COL + " FROM " + Constant.TABLE_TIME_TRACKING, null);

        ArrayList<ApplianceTime> applianceArrayList = new ArrayList<>();

        if (cursorAppliances.moveToFirst()) {
            do {
                applianceArrayList.add(new ApplianceTime(
                        cursorAppliances.getString(0),
                        cursorAppliances.getString(0),
                        cursorAppliances.getString(0),
                        cursorAppliances.getString(0),
                        cursorAppliances.getString(0),
                        cursorAppliances.getString(0)));
            } while (cursorAppliances.moveToNext());
        }
        cursorAppliances.close();
        return applianceArrayList;
    }

    public void updateApplianceTimer(String date) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(END_TIME_COL, "23:59:59");

        sqLiteDatabase.update(Constant.TABLE_TIME_TRACKING, contentValues, DATE_COL + "=? AND " + END_TIME_COL + "=?", new String[]{date, "pending"});
        sqLiteDatabase.close();
    }

    public void updatedApplianceTimer(String yesterday, String today) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_TIME_TRACKING + " WHERE " + DATE_COL + "=? AND " + END_TIME_COL + "=?", new String[]{yesterday, "pending"});

        if (cursorAppliances.moveToFirst()) {
            do {
                contentValues.put(APPLIANCE_ID, cursorAppliances.getString(1));
                contentValues.put(START_TIME_COL, "00:00:00");
                contentValues.put(END_TIME_COL, "pending");
                contentValues.put(DATE_COL, today);

                sqLiteDatabase.insert(Constant.TABLE_TIME_TRACKING, null, contentValues);
            } while (cursorAppliances.moveToNext());
        }
        cursorAppliances.close();
    }

    public ArrayList<ApplianceTime> getAppliancesByDate(String date) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursorAppliances = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_TIME_TRACKING + " WHERE " + DATE_COL + "=?", new String[]{date});

        ArrayList<ApplianceTime> applianceTimeArrayList = new ArrayList<>();

        if (cursorAppliances.moveToFirst()) {
            do {
                applianceTimeArrayList.add(new ApplianceTime(
                        cursorAppliances.getString(1),
                        cursorAppliances.getString(2),
                        cursorAppliances.getString(3),
                        cursorAppliances.getString(4),
                        cursorAppliances.getString(5),
                        cursorAppliances.getString(6)));
            } while (cursorAppliances.moveToNext());
        }
        cursorAppliances.close();
        return applianceTimeArrayList;
    }

    public void addThreshold(String energy) {
        String new_energy = "";
        long newThreshold = 0, oldThreshold = 0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        newThreshold = Long.parseLong(energy);
        oldThreshold = Long.parseLong(checkThreshold());

        new_energy = String.valueOf(newThreshold + oldThreshold);

        contentValues.put(ENERGY_COL, new_energy);
        contentValues.put(STATUS_COL, "1");

        sqLiteDatabase.execSQL("delete from " + Constant.TABLE_THRESHOLDS);
        sqLiteDatabase.insert(Constant.TABLE_THRESHOLDS, null, contentValues);
        sqLiteDatabase.close();
    }

    public String checkThreshold() {
        String threshold ="";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constant.TABLE_THRESHOLDS + " WHERE " + STATUS_COL + "=?", new String[]{"1"});
        if (cursor.moveToFirst()) {
            threshold = cursor.getString(1);
        }
        else {
            ContentValues contentValues = new ContentValues();

            contentValues.put(ENERGY_COL, "0");
            contentValues.put(STATUS_COL, "1");

            sqLiteDatabase.execSQL("delete from " + Constant.TABLE_THRESHOLDS);
            sqLiteDatabase.insert(Constant.TABLE_THRESHOLDS, null, contentValues);
            sqLiteDatabase.close();
        }
        cursor.close();
        return threshold;
    }
    public void addAlarm(String energy) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ENERGY_COL, energy);
        contentValues.put(STATUS_COL, "0");

        sqLiteDatabase.insert(Constant.TABLE_ALARM, null, contentValues);
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