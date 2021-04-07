package com.example.evote.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.evote.StartsActivity;
import com.example.evote.about.AboutActivity;
import com.example.evote.R;
import com.example.evote.hasilvote.VoteActivity;
import com.example.evote.profile.ProfileActivity;
import com.example.evote.sigin.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeActivity extends AppCompatActivity {

    private RecyclerView home_listview;
    private List<ModelCalon> calon_model;
    private CalonAdapter calonAdapter;
    private ProgressBar loadBar;
    private ImageView doneView;
    private FirebaseFirestore db;
    public String TAG;
    public String vote = "sudah";

    int hoursToGo = 0;
    int minutesToGo = 0;
    int secondsToGo = 30;

    int millisToGo = secondsToGo*1000+minutesToGo*1000*60+hoursToGo*1000*60*60;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent iProfil = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(iProfil);
                return true;
            case R.id.item2:
                Intent iVote = new Intent(HomeActivity.this, VoteActivity.class);
                startActivity(iVote);
                return true;
            case R.id.item3:
                Intent iAbout = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(iAbout);
                return true;
            case R.id.item4:
                FirebaseAuth.getInstance().signOut();
                Intent iLogout = new Intent(HomeActivity.this, LoginActivity.class);
                iLogout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(iLogout);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        calon_model   = new ArrayList<>();
        home_listview = this.findViewById( R.id.home_listview);
        doneView = this.findViewById(R.id.doneText);
        loadBar = this.findViewById(R.id.load_bar);
        calonAdapter  = new CalonAdapter(calon_model);
        home_listview.setLayoutManager( new LinearLayoutManager( this ) );
        home_listview.setAdapter( calonAdapter );

        new CountDownTimer(millisToGo,3000) {

            @Override
            public void onTick(long millis) {
                int seconds = (int) (millis / 1000) % 60 ;
                int minutes = (int) ((millis / (1000*60)) % 60);
                int hours   = (int) ((millis / (1000*60*60)) % 24);
            }

            @Override
            public void onFinish() {
                Intent i = new Intent(HomeActivity.this, StartsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        }.start();

        db = FirebaseFirestore.getInstance( );
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            System.out.println("Login Dolo");
        } else {
            db.collection("users").document(user.getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String getVote = Objects.requireNonNull(documentSnapshot).getString("vote");
                    if(Objects.equals(getVote, vote)) {
                        loadBar.setVisibility(View.INVISIBLE);
                        home_listview.setVisibility(View.INVISIBLE);
                        doneView.setVisibility(View.VISIBLE);
                    } else {
                        loadBar.setVisibility(View.INVISIBLE);
                        home_listview.setVisibility(View.VISIBLE);
                        doneView.setVisibility(View.INVISIBLE);
                    }

                }
            });

        }

        db.collection( "calon" ).addSnapshotListener((queryDocumentSnapshots, e) -> {
            for (DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                if (doc.getType()== DocumentChange.Type.ADDED){
                    ModelCalon modelCalon = doc.getDocument().toObject( ModelCalon.class );
                    calon_model.add( modelCalon );

                    calonAdapter.notifyDataSetChanged();
                }
            }
        });

    }

}
