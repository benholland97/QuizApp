package bh.h63jav.quizapp;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Properties extends Application {

    private List<Category> cList;
    private List<Question> qList;
    private int numQuestions;
    private String catID;
    private User user;



    public void initData() {
        initCategories();
//        setUser(new User("Test"));
    }

    public void initQuestions(String catID) {
        numQuestions = 0;
        if(catID.equals("RANDOM")) {
            catID = getRandomCategoryID();
        }
//        catID = "Science";
        this.catID = catID;

        qList = new ArrayList<>();
        Log.d("GAMETEST","LOOKING FOR: "+catID);
        DatabaseReference dbQuestionRef = FirebaseDatabase.getInstance().getReference().child("Questions/"+catID);
        dbQuestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()) {
                    try {
                        String qTitle = item.child("Title").getValue().toString();
                        String qDescription= item.child("Description").getValue().toString();
                        int isImgQuestion = Integer.valueOf(item.child("IsImgQuestion").getValue().toString());
                        int count = Integer.valueOf(item.child("Count").getValue().toString());
                        String[] clues = new String[count];
                        String[] answers = new String[count];
                        for (int i=0; i<count; ++i) {
                            clues[i] = item.child("Clue"+(i+1)).getValue().toString();
                            answers[i]= item.child("Ans"+(i+1)).getValue().toString();
                        }
                        Question q = new Question(qTitle,qDescription,clues,answers,count,isImgQuestion);
//                        numQuestions++;
                        qList.add(q);
                    } catch (NullPointerException e) {
                        Log.d("QuizApp","Invalid format of question data to load");
                    }
                }
                Collections.shuffle(qList);
                Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public void initCategories() {
        cList = new ArrayList<>();
        DatabaseReference dbCat = FirebaseDatabase.getInstance().getReference().child("Categories");
        dbCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren()) {
                    try {
                        String catName = item.child("Name").getValue().toString();
                        String catDesc = item.child("Description").getValue().toString();
                        String catImgTxt = item.child("Image").getValue().toString();
                        Category c = new Category(catName,catDesc,catImgTxt);
                        cList.add(c);
//                        numCategories++;
                    } catch (NullPointerException e) {
                        Log.d("QuizApp","Invalid format of category data to load");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Question getQuestionAt(int index) {
        return qList.get(index);
    }

    public int getNumQuestions() {
//        return numQuestions;
        return this.qList.size();
    }

    public List<Question> getQuestionList() {
        return this.qList;
    }

    public String getActiveCategoryID() {
        return this.catID;
    }

    public String getRandomCategoryID() {
        return this.cList.get(new Random().nextInt(cList.size())).getTitle();
    }

    public int getNumCategories() {
        return this.cList.size();
    }

    public List<Category> getCategoryList() {
        return this.cList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void uploadScore(final Result res) {
        final DatabaseReference rankingsDBRef = FirebaseDatabase.getInstance().getReference().child("Rankings/"+catID);
        rankingsDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(user.getUsername())) {
                    rankingsDBRef.child(user.getUsername()).setValue(res);
                    Log.d("DATABASE","added data");
                }
                else {
                    double curPercent = dataSnapshot.child(user.getUsername()).child("percentage").getValue(Double.class);
                    if (res.getPercentage() > curPercent) {
                        rankingsDBRef.child(user.getUsername()).setValue(res);
                        Log.d("DATABASE","uploading new score");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
