package com.example.billsplit;

import android.os.Parcelable;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

class amtcomp implements Comparator<structure> {

    public int compare(structure first, structure second)
    {

        if(first==null)
        {
            return 0;
        }

        if (first.amt != second.amt) {
            return  (int) (first.amt - second.amt);
        }
        else
            return -1;
    }
}

class structure{
    String name;
    double amt;

    public structure()
    {
        name = "";
        amt=0;
    }

}

public class Calculation implements Serializable {

    double graph[][];

    double split[];
    double n;
    int l;
    String names[];
    structure s[] ;



    Calculation(double tb, double[] spl, String[] people)
    {
        n = tb;
        l = people.length;
        graph = new double[l][l];
        split = spl;
        names = new String[l];
        s = new structure[l];
        for(int i=0;i<l;i++)
        {
            s[i] = new structure();
            s[i].name = people[i];
        }

    }

    int pair()
    {
        int k=0;
        int count =0;
        int sp=0, ep=l-1;
        while(sp<ep||k<l-1) {
            if(s[k].amt == 0)
            {
                count++;
            }
            k++;

            if(s[sp].amt == 0 || s[ep].amt==0) {
                if(s[sp].amt==0 && s[ep].amt == 0)
                {ep--;sp++;}
                else if(s[ep].amt == 0)
                {ep--;}
                else if(s[sp].amt == 0)
                {sp++;}
            }
            else if(Math.abs(s[sp].amt)>s[ep].amt)
            {sp++;}
            else if(Math.abs(s[sp].amt)<s[ep].amt)
            {ep--;}
            else if(s[sp].amt + s[ep].amt ==0)
            {
                graph[ep][sp] = s[ep].amt;
                s[ep].amt =0;
                s[sp].amt=0;
                ep--;sp++;
            }
            else
            {sp++;}
        }
        if(s[k].amt == 0)
        {
            count++;

        }
        return count;
    }




   void algorithm()
    {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        double avg = n/l;                        //each person share
        for(int i=0;i<l;i++)
        {
            s[i].amt = Double.parseDouble(numberFormat.format(avg - split[i]));			//amount array which denotes +ve -ve and 0 values
        }

        Arrays.sort(s, new amtcomp());        //sorts the amt array
        int x=0,y=l-1;

        while(x<y)
        {
            int c = pair();
            if(c>=l-1)
                break;
            if(s[y].amt< Math.abs(s[x].amt) )
            {
                graph[y][x] = s[y].amt;                 //update the graph
                s[x].amt = Double.parseDouble(numberFormat.format(s[x].amt + s[y].amt));         //update -ve amt
                s[y].amt = 0;
            }
            else if(s[y].amt> Math.abs(s[x].amt) && s[x].amt!=0)
            {
                graph[y][x] = Math.abs(s[x].amt);        //update the graph
                s[y].amt = Double.parseDouble(numberFormat.format(s[y].amt + s[x].amt));            //update the highest +ve value selected
                s[x].amt = 0;
            }
            if(s[y-1].amt<0)
            {y=l-1;}
            if(s[y].amt >= s[y-1].amt && s[y].amt!=0)
            {
                if(s[x].amt ==0)
                {x++;}
                continue;
            }
            y--;
            if(s[x].amt <0)
            {continue;}
            x++;
        }

    }


}
