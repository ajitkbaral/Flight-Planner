package com.ajitkbaral.flightplanner.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajitkbaral.flightplanner.R;
import com.ajitkbaral.flightplanner.entity.Flight;
import com.squareup.picasso.Picasso;

public class BookFlightActivity extends AppCompatActivity {

    private ImageView image;
    private TextView airways, fromLocation, toLocation, trip, traveller, departureDate, returnDate, totalAmount, fromTime, toTime;
    private Button payFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_flight);
        image = findViewById(R.id.image);
        airways = findViewById(R.id.airways);
        fromTime = findViewById(R.id.fromTime);
        toTime = findViewById(R.id.toTime);
        fromLocation = findViewById(R.id.fromLocation);
        toLocation = findViewById(R.id.toLocation);
        trip = findViewById(R.id.trip);
        traveller = findViewById(R.id.traveller);
        departureDate = findViewById(R.id.departureDate);
        returnDate = findViewById(R.id.returnDate);
        totalAmount = findViewById(R.id.totalAmount);
        payFlight = findViewById(R.id.pay_flight);

        if (getIntent().getSerializableExtra("flight")!=null) {
            Flight flight = (Flight) getIntent().getSerializableExtra("flight");
            fromLocation.setText(flight.getFromLocation());
            toLocation.setText(flight.getToLocation());
            trip.setText(flight.getTrip());
            traveller.setText(String.valueOf(flight.getTraveller()));
            departureDate.setText(flight.getDepartureDate());
            returnDate.setText(flight.getReturnDate());
            fromTime.setText(flight.getFromTime());
            toTime.setText(flight.getToTime());
            totalAmount.setText("NRP "+flight.getAmount()+"/-");
            if (flight.getImage().equals("buddha_air")) {
                airways.setText("Buddha Airways");
            } else {
                airways.setText("Yeti Airways");
            }
            Picasso.get().load(getResources().getIdentifier(flight.getImage(), "drawable", getPackageName())).into(image);

        }

        payFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payFlight.setEnabled(false);
                AlertDialog.Builder dialog = new AlertDialog.Builder(BookFlightActivity.this);
                dialog.setTitle("Confirm Payment");
                dialog.setMessage("Do you want to book the flight?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(BookFlightActivity.this, "Your flight has been booked", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BookFlightActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        payFlight.setEnabled(true);
                    }
                });
                dialog.show();
            }
        });
    }
}
