package bh.h63jav.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AnswerTextWatcher implements TextWatcher {

    private int qNum,index;
    private GameActivity g;
    private EditText ans;
    private Properties p;

    public AnswerTextWatcher(Context c, View view, int qNum, int index) {
        p = (Properties)c.getApplicationContext();
        this.g = (GameActivity) c;
        this.ans = view.findViewById(R.id.txtAnswer);
        this.qNum = qNum;
        this.index = index;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().toLowerCase().equals(p.getQuestionAt(qNum).getAnswers()[index].toLowerCase())){
            int green = Color.parseColor("#a0db8e");
            ans.setBackgroundColor(green);
            ans.setKeyListener(null);
            g.increaseScore();
            Log.d("ANSWER","Worked");
        }

    }
}
