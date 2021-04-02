package com.example.calculator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.RoundingMode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.zip.Inflater;

import static java.lang.Math.*;

public class currencycover extends AppCompatActivity implements View.OnClickListener {
    Double variable1,variable2,variable;
    EditText input;
    TextView firstcurtext,secondcurtext;
    RequestQueue queue;
    String interme;
    boolean rauting;
    ImageButton update,opbw,refresh;
    Button reverse,firstcurr,secondcurr;
    Button numdot,num1,num2,num3,num4,num5,num6,num7,num8,num9,num0;
    String url="http://apilayer.net/api/live?access_key=1c25dccf775bcc5c0e4ad850b72d7d4d&currencies=INR,EUR,GBP,CAD,PLN&source=USD&format=1";
    //ListView listvp;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variable=1.00;
        interme="";
        setContentView(R.layout.histr2test);
        System.out.println("hh---1");
        numdot=findViewById(R.id.numdot);
        num1=findViewById(R.id.num1);
        num2=findViewById(R.id.num2);
        num3=findViewById(R.id.num3);
        num4=findViewById(R.id.num4);
        num5=findViewById(R.id.num5);
        num6=findViewById(R.id.num6);
        num7=findViewById(R.id.num7);
        num8=findViewById(R.id.num8);
        num9=findViewById(R.id.num9);
        num0=findViewById(R.id.num0);
        reverse=findViewById(R.id.reverse);
        numdot.setOnClickListener(this);
        rauting=true;
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        num0.setOnClickListener(this);
        reverse.setOnClickListener(this);

        refresh =findViewById(R.id.refresh);

        refresh.setOnClickListener(this);
        opbw =findViewById(R.id.opbw);
        opbw.setOnClickListener(this);
        firstcurr =findViewById(R.id.firstcurr);
        firstcurr.setOnClickListener(this);
        update =findViewById(R.id.update);
        update.setOnClickListener(this);
        secondcurr=findViewById(R.id.secondcurr);
        secondcurr.setOnClickListener(this);

        secondcurtext =findViewById(R.id.secondcutext);
        firstcurtext =findViewById(R.id.firstcutext);
        //refresh.setText(getDrawable(R.));
        queue= Volley.newRequestQueue(this);


