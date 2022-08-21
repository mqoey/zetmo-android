package com.electricity.monitoring;

public class Constant {

//    public static final String BASE_URL = "http://192.168.1.200/zetmo/api/";
    public static final String BASE_URL = "https://zetmo.zapu1961.org.zw/api/";

    //We will use this to store the user token number into shared preference
    public static final String SHARED_PREF_NAME = "com.electricity.monitoring";

    public static final String DATE = "date";

    public static final String SP_PASSWORD = "password";
    public static final String SP_METER_NUMBER = "meter_number";

    public static final String AREA_ID = "area_id";
    public static final String APPLIANCE_ID = "appliance_id";

    public static final String KEY_EMAIL= "email";
    public static final String KEY_PASSWORD= "password";
    public static final String KEY_ADDRESS= "address";
    public static final String KEY_METER_NUMBER= "meter_number";
    public static final String KEY_FIRST_NAME= "first_name";
    public static final String KEY_LAST_NAME= "last_name";
    public static final String KEY_FCM_TOKEN= "fcm_token";

    //table names

    public static final String TABLE_APPLIANCES = "appliances";
    public static final String TABLE_TIME_TRACKING = "time_tracking";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_TARRIFS = "tarrifs";
    public static final String TABLE_NEIGHBOURHOODS = "neighbourhoods";
    public static final String TABLE_THRESHOLDS = "thresholds";
    public static final String TABLE_ALARM = "alarm";

}