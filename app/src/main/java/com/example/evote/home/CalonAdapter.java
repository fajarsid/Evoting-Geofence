package com.example.evote.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evote.R;
import com.example.evote.detail.DetailActivity;
import com.example.evote.hasilvote.VoteActivity;
import com.example.evote.sigin.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.BoundType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CalonAdapter extends RecyclerView.Adapter<CalonAdapter.ViewHolder> {

    private List<ModelCalon> model_calon;
    private FirebaseFirestore db;
    private String TAG;
    public String vote = "sudah";


    CalonAdapter(List<ModelCalon> model_calon) {
        this.model_calon = model_calon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calon_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String desc_data = model_calon.get(position).getDetail2();
        String nama_data = model_calon.get(position).getNama();
        String norut_data = model_calon.get(position).getNourut();
        String nim_data = model_calon.get(position).getNim();
        String prodi_data = model_calon.get(position).getProdi();
        String angkatan_data = model_calon.get(position).getAngkatan();
        String visi_data = model_calon.get(position).getVisi();
        String misi_data = model_calon.get(position).getMisi();
        String tombol_data = "vote";
        String tombol_detail_data = "detail";
        String image_data = model_calon.get(position).getFoto();
        holder.setNorutview(norut_data);
        holder.setNimview(nim_data);
        holder.setProdiview(prodi_data);
        holder.setAngkatanview(angkatan_data);
        holder.setVisiview(visi_data);
        holder.setMisiview(misi_data);
        holder.setImageView(image_data);
        holder.setTombolDetail(tombol_detail_data);
        holder.setDescView(desc_data);
        holder.setNamaview(nama_data);
        holder.setTombol(tombol_data);

    }

    @Override
    public int getItemCount() {
        return model_calon.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private TextView norutview, namaview, descView, nimview, prodiview, angkatanview, visiview, misiview, fotoview;
        private View mviews;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            mviews = itemView;
        }

        void setNimview(String nimText) {
            nimview = mviews.findViewById(R.id.part1);
            nimview.setText(nimText);
        }

        void setProdiview(String prodiText) {
            prodiview = mviews.findViewById(R.id.part2);
            prodiview.setText(prodiText);
        }

        void setAngkatanview(String angkatanText) {
            angkatanview = mviews.findViewById(R.id.part3);
            angkatanview.setText(angkatanText);
        }

        void setVisiview(String visiText) {
            visiview = mviews.findViewById(R.id.part4);
            visiview.setText(visiText);
        }

        void setMisiview(String misiText) {
            misiview = mviews.findViewById(R.id.part5);
            misiview.setText(misiText);
        }

        void setNorutview(String norutText) {
            norutview = mviews.findViewById(R.id.calon_norut);
            norutview.setText(norutText);
        }

        void setDescView(String desText) {
            descView = mviews.findViewById(R.id.calon_desc);
            descView.setText(desText);
        }

        void setImageView(String potoImage) {
            ImageView imageView = mviews.findViewById(R.id.blog_image);
            Picasso.get().load(potoImage).fit().into(imageView);
            fotoview = mviews.findViewById(R.id.part6);
            fotoview.setText(potoImage);
        }

        void setNamaview(String namaText) {
            namaview = mviews.findViewById(R.id.calon_name);
            namaview.setText(namaText);
        }

        void setTombolDetail(String tombolDetailText) {
            ImageView tombolDetailView = mviews.findViewById(R.id.blog_image);
//            tombolDetailView.setText(tombolDetailText);
            tombolDetailView.setOnClickListener(v -> {
                final Intent intent;
                intent = new Intent(context, DetailActivity.class);
                intent.putExtra("boni", norutview.getText().toString());
                intent.putExtra("nama", namaview.getText().toString());
                intent.putExtra("nim", nimview.getText().toString());
                intent.putExtra("detail2", descView.getText().toString());
                intent.putExtra("prodi", prodiview.getText().toString());
                intent.putExtra("angkatan", angkatanview.getText().toString());
                intent.putExtra("visi", visiview.getText().toString());
                intent.putExtra("misi", misiview.getText().toString());
                intent.putExtra("foto", fotoview.getText().toString());
                context.startActivity(intent);
            });
        }

        void setTombol(String tombolText) {
            String boni = norutview.getText().toString();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            db = FirebaseFirestore.getInstance();
            Button tombolview = mviews.findViewById(R.id.btn_v);
            tombolview.setText(tombolText);
            tombolview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDialogVote(v);
                }

                private void callDialogLoad(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getRootView().getContext());
                    alertDialogBuilder.setTitle("Mohon Tunggu...");
                    alertDialogBuilder
                            .setMessage("Trimakasih Telah Berpartisipasi")
                            .setCancelable(false);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                private void callDialogVote(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(
                            v.getRootView().getContext());
                    builder.setMessage("Anda Yakin?")
                            .setCancelable(false)
                            .setPositiveButton("Pilih",
                                    (dialog, id) -> {
                                        callDialogLoad(v);
                                        db.collection("calon").whereEqualTo("nourut", boni).get().addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                    String idCalon = documentSnapshot.getId();
                                                    String urutCalon = documentSnapshot.getString("nourut");
                                                    db.collection("users").document(userId).get().addOnCompleteListener(taskUsers -> {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot documentUsers = taskUsers.getResult();
                                                            String getVote = Objects.requireNonNull(documentUsers).getString("vote");
                                                            if (Objects.equals(getVote, vote)) {
                                                                Log.d(TAG, "Sudah Voting ", taskUsers.getException());
                                                            } else {
                                                                db.collection("users")
                                                                        .get()
                                                                        .addOnCompleteListener(taskLength -> {
                                                                            if (taskLength.isSuccessful()) {
                                                                                for (QueryDocumentSnapshot documentLength : taskLength.getResult()) {
                                                                                    db.collection("calon").document(idCalon).get().addOnSuccessListener(documentCalon -> {
                                                                                        if (documentCalon.exists()) {
                                                                                            db.collection("users").document(userId).get().addOnSuccessListener(documentUser -> {
                                                                                                if (documentUser.exists()) {
                                                                                                    CollectionReference votes = db.collection("votes");
                                                                                                    Map<String, Object> vote = new HashMap<>();
                                                                                                    vote.put("nama", documentUser.getString("nama"));
                                                                                                    vote.put("nim", documentUser.getString("nim"));
                                                                                                    vote.put("prodi", documentUser.getString("prodi"));
                                                                                                    vote.put("id", userId);
                                                                                                    vote.put("nourut", documentCalon.getString("nourut"));
                                                                                                    vote.put("timestamp", FieldValue.serverTimestamp());
                                                                                                    votes.document(Objects.requireNonNull(documentCalon.getString("nourut"))).collection("data").document(userId).set(vote);

                                                                                                    CollectionReference updateUsers = db.collection("users");
                                                                                                    Map<String, Object> updateVote = new HashMap<>();
                                                                                                    updateVote.put("nama", documentUser.getString("nama"));
                                                                                                    updateVote.put("email", documentUser.getString("email"));
                                                                                                    updateVote.put("nim", documentUser.getString("nim"));
                                                                                                    updateVote.put("prodi", documentUser.getString("prodi"));
                                                                                                    updateVote.put("vote", "sudah");
                                                                                                    updateUsers.document(userId).set(updateVote);

                                                                                                    Double mob = documentCalon.getDouble("hasil");
                                                                                                    Map<String, Object> voteCalon = new HashMap<>();
                                                                                                    voteCalon.put("hasil", mob+1);
                                                                                                    db.collection("calon").document(idCalon).set(voteCalon, SetOptions.merge());

                                                                                                    final Intent intent;
                                                                                                    intent = new Intent(context, HomeActivity.class);
                                                                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                    context.startActivity(intent);
                                                                                                } else {
                                                                                                    Log.d(TAG, "User Tidak Ada");
                                                                                                }
                                                                                            });
                                                                                        } else {
                                                                                            Log.d(TAG, "Calon Tidak Ada");
                                                                                        }
                                                                                    });
                                                                                }
                                                                            } else {
                                                                                Log.d(TAG, "Error getting documents: ", taskLength.getException());
                                                                            }
                                                                        });
                                                            }

                                                        } else {
                                                            System.out.println("Gagal");
                                                        }
                                                    });

                                                }
                                            }
                                        });
                                    })
                            .setNegativeButton("Tidak",
                                    (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        }
    }


}
