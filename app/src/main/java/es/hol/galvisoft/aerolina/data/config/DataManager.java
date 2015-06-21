package es.hol.galvisoft.aerolina.data.config;

import android.content.Context;


public class DataManager {

    public static final String DB_NAME = "aerolinea.db";
    public static final int DB_VERSION = 1;
    public static boolean REINSTALL_ON_UPGRADE = true;
    protected DataBaseHelper helper;


    public DataManager(Context ctx) {
        helper = new DataBaseHelper(ctx, DB_NAME, DB_VERSION);
    }
}
