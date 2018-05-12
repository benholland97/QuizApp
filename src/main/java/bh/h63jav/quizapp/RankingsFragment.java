package bh.h63jav.quizapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class RankingsFragment extends Fragment {

    private View view;
    private ListView listViewRankings;

    private ListAdapter listAdapter;
    private Properties p;
    private MenuActivity m;
    private TextView rankingTitle1;
    private TextView rankingTitle2;
    private ImageButton backBtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rankings, container, false);
        p = (Properties) getActivity().getApplicationContext();
        m = (MenuActivity) getActivity();
        rankingTitle1 = (TextView) view.findViewById(R.id.rankingsCatTitle);
        rankingTitle2 = (TextView) view.findViewById(R.id.rankingsPlayerTitle);
        listViewRankings = (ListView) view.findViewById(R.id.rankingsList);
        backBtn = (ImageButton) view.findViewById(R.id.backButtonRankings);
        Bundle extras = this.getArguments();
        if(extras != null) {
            String category = extras.getString("Category");
            Log.d("RANKINGS",category);
            setupRankingsList(category);
        } else {
            setUpCategoryRankings();
        }
        return view;
    }

    private void setUpCategoryRankings() {
        rankingTitle1.setText("Category");
        rankingTitle2.setText("Player");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.setViewPager(0);
            }
        });
        final List<Category> cList = p.getCategoryList();
        final List<HashMap<String,String>> catHMlist = new ArrayList<>();
        DatabaseReference rankingsRef = FirebaseDatabase.getInstance().getReference().child("Rankings");
        for (int i=0; i<cList.size(); ++i) {
            final String catID = cList.get(i).getTitle();
            DatabaseReference catRef = rankingsRef.child(catID);
            catRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    double topScore = 0;
                    String player = "";
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        double score =  child.child("percentage").getValue(Double.class);
                        if (score > topScore) {
                            topScore = score;
                            player = child.getKey();
                        }
                    }
                    String percentage = topScore + " %";
                    HashMap<String,String> catHM = new HashMap<>();
                    catHM.put("txt1",catID);
                    catHM.put("txt2",player);
                    catHMlist.add(catHM);
                    listAdapter = new RankingsAdapter(m, catHMlist);
                    listViewRankings.setAdapter(listAdapter);
                    listViewRankings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Bundle extras = new Bundle();
                            extras.putString("Category",cList.get(position).getTitle());
                            m.setViewPagerArgs(3,extras);
                        }
                    });
                    Log.d("RANKINGS","value of hashmap:"+catHM.toString());
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    private void setupRankingsList(String category) {
        Log.d("RANKINGS","VALUE PASSED:\t" + category);
        rankingTitle1.setText("Player");
        rankingTitle2.setText("Score");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"RETURN BACK",Toast.LENGTH_SHORT).show();
                m.setViewPagerArgs(3,null);
            }
        });
        DatabaseReference rankingsRef = FirebaseDatabase.getInstance().getReference().child("Rankings/"+category);
        List<Category> cList = p.getCategoryList();
        final List<HashMap<String,String>> catHMlist = new ArrayList<>();
        rankingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double score = 0;
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    String percentage =  child.child("percentage").getValue().toString();
//                    String percentage = toString(score);
                    HashMap<String,String> catHM = new HashMap<>();
                    catHM.put("txt1",child.getKey());
                    catHM.put("txt2",percentage);
                    catHMlist.add(catHM);
                }
                Collections.sort(catHMlist, new Comparator<HashMap<String, String>>() {
                    @Override
                    public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                        double l = Double.parseDouble(o1.get("txt2"));
                        double r = Double.parseDouble(o2.get("txt2"));
                        return -Double.compare(l, r);
                    }
                });
                listAdapter = new RankingsAdapter(getActivity(), catHMlist);
                listViewRankings.setAdapter(listAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


