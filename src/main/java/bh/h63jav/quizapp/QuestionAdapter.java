package bh.h63jav.quizapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

class QuestionAdapter extends ArrayAdapter<String> {


    private int index;
    private int isImgQuestion;
    private String catID;

    public QuestionAdapter(@NonNull Context context, String[] questionClues, int index, int isImgQuestion, String catID) {
        super(context, R.layout.layout_question_item, questionClues);
        this.index = index;
        this.isImgQuestion = isImgQuestion;
        Log.d("IMAGE","oN ENTRY CATid:"+catID);
        this.catID = catID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_question_item, parent, false);
        String clue = getItem(position);
        TextView clueText = (TextView) view.findViewById(R.id.txtQuestionClue);
        ImageView clueImg = (ImageView) view.findViewById(R.id.imgQuestionClue);
        if (isImgQuestion == 0) {
            clueImg.setVisibility(View.GONE);
            clueText.setVisibility(View.VISIBLE);
            clueText.setText(clue);
        } else if (isImgQuestion == 1) {
            clueText.setVisibility(View.GONE);
            clueImg.setVisibility(View.VISIBLE);
            String imgTitle = "Questions/"+catID+"/"+(index+1)+"/"+clue+".png";
            Log.d("IMAGE",imgTitle);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(imgTitle);
            Glide.with(view)
                    .load(storageReference)
                    .apply(new RequestOptions().placeholder(R.drawable.question_mark).error(R.drawable.question_mark))
                    .into(clueImg);
        }

        AnswerTextWatcher answerTextWatcher = new AnswerTextWatcher(getContext(),view,index,position);
        EditText ansText = (EditText) view.findViewById(R.id.txtAnswer);
        ansText.addTextChangedListener(answerTextWatcher);


        return view;
    }

}
