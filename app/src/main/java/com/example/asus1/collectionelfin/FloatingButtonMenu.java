package com.example.asus1.collectionelfin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class FloatingButtonMenu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_button_menu);
        ImageButton file = (ImageButton)findViewById(R.id.image_but_file);
        ImageButton write = (ImageButton)findViewById(R.id.image_but_write);
        Button Bfile = (Button)findViewById(R.id.but_file);
        Button Bwrite = (Button)findViewById(R.id.but_write);
        file.setOnClickListener(this);
        write.setOnClickListener(this);
        Bfile.setOnClickListener(this);
        Bwrite.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.but_write:
            case R.id.image_but_write:

                break;
            case R.id.but_file:
            case R.id.image_but_file:

                break;

            default:
                FloatingButtonMenu.this.finish();
                break;
        }
    }
}
