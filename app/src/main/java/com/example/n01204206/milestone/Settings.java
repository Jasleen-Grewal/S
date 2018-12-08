package com.example.n01204206.milestone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private TextView change_Password;
    private Button confirm_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        change_Password = (TextView)findViewById(R.id.change_password);
        confirm_change = (Button)findViewById(R.id.confirm_change);

        confirm_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Settings.this,Password.class);
                startActivity(i);
            }
        });
    }
}
