package com.electricity.monitoring;

public class Constant {

    Constant()
    {
        //write your action here if need
    }

    //For retrofit base url must end with /
//    public static final String BASE_URL = "http://192.168.189.70/zetmo/api/";
//    public static final String BASE_URL = "http://192.168.1.200/zetmo/api/";
    public static final String BASE_URL = "https://zetmo.fkultimate.co.zw/api/";
    //We will use this to store the user token number into shared preference
    public static final String SHARED_PREF_NAME = "com.electricity.monitoring";

    public static final String USER_ID = "user_id";
    public static final String DATE = "date";

    public static final String SP_PASSWORD = "password";
    public static final String SP_EMAIL = "email";
    public static final String SP_METER_NUMBER = "meter_number";

    public static final String APPLIANCE_ID = "appliance_id";
    public static final String APPLIANCE_NAME = "appliance_name";
    public static final String APPLIANCE_DESCRIPTION = "appliance_description";
    public static final String APPLIANCE_CONDITION = "appliance_condition";
    public static final String APPLIANCE_CONSUMPTION = "appliance_consumption";
    public static final String APPLIANCE_YEAR = "appliance_year";

    public static final String KEY_EMAIL= "email";
    public static final String KEY_PASSWORD= "password";
    public static final String KEY_ADDRESS= "address";
    public static final String KEY_METER_NUMBER= "meter_number";
    public static final String KEY_FIRST_NAME= "first_name";
    public static final String KEY_LAST_NAME= "last_name";

    public static final String NEIGHBOURHOOD_ID = "neighbourhood_id";
    public static final String NEIGHBOURHOOD_NAME = "neighbourhood_name";
    public static final String NEIGHBOURHOOD_AREA = "neighbourhood_area";

    //table names

    public static final String TABLE_APPLIANCES = "appliances";
    public static final String TABLE_TIME_TRACKING = "time_tracking";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_TARRIFS = "tarrifs";

}

