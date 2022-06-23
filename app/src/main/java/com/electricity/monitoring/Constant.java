package com.electricity.monitoring;

public class Constant {

    Constant()
    {
        //write your action here if need
    }

    //For retrofit base url must end with /
    public static final String BASE_URL = "https://zetmo.fkultimate.co.zw/api/";
    //We will use this to store the user token number into shared preference
    public static final String SHARED_PREF_NAME = "com.electricity.monitoring";

    public static final String SP_PASSWORD = "password";
    public static final String SP_EMAIL = "email";
    public static final String SP_METER_NUMBER = "meter_number";

    public static final String APPLIANCE_ID = "appliance_id";
    public static final String APPLIANCE_NAME = "appliance_name";
    public static final String APPLIANCE_DESCRIPTION = "appliance_description";
    public static final String APPLIANCE_CONDITION = "appliance_condition";
    public static final String APPLIANCE_CONSUMPTION = "appliance_consumption";
    public static final String APPLIANCE_YEAR = "appliance_year";

    //all table names
    public static final String KEY_EMAIL= "email";
    public static final String KEY_PASSWORD= "password";
    public static final String KEY_ADDRESS= "address";
    public static final String KEY_METER_NUMBER= "meter_number";
    public static final String KEY_FIRST_NAME= "first_name";
    public static final String KEY_LAST_NAME= "last_name";

}

