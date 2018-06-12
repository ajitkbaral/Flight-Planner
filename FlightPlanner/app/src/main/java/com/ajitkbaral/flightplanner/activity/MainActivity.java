package com.ajitkbaral.flightplanner.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ajitkbaral.flightplanner.R;
import com.ajitkbaral.flightplanner.entity.Flight;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Context mContext;
    private ImageView image;
    private EditText departureDate, returnDate, selectedEditText;
    private DatePickerDialog dpd;
    private RadioGroup radioTrip;
    private RadioButton radioTripButton;
    private Button searchFlight;
    private Spinner spinnerFromLocation, spinnerToLocation, spinnerTraveller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        image =  findViewById(R.id.image);
        Picasso.get().load(R.drawable.flight).into(image);
        radioTrip = findViewById(R.id.radioTrip);
        departureDate = findViewById(R.id.departure_date);
        returnDate = findViewById(R.id.return_date);
        spinnerFromLocation = findViewById(R.id.spinnerFrom);
        spinnerToLocation = findViewById(R.id.spinnerTo);
        spinnerTraveller = findViewById(R.id.spinnerTraveller);
        searchFlight = findViewById(R.id.search_flight);

        init();
        radioTripChangeListener();

        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog();
                selectedEditText = (EditText) v;
            }

        });

        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog();
                selectedEditText = (EditText) v;
            }

        });

        searchFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!departureDate.getText().toString().isEmpty()) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    Flight flight = new Flight();
                    flight.setFromLocation(spinnerFromLocation.getSelectedItem().toString());
                    flight.setToLocation(spinnerToLocation.getSelectedItem().toString());
                    flight.setTraveller(Integer.parseInt(spinnerTraveller.getSelectedItem().toString()));
                    flight.setTrip(radioTripButton.getText().toString());
                    flight.setDepartureDate(departureDate.getText().toString());
                    flight.setReturnDate(returnDate.getText().toString());
                    intent.putExtra("flight", flight);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Please select Departure Date", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void radioTripChangeListener() {
        radioTrip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioTripButton = findViewById(checkedId);
                switch (checkedId) {
                    case R.id.radioOneWay:
                        if (!returnDate.getText().toString().isEmpty())
                            returnDate.setText("");
                        returnDate.setEnabled(false);
                        break;
                    case R.id.radioRoundTrip:
                        returnDate.setEnabled(true);
                        break;
                }
            }
        });
    }

    private void init() {
        returnDate.setEnabled(false);
        radioTripButton = findViewById(R.id.radioOneWay);

    }

    private void dateDialog() {
        Calendar now = Calendar.getInstance();
                /*
                It is recommended to always create a new instance whenever you need to show a Dialog.
                The sample app is reusing them because it is useful when looking for regressions
                during testing
                 */
        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    MainActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            dpd.initialize(
                    MainActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = ""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        selectedEditText.setText(date);
    }
}
