package bh.h63jav.quizapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class RankingsAdapter extends ArrayAdapter<HashMap<String,String>> {

    private MenuActivity m;


    public RankingsAdapter(@NonNull Context context, @NonNull HashMap<String, String>[] objects) {
        super(context, R.layout.layout_ranking_item, objects);
    }

    public RankingsAdapter(@NonNull Context context, @NonNull List<HashMap<String, String>> objects) {
        super(context, R.layout.layout_ranking_item, objects);
        m = (MenuActivity) context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_ranking_item, parent, false);
        TextView txtView1 = (TextView) view.findViewById(R.id.rankingsTxtItem1);
        TextView txtView2 = (TextView) view.findViewById(R.id.rankingsTxtItem2);
        Log.d("Rankings",getItem(position).toString());

        final String txt1 = getItem(position).get("txt1");
        String txt2 = getItem(position).get("txt2");

        txtView1.setText(txt1);
        txtView2.setText(txt2);

        return view;
    }
}
