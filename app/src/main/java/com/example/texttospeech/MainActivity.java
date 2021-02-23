package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech mTextToSpeech;
    private EditText mText;
    private Button mSpeech;
    private Locale spanish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = findViewById(R.id.txtText);
        mSpeech = findViewById(R.id.btnSpeech);
        spanish = new Locale("es", "ES");
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mTextToSpeech.setLanguage(spanish);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Lenguaje no soportado");
                    }
                    else{
                        mSpeech.setEnabled(true);
                    }
                }
                else{
                    Log.e("TTS", "Fallo al inicializar");
                }
            }
        });
    }


    public void onTextToSpeech(View v){
        String text = mText.getText().toString();
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if(mTextToSpeech != null){
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }

        super.onDestroy();
    }
}