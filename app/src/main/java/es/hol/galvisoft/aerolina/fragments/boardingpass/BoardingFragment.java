package es.hol.galvisoft.aerolina.fragments.boardingpass;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.io.File;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.BoardingPassActivity;
import es.hol.galvisoft.aerolina.utils.TimeUtil;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class BoardingFragment extends Fragment {
    private BoardingPassActivity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_boarding, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ParseObject reservation = context.getReservation();
        ((TextView) view.findViewById(R.id.origin)).setText(
                reservation.getParseObject("flight").getParseObject("origin").getString("iata")
                        + "-" + reservation.getParseObject("flight").getParseObject("origin").getString("name")
                        + ", " + reservation.getParseObject("flight").getParseObject("origin").getString("city"));
        view.findViewById(R.id.origin).setSelected(true);
        ((TextView) view.findViewById(R.id.destiny)).setText(
                reservation.getParseObject("flight").getParseObject("destination").getString("iata")
                        + "-" + reservation.getParseObject("flight").getParseObject("destination").getString("name")
                        + ", " + reservation.getParseObject("flight").getParseObject("destination").getString("city"));
        view.findViewById(R.id.destiny).setSelected(true);
        ((TextView) view.findViewById(R.id.date)).setText(
                TimeUtil.DATE_FORMATTER.format(reservation.getParseObject("flight").getDate("departureDate")));
        ((TextView) view.findViewById(R.id.time_round)).setText(
                TimeUtil.HOUR_MILITAR_FORMATTER.format(reservation.getParseObject("flight").getDate("departureDate")));
        ((TextView) view.findViewById(R.id.time_trip)).setText(
                TimeUtil.HOUR_MILITAR_FORMATTER.format(reservation.getParseObject("flight").getDate("arrivalDate")));
        ((TextView) view.findViewById(R.id.price)).setText(
                Double.toString(reservation.getDouble("price")));
        ((TextView) view.findViewById(R.id.seat)).setText(reservation.getParseObject("seat").getString("code"));
        ((TextView) view.findViewById(R.id.class_type)).setText(reservation.getParseObject("classType").getString("name"));
        File imgFile = new File(Environment.getExternalStorageDirectory()
                + File.separator
                + reservation.getObjectId() + ".jpg");

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) view.findViewById(R.id.qr);
            myImage.setImageBitmap(myBitmap);

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = (BoardingPassActivity) activity;
    }
}
