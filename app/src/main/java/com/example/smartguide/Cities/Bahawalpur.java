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
import com.example.smartguide.databinding.ActivityBahawalpurBinding;
import com.example.smartguide.weatherSystem.CheckWeather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bahawalpur extends AppCompatActivity {

    EditText Searchtext;
    private ExampleAdapter adapter;
    private List<ExampleItem> exampleList;

    List<String> airPorts = new ArrayList<String>();
    List<String> historicalPlaces = new ArrayList<String>();
    List<String> mosques = new ArrayList<String>();
    List<String> hospitals = new ArrayList<String>();
    List<String> restaurants = new ArrayList<String>();
    List<String> hotels = new ArrayList<String>();
    List<String> malls = new ArrayList<String>();

    DBhelperLahore dbhelper;

    private ActivityBahawalpurBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBahawalpurBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbhelper = new DBhelperLahore(this);
        Cursor res = dbhelper.GetCustomerDetails("Bahawalpur");
        if (res.getCount()==0)
        {
            Toast.makeText(Bahawalpur.this, "Data does not exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("User Name : "+res.getString(0)+"\n");
            buffer.append("Review : "+res.getString(1)+"\n");
            buffer.append("City Name : "+res.getString(2)+"\n\n");
        }

        binding.resultReview.setText(buffer.toString());

        airPorts = Arrays.asList("Bahawalpur Airport");
        historicalPlaces = Arrays.asList("Noor Mahal",
                "Darbar Mahal",
                "Derawar Fort",
                "Tomb of Bibi Jawindi",
                "Abbasi Jamia Masjid Qila Derawar",
                "Head Panjnad",
                "Dubai Palace",
                "Bahawalpur Museum",
                "The Abbasi Royal Graveyard",
                "Gulzar-e-Sadiq",
                "Rohi Park",
                "Bahawalpur Water Lake",
                "Fawara Chowk Bahawalpur");
        mosques = Arrays.asList("Al-Sadiq Mosque",
                "Jamia Masjid Al-Haqq Bahawalpur",
                "Masjid Toheed Bahwalwapur",
                "Ghousia Masjid Bahawalpur",
                "Haji Masjid Muhammad Sharif Bahwalpur");
        hospitals = Arrays.asList("");
        restaurants = Arrays.asList("Panda, A Family Restaurant",
                "Kababish Grill",
                "Salt and pepper",
                "Libra Valley",
                "4 Seasons Restaurants",
                "City Cafe and Grill",
                "Saffron Cafe, Bakery & Restaurant",
                "BBQ Tonight Bahawalpur",
                "LeGrande Cafe",
                "Meeruth Grill House",
                "Lataska Restaurants",
                "Kebabish Nihari",
                "Lez Grill",
                "Lazeezo Restaurants",
                "Habit Restaurants",
                "Subway");
        hotels = Arrays.asList("Hotel One Bahawalpur",
                "Hotel Executive Lodges",
                "5th Avenue Hotel",
                "Royal Mark Hotel Bahawalpur",
                "Paradise Hotel inn",
                "Alines Hotel",
                "Luxury Hotel");
        malls = Arrays.asList("City Mall Bahawalpur",
                "Bahawalpur Trade Centre",
                "Master Mall",
                "Arena Mall",
                "SS Tower Shopping Mall",
                "Apex Mall Bhawalpur",
                "Bahwalpur Pace Centre",
                "The Bouleverd",
                "Bobby Plaza",
                "Pelican Mall DHA Bahawalpur",
                "Haqqi Centre");

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
                Bahawalpur.this.filterQuery(editable.toString());
            }
        });
        binding.popupCloseButton.setOnClickListener(v->{
            binding.searchPopup.setVisibility(View.INVISIBLE);
        });
        // search system


        // Air ports
        LinearLayoutManager layoutManagerAP = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewAirport.setLayoutManager(layoutManagerAP);
        RecyclerAdapter adapterAirport=new RecyclerAdapter(this,airPorts);
        binding.recyclerViewAirport.setAdapter(adapterAirport);


        //Historical places
        LinearLayoutManager layoutManagerHP = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewHistoricalPlaces.setLayoutManager(layoutManagerHP);
        RecyclerAdapter adapterHP=new RecyclerAdapter(this,historicalPlaces);
        binding.recyclerViewHistoricalPlaces.setAdapter(adapterHP);

        //Mosques
        LinearLayoutManager layoutManagerM = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMosques.setLayoutManager(layoutManagerM);
        RecyclerAdapter adapterM=new RecyclerAdapter(this,mosques);
        binding.recyclerViewMosques.setAdapter(adapterM);

        //Hospitals
        LinearLayoutManager layoutManagerHos = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewHospitals.setLayoutManager(layoutManagerHos);
        RecyclerAdapter adapterHos=new RecyclerAdapter(this,hospitals);
        binding.recyclerViewHospitals.setAdapter(adapterHos);

        //Restaurants
        LinearLayoutManager layoutManagerRes = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewRestaurants.setLayoutManager(layoutManagerRes);
        RecyclerAdapter adapterRes=new RecyclerAdapter(this,restaurants);
        binding.recyclerViewRestaurants.setAdapter(adapterRes);


        //Hotels
        LinearLayoutManager layoutManagerHot = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewHotels.setLayoutManager(layoutManagerHot);
        RecyclerAdapter adapterHot=new RecyclerAdapter(this,hotels);
        binding.recyclerViewHotels.setAdapter(adapterHot);

        //Malls
        LinearLayoutManager layoutManagerMal = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMalls.setLayoutManager(layoutManagerMal);
        RecyclerAdapter adapterMal=new RecyclerAdapter(this,malls);
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
            Intent intent=new Intent(Bahawalpur.this, CheckWeather.class);
            intent.putExtra("City",binding.textViewCityName.getText().toString());
            startActivity(intent);
        });
        binding.postReview.setOnClickListener(v->{
            Boolean checkInsert=dbhelper.InsertCustomerDetails(binding.editTextName.getText().toString(),binding.editTextReview.getText().toString(),"Bahawalpur");
            Toast.makeText(this, "Review Posted", Toast.LENGTH_SHORT).show();
        });
        binding.textViewCityName.setOnClickListener(v->{



            AlertDialog.Builder builder = new AlertDialog.Builder(Bahawalpur.this);
            builder.setCancelable(true);
            builder.setTitle("Reviews");
            builder.setMessage(buffer.toString());
            builder.show();
        });

    }

    private void fillExampleList() {


        this.exampleList = new ArrayList();
        for (int i = 0; i < airPorts.size(); i++) {
            this.exampleList.add(new ExampleItem(airPorts.get(i)));
        }
        for (int i = 0; i < historicalPlaces.size(); i++) {
            this.exampleList.add(new ExampleItem(historicalPlaces.get(i)));
        }
        for (int i = 0; i < mosques.size(); i++) {
            this.exampleList.add(new ExampleItem(mosques.get(i)));
        }
        for (int i = 0; i < hospitals.size(); i++) {
            this.exampleList.add(new ExampleItem(hospitals.get(i)));
        }
        for (int i = 0; i < restaurants.size(); i++) {
            this.exampleList.add(new ExampleItem(restaurants.get(i)));
        }
        for (int i=0; i<malls.size(); i++){
            this.exampleList.add(new ExampleItem(malls.get(i)));
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