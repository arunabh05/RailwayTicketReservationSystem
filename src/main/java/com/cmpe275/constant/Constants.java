package com.cmpe275.constant;

/**
 * @author arunabh.shrivastava
 */
public class Constants {

    public static Long DEFAULT_TRAIN_CAPACITY = new Long(1000);
    public static final String NORTHBOUND_NAME_PREFIX = "NB";
    public static final String SOUTHBOUND_NAME_PREFIX = "SB";

    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
    public static final long STOP_DURATION = 3 * ONE_MINUTE_IN_MILLIS;
    public static final long TRAVEL_TIME = 5 * ONE_MINUTE_IN_MILLIS;
}
