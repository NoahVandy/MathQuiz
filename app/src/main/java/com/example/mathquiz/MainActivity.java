package com.example.mathquiz;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_start, btn_answer0, btn_answer1, btn_answer2, btn_answer3;
    TextView tv_score, tv_question, tv_timer, tv_bottomMessage;
    ProgressBar prog_timer;
    Game g = new Game();

    int secondsLeft = 30;

    CountDownTimer gameTimer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondsLeft--;
            tv_timer.setText(Integer.toString(secondsLeft) + " secs");
            prog_timer.setProgress(30 - secondsLeft);
        }

        @Override
        public void onFinish() {
            btn_answer0.setEnabled(false);
            btn_answer1.setEnabled(false);
            btn_answer2.setEnabled(false);
            btn_answer3.setEnabled(false);
            tv_bottomMessage.setText("Time is up! " + g.getNumberCorrect() + "/" + (g.getTotalQuestions() -1));
            secondsLeft--;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_start.setVisibility(View.VISIBLE);
                }
            }, 4000);

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);
        btn_answer0 = findViewById(R.id.btn_answer0);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);

        tv_score = findViewById(R.id.tv_score);
        tv_timer = findViewById(R.id.tv_timer);
        tv_question = findViewById(R.id.tv_question);
        tv_bottomMessage = findViewById(R.id.tv_bottomMessage);

        prog_timer = findViewById(R.id.prog_timer);


        btn_start.bringToFront();
        tv_timer.setText("0 Sec");
        tv_question.setText("");
        tv_bottomMessage.setText("Press go");
        tv_score.setText("0 pts");

        View.OnClickListener startButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button start_button = (Button)v;

                start_button.setVisibility(View.INVISIBLE);
                secondsLeft = 30;
                g = new Game();
                nextTurn();
                gameTimer.start();
                tv_score.setText("0");

            }
        };

        View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button)v;

                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());
                //Toast.makeText(MainActivity.this, "AnswerSlected=" + answerSelected, Toast.LENGTH_SHORT).show();
                g.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(g.getScore()));
                nextTurn();
            }
        };

        btn_start.setOnClickListener(startButtonClickListener);

        btn_answer0.setOnClickListener(answerButtonClickListener);
        btn_answer1.setOnClickListener(answerButtonClickListener);
        btn_answer2.setOnClickListener(answerButtonClickListener);
        btn_answer3.setOnClickListener(answerButtonClickListener);
    }

    private void nextTurn(){
        //create new question
        g.makeNewQuestion();
        int[] answers = g.getCurrentQuestion().getAnswerArray();
        //set text on answer buttons
        btn_answer0.setText(Integer.toString(answers[0]));
        btn_answer1.setText(Integer.toString(answers[1]));
        btn_answer2.setText(Integer.toString(answers[2]));
        btn_answer3.setText(Integer.toString(answers[3]));

        btn_answer0.setEnabled(true);
        btn_answer1.setEnabled(true);
        btn_answer2.setEnabled(true);
        btn_answer3.setEnabled(true);

        tv_question.setText(g.getCurrentQuestion().getQuestionPhrase());
        //enable answer buttons
        //start the timer

        tv_bottomMessage.setText(g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));
    }
}
