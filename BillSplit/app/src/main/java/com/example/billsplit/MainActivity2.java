package com.example.billsplit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    LinearLayout layoutList;
    Calculation c ;
    Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        layoutList = findViewById(R.id.list);
        done = findViewById(R.id.done);
        Intent k = getIntent();
        double tb = k.getDoubleExtra("tb",0);
       double[] splits = k.getDoubleArrayExtra("splits");
       String[] names = k.getStringArrayExtra("names");
       c = new Calculation(tb,splits,names);
       c.algorithm();


        for(int i = 0;i<c.l;i++)
        {
            for(int j = 0;j<c.l;j++)
            {
                if(c.graph[i][j]>0)
                    addView(c.s[i].name,c.s[j].name,c.graph[i][j]);
            }
        }
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,MainActivity.class));
            }
        });


    }

    private void addView(String src, String dest,double amt)
    {
        View resultView = getLayoutInflater().inflate(R.layout.disp_row,null,false);
        TextView text = resultView.findViewById(R.id.names);
        text.setText(src + " pays ₹" + String.format("%.2f",amt) + " to " + dest);


        layoutList.addView(resultView);

    }
}