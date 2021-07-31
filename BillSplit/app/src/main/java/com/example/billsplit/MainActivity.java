package com.example.billsplit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.billsplit.Calculation;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    double tb;
    int l;
    TextWatcher w;
    EditText bill,sp[];
    Button calc,add;
    LinearLayout layoutList;
    String names[];
    double splits[] ;
    TextView watcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layoutList = findViewById(R.id.layout_list);
        bill = (EditText) findViewById(R.id.bill);
        watcher = findViewById(R.id.watcher);
        add = findViewById(R.id.add);
        calc = (Button) findViewById(R.id.calculate);
        sp = new EditText[' '];

         w = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                double spl;
                double sum = 0;
                for (int i = 0; i < layoutList.getChildCount(); i++) {
                    View splitRow = layoutList.getChildAt(i);
                    EditText n = splitRow.findViewById(R.id.name);
                    sp[i] = splitRow.findViewById(R.id.split);
                    if(!n.getText().toString().equals("")&&!sp[i].getText().toString().equals("")) {
                        spl = Double.parseDouble(sp[i].getText().toString());
                    }
                    else
                        spl=0;
                    sum+=spl;
                }
                watcher.setText("Total: "+String.valueOf(sum));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        addView();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();

            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names = new String[layoutList.getChildCount()];
                splits = new double[layoutList.getChildCount()];
                if(!bill.getText().toString().equals("")) {
                    tb = Double.parseDouble(bill.getText().toString());
                    boolean fullName = true;
                    for(int i = 0;i<layoutList.getChildCount();i++){
                        View splitRow = layoutList.getChildAt(i);
                        EditText n = splitRow.findViewById(R.id.name);
                        if(n.getText().toString().equals(""))
                            fullName = false;
                    }


                    if (checkList(tb)&&fullName) {
                        for (int i = 0; i < layoutList.getChildCount(); i++) {
                            View splitRow = layoutList.getChildAt(i);
                            EditText n = splitRow.findViewById(R.id.name);
                            EditText s = splitRow.findViewById(R.id.split);
                            if(!n.getText().toString().equals("")) {
                                names[i] = n.getText().toString();
                                splits[i] = Double.parseDouble(s.getText().toString());
                            }
                            else
                                splits[i]=0;
                        }

                        Intent i = new Intent(MainActivity.this,MainActivity2.class);
                        i.putExtra("tb",  tb);
                        i.putExtra("splits",  splits);
                        i.putExtra("names", names);

                        startActivity(i);
                    }
                    else
                        Toast.makeText(MainActivity.this,"Invalid Split Entries",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(MainActivity.this,"Invalid Bill",Toast.LENGTH_LONG).show();



            }
        });

    }


    private void addView()
    {
        View splitList = getLayoutInflater().inflate(R.layout.add_split,null,false);
        EditText name = splitList.findViewById(R.id.name);
        EditText split = splitList.findViewById(R.id.split);
        ImageView delete = splitList.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeView(splitList);

            }
        });

        layoutList.addView(splitList);

        for (int i = 0; i < layoutList.getChildCount(); i++) {
            View splitRow = layoutList.getChildAt(i);
            sp[i] = splitRow.findViewById(R.id.split);
            sp[i].addTextChangedListener(w);
        }

    }

    private void removeView(View view)
    {

        EditText s = view.findViewById(R.id.split);
        s.setText("0");
        layoutList.removeView(view);
    }

    private boolean checkList(double tb)
    {
        double sum = 0;

        for(int i = 0;i<layoutList.getChildCount();i++)
        {
            View splitRow = layoutList.getChildAt(i);
            EditText s = splitRow.findViewById(R.id.split);
            EditText n = splitRow.findViewById(R.id.name);
            if(n.getText().toString().equals(""))
                continue;
            if(s.getText().toString().equals(""))
                s.setText("0");
            sum+= Double.parseDouble(s.getText().toString());

        }
        if(sum == tb)
            return true;
        else
            return false;
    }
}






/*calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] temp1 = new String[' '];
                String[] temp2 = new String[' '];

                tb = Double.parseDouble(bill.getText().toString());
                temp1 = splits.getText().toString().split(",");
                temp2 = names.getText().toString().split(",");
                people = new String[temp2.length];
                people = temp2;
                double spl[] = new double[temp2.length];

                for(int i =0; i<temp1.length;i++)
                {
                    people[i].trim();
                    temp1[i].trim();
                    spl[i] = Double.parseDouble(String.valueOf(temp1[i]));
                }


                Calculation calc = new Calculation(tb, spl, people);
                double[][] algorithm = calc.algorithm();
                String[] arr = new String[calc.l];
                int x = 0;
                for(int j = 0;j<calc.l;j++)
                    for (int k = 0; k<calc.l;k++)
                        if(algorithm[j][k]>0)
                        {
                            arr[x] = calc.s[j].name+" has to pay "+algorithm[j][k]+" to "+calc.s[k].name;
                            x++;
                        }
                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                i.putExtra("val",arr);
                i.putExtra("count",calc.l);

                startActivity(i);

            }
        });*/