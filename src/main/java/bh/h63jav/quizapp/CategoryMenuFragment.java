package bh.h63jav.quizapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CategoryMenuFragment extends Fragment {

    private TextSwitcher cTitle, cDescription;
    private List<Category> cList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_menu, container, false);
        Properties p = (Properties) getActivity().getApplicationContext();
        cList = p.getCategoryList();
        CategoryAdapter adapter = new CategoryAdapter(cList, getActivity());
        FeatureCoverFlow coverFlow = (FeatureCoverFlow) view.findViewById(R.id.cvrflwCategory);
        cTitle = (TextSwitcher) view.findViewById(R.id.ttlCategory);
        cDescription = (TextSwitcher) view.findViewById(R.id.txtCategoryDesc);
        cTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                return (TextView) inflater.inflate(R.layout.layout_category_title, null);
            }
        });
        cDescription.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                return (TextView) inflater.inflate(R.layout.layout_category_description, null);
            }
        });
        coverFlow.setAdapter(adapter);
        coverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                Log.d("DEBUG",cList.toString());
                Log.d("DEBUG", "pos:\t"+position);
                position = (position<0)? 0:position;
                cTitle.setText(cList.get(position).getTitle());
                cDescription.setText(cList.get(position).getSummary());
            }

            @Override
            public void onScrolling() {

            }
        });
        coverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Properties p = (Properties) getActivity().getApplicationContext();
                p.initQuestions(cList.get(i).getTitle());
                Toast.makeText(getActivity(), "Starting" + cList.get(i).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
