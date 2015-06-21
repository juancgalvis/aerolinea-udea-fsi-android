package es.hol.galvisoft.aerolina.fragments.find;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import es.hol.galvisoft.aerolina.R;
import es.hol.galvisoft.aerolina.data.AirportDataManager;
import es.hol.galvisoft.aerolina.fragments.CustomDialogFragment;
import es.hol.galvisoft.aerolina.model.Airport;

/**
 * Creado por Juan Carlos el dia 31/05/2015.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private TextView tvOriginCity, tvDestinyCity, tvRoundDate, tvTripDate;
    private CheckBox cbRoundTrip;
    private List<Airport> airports;
    private long roundAirport, tripAirport;
    private String originName, destinationName, roundDate, tripDate;
    private Activity context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_round: {
                selectRoundDate();
                break;
            }
            case R.id.date_trip: {
                selectTripDate();
                break;
            }
            case R.id.destiny: {
                selectDestiny();
                break;
            }
            case R.id.origin: {
                selectOrigin();
                break;
            }
        }
    }

    private void configureUI() {
        AirportDataManager airportDM = new AirportDataManager(context);
        airports = airportDM.getAll();
        tvOriginCity = (TextView) context.findViewById(R.id.origin);
        tvOriginCity.setSelected(true);
        tvOriginCity.setOnClickListener(this);
        tvDestinyCity = (TextView) context.findViewById(R.id.destiny);
        tvDestinyCity.setSelected(true);
        tvDestinyCity.setOnClickListener(this);
        tvRoundDate = (TextView) context.findViewById(R.id.date_round);
        tvRoundDate.setOnClickListener(this);
        tvTripDate = (TextView) context.findViewById(R.id.date_trip);
        tvTripDate.setOnClickListener(this);
        final TableRow tableRow = (TableRow) context.findViewById(R.id.row_date_trip);
        cbRoundTrip = (CheckBox) context.findViewById(R.id.roundtrip);
        cbRoundTrip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tableRow.setVisibility(View.VISIBLE);
                } else {
                    tableRow.setVisibility(View.GONE);
                }
            }
        });
        if (roundDate != null) {
            tvRoundDate.setText(roundDate);
            tvOriginCity.setText(originName);
            tvDestinyCity.setText(destinationName);
            if (tripDate != null) {
                tvTripDate.setText(tripDate);
                cbRoundTrip.setChecked(true);
            }
        }
    }

    public void selectOrigin() {
        DialogFragment dialogFragment = new CustomDialogFragment();
        ((CustomDialogFragment) dialogFragment).setAirports(airports);
        Bundle args = new Bundle();
        args.putInt(CustomDialogFragment.TYPE, CustomDialogFragment.ORIGIN_CITY);
        dialogFragment.setArguments(args);
        dialogFragment.show(context.getFragmentManager(), "cities");
    }

    public void selectDestiny() {
        List<Airport> destAirports = new ArrayList<>();
        for (Airport airport : airports) {
            if (airport.getId() != roundAirport) {
                destAirports.add(airport);
            }
        }
        DialogFragment dialogFragment = new CustomDialogFragment();
        ((CustomDialogFragment) dialogFragment).setAirports(destAirports);
        Bundle args = new Bundle();
        args.putInt(CustomDialogFragment.TYPE, CustomDialogFragment.DESTINY_CITY);
        dialogFragment.setArguments(args);
        dialogFragment.show(context.getFragmentManager(), "cities");
    }

    public void selectRoundDate() {
        DialogFragment dialogFragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putInt(CustomDialogFragment.TYPE, CustomDialogFragment.DATE_ROUND);
        dialogFragment.setArguments(args);
        dialogFragment.show(context.getFragmentManager(), "cities");
    }

    public void selectTripDate() {
        if (TextUtils.isEmpty(tvRoundDate.getText())) {
            showToast(getResources().getString(R.string.should_round_date_first));
            return;
        }
        DialogFragment dialogFragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putInt(CustomDialogFragment.TYPE, CustomDialogFragment.DATE_TRIP);
        dialogFragment.setArguments(args);
        dialogFragment.show(context.getFragmentManager(), "cities");
    }

    public void setOriginCity(long code, String name) {
        this.originName = name;
        this.tvOriginCity.setText(name);
        this.tvOriginCity.setContentDescription(Long.toString(code));
        roundAirport = code;
        if (roundAirport == tripAirport) {
            setDestinyCity(0, null);
        }
    }

    public long getOriginCity() {
        return roundAirport;
    }

    public void setDestinyCity(long code, String name) {
        this.destinationName = name;
        this.tvDestinyCity.setText(name);
        this.tvDestinyCity.setContentDescription(Long.toString(code));
        tripAirport = code;
    }

    public long getDestinyCity() {
        return tripAirport;
    }

    public boolean isRoundTrip() {
        return cbRoundTrip.isChecked();
    }

    public void setDateRound(int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        GregorianCalendar today = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        GregorianCalendar roundDate = new GregorianCalendar(year, monthOfYear - 1, dayOfMonth);
        if (today.getTimeInMillis() <= roundDate.getTimeInMillis()) {
            this.roundDate = formatearFecha(year, monthOfYear, dayOfMonth);
            tvRoundDate.setText(this.roundDate);
            tvTripDate.setText(null);
        } else {
            showToast(getResources().getString(R.string.bad_round_date));
        }
    }

    public String getDateRound() {
        return tvRoundDate.getText().toString();
    }

    public void setDateTrip(int year, int monthOfYear, int dayOfMonth) {
        String round[] = TextUtils.split(tvRoundDate.getText().toString(), "-");
        GregorianCalendar roundDate = new GregorianCalendar(Integer.parseInt(round[2]), Integer.parseInt(round[1]) - 1, Integer.parseInt(round[0]));
        GregorianCalendar tripDate = new GregorianCalendar(year, monthOfYear - 1, dayOfMonth);
        if (roundDate.getTimeInMillis() <= tripDate.getTimeInMillis()) {
            this.tripDate = formatearFecha(year, monthOfYear, dayOfMonth);
            tvTripDate.setText(this.tripDate);
        } else {
            showToast(getResources().getString(R.string.bad_trip_date));
        }
    }

    public String getDateTrip() {
        return tvTripDate.getText().toString();
    }

    public boolean next() {
        boolean error = false;
        if (TextUtils.isEmpty(tvOriginCity.getText())) {
            error = true;
            ((TextView) context.findViewById(R.id.origin_required)).setTextColor(Color.RED);
        } else {
            ((TextView) context.findViewById(R.id.origin_required)).setTextColor(Color.WHITE);
        }
        if (TextUtils.isEmpty(tvDestinyCity.getText())) {
            error = true;
            ((TextView) context.findViewById(R.id.destiny_required)).setTextColor(Color.RED);
        } else {
            ((TextView) context.findViewById(R.id.destiny_required)).setTextColor(Color.WHITE);
        }
        if (TextUtils.isEmpty(tvRoundDate.getText())) {
            error = true;
            ((TextView) context.findViewById(R.id.date_round_required)).setTextColor(Color.RED);
        } else {
            ((TextView) context.findViewById(R.id.date_round_required)).setTextColor(Color.WHITE);
        }
        if (TextUtils.isEmpty(tvTripDate.getText()) && cbRoundTrip.isChecked()) {
            error = true;
            ((TextView) context.findViewById(R.id.date_trip_required)).setTextColor(Color.RED);
        } else {
            ((TextView) context.findViewById(R.id.date_trip_required)).setTextColor(Color.WHITE);
        }
        if (error) {
            showToast(getResources().getString(R.string.required_fields_find));
        }
        return !error;
    }

    private String formatearFecha(int year, int month, int day) {
        String mes = Integer.toString(month);
        String dia = Integer.toString(day);
        if (month < 10) {
            mes = "0" + mes;
        }
        if (day < 10) {
            dia = "0" + dia;
        }
        return (year + "-" + mes + "-" + dia);
    }

    private void showToast(String resource) {
        Toast.makeText(context, resource, Toast.LENGTH_SHORT).show();
    }

}
