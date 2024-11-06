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
import com.example.smartguide.databinding.ActivityMultanBinding;
import com.example.smartguide.weatherSystem.CheckWeather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Multan extends AppCompatActivity {

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

    private ActivityMultanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMultanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbhelper = new DBhelperLahore(this);
        Cursor res = dbhelper.GetCustomerDetails("Multan");
        if (res.getCount()==0)
        {
            Toast.makeText(Multan.this, "Data does not exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("User Name : "+res.getString(0)+"\n");
            buffer.append("Review : "+res.getString(1)+"\n");
            buffer.append("City Name : "+res.getString(2)+"\n\n");
        }

        binding.resultReview.setText(buffer.toString());

        airPorts = Arrays.asList("Multan International Airport");
        historicalPlaces = Arrays.asList("Tomb of Shah Rukn e Alam",
                "Tomb of Shah Gardez",
                "Multan Fort",
                "Ghanta Ghar, Multan",
                "Bibi Pak Daman Mausoleum",
                "Patrick Alexander Vans Agnew Monument",
                "Multan Art Council",
                "Shrine of Baha-ud-Din Zakariya");
        mosques = Arrays.asList("Eid Ghah",
                "Sawi Masjid",
                "Markazi Laal Jamia Masjid",
                "Khuddaka Mosque",
                "Jamy Masjid Madni Chowk",
                "Abdali Masjid Multan",
                "Al-Muzaffar Mosque",
                "Qasim Bela Masjid",
                "Masjid Wapda Town");
        hospitals = Arrays.asList("Buch International Hospital",
                "Mukhtar A Shaikh Hospital",
                "Bakhtawar Amin Medical and Dental College",
                "Al Kareem Medical and Surgical Complex",
                "Haleema Hospital Complex",
                "Fatima Medical Centre",
                "Multan Medical Complex",
                "National Diagnostic Centre");
        restaurants = Arrays.asList("Ramada Multan",
                "Bundu Khan",
                "Zanziban",
                "Shangrilla",
                "Shahjahan grill",
                "Tasty Plus Restaurant",
                "Almaida Pizaa Garden",
                "Bar-B-Q Tonight",
                "X Fries");
        hotels = Arrays.asList("New Pride Hotel",
                "Hotel Sarena Palace",
                "Hotel Grace inn",
                "Hotel Crown inn",
                "Hotel One Tariq Road Multan",
                "Avari Xpress Multan",
                "Ramada by Wyndham Multan",
                "Hotel Pak Continental",
                "S Chalet Multan",
                "Avari Boutique Multan",
                "Belmorris  Hotels & Resorts",
                "Hotel Mid City");

        malls = Arrays.asList("");

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
               Multan.this.filterQuery(editable.toString());
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
            Intent intent=new Intent(Multan.this, CheckWeather.class);
            intent.putExtra("City",binding.textViewCityName.getText().toString());
            startActivity(intent);
        });

        binding.postReview.setOnClickListener(v->{
            Boolean checkInsert=dbhelper.InsertCustomerDetails(binding.editTextName.getText().toString(),binding.editTextReview.getText().toString(),"Multan");
            Toast.makeText(this, "Review Posted", Toast.LENGTH_SHORT).show();
        });
        binding.textViewCityName.setOnClickListener(v->{



            AlertDialog.Builder builder = new AlertDialog.Builder(Multan.this);
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