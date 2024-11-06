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
import com.example.smartguide.databinding.ActivityLahoreBinding;
import com.example.smartguide.weatherSystem.CheckWeather;

import java.util.ArrayList;
import java.util.List;

public class Lahore extends AppCompatActivity {

    EditText Searchtext;
    private ExampleAdapter adapter;
    private List<ExampleItem> exampleList;


    List<String> airPortsLhr = new ArrayList<String>();
    List<String> historicalPlacesLhr = new ArrayList<String>();
    List<String> mosquesLhr = new ArrayList<String>();
    List<String> hospitalsLhr = new ArrayList<String>();
    List<String> restaurantsLhr = new ArrayList<String>();

    DBhelperLahore dbhelper;

    ActivityLahoreBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLahoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbhelper = new DBhelperLahore(this);
        Cursor res = dbhelper.GetCustomerDetails("Lahore");
        if (res.getCount()==0)
        {
            Toast.makeText(Lahore.this, "Data does not exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("User Name : "+res.getString(0)+"\n");
            buffer.append("Review : "+res.getString(1)+"\n");
            buffer.append("City Name : "+res.getString(2)+"\n\n");
        }

        binding.resultReview.setText(buffer.toString());

        // Airports
        airPortsLhr.add("Allama Iqbal International Airport");

        //Historical Places
        historicalPlacesLhr.add("Lahore Fort");
        historicalPlacesLhr.add("Shalamar Garden");
        historicalPlacesLhr.add("Minar-e-Pakistan");
        historicalPlacesLhr.add("Lahore Museum");
        historicalPlacesLhr.add("Iqbal Museum");
        historicalPlacesLhr.add("Quaid-e-Azam Library");
        historicalPlacesLhr.add("Lahore Zoo");
        historicalPlacesLhr.add("Anarkali Bazaar");
        historicalPlacesLhr.add("Joyland");
        historicalPlacesLhr.add("Jallu Park");
        historicalPlacesLhr.add("Bhagh-e-Jinnah");
        historicalPlacesLhr.add("Tomb of Allama Iqbal");
        historicalPlacesLhr.add("Data Darbar");
        historicalPlacesLhr.add("Tomb of Anarkali");
        historicalPlacesLhr.add("Tomb of Noor Jahan");

        //Mosques
        mosquesLhr.add("Badshahi Masjid");
        mosquesLhr.add("Masjid Wazir Khan");
        mosquesLhr.add("Moti Masjid");
        mosquesLhr.add("Sunehri Masjid");

        //Hospitals
        hospitalsLhr.add("Faisal Hospital");
        hospitalsLhr.add("Shalimar Hospital");
        hospitalsLhr.add("Fatima Memorial Hospital");
        hospitalsLhr.add("Mid City Hospital");
        hospitalsLhr.add("Hameed Latif Hospital");
        hospitalsLhr.add("Bahria International Hospital");
        hospitalsLhr.add("Jinah Hospital");

        //restaurants
        restaurantsLhr.add("Haveli Restaurant");
        restaurantsLhr.add("Andaaz Restaurant");
        restaurantsLhr.add("Spice Bazaar");
        restaurantsLhr.add("The Poet Boutique Restaurant");
        restaurantsLhr.add("Monal Restaurant");
        restaurantsLhr.add("Lalquila Restaurant");
        restaurantsLhr.add("Covo Restaurant");
        restaurantsLhr.add("Red Lotus");
        restaurantsLhr.add("Fajiama Restaurant");
        restaurantsLhr.add("Salt and Pepper Village");
        restaurantsLhr.add("The Brasserie");


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
                Lahore.this.filterQuery(editable.toString());
            }
        });
        binding.popupCloseButton.setOnClickListener(v->{
           binding.searchPopup.setVisibility(View.INVISIBLE);
        });
        // search system



        // Air ports
        LinearLayoutManager layoutManagerAP = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewAirport.setLayoutManager(layoutManagerAP);
        RecyclerAdapter adapterAirport=new RecyclerAdapter(this, airPortsLhr);
        binding.recyclerViewAirport.setAdapter(adapterAirport);


        //Historical places
        LinearLayoutManager layoutManagerHP = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewHistoricalPlaces.setLayoutManager(layoutManagerHP);
        RecyclerAdapter adapterHP=new RecyclerAdapter(this, historicalPlacesLhr);
        binding.recyclerViewHistoricalPlaces.setAdapter(adapterHP);

        //Mosques
        LinearLayoutManager layoutManagerM = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewMosques.setLayoutManager(layoutManagerM);
        RecyclerAdapter adapterM=new RecyclerAdapter(this, mosquesLhr);
        binding.recyclerViewMosques.setAdapter(adapterM);

        //Hospitals
        LinearLayoutManager layoutManagerHos = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewHospitals.setLayoutManager(layoutManagerHos);
        RecyclerAdapter adapterHos=new RecyclerAdapter(this, hospitalsLhr);
        binding.recyclerViewHospitals.setAdapter(adapterHos);

        //Restaurants
        LinearLayoutManager layoutManagerRes = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recyclerViewRestaurants.setLayoutManager(layoutManagerRes);
        RecyclerAdapter adapterRes=new RecyclerAdapter(this, restaurantsLhr);
        binding.recyclerViewRestaurants.setAdapter(adapterRes);

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
            Intent intent=new Intent(Lahore.this, CheckWeather.class);
            intent.putExtra("City",binding.textViewCityName.getText().toString());
            startActivity(intent);
        });

        binding.postReview.setOnClickListener(v->{
            Boolean checkInsert=dbhelper.InsertCustomerDetails(binding.editTextName.getText().toString(),binding.editTextReview.getText().toString(),"Lahore");
            Toast.makeText(this, "Review Posted", Toast.LENGTH_SHORT).show();
        });
        binding.textViewCityName.setOnClickListener(v->{

            AlertDialog.Builder builder = new AlertDialog.Builder(Lahore.this);
            builder.setCancelable(true);
            builder.setTitle("Reviews");
            builder.setMessage(buffer.toString());
            builder.show();
        });

    }


    private void fillExampleList() {
        this.exampleList = new ArrayList();
        for (int i=0; i<airPortsLhr.size(); i++){
            this.exampleList.add(new ExampleItem(airPortsLhr.get(i)));
        }
        for (int i=0; i<historicalPlacesLhr.size(); i++){
            this.exampleList.add(new ExampleItem(historicalPlacesLhr.get(i)));
        }
        for (int i=0; i<mosquesLhr.size(); i++){
            this.exampleList.add(new ExampleItem(mosquesLhr.get(i)));
        }
        for (int i=0; i<hospitalsLhr.size(); i++){
            this.exampleList.add(new ExampleItem(hospitalsLhr.get(i)));
        }
        for (int i=0; i<restaurantsLhr.size(); i++){
            this.exampleList.add(new ExampleItem(restaurantsLhr.get(i)));
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