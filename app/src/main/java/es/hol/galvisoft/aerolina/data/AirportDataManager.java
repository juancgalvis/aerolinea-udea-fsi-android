package es.hol.galvisoft.aerolina.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.hol.galvisoft.aerolina.data.config.DataManager;
import es.hol.galvisoft.aerolina.model.Airport;

public class AirportDataManager extends DataManager {

    // ++++++++++++++++++++++ ATRIBUTOS ++++++++++++++++++++++

    public static final String TABLE_NAME = "airport";

    public static final int COL_ID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_CITY = 2;
    public static final int COL_IATA = 3;

    public static final String[] COLUMNS = {"id", "name", "city", "iata"};

    public AirportDataManager(Context ctx) {
        super(ctx);
    }

    public List<Airport> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMNS[COL_IATA], null);

        ArrayList<Airport> list = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext()) {
            list.add(getAirportFromCursor(cursor));
        }

        cursor.close();
        helper.close();

        return list;
    }

    private Airport getAirportFromCursor(Cursor cursor) {
        Airport row = new Airport();
        row.setId(cursor.getInt(COL_ID));
        row.setName(cursor.getString(COL_NAME));
        row.setCity(cursor.getString(COL_CITY));
        row.setIata(cursor.getString(COL_IATA));
        return row;
    }

    public Airport getAirport(Integer id) {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMNS[COL_ID] + " = ?", new String[]{String.valueOf(id)});

        Airport row = null;

        if (cursor.moveToNext()) {
            row = getAirportFromCursor(cursor);
        }

        cursor.close();
        helper.close();

        return row;
    }

}