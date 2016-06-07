package group.project.buddi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static SeekBar mSeekNoise;
    private static SeekBar mSeekActive;
    private static SeekBar mSeekFriendliness;
    private static SeekBar mSeekTraining;
    private static SeekBar mSeekHealth;

    private static Button nextButton;

    String noiseLevel;
    String activityLevel;
    String friendLevel;
    String trainingLevel;
    String healthLevel;

    public void seekBar(){
        mSeekNoise = (SeekBar)findViewById(R.id.seekBarNoise);
        mSeekActive = (SeekBar)findViewById(R.id.seekBarActive);
        mSeekFriendliness = (SeekBar)findViewById(R.id.seekBarFriendliness);
        mSeekTraining = (SeekBar)findViewById(R.id.seekBarTraining);
        mSeekHealth = (SeekBar)findViewById(R.id.seekBarHealthiness);

        nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setElevation(0);


        // Load existing data if it exists
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.oauth), Context.MODE_PRIVATE);

        String code = sharedPref.getString("code", "A0B0C0D0E0");

        
        mSeekNoise.setProgress(Integer.valueOf(code.substring(1,2)));
        mSeekActive.setProgress(Integer.valueOf(code.substring(3,4)));
        mSeekFriendliness.setProgress(Integer.valueOf(code.substring(5,6)));
        mSeekTraining.setProgress(Integer.valueOf(code.substring(7,8)));
        mSeekHealth.setProgress(Integer.valueOf(code.substring(9,10)));



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noiseLevel = String.valueOf(mSeekNoise.getProgress());
                activityLevel = String.valueOf(mSeekActive.getProgress());
                friendLevel = String.valueOf(mSeekFriendliness.getProgress());
                trainingLevel = String.valueOf(mSeekTraining.getProgress());
                healthLevel = String.valueOf(mSeekHealth.getProgress());

                // Store scores in shared preferences
                Context context = QuizActivity.this;
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.oauth), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("noiselvl", noiseLevel);
                editor.putString("activitylvl", activityLevel);
                editor.putString("friendlvl", friendLevel);
                editor.putString("traininglvl", trainingLevel);
                editor.putString("healthlvl", healthLevel);

                editor.putString("score", noiseLevel + activityLevel + friendLevel + trainingLevel + healthLevel );
                editor.commit();

                Intent intent = new Intent(QuizActivity.this, RankingActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setElevation(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        seekBar();
    }
}
