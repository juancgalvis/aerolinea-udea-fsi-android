package es.hol.galvisoft.aerolina.fragments.myflights;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.activities.BoardingPassActivity;
import es.hol.galvisoft.aerolina.activities.MyFlightsActivity;
import es.hol.galvisoft.aerolina.adapters.SeatAdapter;
import es.hol.galvisoft.aerolina.services.QRTask;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class SeatFragment extends Fragment implements AdapterView.OnItemClickListener {
    private MyFlightsActivity context;
    private GridView gvSeats;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (MyFlightsActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myflights_select_seat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureUI();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final ParseObject seat = (ParseObject) gvSeats.getAdapter().getItem(position);
        final ParseObject reservation = context.getReservation();
        reservation.put("checkin", true);
        reservation.put("seat", seat);
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Realizando checkin", "Por favor espere...", true, false);
        reservation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    QRTask qrTask = new QRTask() {
                        @Override
                        protected void onPostExecute(Bitmap bitmap) {
                            super.onPostExecute(bitmap);
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Checkin Realizado correctamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), BoardingPassActivity.class));
                            getActivity().finish();
                        }
                    };
                    String qr = "{" +
                            "    \"user\": \"" + ParseUser.getCurrentUser().getObjectId() + "\"," +
                            "    \"reservation\": \"" + reservation.getObjectId() + "\"," +
                            "    \"seat\": \"" + seat.getObjectId() + "\"," +
                            "    \"flight\": \"" + reservation.getParseObject("flight").getObjectId() + "\"" +
                            "}";
                    qrTask.execute(qr, reservation.getObjectId());
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void configureUI() {
        gvSeats = (GridView) context.findViewById(R.id.gv_seats);
        gvSeats.setOnItemClickListener(this);
        ParseQuery<ParseObject> querySeats = ParseQuery.getQuery("Seat");
        querySeats.whereEqualTo("classType", context.getReservation().get("classType"));
        querySeats.orderByAscending("code");
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Consultando asientos disponibles", "Por favor espere...", true, false);
        querySeats.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> seats, ParseException e) {
                if (e == null) {
                    gvSeats.setAdapter(new SeatAdapter(context, R.layout.item_reservations, seats));
                } else {
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                try {
                    progressDialog.dismiss();
                } catch (Exception ignored) {
                }
            }
        });
    }

    public boolean next() {
        return true;
    }

}
