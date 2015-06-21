package es.hol.galvisoft.aerolina.data.config;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String EXTENSION = ".sql";
    public static final int INITIAL_VERSION = 1;


    private Context ctx;
    private String name;
    private int version;

    public DataBaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.ctx = context;
        this.name = name;
        this.version = version;
    }

    private String[] parseSQLSentences(String sqlfileContent) {
        //Regex: ";(?=([^']*'[^']*')*[^']*$)"
        String otherThanQuote = " [^'] ";
        String quotedString = String.format(" ' %s* ' ", otherThanQuote);
        String regex = String.format("(?x) " + // enable comments, ignore white spaces
                        ";                         " + // match a semi colon
                        "(?=                       " + // start positive look ahead
                        "  (                       " + //   start group 1
                        "    %s*                   " + //     match 'otherThanQuote' zero or more times
                        "    %s                    " + //     match 'quotedString'
                        "  )*                      " + //   end group 1 and repeat it zero or more times
                        "  %s*                     " + //   match 'otherThanQuote'
                        "  $                       " + // match the end of the string
                        ")                         ", // stop positive look ahead
                otherThanQuote, quotedString, otherThanQuote);

        return sqlfileContent.split(regex);
    }

    private void runSqlFile(SQLiteDatabase db, String filename) {
        try {
            Log.d("drainer", "Ejecutando archivo SQL: " + filename);
            String sqlfileContent = AssetsHelper.readFileAsset(ctx, filename);
            String[] sentences = parseSQLSentences(sqlfileContent);
            for (String sql : sentences)
                db.execSQL(sql);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }


    private void applyPatch(SQLiteDatabase db, int v) {
        String filename = this.name + "." + v + EXTENSION;

        if (v == INITIAL_VERSION && !AssetsHelper.fileExists(ctx, filename)) {
            runSqlFile(db, this.name + EXTENSION);
            return;
        }

        runSqlFile(db, filename);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("drainer", "Creating database");
        applyPatch(db, INITIAL_VERSION);

        if (version > INITIAL_VERSION)
            doUpgrade(db, INITIAL_VERSION, version);
    }

    private void doCreate(SQLiteDatabase db) {
        Log.d("drainer", "Creating database");
        applyPatch(db, INITIAL_VERSION);
    }

    private void doUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Load each upgrade to the new Version
        Log.d("drainer", "Upgrading Database");
        for (int v = oldVersion + 1; v <= newVersion; v++)
            applyPatch(db, v);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;

        if (DataManager.REINSTALL_ON_UPGRADE) {
            //Delete it
            ctx.deleteDatabase(name);
            db = ctx.openOrCreateDatabase(name, newVersion, null);
            doCreate(db);
        }

        doUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
//		Log.d("drainer", "Opening Database");
        super.onOpen(db);
    }

    @Override
    public synchronized void close() {
//		Log.d("drainer", "Closing Helper");
        super.close();
    }

    public Context getContext() {
        return ctx;
    }


}
