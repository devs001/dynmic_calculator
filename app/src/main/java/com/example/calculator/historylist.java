package com.example.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class historylist extends AppCompatActivity {
    Button histr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.histroylist);
        String histroystr="";
        try {
            histroystr=  readjson("histroy.json").toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            pushinhistroy(histroystr);
        } catch (Exception epp) {
            epp.printStackTrace();
        }
    }
    public StringBuilder readjson(String FN) throws Exception {
        File fl = new File(getApplicationContext().getFilesDir(),FN);
        StringBuilder strbld= new StringBuilder();
        if(fl.exists()) {
            FileReader fr = new FileReader(fl);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                strbld.append(line).append("\n");
                line = bfr.readLine();
            }
            bfr.close();
            String res = strbld.toString();
            JSONArray js = new JSONArray(res);
            int in = js.length();
            for (int i = 0; i < in; i++) {
                JSONObject jo = js.getJSONObject(i);
                String re = jo.get("resulte").toString();
                String interm = jo.get("interme").toString();
                System.out.println(re + " -" + interm);

            }
            System.out.println(res);
        }
        return strbld;
    }


    public void pushinhistroy(String res) throws Exception {
        JSONArray js = new JSONArray(res);
        ArrayList<history> listh =new ArrayList<>();
        int in = js.length();
        for (int i = 0; i < in; i++) {
            JSONObject jo = js.getJSONObject(i);
            String re = jo.get("resulte").toString();
            String interm = jo.get("interme").toString();
            listh.add(new history(re,interm));
        }
        final histroyAdopter hisA=new histroyAdopter(this,listh);
        final TextView resulte = findViewById(R.id.listre);
        final TextView interme = findViewById(R.id.lisinterme);
        ListView listView = findViewById(R.id.listv);
        listView.setAdapter(hisA);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                startactivity(hisA.getItem(i).getResulte(),hisA.getItem(i).getInterme());
            }
        });
    }

    public void startactivity(String res,String interm){
        Intent intetmain = new Intent(this,MainActivity.class);
        intetmain.putExtra("resulte",res);
        intetmain.putExtra("interme",interm);
        startActivity(intetmain);
    }

}
