package com.example.whowantstobemillionaire_yadavl1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {

    private ImageView centerImage;
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String prizeAmt = bundle.getString("prize_amt");
            if (prizeAmt != null) {
                showVictory(prizeAmt);
            } else {
                showGameOver();
            }
        } else {
            showGameOver();
        }

        // Animation code
        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        centerImage.startAnimation(animation);
    }

    private void showVictory(String prizeAmt) {
        setContentView(R.layout.activity_victory);
        centerImage = findViewById(R.id.logo_id);
        tvMessage = findViewById(R.id.tvAmt);
        centerImage.setImageResource(R.mipmap.win);
        tvMessage.setText("Congratulations! You won $" + prizeAmt);
    }

    private void showGameOver() {
        setContentView(R.layout.activity_game_over);
        centerImage = findViewById(R.id.logo_id);
        tvMessage = findViewById(R.id.tvSorry);
        centerImage.setImageResource(R.mipmap.wwtbam);
        tvMessage.setText("Sorry, you did not win this time.");
    }


    public void goToHome(View view) {
        Intent intent = new Intent(this, com.example.whowantstobemillionaire_yadavl1.QuizStartActivity.class);
        startActivity(intent);
    }
}
