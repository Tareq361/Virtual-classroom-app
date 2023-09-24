package com.example.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class materialView extends AppCompatActivity {
MaterialResponse materialResponse;
TextView title,desc,duetime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_view);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        TextView materialText = findViewById(R.id.materialtextId);


        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        duetime=findViewById(R.id.time);
        Intent intent=getIntent();
        if(intent.getExtras()!=null){
            materialResponse=(MaterialResponse) intent.getSerializableExtra("data1");
            title.setText(materialResponse.getTitle());
            String Time=materialResponse.getDueTime();
            String newtime="";
            try { SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Time=sdf.parse(Time).toString(); }
            catch (Exception e) {  }

            for(int i=0;i<10;i++){
                newtime=newtime+Time.charAt(i);
            }
            duetime.setText(duetime.getText().toString()+" "+newtime);
            desc.setText(materialResponse.getDescription());
            materialText.setText(materialResponse.getFile());

            materialText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.loadUrl("http://192.168.0.106:3000/pdf/"+materialResponse.getId());
                }
            });



        }

    }
}