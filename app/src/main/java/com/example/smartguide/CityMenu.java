package com.example.smartguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartguide.Cities.Bahawalpur;
import com.example.smartguide.Cities.Islamabad;
import com.example.smartguide.Cities.Lahore;
import com.example.smartguide.Cities.Multan;
import com.example.smartguide.Cities.Sialkot;
import com.example.smartguide.databinding.ActivityCityMenuBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CityMenu extends AppCompatActivity {


    EditText Searchtext;
    private ExampleAdapter adapter;
    private List<ExampleItem> exampleList;



    // For Islamabad
    List<String> Isl = new ArrayList<String>();
    List<String> airPortsIsl = new ArrayList<String>();
    List<String> historicalPlacesIsl = new ArrayList<String>();
    List<String> mosquesIsl = new ArrayList<String>();
    List<String> hospitalsIsl = new ArrayList<String>();
    List<String> restaurantsIsl = new ArrayList<String>();
    List<String> hotelsIsl = new ArrayList<String>();
    List<String> mallsIsl = new ArrayList<String>();

    // For Lahore
    List<String> Lhr = new ArrayList<String>();
    List<String> airPortsLhr = new ArrayList<String>();
    List<String> historicalPlacesLhr = new ArrayList<String>();
    List<String> mosquesLhr = new ArrayList<String>();
    List<String> hospitalsLhr = new ArrayList<String>();
    List<String> restaurantsLhr = new ArrayList<String>();


    private ActivityCityMenuBinding binding;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDataIntoCityLists();


        // github
        fillExampleList();

        // setting near me popup
        binding.nearMe.setOnClickListener(v->{
            binding.linearLayoutCityMenu.setVisibility(View.INVISIBLE);
            binding.frameLayoutSearch.setVisibility(View.INVISIBLE);

            binding.nearmePopup.setVisibility(View.VISIBLE);
        });

        binding.nearMeCloseButton.setOnClickListener(v->{
            binding.nearmePopup.setVisibility(View.INVISIBLE);

            binding.linearLayoutCityMenu.setVisibility(View.VISIBLE);
            binding.frameLayoutSearch.setVisibility(View.VISIBLE);
        });
        binding.nearmeHotels.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://maps.google.com/?q="+"Hotels"));
            v.getContext().startActivity(intent);
        });
        binding.nearmeWashrooms.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://maps.google.com/?q="+"Washrooms"));
            v.getContext().startActivity(intent);
        });
        binding.nearmeATM.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://maps.google.com/?q="+"ATM"));
            v.getContext().startActivity(intent);
        });
        binding.nearmePark.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://maps.google.com/?q="+"park"));
            v.getContext().startActivity(intent);
        });
        binding.nearmeHospitals.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://maps.google.com/?q="+"hospitals"));
            v.getContext().startActivity(intent);
        });
        binding.nearmeMosque.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://maps.google.com/?q="+"mosque"));
            v.getContext().startActivity(intent);
        });
        binding.nearmeMarts.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://maps.google.com/?q="+"Mart"));
            v.getContext().startActivity(intent);
        });
        binding.nearmeFuel.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://maps.google.com/?q="+"gas"));
            v.getContext().startActivity(intent);
        });
        binding.nearmeParking.setOnClickListener(v->{
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("https://maps.google.com/?q="+"parking"));
            v.getContext().startActivity(intent);
        });
        // setting extras popup
        binding.Extras.setOnClickListener(v->{
            binding.linearLayoutCityMenu.setVisibility(View.INVISIBLE);
            binding.frameLayoutSearch.setVisibility(View.INVISIBLE);

            binding.extrasPopup.setVisibility(View.VISIBLE);
        });

        binding.extrasCloseButton.setOnClickListener(v->{
            binding.extrasPopup.setVisibility(View.INVISIBLE);

            binding.linearLayoutCityMenu.setVisibility(View.VISIBLE);
            binding.frameLayoutSearch.setVisibility(View.VISIBLE);
        });

        binding.CurencyConverter.setOnClickListener(v->{
           Intent intent = new Intent(CityMenu.this , CurrencyConverter.class);
           startActivity(intent);
        });

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
                CityMenu.this.filterQuery(editable.toString());
            }
        });
        // github



        // initializing data for search system
        Lhr.addAll(airPortsLhr);
        Lhr.addAll(historicalPlacesLhr);
        Lhr.addAll(mosquesLhr);
        Lhr.addAll(hospitalsLhr);
        Lhr.addAll(restaurantsLhr);

        Isl.addAll(airPortsIsl);
        Isl.addAll(historicalPlacesIsl);
        Isl.addAll(mosquesIsl);
        Isl.addAll(hospitalsIsl);
        Isl.addAll(restaurantsIsl);
        Isl.addAll(hotelsIsl);
        Isl.addAll(mallsIsl);




        System.out.println("______________________________");
        for(int a=0;a<Isl.size();a++){
               System.out.println(Isl.get(a));
        }


        // code for shared prefrences to keep user loged in
        sp = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("logedin" , 1);
        editor.commit();

        binding.llExplore.setOnClickListener(v->{
            /*Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("uber://?action=setPickup&pickup=my_location"));
            startActivity(intent);
            Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();*/
        });


        binding.popupCloseButton.setOnClickListener(v->{
            binding.searchPopup.setVisibility(View.INVISIBLE);
        });

        binding.llProfile.setOnClickListener(v->{
            Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
        });

        binding.FLIslamabad.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), Islamabad.class);
            startActivity(intent);
        });

        binding.FLLahore.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), Lahore.class);
            startActivity(intent);
        });

        binding.FLSialkot.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), Sialkot.class);
            startActivity(intent);
        });
        binding.FLBahawalpur.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), Bahawalpur.class);
            startActivity(intent);
        });
        binding.FLMultan.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), Multan.class);
            startActivity(intent);
        });


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

    private void fillExampleList() {
        this.exampleList = new ArrayList();
        this.exampleList.add(new ExampleItem("Islamabad"));
        this.exampleList.add(new ExampleItem("Lahore"));
        this.exampleList.add(new ExampleItem("Sialkot"));
        this.exampleList.add(new ExampleItem("Multan"));
        this.exampleList.add(new ExampleItem("Bahawalpur"));
    }

    public void setDataIntoCityLists(){
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

        // Islamabad
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


    }
}