        queue.add(refreshvariable(url));
        //Intent backto = getIntent();
        try {
            setVariable();
        }
        catch ( Exception e){
            File f = new File("save.json");
            if (!f.exists()){
                try {
                    savedata(new String[]{"USA", "1", "USA", "1"});
                }
                catch (Exception sd){
                    sd.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(), "variabl- "+variable, //Toast.LENGTH_LONG).show();
        firstcurtext.setSelected(true);
        secondcurtext.setSelected(false);
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.refresh:
                setVariable();
                break;
            case R.id.update:
                queue= Volley.newRequestQueue(this);
                queue.add(refreshvariable(url));
                break;
            case R.id.opbw:
                if(0<interme.length()) {
                    interme = interme.substring(0, interme.length()-1);
                        doOpration();

                }
                break;
            case R.id.reverse:
                if(rauting){
                    rauting=false;
                    interme="0";
                    firstcurtext.setSelected(false);
                    secondcurtext.setSelected(true);
                    variable=1/variable;
                    //Toast.makeText(this,"variable-"+variable.toString(),//Toast.LENGTH_LONG).show();
                }
                else {
                    rauting=true;
                    interme="0";
                    firstcurtext.setSelected(true);
                    secondcurtext.setSelected(false);
                    variable=1/variable;
                    System.out.println("variable-"+variable.toString());
                }
                break;
            case R.id.firstcurr:
                try {
                    //Toast.makeText(getApplicationContext(),"aar",//Toast.LENGTH_LONG).show();
                    try {
                        setContentView(R.layout.histroylist);
                        //View view = LayoutInflater.from(this).inflate(R.layout.histroylist,null);
                        //getWindow().getDecorView().
                        //getWindow().getDecorView().setVisibility(View.INVISIBLE);
                        //findViewById(R.id.listv).setVisibility(View.VISIBLE);
                        final ArrayList<history> arlis = new ArrayList<>();
                        String str = readfile("currncy.json");
                        JSONObject jo = new JSONObject(str);
                        for (int i = 0; i < jo.names().length(); i++) {
                            String d=jo.names().getString(i);
                            String dp = (jo.get(jo.names().getString(i))).toString();
                            System.out.println(d+" "+dp);
                            arlis.add(new history(d,dp));
                        }
                        //Toast.makeText(getApplicationContext(), "aar"+arlis.size()+"sss", //Toast.LENGTH_LONG).show();
                        histroyAdopter hA = new histroyAdopter(getApplicationContext(), arlis);
                        ListView listvp = findViewById(R.id.listv);
                        listvp.setAdapter(hA);
                        //Toast.makeText(getApplicationContext(), "aar"+arlis.size()+hA.isEmpty(), //Toast.LENGTH_LONG).show();
                        listvp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String var1="1";
                                String var2="1";
                                String currncy1="USA";
                                String currncy2="USA";
                                String[] list=new String[4];
                                //Toast.makeText(getApplicationContext(), "good inside", //Toast.LENGTH_LONG).show();
                                File f = new File("save.json");
                                    try{
                                   list=getSavedData();
                                        currncy1=list[0];
                                       var1=list[1];
                                        currncy2=list[2];
                                         var2=list[3];
                                }
                                    catch (Exception d){
                                        System.out.println("444"+d);
                                    }
                                try {
                                    //njo.put( arlis.get(i).getResulte(), arlis.get(i).getInterme());
                                    //njo.put(currncy2, var2);
                                    String[] nList = new String[] {arlis.get(i).getResulte(), arlis.get(i).getInterme(),list[2], list[3]};
                                    savedata(nList);
                                }
                                catch (Exception dp){
                                    dp.printStackTrace();
                                }
                                //variable = Double.parseDouble(arlis.get(i).getInterme());
                                ////Toast.makeText(getApplicationContext(), "variabl- "+variable, //Toast.LENGTH_LONG).show();
                                //setContentView(R.layout.histr2test);
                                Intent intetmain = new Intent(getApplicationContext(),currencycover.class);
                                //intetmain.putExtra("var",variable);
                                //intetmain.putExtra("interme",interm);
                                startActivity(intetmain);
                            }
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    //setContentView(R.layout.histroylist);
                    //Toast.makeText(getApplicationContext(),"paar",//Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.secondcurr:
                try {
                    //Toast.makeText(getApplicationContext(),"aar",//Toast.LENGTH_LONG).show();
                    try {
                        setContentView(R.layout.histroylist);
                        //View view = LayoutInflater.from(this).inflate(R.layout.histroylist,null);
                        //getWindow().getDecorView().
                        //getWindow().getDecorView().setVisibility(View.INVISIBLE);
                        //findViewById(R.id.listv).setVisibility(View.VISIBLE);
                        final ArrayList<history> arlis = new ArrayList<>();
                        String str = readfile("currncy.json");
                        JSONObject jo = new JSONObject(str);
                        for (int i = 0; i < jo.names().length(); i++) {
                            String d=jo.names().getString(i);
                            String dp = (jo.get(jo.names().getString(i))).toString();
                            System.out.println(d+" "+dp);
                            arlis.add(new history(d,dp));
                        }
                        //Toast.makeText(getApplicationContext(), "aar"+arlis.size()+"sss", //Toast.LENGTH_LONG).show();
                        histroyAdopter hA = new histroyAdopter(getApplicationContext(), arlis);
                        final ListView listvp = findViewById(R.id.listv);
                        listvp.setAdapter(hA);
                        //Toast.makeText(getApplicationContext(), "aar"+arlis.size()+hA.isEmpty(), //Toast.LENGTH_LONG).show();
                        ;
                        listvp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String var1="1";
                                String var2="1";
                                String currncy1="USA";
                                String currncy2="USA";
                                String[] list= new String[4];
                                File f = new File("save.json");
                                    try{
                                        list=getSavedData();

                                    }
                                    catch (Exception d){
                                        System.out.println("444"+d);
                                    }
                                try {
                                    String[] nList = new String[] {list[0], list[1],arlis.get(i).getResulte(), arlis.get(i).getInterme()};
                                    savedata(nList);
                                }
                                catch (Exception dp){
                                    dp.printStackTrace();
                                }
                                //variable = Double.parseDouble(arlis.get(i).getInterme());
                                ////Toast.makeText(getApplicationContext(), "variabl- "+variable, //Toast.LENGTH_LONG).show();
                                //setContentView(R.layout.histr2test);
                                Intent intetmain = new Intent(getApplicationContext(),currencycover.class);
                                //intetmain.putExtra("var",variable);
                                //intetmain.putExtra("interme",interm);
                                startActivity(intetmain);
                            }
                        });
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    //setContentView(R.layout.histroylist);
                    //Toast.makeText(getApplicationContext(),"paar",//Toast.LENGTH_LONG).show();

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case R.id.num1:
                /*if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }*/
                interme=interme+"1";
                doOpration();
                break;
            case R.id.num2:
                interme=interme+"2";
                doOpration();
                break;
            case R.id.num3:
                interme=interme+"3";
                ;doOpration();
                break;
            case R.id.num4:
                interme=interme+"4";
                doOpration();
                break;
            case R.id.num5:
                interme=interme+"5";
                ;doOpration();
                break;
            case R.id.num6:
                interme=interme+"6";
                ;doOpration();
                break;
            case R.id.num7:
                interme=interme+"7";
                ;doOpration();
                break;
            case R.id.num8:
                interme=interme+"8";
                doOpration();
                break;
            case R.id.num9:
                interme=interme+"9";
                doOpration();
                break;
            case R.id.num0:
                interme=interme+"0";
                doOpration();
                break;
            case R.id.numdot:
                interme=interme+".";
                doOpration();
                break;



        }
    }

    public void doOpration(){
        setVariable();
        DecimalFormat df = new DecimalFormat("0.000");
        if(rauting) {
            if(!interme.isEmpty()) {
                firstcurtext.setText(interme);
                String input = df.format(variable * Double.parseDouble(firstcurtext.getText().toString()));
                secondcurtext.setText((input));
                int i = checkfont(1);
                if (i == 1) {
                    firstcurtext.setTextSize(20);
                }
            }
            else {
                firstcurtext.setText("");
            }
        }
        else {
            if (!interme.isEmpty()) {
                secondcurtext.setText(interme);
                String input = df.format(variable * Double.parseDouble(secondcurtext.getText().toString()));

                firstcurtext.setText(input);
                int i = checkfont(0);
                if (i == 1) {
                    firstcurtext.setTextSize(20);
                }

            }
            else {
                secondcurtext.setText("");
            }
        }
    }

    public int checkfont(int i){
        String str="";
        int re=0;
        if(1==0){
            str=firstcurtext.getText().toString();
        }
        else {
            str=firstcurtext.getText().toString();
        }
        int lenth=str.length();
        if(lenth==10 || lenth==15){
            re= 1;
        }
        return re;

    }


    public StringRequest refreshvariable(String url){
        StringRequest jor = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                            Log.d("ee","www"+response.toString());
                              try {
                                  JSONObject jo = new JSONObject(response);
                                  //Toast.makeText(getApplicationContext(),jo.getString("quotes"),//Toast.LENGTH_LONG).show();
                                  savejson(jo.get("quotes").toString(),"currncy.json");
                              }
                              catch (Exception d){
                                  d.printStackTrace();
                              }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),"it doe",//Toast.LENGTH_SHORT).show();
                Log.d("somting","ent wrong");
                try {
                    if (error.getMessage() == null) {
                        //Toast.makeText(getApplicationContext(),"ggg",//Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("ee","www"+error.getMessage());
                        //Toast.makeText(getApplicationContext(),error.getMessage(),//Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }


        });
        return jor;
    }

