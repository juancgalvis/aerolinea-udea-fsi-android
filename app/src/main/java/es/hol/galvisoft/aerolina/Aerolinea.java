package es.hol.galvisoft.aerolina;

import android.app.Application;

import com.parse.Parse;

/**
 * Creado por Juan Carlos el dia 19/07/2015.
 */
public class Aerolinea extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "0HCUh6LvopdYVtTKiNnWpvpISGzownaAgXW6rVIo", "Uo9bdBIvQFmHwOSijlFBJstHdqIS5Ik2G147lJ0s");
    }
}