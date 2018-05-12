package bh.h63jav.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.Random;

public class MainMenuFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_menu,container,false);
        setupMainMenuGrid();
        return view;
    }

    private void setupMainMenuGrid() {
        GridLayout menuGrid = (GridLayout) view.findViewById(R.id.gridMenu);
        int numMenuItems = menuGrid.getChildCount();
        CardView[] menuItems = new CardView[numMenuItems];
        for (int i = 0; i < numMenuItems; i++) {
            menuItems[i] = (CardView) menuGrid.getChildAt(i);
            final int finalI = i;
            menuItems[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(finalI) {
                        case 0:
                            Properties p = (Properties) getActivity().getApplicationContext();
                            p.initQuestions("RANDOM");
//                            Intent intent = new Intent(getActivity(),GameActivity.class);
//                            startActivity(intent);
                            Toast.makeText(getActivity(), "Pressed item "+finalI, Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
//                            intent = new Intent(MenuActivity.this,DisplayActivity.class);
                            Toast.makeText(getActivity(), "Loading Category Selection", Toast.LENGTH_SHORT).show();
                            ((MenuActivity)getActivity()).setViewPager(1);
                            break;
                        case 2:
                            Toast.makeText(getActivity(), "Loading Rankings", Toast.LENGTH_SHORT).show();
                            ((MenuActivity)getActivity()).setViewPager(3);
                            break;
                        default:
                            Toast.makeText(getActivity(), "No action provided for selected card item", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
    }
}
