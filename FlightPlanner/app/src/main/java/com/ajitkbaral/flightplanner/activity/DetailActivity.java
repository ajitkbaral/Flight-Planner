package com.ajitkbaral.flightplanner.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.ajitkbaral.flightplanner.R;
import com.ajitkbaral.flightplanner.adapter.FlightAdapter;
import com.ajitkbaral.flightplanner.entity.Flight;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView fromLocation, toLocation, trip, traveller, departureDate, returnDate;
    int travellers=1, tripType=1;
    private Flight flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        fromLocation = findViewById(R.id.fromLocation);
        toLocation = findViewById(R.id.toLocation);
        trip = findViewById(R.id.trip);
        traveller = findViewById(R.id.traveller);
        departureDate = findViewById(R.id.departureDate);
        returnDate = findViewById(R.id.returnDate);
        recyclerView = findViewById(R.id.recyclerViewFlight);

        if (getIntent().getSerializableExtra("flight")!=null) {
            flight = (Flight) getIntent().getSerializableExtra("flight");
            if (!flight.getTrip().equals("One way"))
                tripType = 2;
            fromLocation.setText(flight.getFromLocation());
            toLocation.setText(flight.getToLocation());
            trip.setText(flight.getTrip());
            traveller.setText(String.valueOf(flight.getTraveller()));
            departureDate.setText(flight.getDepartureDate());
            if (!flight.getReturnDate().equals(""))
                returnDate.setText(flight.getReturnDate());
            else
                returnDate.setText("-");
            travellers = flight.getTraveller();

        }
        populateFlight();
    }

    private void populateFlight() {
        List<Flight> flights = new ArrayList<>();

        for (int i=0; i<10; i++) {
            Flight flight = new Flight();
            flight.setFromLocation(fromLocation.getText().toString());
            flight.setToLocation(toLocation.getText().toString());
            flight.setTraveller(travellers);
            flight.setTrip(trip.getText().toString());
            flight.setDepartureDate(departureDate.getText().toString());
            flight.setReturnDate(returnDate.getText().toString());
            flight.setFromTime(String.valueOf(5+i)+":60 AM");
            flight.setToTime(String.valueOf(7+i)+":15 AM");
            flight.setAmount(String.valueOf(travellers * 7590 + i*10*tripType));
            if (i%2==0)
                flight.setImage("buddha_air");
            else
                flight.setImage("yeti_air");


            flights.add(flight);
        }

        recyclerView.setAdapter(new FlightAdapter(flights,
                new FlightAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Flight item) {
                        Intent intent = new Intent(DetailActivity.this, BookFlightActivity.class);
                        intent.putExtra("flight", item);
                        startActivity(intent);
                    }
                }));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
