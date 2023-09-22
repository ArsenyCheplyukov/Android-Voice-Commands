package com.arsenyvoid.voicecommands;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static android.Manifest.permission.RECORD_AUDIO;

public class MainActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private Intent intentRecognizer;
    private TextView textView;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);

        textView = findViewById(R.id.textView);
        intentRecognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intentRecognizer.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        // INIT COMMAND LIST
        ArrayList<String> commandList = new ArrayList();
        commandList.add("right");
        commandList.add("left");
        commandList.add("forward");
        commandList.add("backward");
        commandList.add("up");
        commandList.add("down");
        commandList.add("increase");
        commandList.add("decrease");
        commandList.add("clockwise");
        commandList.add("anticlockwise");

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                //textView.setText("Start recognizing...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                //textView.setText("Stop recognizing...");
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                //String string = "";
                //if(matches!=null){
                //    string = matches.get(0);
                //    textView.setText(string);
                //}
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String string = "";
                if (matches != null) {
                    string = matches.get(0);
                    //if (commandList.contains("right")) {
                    textView.setText(string);
                    //}
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    public void StartButton(View view){
        mToastRunnable.run();
    }

    public void StopButton(View view){
        mHandler.removeCallbacks(mToastRunnable);
        speechRecognizer.stopListening();
    }

    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            speechRecognizer.startListening(intentRecognizer);
            mHandler.postDelayed(this, 5000);
        }
    };
}