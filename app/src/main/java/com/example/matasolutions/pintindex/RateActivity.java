package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RateActivity extends AppCompatActivity {

    Pub pub;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    DrinkType drinkType;
    Brand brand;
    Amount amount;

    ArrayList<Pub> pubsWithProduct;

    Button actionButton;


    Button sort_high_to_low;
    Button sort_low_to_high;

    FirebaseDatabase database;
    DatabaseReference myRef;


    Button button_submit;

    Profile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        profile = new Profile();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("userData");

        String name = (String) getIntent().getSerializableExtra("name");

        pub = getIntent().getExtras().getParcelable("pub");


        final ArrayList<RatingEntry> ratingEntries = new ArrayList<>();

        ratingEntries.add(new RatingEntry(RatingType.ATMOSPHERE));
        ratingEntries.add(new RatingEntry(RatingType.HYGIENE));
        ratingEntries.add(new RatingEntry(RatingType.SERVICE));
        ratingEntries.add(new RatingEntry(RatingType.VALUE_FOR_PRICE));


        recyclerView =  findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(ratingEntries);


        recyclerView.setAdapter(mAdapter);

        //info_view.setText(((MyAdapter) mAdapter).getCallback());

        button_submit = findViewById(R.id.button_submit);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (profile.CheckIfNotRatedYet(pub.ID)) {

                    for (int i = 0; i < ratingEntries.size(); i++) {

                        String msg = ratingEntries.get(i).ratingType.toString() + String.valueOf(ratingEntries.get(i).input_rating);

                        Log.i("TAG", msg);

                        RatingEntry thisEntry = ratingEntries.get(i);


                        pub.ratings.AddNewEntry(thisEntry);
                        profile.ratingEntries.add(thisEntry);
                        profile.ratedPubIds.add(pub.ID);
                        String id = profile.user_uID;


                        profile.pubRatingEntries.add(new PubRatingEntry(pub.getID(),profile.ratingEntries));
                    }

                    myRef.child(profile.user_uID).child("ratingEntries").setValue(profile.pubRatingEntries);
                    Intent intent = new Intent(getApplicationContext(), PubActivity.class);

                    intent.putExtra("pub", pub);

                    Bundle args = new Bundle();
                    args.putParcelable("coordinates", pub.coordinates);
                    intent.putExtra("bundle", args);


                    startActivity(intent);


                }
            }

        });



    }




    //Adapter

    public  class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        public ArrayList<RatingEntry> mDataset;

        public String callback;

        public String setCallback(String data){
            return callback = data;
        }

        public String getCallback(){
            return callback;
        }

        public OnItemClick mCallback;


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder



        //View Holder

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case

            public TextView ratingType;

            public ImageView star_1;
            public ImageView star_2 ;
            public ImageView star_3;
            public ImageView star_4;
            public ImageView star_5;

            RatingBar ratingBar;


            public MyViewHolder(final View v) {
                super(v);

                ratingBar = v.findViewById(R.id.rating_stars);

                ratingType = v.findViewById(R.id.rating_type);


            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<RatingEntry> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.ratingcategory, parent, false);

            MyAdapter.MyViewHolder vh = new MyAdapter.MyViewHolder(v);
            return vh;
        }


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final RatingEntry rating = mDataset.get(position);

            // Set item views based on your views and data model

            TextView ratingType = holder.ratingType;

            final RatingBar ratingBar = holder.ratingBar;


            ratingType.setText(rating.ratingType.toString());

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    rating.input_rating = ratingBar.getRating();
                    Log.i("TAG", "RATING IS: " + rating.input_rating);

                }
            });
        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }


    }

}
