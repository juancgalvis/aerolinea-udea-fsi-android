package es.hol.galvisoft.aerolina.services;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Creado por Juan Carlos el dia 20/07/2015.
 */
public class QRTask extends AsyncTask<String, Void, Bitmap> {
    private String reservationCode;

    @Override
    protected Bitmap doInBackground(String... params) {
        reservationCode = params[1];
        return HttpManager.getBitmapFromURL(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        FileOutputStream fos;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator
                + reservationCode + ".jpg");
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(bytes.toByteArray());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
