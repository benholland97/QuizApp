package bh.h63jav.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class EndQuizFragment extends Fragment {

    private Button btnTryAgain, btnHome;
    private TextView txtResultsCat, txtResultsTotal;
    private ProgressBar resultsProgressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end_quiz, container, false);

        btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);
        btnHome = (Button) view.findViewById(R.id.btnHome);
        txtResultsCat = (TextView) view.findViewById(R.id.txtResultsCat);
        txtResultsTotal = (TextView) view.findViewById(R.id.txtResultsTotal);
        resultsProgressBar = (ProgressBar) view.findViewById(R.id.progressBarResults);

        Bundle extras = this.getArguments();
        if (extras != null) {
            txtResultsCat.setText(extras.getString("CATID"));
            txtResultsTotal.setText(String.format("Score : %d / %d",extras.getInt("SCORE"),extras.getInt("MAX")));

            resultsProgressBar.setMax(extras.getInt("MAX"));
            resultsProgressBar.setProgress(extras.getInt("SCORE"));
        }

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),GameActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MenuActivity)getActivity()).setViewPager(0);
            }
        });

        return view;
    }
}
