package com.example.mkash32.jokeandroidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);
        TextView tv_joke = (TextView) findViewById(R.id.tv_joke);
        String joke = getIntent().getStringExtra("joke");
        if(joke == null) {
            tv_joke.setText("Joke unavailable");
        }
        tv_joke.setText(joke);
    }
}
