package com.example.evote.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evote.R;
import com.example.evote.home.HomeActivity;
import com.example.evote.sigin.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    TextView nourutId;
    TextView dNama, dNim, dProdi, dAngkatan, dKeterangan, dVisi, dMisi;
    ImageView potoCalon, pickem;
    Button btnDetail;
    private FirebaseFirestore db;
    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nourutId = findViewById(R.id.idFrom);
        dNama = findViewById(R.id.d_nama);
        dNim = findViewById(R.id.d_nim);
        dProdi = findViewById(R.id.d_prodi);
        dAngkatan = findViewById(R.id.d_angkatan);
        dKeterangan = findViewById(R.id.d_keterangan);
        dVisi = findViewById(R.id.d_visi);
        dMisi = findViewById(R.id.d_misi);
        potoCalon = findViewById(R.id.fotoDetail);

        nourutId.setText(getIntent().getStringExtra("foto"));
        dNama.setText(getIntent().getStringExtra("nama"));
        dNim.setText(getIntent().getStringExtra("nim"));
        dProdi.setText(getIntent().getStringExtra("prodi"));
        dKeterangan.setText(getIntent().getStringExtra("detail2"));
        dAngkatan.setText(getIntent().getStringExtra("angkatan"));
        dVisi.setText(getIntent().getStringExtra("visi"));
        dMisi.setText(getIntent().getStringExtra("misi"));
        String fotoImage = nourutId.getText().toString();
        Picasso.get().load(fotoImage).fit().into(potoCalon);

        pickem = findViewById(R.id.btn_detail);

        pickem.setOnClickListener(v -> {
            Intent i = new Intent(DetailActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        });

    }
}
