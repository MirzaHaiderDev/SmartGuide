package com.example.smartguide.Cities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.DataBaseForReviews.DBhelperLahore;
import com.example.smartguide.ExampleAdapter;
import com.example.smartguide.ExampleItem;
import com.example.smartguide.R;
import com.example.smartguide.databinding.ActivityIslamabadBinding;
import com.example.smartguide.weatherSystem.CheckWeather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Islamabad extends AppCompatActivity {

    EditText Searchtext;
    private ExampleAdapter adapter;
    private List<ExampleItem> exampleList;

    List<String> airPortsIsl = new ArrayList<String>();
    List<String> historicalPlacesIsl = new ArrayList<String>();
    List<String> mosquesIsl = new ArrayList<String>();
    List<String> hospitalsIsl = new ArrayList<String>();
    List<String> restaurantsIsl = new ArrayList<String>();
    List<String> hotelsIsl = new ArrayList<String>();
    List<String> mallsIsl = new ArrayList<String>();

    DBhelperLahore dbhelper;

    ActivityIslamabadBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIslamabadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbhelper = new DBhelperLahore(this);


        Cursor res = dbhelper.GetCustomerDetails("Islamabad");
        if (res.getCount()==0)
        {
            Toast.makeText(Islamabad.this, "Data does not exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Name : "+res.getString(0)+"\n");
            buffer.append("Review : "+res.getString(1)+"\n");
            buffer.append("City : "+res.getString(2)+"\n\n");
        }
        binding.resultReview.setText(buffer.toString());

        airPortsIsl = Arrays.asList("Islamabad International Airport");
        historicalPlacesIsl = Arrays.asList("Daman-e-Koh","Margalla Hills",
                "Pakistan Monument",
                "Saidpur Village",
                "Bari Imam Shrine",
                "Shahdara Valley",
                "Fatima Church",
                "Pak China Friendship Center",
                "Galrah Shrine",
                "Saudi Pak Tower Building",
                "Parliament House",
                "Jinnah Sports Stadium",
                "Supreme Court of Pakistan"
                );
        mosquesIsl = Arrays.asList("Faisal Masjid" , "Lal Masjid");
        hospitalsIsl = Arrays.asList("Islamabad International Hospital",
                "Islamabad Medical and Surgical Hospital",
                "Life Care International Hospital",
                "Shifa Internation Hospital",
                "Maroof International Hospital");
        restaurantsIsl = Arrays.asList("Tandoori Restaurant F-10",
                "Atrio Cafe and Grills",
                "Monal Islamabad",
                "Kabul Restaurant",
                "Chaaye Khana",
                "KHIVA Restaurant",
                "Des Pardes Restaurant",
                "The Royal Elephant",
                "HOWDY Islamabad",
                "Desi Accent",
                "Sakuru Japanese Mart",
                "OX and Grill Restaurant");
        hotelsIsl = Arrays.asList("Islamabad Sarena Hostel",
                "Islamabad Regalia Hotel",
                "Atlas Hotel",
                "Roomy Signature",
                "Hotel Margala",
                "Hotel Al-Habib",
                "Hotel Red Line",
                "Shelton's Ambassador",
                "Harvey Islamabad");
        mallsIsl = Arrays.asList("Giga Mall",
                "The Centaurus Mall",
                "Mall of Islamabad",
                "The Aquatic Mall",
                "Safa Gold Mall",
                "V8 Mall",
                "Gulberg Arena Mall",
                "Paris Shopping Mall",
                "The Fort Mall",
                "Galleria Islamabad",
                "Florence Islamabad",
                "Whiteley Islamabad",
                "Al-Jannat Mall Islamabad");


        // search system
        fillExampleList();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.adapter = new ExampleAdapter(this.exampleList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.adapter);


        this.Searchtext = (EditText) findViewById(R.id.search_input);
        this.Searchtext.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.searchPopup.setVisibility(View.VISIBLE);
            }

            public void afterTextChanged(Editable editable) {
                Islamabad.this.filterQuery(editable.toString());
            }
        });
        binding.popupCloseButton.setOnClickListener(v->{
            binding.searchPopup.setVisibility(View.INVISIBLE);
        });
        // search system

        // Air ports
        LinearLayoutManager layoutManagerAP = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewAirport.setLayoutManager(layoutManagerAP);
        RecyclerAdapter adapterAirport=new RecyclerAdapter(this, airPortsIsl);
        binding.recyclerViewAirport.setAdapter(adapterAirport);


        //Historical places
        LinearLayoutManager layoutManagerHP = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewHistoricalPlaces.setLayoutManager(layoutManagerHP);
        RecyclerAdapter adapterHP=new RecyclerAdapter(this, historicalPlacesIsl);
        binding.recyclerViewHistoricalPlaces.setAdapter(adapterHP);

        //Mosques
        LinearLayoutManager layoutManagerM = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMosques.setLayoutManager(layoutManagerM);
        RecyclerAdapter adapterM=new RecyclerAdapter(this, mosquesIsl);
        binding.recyclerViewMosques.setAdapter(adapterM);

        //Hospitals
        LinearLayoutManager layoutManagerHos = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewHospitals.setLayoutManager(layoutManagerHos);
        RecyclerAdapter adapterHos=new RecyclerAdapter(this, hospitalsIsl);
        binding.recyclerViewHospitals.setAdapter(adapterHos);

        //Restaurants
        LinearLayoutManager layoutManagerRes = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewRestaurants.setLayoutManager(layoutManagerRes);
        RecyclerAdapter adapterRes=new RecyclerAdapter(this, restaurantsIsl);
        binding.recyclerViewRestaurants.setAdapter(adapterRes);


        //Hotels
        LinearLayoutManager layoutManagerHot = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewHotels.setLayoutManager(layoutManagerHot);
        RecyclerAdapter adapterHot=new RecyclerAdapter(this, hotelsIsl);
        binding.recyclerViewHotels.setAdapter(adapterHot);

        //Malls
        LinearLayoutManager layoutManagerMal = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMalls.setLayoutManager(layoutManagerMal);
        RecyclerAdapter adapterMal=new RecyclerAdapter(this, mallsIsl);
        binding.recyclerViewMalls.setAdapter(adapterMal);


        binding.llHotel.setOnClickListener(v->{
            String url = "https://www.sastaticket.pk/airline/pakistan-international-airlines";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        binding.llRide.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("uber://?action=setPickup&pickup=my_location"));
            startActivity(intent);
        });

        binding.Weather.setOnClickListener(v->{
            Intent intent=new Intent(Islamabad.this, CheckWeather.class);
            intent.putExtra("City",binding.textViewCityName.getText().toString());
            startActivity(intent);
        });

        binding.postReview.setOnClickListener(v->{
            Boolean checkInsert=dbhelper.InsertCustomerDetails(binding.editTextName.getText().toString(),binding.editTextReview.getText().toString(),"Islamabad");
            Toast.makeText(this, "Review Posted", Toast.LENGTH_SHORT).show();
        });
        binding.textViewCityName.setOnClickListener(v->{

            AlertDialog.Builder builder = new AlertDialog.Builder(Islamabad.this);
            builder.setCancelable(true);
            builder.setTitle("Reviews");
            builder.setMessage(buffer.toString());
            builder.show();
        });

       /* String ap = "";
        for (int index=0; index < airPorts.size(); index++) {
            ap = ap + airPorts.get(index)  + "\n";
        }
        binding.textViewAirports.setText(ap);


        String hp = "";
        for (int index=0; index < historicalPlaces.size(); index++) {
            hp = hp  + historicalPlaces.get(index) + "\n";
        }
        binding.textViewHistoricalPlacesList.setText(hp);

        String mosq ="";
        for (int index=0; index < mosques.size(); index++) {
            mosq = mosq  + mosques.get(index) + "\n";
        }
        binding.textViewMosques.setText(mosq);

        String hosp = "";
        for (int index=0; index < hospitals.size(); index++) {
            hosp = hosp  + hospitals.get(index) + "\n";
        }
        binding.textViewHospitals.setText(hosp);

        String res = "";
        for (int index=0; index < restaurants.size(); index++) {
            res = res  + restaurants.get(index) + "\n";
        }
        binding.textViewRestaurants.setText(res);

        String htl = "";
        for (int index=0; index < hotels.size(); index++) {
            htl = htl  + hotels.get(index) + "\n";
        }
        binding.textViewHotels.setText(res);

        String mall = "";
        for (int index=0; index < malls.size(); index++) {
            mall = mall  + malls.get(index) + "\n";
        }
        binding.textViewMalls.setText(res); */


    }


    private void fillExampleList() {


        this.exampleList = new ArrayList();
        for (int i = 0; i < airPortsIsl.size(); i++) {
            this.exampleList.add(new ExampleItem(airPortsIsl.get(i)));
        }
        for (int i = 0; i < historicalPlacesIsl.size(); i++) {
            this.exampleList.add(new ExampleItem(historicalPlacesIsl.get(i)));
        }
        for (int i = 0; i < mosquesIsl.size(); i++) {
            this.exampleList.add(new ExampleItem(mosquesIsl.get(i)));
        }
        for (int i = 0; i < hospitalsIsl.size(); i++) {
            this.exampleList.add(new ExampleItem(hospitalsIsl.get(i)));
        }
        for (int i = 0; i < restaurantsIsl.size(); i++) {
            this.exampleList.add(new ExampleItem(restaurantsIsl.get(i)));
        }
        for (int i=0; i<mallsIsl.size(); i++){
            this.exampleList.add(new ExampleItem(mallsIsl.get(i)));
        }
    }

    public void filterQuery(String text) {
        ArrayList<ExampleItem> filterdNames = new ArrayList<>();
        for (ExampleItem s : this.exampleList) {
            if (s.getText1().toLowerCase().contains(text) ) {
                filterdNames.add(s);
            }
        }
        this.adapter.setFilter(filterdNames);
    }
}