    public void savejson(String str,String FN) throws Exception{

        File fs = new File(getApplicationContext().getFilesDir(),FN);
        FileWriter fr = new FileWriter(fs);
        BufferedWriter bfr = new BufferedWriter(fr);
        bfr.write(str);
        //JSONObject jo = new JSONObject();
        bfr.close();
    }
    public String readfile(String filename) throws Exception{
        File file = new File(getApplicationContext().getFilesDir(),filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader buFileRead = new BufferedReader(fileReader);
        StringBuilder strbld = new StringBuilder();
        String str = buFileRead.readLine();
        while (str!=null){
            strbld.append(str).append("\n");
            str=buFileRead.readLine();
        }
        buFileRead.close();
        return strbld.toString();
    }

    public void setVariable(){
        try {
            String[] list=getSavedData();
            //JSONObject joq = new JSONObject(jo.get("var").toString()) ;
            System.out.println("gggggg-------------ggggggggggggg");
         variable1=Double.parseDouble(list[1]);
         variable2=Double.parseDouble(list[3]);
         firstcurr.setText(list[0]);
            secondcurr.setText(list[2]);
            //Toast.makeText(getApplicationContext(),variable1.toString()+variable2.toString(),//Toast.LENGTH_SHORT).show();
        }
        catch (Exception f){
            //Toast.makeText(getApplicationContext(),"faied set variable +",//Toast.LENGTH_SHORT).show();
            f.printStackTrace();
        }
        if(rauting) {
            variable = variable2 / variable1;
        }
        else {
            variable = variable1 / variable2;

        }
        //Toast.makeText(getApplicationContext(),"main variable"+variable.toString(),//Toast.LENGTH_SHORT).show();
    }
    public  String[] getSavedData() throws Exception{
        String[] list=new String[4];
        String var2,var1,currncy1,currncy2;
        String str =readfile("save.json");
        JSONObject ojo = new JSONObject(str);
        JSONObject jovar1=new JSONObject(ojo.getString("var1"));
        JSONObject jovar2=new JSONObject(ojo.getString("var2"));
        var1=jovar1.getString(jovar1.names().getString(0));
        var2=jovar2.getString(jovar2.names().getString(0));
        currncy1=jovar1.names().get(0).toString();
        currncy2=jovar2.names().get(0).toString();
        list[0]=currncy1;
        list[1]=var1;
        list[2]=currncy2;
        list[3]=var2;
        return list;

    }
    public void savedata(String[] list) throws Exception{
        JSONObject ojo = new JSONObject();
        JSONObject jovar1=new JSONObject();
        jovar1.put(list[0],list[1]);
        JSONObject jovar2=new JSONObject();
        jovar2.put(list[2],list[3]);
        ojo.put("var1",jovar1.toString());
        ojo.put("var2",jovar2.toString());
        System.out.println(ojo.toString());
        savejson(ojo.toString(),"save.json");
    }

}
