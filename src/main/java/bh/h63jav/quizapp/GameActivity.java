package bh.h63jav.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends AppCompatActivity {

    private ListAdapter listAdapter;



//    private List<Question> qList;
    private Properties p;

    final static long INTERVAL = 500; //0.5 sec
    final static long QUESTION_DELAY = 5000; //5 sec per question
    final static int SCORE_VALUE = 10;
    private long timeout;
    private int progressValue;

    private CountDownTimer countDown;

    private int totalNumQuestionsSet, score, currentQuestion;

    private ProgressBar progressBar;
    private TextView questionTitle, txtScore, txtCurrentQuestion;
    private ListView listViewQuestions;
    private Button submitBtn;
    private String catID;
    private int playerScore;
    private TextView questionDesc;
    private int maxAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
//        catID = getIntent().getStringExtra("CategoryID");
//        setupQuestionList();
        setup();
    }

    private void setup() {
        p = (Properties)getApplicationContext();
        totalNumQuestionsSet = p.getNumQuestions();
        currentQuestion = 0; playerScore = 0; maxAvailable=0;
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBarGame);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitleGame);
        ImageView exitIcon = (ImageView) findViewById(R.id.toolBarIconExit);
        exitIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this,MenuActivity.class);
                finish();
                startActivity(intent);
                Toast.makeText(GameActivity.this, "Exiting game", Toast.LENGTH_SHORT).show();
            }
        });
        catID = p.getActiveCategoryID();
        toolbarTitle.setText(catID);
        listViewQuestions = (ListView) findViewById(R.id.listViewQuestions);
        questionTitle = (TextView) findViewById(R.id.txtQuestionTitle);
        questionDesc = (TextView) findViewById(R.id.txtQuestionDesc);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtCurrentQuestion = (TextView) findViewById(R.id.txtCurrentQuestion);
        progressBar = (ProgressBar) findViewById(R.id.progressBarGame);
        submitBtn = (Button) findViewById(R.id.btnSubmitAnswers);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitBtnPress();
            }
        });

    }

    private void submitBtnPress() {
        countDown.cancel();
        currentQuestion++;
//        maxAvailable += totalNumQuestionsSet*10;
        showQuestion(currentQuestion);
    }

    private void showQuestion(int i) {
        Log.d("DEBUGbro","Attempting to show question"+i);
        if (i < totalNumQuestionsSet ) {
            Question q = p.getQuestionAt(i);
            setupCountDownTimer(q.getCount());
            questionTitle.setText(q.getTitle());
            questionDesc.setText(q.getDesc());
            txtCurrentQuestion.setText(String.format("%d / %d", (currentQuestion+1), totalNumQuestionsSet));
            progressBar.setProgress(0);
            progressValue = 0;
            Log.d("DEBUGbro","QUESTION NUM "+currentQuestion);
            int isImgQuestion = q.getImgQuestion();
            String [] clues = q.getClues();
            listAdapter = new QuestionAdapter(this, clues, i, isImgQuestion, catID);
            listViewQuestions.setAdapter(listAdapter);
            String scoreTxt = score + "";
            txtScore.setText(scoreTxt);
            maxAvailable += SCORE_VALUE * clues.length;
            countDown.start();
        }
        else {
            Log.d("DEBUGbro","EXITING APP");
            endGame();
        }
    }

    private void endGame() {
        Intent intent = new Intent(this,MenuActivity.class);
        Bundle results = new Bundle();
        results.putInt("SCORE", score);
        results.putString("CATID", catID);
        results.putInt("MAX", maxAvailable);
        results.putBoolean("END",true);
        intent.putExtras(results);
        Result res = new Result(p.getUser(),catID,score,maxAvailable);
        p.uploadScore(res);
        startActivity(intent);
    }

    public void increaseScore() {
        score+=SCORE_VALUE;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showQuestion(currentQuestion);
    }

    private void setupCountDownTimer(final int numAns) {
        timeout = QUESTION_DELAY * numAns; //3 sec per question
        progressBar.setMax(numAns*2*(int)QUESTION_DELAY/1000);
        countDown = new CountDownTimer(timeout,INTERVAL) {
            @Override
            public void onTick(long milisec) {
                progressValue++;
                progressBar.setProgress(progressValue);
            }

            @Override
            public void onFinish() {
                countDown.cancel();
//                maxAvailable += totalNumQuestionsSet*10;
                currentQuestion++;
                showQuestion(currentQuestion);
            }
        };
    }

}
