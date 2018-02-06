package com.example.mukhe.dynamicautocomplete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AutoCompleteTextView textView=findViewById(R.id.text);
        final CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(),android.R.layout.simple_dropdown_item_1line);
        textView.setAdapter(customAdapter);
        textView.setThreshold(1);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String trainName=customAdapter.getItem(i).getTrainNumber();
                textView.setText(trainName);
            }
        });
    }
}
