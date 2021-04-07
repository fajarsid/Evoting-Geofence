package com.example.evote.hasilvote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.evote.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VoteActivity extends AppCompatActivity {

    FirebaseFirestore db;
    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        BarChart barChart = findViewById(R.id.barchart);
        db = FirebaseFirestore.getInstance();


        db.collection("calon")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int index = 0;
                        ArrayList<String> labels = new ArrayList<>();
                        ArrayList<BarEntry> jmhVote = new ArrayList<>();
                        BarDataSet bardataset = new BarDataSet(jmhVote, "Cells");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Double d = document.getDouble("hasil");
                            int b = Objects.requireNonNull(d).intValue();
                            jmhVote.add(new BarEntry(b, index));
                            labels.add(document.getString("nama"));

                        index++;
                        }
                        BarData data = new BarData(labels, bardataset);

                        barChart.setData(data);
                        barChart.setDescription("HASIL VOTE");
                        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                        barChart.animateY(2000);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });


//        getListItems();
//        Log.d(TAG, "onCreate: LIST IN ONCREATE = " + mArrayList);

//        db.collection("votes").get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot documentVote : task.getResult()) {
//                    db.collection("votes")
//                            .whereEqualTo("nourut", documentVote.getId())
//                            .get()
//                            .addOnCompleteListener(task2 -> {
//                                if (task2.isSuccessful()) {
//                                    for (QueryDocumentSnapshot documentNorut : task2.getResult()) {
//                                        Log.d(TAG, task2.getResult().size() + "");
//                                    }
//                                } else {
//                                    Log.d(TAG, "Error getting documents: ", task2.getException());
//                                }
//                            });
//                }
//            } else {
//                Log.d(TAG, "Error getting documents: ", task.getException());
//            }
//        });

    }

//    private void getListItems() {
//        db.collection("votes")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot documentVotes : task.getResult()) {
//                            db.collection("votes").document(documentVotes.getId()).collection("data").get().addOnCompleteListener(task1 -> {
//                                if (task1.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task1.getResult()) {
//                                        if (document.exists()) {
//                                            if(documentVotes.getId() == document.getId()) {
//
//                                                System.out.println("DATANYA : " + task1.getResult().size());
//                                            }
//                                        }
//                                    }
//                                }
//                            });
//                        }
//                    } else {
//                        Log.d(TAG, "Error getting documents: ", task.getException());
//                    }
//                });
//
//    }

//    public Task<Integer> getCount(final DocumentReference ref) {
//        // Sum the count of each shard in the subcollection
//        return ref.collection("votes").get()
//                .continueWith(new Continuation<QuerySnapshot, Integer>() {
//                    @Override
//                    public Integer then(@NonNull Task<QuerySnapshot> task) throws Exception {
//                        int count = 0;
//                        for (DocumentSnapshot snap : task.getResult()) {
//                            hasil shard = snap.toObject(hasil.class);
//                            count += shard.count;
//                            System.out.println(count);
//                        }
//                        return count;
//                    }
//                });
//    }

//    public Task<Void> createCounter(final DocumentReference ref, final int numShards) {
//        // Initialize the counter document, then initialize each shard.
//        return ref.set(new Counter(numShards))
//                .continueWithTask(new Continuation<Void, Task<Void>>() {
//                    @Override
//                    public Task<Void> then(@NonNull Task<Void> task) throws Exception {
//                        if (!task.isSuccessful()) {
//                            throw task.getException();
//                        }
//
//                        List<Task<Void>> tasks = new ArrayList<>();
//
//                        // Initialize each shard with count=0
//                        for (int i = 0; i < numShards; i++) {
//                            Task<Void> makeShard = ref.collection("shards")
//                                    .document(String.valueOf(i))
//                                    .set(new Shard(0));
//
//                            tasks.add(makeShard);
//                        }
//
//                        return Tasks.whenAll(tasks);
//                    }
//                });
//    }
}
