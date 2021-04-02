package com.example.calculator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal.*;
import androidx.appcompat.app.AppCompatActivity;
//import java.lang.reflect.Array;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.math.*;
import java.util.regex.Pattern;

import android.content.Intent;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
//import android.widget.Switch;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.*;
public class MainActivity extends AppCompatActivity implements OnClickListener {
    Button refresh,change,num1,numdot,num2,num3,num4,num5,num6,num7,num8,num9,num0,opad,opsb,opmu,opdv,claer,equal,opbw,opper,opsq;
    TextView result,intermRe,oprations,finals,rawstrings;
    MathContext mc = new MathContext(7,RoundingMode.CEILING);
    BigDecimal nfinalRe= new BigDecimal("0");
           BigDecimal finalRe= new BigDecimal("0");
    int opration;
    String rawstring="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        result = findViewById(R.id.Result);
        intermRe=findViewById(R.id.intermRe);
        //oprations=findViewById(R.id.opration);
        //finals =findViewById(R.id.finals);
        //rawstrings=findViewById(R.id.rawstring);



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
        opdv=findViewById(R.id.opdv);
        opad=findViewById(R.id.opad);
        opsb=findViewById(R.id.opsb);
        claer=findViewById(R.id.claer);
        equal=findViewById(R.id.eqaul);
        opmu= findViewById(R.id.opmu);
        opbw=findViewById(R.id.opbw);
        opper=findViewById(R.id.opper);
        refresh=findViewById(R.id.histroy);
        opsq=findViewById(R.id.opsq);
        change =findViewById(R.id.curr);
    change.setOnClickListener(this);
        numdot =findViewById(R.id.numdot);
        numdot.setOnClickListener(this);
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
        opdv.setOnClickListener(this);
        opad.setOnClickListener(this);
        opsb.setOnClickListener(this);
        opmu.setOnClickListener(this);
        claer.setOnClickListener(this);
        equal.setOnClickListener(this);
        opbw.setOnClickListener(this);
        opper.setOnClickListener(this);
        refresh.setOnClickListener(this);
        opsq.setOnClickListener(this);
        Intent inthistory=getIntent();
        System.out.println("---- "+inthistory.getStringExtra("resulte")+" ---");

        if(inthistory.getStringExtra("resulte")!=null || inthistory.getStringExtra("interme")!=null ) {
            result.setText(inthistory.getStringExtra("resulte"));
            intermRe.setText(inthistory.getStringExtra("interme"));
            /*Pattern pat = Pattern.compile("[^0-9.]");
            Matcher matcher = pat.matcher(result.getText().toString());
            String oprator="";
            int intex=-1;
            while(matcher.find()){
                oprator=matcher.group();
                intex = matcher.end();
            }
            if(!oprator.equals("") || intex!=0){
                reset(oprator,intex);
            }*/
            String str=result.getText().toString();
            getrawstring();
            show_stat();
            if (opration==1){
                finalRe= (new BigDecimal(intermRe.getText().toString())).subtract( new BigDecimal(rawstring));
            }
            else if (opration==2){
                finalRe= (new BigDecimal(intermRe.getText().toString())).add( new BigDecimal(rawstring));
            }
            else if(str.contains("+") || str.contains("-")) {
                int in=findsubadd();
                String ress =result.getText().toString();
                String str2= ress.substring(in+1);
                nfinalRe=calculate(str2);
                if(ress.charAt(in)=='+'){
                    finalRe= (new BigDecimal(intermRe.getText().toString())).subtract( new BigDecimal(rawstring));
                }
                else if (ress.charAt(in)=='-') {
                    finalRe = (new BigDecimal(intermRe.getText().toString())).add(new BigDecimal(rawstring));
                }
            }
            else {
                nfinalRe=calculate(str);
            }
            show_stat();


        }
    }

    public void onClick(View V){

        //rawstrings.setText("r"+rawstring);
        //finals.setText("f"+finalRe);
        //oprations.setText("o"+Integer.toString(opration));
        switch(V.getId()){
            case R.id.histroy:
                /*String histroystr="";
                try {
                  histroystr= readjson("histroy.json").toString();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    pushinhistroy(histroystr);
                } catch (Exception epp) {
                    epp.printStackTrace();
                }*/
                Intent historyintent = new Intent(this,historylist.class);
                startActivity(historyintent);
               // rawstrings.setText("r"+rawstring);
                //finals.setText("f"+finalRe);
                //oprations.setText("o"+nfinalRe);
                break;

            case R.id.numdot:
                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                rawstring = rawstring + ".";
                result.setText(result.getText().toString()+".");
                doOpration(finalRe,rawstring);

                break;
            case R.id.num1:
                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                rawstring = rawstring + "1";
                result.setText(result.getText().toString()+"1");
                doOpration(finalRe,rawstring);

                break;
            case R.id.num2:
                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                     opration=4;
                }
                rawstring = this.rawstring + "2";
                 result.setText(result.getText()+"2");
               ;doOpration(finalRe,this.rawstring);
                break;
            case R.id.num3:
                                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                this.rawstring = this.rawstring + "3";
                 result.setText(result.getText()+"3");
               ;doOpration(finalRe,this.rawstring);
                break;
            case R.id.num4:
                                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                this.rawstring = this.rawstring + "4";
                 result.setText(result.getText()+"4");
               ;doOpration(finalRe,this.rawstring);
                break;
            case R.id.num5:
                                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                this.rawstring = this.rawstring + "5";
                 result.setText(result.getText()+"5");
               ;doOpration(finalRe,this.rawstring);
                break;
            case R.id.num6:
                                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                this.rawstring = this.rawstring + "6";
                 result.setText(result.getText()+"6");
               ;doOpration(finalRe,this.rawstring);
                break;
            case R.id.num7:
                                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                this.rawstring = this.rawstring + "7";
                 result.setText(result.getText()+"7");
               ;doOpration(finalRe,this.rawstring);
                break;
            case R.id.num8:
                                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                this.rawstring = this.rawstring + "8";
                 result.setText(result.getText()+"8");
               doOpration(finalRe,this.rawstring);
                break;
            case R.id.num9:
                                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                this.rawstring = this.rawstring + "9";
                 result.setText(result.getText()+"9");
               doOpration(finalRe,this.rawstring);
                break;
            case R.id.num0:
                                if (result.getText().toString().endsWith("%") || result.getText().toString().endsWith("(^2)")){
                    result.setText(result.getText().toString()+"*");
                    opration=4;
                }
                this.rawstring = this.rawstring + "0";
                result.setText(result.getText()+"0");
                doOpration(finalRe,this.rawstring);

                break;
            case R.id.opad:
                //we dont wan rawstring in fanial result but interm resulte
                //
                //
                // System.out.println(calculate("12+12*3/2-2")+"-----////");

                if(opration==5) {
                opration = 1;
                this.rawstring = "0";
                result.setText(result.getText().toString() + "+");
                }
                /*else if(opration==6){
                    opration=1;
                    result.setText(result.getText().toString() + "+");

                }*/
                //if its opration is in second part
                else if(opration!=0){
                    String wop=result.getText().toString();
                    Character lChar = wop.charAt(wop.length()-1);
                    String s = Character.toString(lChar);
                    boolean p = Pattern.matches("[^0-9.]",s);
                    System.out.println("------------->"+p);
                    if(p && !wop.endsWith("%")){

                        result.setText(chopLast(wop));
                    }
                    System.out.println("------------->"+result);
                    doOpration(finalRe,this.rawstring);
                    this.rawstring ="0";
                    finalRe=new BigDecimal(intermRe.getText().toString());
                    result.setText(result.getText().toString() + "+");
                    opration=1;
                }
                else{
                    if(this.rawstring.isEmpty())
                    {
                        ////Toast.makeText(this,"its emty",////Toast.LENGTH_SHORT).show();
                    }
                    else {
                finalRe = new BigDecimal(this.rawstring);
                opration = 1;
                this.rawstring = "0";

                result.setText(result.getText().toString() + "+");}
            }
                break;
            case R.id.opsb:
                String wss = result.getText().toString();
                System.out.println(wss.charAt(wss.length()-1));
                //if its opration is in second part
                if(opration==5) {
                    opration = 2;
                    this.rawstring = "0";
                    result.setText(result.getText().toString() + "-");
                }
                else if(opration!=0){
                    String wop=result.getText().toString();
                    Character lChar = wop.charAt(wop.length()-1);
                    String s = Character.toString(lChar);
                    boolean p = Pattern.matches("[^0-9.]",s);
                    System.out.println("------------->"+p);
                    if(p && !wop.endsWith("%")){
                        result.setText(chopLast(wop));
                    }
                    System.out.println("------------->"+result);
                    doOpration(finalRe,this.rawstring);
                    this.rawstring ="0";
                    opration=2;
                    finalRe=new BigDecimal(intermRe.getText().toString());
                    result.setText(result.getText().toString() + "-");
                }

                else {

                        finalRe = new BigDecimal(this.rawstring);
                        opration = 2;
                        this.rawstring = "0";
                        result.setText(result.getText().toString() + "-");

                }
                break;
            case R.id.opdv:
                wss = result.getText().toString();
                System.out.println(wss.charAt(wss.length()-1));
                //if its opration is in second part
                if(opration==5) {
                    opration = 3;
                    this.rawstring = "0";
                    finalRe=nfinalRe;
                    result.setText(result.getText().toString() + "/");
                }
                else if(opration!=0){
                    String wop=result.getText().toString();
                    // if its 12+34+ + / we need to remove +
                    if(!checkdigi(wop) && !wop.endsWith("%")){
                        result.setText(chopLast(wop));
                        doOpration(finalRe,this.rawstring);

                        if (opration==2 || opration==1) {
                            finalRe = new BigDecimal(intermRe.getText().toString());
                        }
                        else if (opration==3 || opration==4){
                            nfinalRe = new BigDecimal(intermRe.getText().toString());
                        }
                    }
                    if (opration==1 || opration==2){
                        ////Toast.makeText(this,"its done",////Toast.LENGTH_SHORT).show();
                        nfinalRe=new BigDecimal(rawstring);
                    }
                    else if (opration==3 || opration==4){
                        nfinalRe = nfinalRe.add(new BigDecimal(rawstring));
                    }
                    this.rawstring ="0";
                    opration=3;
                    result.setText(result.getText().toString() + "/");
                }
                else {
                    int len =wss.length();
                    if(this.rawstring.isEmpty() || len==0){
                        ////Toast.makeText(this,"its emty",////Toast.LENGTH_SHORT).show();
                    }
                    else {
                        nfinalRe = new BigDecimal(this.rawstring);
                        finalRe =nfinalRe;
                        opration = 3;
                        this.rawstring = "0";
                        result.setText(result.getText().toString() + "/");
                    }
                }
                break;
            case R.id.claer:
                String wop1=intermRe.getText().toString();
                if(wop1.length()>0) {
                    //for duable click on clear
                    try {
                        try {

                            readjson("histroy.json");
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        jsonhandling("histroy.json");
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    BigDecimal op1 = new BigDecimal(intermRe.getText().toString());
                    finalRe = new BigDecimal("0");
                    rawstring = "";
                    result.setText(null);
                    intermRe.setText(null);
                    finalRe = BigDecimal.ZERO;
                    opration = 0;
                    nfinalRe=BigDecimal.ZERO;
                }
                else {
                    ////Toast.makeText(this,"its empty",////Toast.LENGTH_SHORT);
                }
                break;
            case R.id.eqaul:
                String wop=intermRe.getText().toString();
                if(wop.length()>0) {
                    if (opration == 5) {
                        ////Toast.makeText(this, "done this command already", ////Toast.LENGTH_SHORT).show();
                    }
                    // first click to claer interm text and put result
                    else {
                        try {
                            readjson("histroy.json");
                            jsonhandling("histroy.json");
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        BigDecimal op12 = new BigDecimal(intermRe.getText().toString());
                        System.out.println(op12);
                        finalRe = op12;
                        System.out.println(finalRe);
                        opration = 5;
                        result.setText(op12.toString());
                        rawstring = "0";
                        intermRe.setText(null);
                    }
                }
                else {
                    ////Toast.makeText(this, "its empty", ////Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.opmu:
               wss = result.getText().toString();
                System.out.println(wss.charAt(wss.length()-1));
                //if its opration is in second part
                if(opration==5) {
                    opration = 4;
                    this.rawstring = "0";
                    result.setText(result.getText().toString() + "*");
                }
                else if(opration!=0){
                    //---->if last char is +,-or / then cut it.
                    String res=result.getText().toString();
                    //Character lChar = res.charAt(res.length()-1);
                    //String s = Character.toString(lChar);
                    //boolean p = Pattern.matches("[^0-9.]",s);
                    if(!checkdigi(res) && !res.endsWith("%") && !res.endsWith("(^2)")){
                        //if(!res.endsWith("%") || !res.endsWith("(^2)")) {
                            result.setText(chopLast(res));
                            //if (opration==1 || opration==2){
                            //  ////Toast.makeText(this,"its done",////Toast.LENGTH_SHORT).show();
                            //nfinalRe=Integer.parseInt(rawstring);
                            //}
                            //else {
                            //  ////Toast.makeText(this,"its not"+nfinalRe,////Toast.LENGTH_SHORT).show();
                            //}
                            doOpration(finalRe, this.rawstring);
                            if (opration == 2 || opration == 1) {
                                finalRe = new BigDecimal(intermRe.getText().toString());
                            } else if (opration == 3 || opration == 4) {
                                nfinalRe = new BigDecimal(intermRe.getText().toString());
                            }
                        //}
                    }
                    if (opration==1 || opration==2){
                        ////Toast.makeText(this,"its done",////Toast.LENGTH_SHORT).show();
                        nfinalRe=new BigDecimal(rawstring);
                    }
                    else if (opration==3 || opration==4){
                        nfinalRe = nfinalRe.multiply(BigDecimal.valueOf(Integer.parseInt(rawstring)));
                    }
                    this.rawstring ="0";
                    opration=4;
                    //finalRe=new BigDecimal(intermRe.getText().toString());
                    result.setText(result.getText().toString()+"*");
                }
                else {
                    int len =wss.length();
                    if(this.rawstring.isEmpty() || len==0){
                        ////Toast.makeText(this,"its emty",////Toast.LENGTH_SHORT).show();
                    }
                    else {
                        finalRe = new BigDecimal(this.rawstring);
                        nfinalRe=finalRe;
                        opration = 4;
                        this.rawstring = "0";
                        result.setText(result.getText().toString() + "*");
                    }
                }
            break;


        case R.id.opbw:
            String reS=result.getText().toString();
            System.out.println("tot3");
            /* if(lChar.equals('+') || lChar.equals('/') || lChar.equals('*') || lChar.equals('-')){
            }
            else {
            } */
            //if resulte is not epmty
            if(reS.length()==1){
                System.out.println("pop hes");
                //BigDecimal op1 = new BigDecimal(intermRe.getText().toString());
                finalRe = new BigDecimal("0");
                rawstring = "";
                result.setText("");
                intermRe.setText(null);
                finalRe = BigDecimal.ZERO;
                opration = 0;
                nfinalRe=BigDecimal.ZERO;
                System.out.println("pop hes");
                break;
            }
            if(!result.getText().toString().isEmpty()) {
                //cheak if last char is a digit or not
                boolean p = checkdigi(reS);
                Character charat2=reS.charAt(reS.length()-2);
                if(p) {
                    //if rawstring=002
                    System.out.println("tot2");
                    if(rawstring.contains(".")) {
                        rawstring = Float.toString(Float.parseFloat(rawstring));
                    }
                    else {
                        rawstring = Integer.toString(Integer.parseInt(rawstring));

                    }
                    if (rawstring.length()>1 ){
                        rawstring = chopLast(rawstring);
                       // if (charat2=='/' || charat2=='*') {
                        String str= result.getText().toString();
                        result.setText(chopLast(str).toString());
                        doOpration(finalRe, rawstring);
                        //}
                        //else {
                            //intermRe.setText("--");
                        //}
                    }
                    else if(reS.endsWith("%")){
                        BigDecimal c = BigDecimal.ZERO;
                        c=finalRe.divide(BigDecimal.valueOf(100),mc);
                        c.setScale(7,RoundingMode.CEILING);
                        intermRe.setText( c.toString());
                        result.setText(chopLast(result.getText().toString()));
                    }
                    else {

                        //if rawstring just have 1 char we cant use doOprations
                        if (charat2=='/' || charat2=='*'){
                            rawstring="";
                        }
                        else{
                            rawstring="0";
                        }
                        intermRe.setText("");
                        result.setText(chopLast(result.getText().toString()));

                        // ^|^|^
                    }

                }
                else{
                    // for oprator rollback
                    String oprator="";
                    int intex=-1;
                    show_stat();
                    //rawstring = chopLast(rawstring);
                    System.out.println("tot1");
                    String resstr1=result.getText().toString();
                    boolean u=resstr1.endsWith("%");
                    //23+34(^2)
                    if(resstr1.endsWith("(^2)")) {
                        result.setText(resstr1.substring(0,(resstr1.length()-4)));
                    }
                    else {
                        result.setText(chopLast(resstr1));
                    }
                    //---> for check where is next oprator <---
                    Pattern pat = Pattern.compile("[^0-9.]");
                    Matcher matcher = pat.matcher(result.getText().toString());
                    System.out.println("tot");
                    while(matcher.find()){
                        oprator=matcher.group();
                        intex = matcher.end();
                    }
                    String resstr2=result.getText().toString();
                    //check for %
                    if(u){
                        nfinalRe=nfinalRe.multiply(BigDecimal.valueOf(100));
                    }
                    if(oprator.equals("") || intex==-1){
                        // if eg.->"12+" then finalRe should be zero rawString get finalRe just likestrate
                        getrawstring();
                        finalRe=BigDecimal.ZERO;
                        opration=0;
                        show_stat();
                        System.out.println(("----------------------->here"));
                        doOpration(finalRe,rawstring);
                    }
                    else {
                        String st= result.getText().toString();
                        int a=st.length();
                        //if 23+34%+
                        if(intex==a){
                            if (st.endsWith("%")){
                                //problem here (punchar lagaya hai)
                                opration=6;
                            }
                        }
                        else {
                            if (resstr1.endsWith("+") || resstr1.endsWith("-")) {
                                ////Toast.makeText(this,"ff0",////Toast.LENGTH_SHORT).show();
                                if(oprator.equals("*") || oprator.equals("/")){
                                    ////Toast.makeText(this,"ff1",////Toast.LENGTH_SHORT).show();
                                    ///--------->
                                    if(resstr2.contains("+") || resstr2.contains("-")){
                                        ////Toast.makeText(this,"ff2",////Toast.LENGTH_SHORT).show();
                                        int in=findsubadd();
                                        String str= resstr2.substring(in+1);
                                        nfinalRe=calculate(str);
                                        str=resstr2.substring(0,in);
                                        finalRe=calculate(str);
                                        if(resstr2.charAt(in)=='+' ){
                                            intermRe.setText(finalRe.add(nfinalRe).toString());
                                        }
                                        else if(resstr2.charAt(in)=='-'){
                                            intermRe.setText(finalRe.add(nfinalRe).toString());
                                        }
                                    }
                                    else {
                                        ////Toast.makeText(this,"elf3",////Toast.LENGTH_SHORT).show();
                                        nfinalRe=calculate(resstr2);
                                        intermRe.setText( (nfinalRe.toString()));
                                    }
                                   rawstring=resstr2.substring(intex);
                                    opration=findoprator(oprator);
                                    //doOpration(finalRe,rawstring);


                                }
                                else {
                                reset(oprator,intex);}
                            }
                            else {

                                System.out.println(("----------------------->here"));
                                reset(oprator, intex);
                            }
                        }
                    }
                }
            }
            break;
            case R.id.opper:
                wss = result.getText().toString();
                if(wss.isEmpty()){
                    System.out.println("rem"+wss);
                    break;
                }
                System.out.println("rem2"+wss);
                Character char2='?';
                if(wss.length()>=2) {
                    System.out.println("jojdf");
                    char2 = wss.charAt(wss.length() - 2);
                }
                //if its opration is in second part


                if(opration==5) {
                    this.rawstring = "0";
                    BigDecimal c = BigDecimal.ZERO;
                    c = finalRe.divide(BigDecimal.valueOf(100),mc);
                    c.setScale(7,RoundingMode.CEILING);
                    intermRe.setText(c.toString());
                    result.setText(result.getText().toString() + "%");
                }
                // rg if 300+200%*+% or 300+200%+%
                else if(char2=='%' || wss.endsWith("%")){
                    ////Toast.makeText(this,"its invalid input",////Toast.LENGTH_SHORT).show();
                }
                else if(opration!=0){
                    //---->if last char is +,-or / then cut it.
                    String res=result.getText().toString();
                    Character lChar = res.charAt(res.length()-1);
                    String s = Character.toString(lChar);
                    boolean p = Pattern.matches("[^0-9.]",s);
                    if(p && !res.endsWith("(^2)")){
                        result.setText(chopLast(res));
                        getrawstring();
                    }
                    //-->check complete<---

                    //doOpration(finalRe,this.rawstring);
                    //this.rawstring ="0";
                    //finalRe=new BigDecimal(intermRe.getText().toString());
                    //opration for %
                    //intermRe.setText(Integer.toString(finalRe/100));
                    //finalRe=new BigDecimal(intermRe.getText().toString());

                    opration=6;
                    doOpration(finalRe,rawstring);
                    // nfinalRe = new BigDecimal(intermRe.getText().toString());
                    this.rawstring = "0";
                    result.setText(result.getText().toString() + "%");
                }
                else {
                    int len =wss.length();
                    if(this.rawstring.isEmpty() || len==0){
                        ////Toast.makeText(this,"its emty",////Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //eg. reselt=200 so final=0; rawstring
                        opration=6;
                        doOpration(finalRe,rawstring);
                       // nfinalRe = new BigDecimal(intermRe.getText().toString());
                        this.rawstring = "0";
                        result.setText(result.getText().toString() + "%");
                    }
                }
                break;
            case R.id.curr:
                Intent currnecypage = new Intent(this,currencycover.class);
                startActivity(currnecypage);
                break;



            case R.id.opsq:
                wss = result.getText().toString();
                show_stat();
                if(wss.isEmpty()) {

                    System.out.println("joj");

                        break;

                }
                System.out.println("po1");
                Character chart='?';
                System.out.println("po2");

                if(wss.length()>=2) {
                    System.out.println("jojdf");
                    chart = wss.charAt(wss.length() - 2);
                }
                System.out.println("po3");
                //if its opration is in second part
                if(opration==5) {
                    System.out.println("opration =");
                    this.rawstring = "0";
                    getrawstring();
                    intermRe.setText((finalRe.multiply(nfinalRe)).toString());
                    finalRe=new BigDecimal(intermRe.getText().toString());
                    result.setText(result.getText().toString()+"(^2)");
                }
                // rg if 300+200%*+% or 300+200%+%
                else if(chart=='%' || wss.endsWith("%")){
                    Toast.makeText(this,"its invalid input",Toast.LENGTH_SHORT).show();
                }
                else if(opration!=0){
                    System.out.println("its in main");
                    //---->if last char is +,-or / then cut it.
                    String res=result.getText().toString();
                    Character lChar = res.charAt(res.length()-1);
                    String s = Character.toString(lChar);
                    boolean p = Pattern.matches("[^0-9.]",s);
                    if(p){
                        result.setText(chopLast(res));
                        getrawstring();
                    }
                    //-->check complete<---
                    opration=7;
                    result.setText(result.getText().toString() + "(^2)");
                    doOpration(finalRe,this.rawstring);
                    //this.rawstring ="0";
                    //finalRe=new BigDecimal(intermRe.getText().toString());
                    //opration for %
                    //intermRe.setText(Integer.toString(finalRe/100));
                    //finalRe=new BigDecimal(intermRe.getText().toString());

                }
                else {
                    System.out.println("its initinal stateg");
                    int len =wss.length();
                    if(this.rawstring.equals("0") || len==0){
                        Toast.makeText(this,"its emty",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //eg. reselt=200 so final=0; rawstring
                        System.out.println("joj2");
                        if(rawstring.contains(".")) {
                            rawstring = Float.toString(Float.parseFloat(rawstring));
                            intermRe.setText(Float.toString(Float.parseFloat(rawstring)*Float.parseFloat(rawstring)));

                        }
                        else {
                            rawstring = Integer.toString(Integer.parseInt(rawstring));
                            intermRe.setText(Integer.toString(Integer.parseInt(rawstring)*Integer.parseInt(rawstring)));


                        }
                        finalRe = new BigDecimal(intermRe.getText().toString());
                        this.rawstring = "0";
                        result.setText(result.getText().toString() + "(^2)");
                    }
                }
                System.out.println("po5----");
                break;

                //getrawstring();
                //intermRe.setText(Integer.toString(Integer.parseInt(rawstring)*Integer.parseInt(rawstring)));
                //finalRe=new BigDecimal(intermRe.getText().toString());
                //result.setText(result.getText().toString()+"^2");
        }
    }



    public boolean checkdigi(String wop){
        Character lChar = wop.charAt(wop.length()-1);
        String s = Character.toString(lChar);
        return Pattern.matches("\\d|\\.", s);
    }

    public void getrawstring(){
      String  res=result.getText().toString();
      int intex=-1;
      String opra="";
      Pattern par = Pattern.compile("[^0-9.]");
      Matcher matcher = par.matcher(res);
      while (matcher.find()){
          intex=matcher.end();
          opra=matcher.group();
      }
      if(intex!=-1 && !opra.equals("")){
          if (intex==res.length()){
              rawstring="";
          }
          else {
              rawstring=res.substring(intex);
          }
        }
      else {
          if(!res.contains("+") || !res.contains("-") || !res.contains("*") || !res.contains("%") || !res.contains("/")){
              rawstring=res;
          }

      }
        opration =findoprator(opra);

    }

    public int  lastOurrencOfNonDigit(String res){

        String drawstring;
        //if found nothing then full string
        int intex=0;
        String opra="";
        Pattern par = Pattern.compile("[^0-9.]");
        Matcher matcher = par.matcher(res);
        while (matcher.find()){
            intex=matcher.end();
            opra=matcher.group();
        }
        if(intex!=-1 && opra==""){
            if (intex==res.length()){
                drawstring="";
            }
            else {
                drawstring=res.substring(intex);
            }
        }
        else {
            if(!res.contains("+") || !res.contains("-") || !res.contains("*") || !res.contains("%") || !res.contains("/")){
                drawstring=res;
            }
        }
        return intex;
    }

    public void doOpration(BigDecimal a,String B){

            if (B.equals("")){
                if (opration==3 || opration==4){
                    B="1";
                }
                else {
                    B="1";
                }

            }

        BigDecimal b=new BigDecimal(B);
        if (0==b.compareTo(BigDecimal.ZERO) ) {
            if (opration == 4) {
                b = BigDecimal.ONE;
            }
        }
        if(opration==1){
            intermRe.setText("0");
            intermRe.setText(((a.add(b)).toString()));
        }
        if(opration==2){
            intermRe.setText("0");
            BigDecimal c = BigDecimal.ZERO;
            c = a.subtract(b,mc);
            c.setScale(7,RoundingMode.CEILING);
            intermRe.setText(c.toString());
        }
        if(opration==3){
            if (0==b.compareTo(BigDecimal.ZERO)){
                ////Toast.makeText(getApplicationContext(),"cant be divsible by zero 0",////Toast.LENGTH_SHORT).show();
            }
            else {
                intermRe.setText("0");
                int intex=findsubadd();
                if (intex!=-1) {
                    String str= result.getText().toString();
                    String sbstr= result.getText().toString().substring(intex+1);
                    System.out.println("--------  "+sbstr);
                    nfinalRe=calculate(sbstr);
                    intermRe.setText((finalRe.add(nfinalRe)).toString());
                }
                else{
                    nfinalRe=calculate(result.getText().toString());
                    intermRe.setText(((nfinalRe).toString()));
                }
            }
        }
        if(opration==4){

            int intex=findsubadd();
            if (intex!=-1) {
                String str= result.getText().toString();
                String sbstr= result.getText().toString().substring(intex+1);
                System.out.println("--------  "+sbstr);
                nfinalRe=calculate(sbstr);
                intermRe.setText((finalRe.add(nfinalRe)).toString());
            }
            else{
                nfinalRe=calculate(result.getText().toString());
                intermRe.setText(((nfinalRe).toString()));
            }
        }
        if(opration==6){
            int intex=findsubadd();
            if (intex!=-1) {
                String str= result.getText().toString();
                String sbstr= result.getText().toString().substring(intex+1);
                System.out.println("--------  "+sbstr);
                nfinalRe=calculate(sbstr);
                ////Toast.makeText(getApplicationContext(),"can --> "+nfinalRe,////Toast.LENGTH_SHORT).show();
                //---->>> use diffent for - or + --->
                intermRe.setText((finalRe.add(nfinalRe)).toString());
            }
            else{
                nfinalRe=calculate(result.getText().toString());
                ////Toast.makeText(getApplicationContext(),"can2222 --> "+nfinalRe,////Toast.LENGTH_SHORT).show();
                intermRe.setText((nfinalRe).toString());
            }

        }
        if(opration==7){
            int intex=findsubadd();
            if (intex!=-1) {
                String str= result.getText().toString();
                String sbstr= result.getText().toString().substring(intex+1);
                System.out.println("--------  "+sbstr);
                nfinalRe=calculate(sbstr);
                ////Toast.makeText(getApplicationContext(),"can --> "+nfinalRe,////Toast.LENGTH_SHORT).show();
                //---->>> use diffent for - or + --->
                intermRe.setText( finalRe.add(nfinalRe).toString());
            }
            else{
                nfinalRe=calculate(result.getText().toString());
                ////Toast.makeText(getApplicationContext(),"can2222 --> "+nfinalRe,////Toast.LENGTH_SHORT).show();
                intermRe.setText( nfinalRe.toString());
            }

        }

    }

    public void multip(){
        String wss = result.getText().toString();
        System.out.println(wss.charAt(wss.length()-1));
        //if its opration is in second part
        if(opration==5) {
            opration = 4;
            this.rawstring = "0";
            result.setText(result.getText().toString() + "*");
        }
        else if(opration!=0){
            //---->if last char is +,-or / then cut it.
            String res=result.getText().toString();
            Character lChar = res.charAt(res.length()-1);
            String s = Character.toString(lChar);
            boolean p = Pattern.matches("[^0-9.]",s);
            if(p && !res.endsWith("%")){
                result.setText(chopLast(res));
            }
            //-->check complete<---

            doOpration(finalRe,this.rawstring);
            this.rawstring ="0";
            opration=4;
            finalRe=new BigDecimal(intermRe.getText().toString());
            result.setText(result.getText().toString() + "*");
        }
        else {
            int len =wss.length();
            if(this.rawstring.isEmpty() || len==0){
                ////Toast.makeText(this,"its emty",////Toast.LENGTH_SHORT).show();
            }
            else {
                finalRe = new BigDecimal(this.rawstring);
                opration = 4;
                this.rawstring = "0";
                result.setText(result.getText().toString() + "*");
            }
        }
    }


    public BigDecimal doOpration2(BigDecimal a,BigDecimal b,int opration2){
        BigDecimal c=new BigDecimal("1");
        if(opration2==1){
            c=a.add(b);
        }
        if(opration2==2){
           c=a.subtract(b);
        }
        if(opration2==3){
            if (0==b.compareTo(BigDecimal.ZERO)){
                ////Toast.makeText(getApplicationContext(),"cant be divsible by zero 0 op2",////Toast.LENGTH_SHORT).show();
            }
            else {
                c.setScale(6,RoundingMode.CEILING);
                c=a.divide(b,mc);
            }
        }
        if(opration2==4){
            c=a.multiply(b);
        }
        if(opration2==6){
            c.setScale(7,RoundingMode.CEILING);
            c=a.divide(BigDecimal.valueOf(100),mc);
        }
        return c;
    }


    public String chopLast(String a){
        if(a!=null && a.length()>1){
            a=a.substring(0,a.length()-1);
        }
        else if(a.length()==1){
            a="0";
        }
        return a;
    }


    public void reset(String str,int o){
        //o have AAA+BB postion "+" rawstring
        String resStr=result.getText().toString();
        //if last opration is % we dont have rawstring
       int opra= findoprator(str);
        //if last opration is NOT %
        if(opra!=6 ){
        rawstring = resStr.substring(o);
        }
        //reverseOprator();
        //oprator set
        //int revop = reverseOprator(opration);
        reversefinal(str,o);
        if(opra!=-1) {
            opration = opra;
        }

        ////Toast.makeText(this, "nfinal"+nfinalRe,////Toast.LENGTH_SHORT).show();
        doOpration(finalRe,(rawstring));

    }


    public int findoprator(String str){
        int opra=-1;
        switch(str){
            case "+":
                opra=1;
                break;
            case "-":
                opra=2;
                break;
            case "/":
                opra=3;
                break;
            case "*":
                opra=4;
                break;
            case "%":
                opra=6;
                break;
        }
        return opra;
    }


    public int reverseOprator(int revopration){
        switch(revopration){
            case 1:
                revopration=2;
                break;
            case 2:
                revopration=1;
                break;
            case 3:
                revopration=4;
                break;
            case 4:
                revopration=3;
                break;
            case 6:
                revopration=6;
        }
        return revopration;
    }


    public void reversefinal(String reop,int lastintex){
        String res=result.getText().toString();
        switch(reop){
                case "-":
                    if (opration==1 || opration==2) {
                        if(rawstring.contains(".")) {
                            finalRe = finalRe.add(BigDecimal.valueOf(Float.parseFloat(rawstring)));
                        }
                        else {
                            finalRe = finalRe.add(BigDecimal.valueOf(Integer.parseInt(rawstring)));


                        }
                     }
                    break;
                 case "+":
                     if (opration==1 || opration==2) {
                         if(rawstring.contains(".")) {
                             finalRe = finalRe.subtract(BigDecimal.valueOf(Float.parseFloat(rawstring)));
                         }
                         else {
                             finalRe = finalRe.subtract(BigDecimal.valueOf(Integer.parseInt(rawstring)));


                         }
                      }
                 break;


            case "*":
                setfinals(lastintex);
                int intexadsb=findsubadd();
                //--->if last  34+45* we need to change final result now
               /* if (res.charAt(lastintex)=='+') {
                    finalRe = finalRe - nfinalRe*Integer.parseInt(rawstring);
                }

                else if(res.charAt(lastintex)=='-') {
                    finalRe = finalRe + nfinalRe*Integer.parseInt(rawstring);
                }*/
                break;
            case "/":
                if(rawstring.equals("") || rawstring.equals("0")) {
                    ////Toast.makeText(this, "final cant reverse 0 or empty rawstring",////Toast.LENGTH_SHORT).show();
                }
                else {
                    setfinals(lastintex);
                    //finalRe = finalRe * Integer.parseInt(rawstring);
                }
                break;
            case "%":
                finalRe = finalRe.multiply(BigDecimal.valueOf(100));
                break;
        }

    }


    public void show_stat(){
        System.out.println("here are num -------........----------->>>>>");
        System.out.println("rawsstring-> "+rawstring);
        System.out.println("opration-> "+opration);
        System.out.println("finalRe-> "+finalRe);
        System.out.println("resulte-> "+result.getText().toString());
        System.out.println("interme->  "+intermRe.getText().toString());
        //----------------> computing a string <--------
    }


    public void setfinals(int lastintex){
        String res=result.getText().toString();
        if (res.endsWith("+") || res.endsWith("-")){
            chopLast(res);
        }
        if(res.contains("+") || res.contains("-")){

            int i1= res.lastIndexOf("+");
            int i2= res.lastIndexOf("-");
            int i=0;
            if (i1>i2){
                i=i1;
            }
            else {
                i=i2;
            }
;            String nfinalstr= res.substring(i+1,lastintex-1);
            System.out.println(("----------------------->here1 -  }"+nfinalstr));
            nfinalRe=calculate(nfinalstr);
            //System.out.println(("----------------------->here123"));
            //if(res.charAt(i) =='-'){
              //  nfinalRe = finalRe+nfinalRe;
            //}
            //else if(res.charAt(i)=='+'){
              //  finalRe = finalRe-nfinalRe;
            //}
        }
        else {
            nfinalRe=calculate(res);
            finalRe=BigDecimal.ZERO;
        }
    }


    public BigDecimal calculate(String s){
        BigDecimal Final = new BigDecimal("0");
        if (s.contains("(^2)")){
            //<<--  use pattran matcher

            while (s.contains("(^2)")){
                System.out.println("String in sq "+s);
                int i=s.indexOf("(^2)");
                System.out.println("intex of (^2) "+i);
                String str=s.substring(0,i-1);
                System.out.println("start str-"+str);
                int in=lastOurrencOfNonDigit(str);

                    System.out.println("last ourrense " + in);
                    String strInters = s.substring(in, i);
                BigDecimal inf= new BigDecimal(strInters);
                System.out.println("int"+inf);
                inf=inf.multiply(inf);
                s=s.substring(0,in)+inf+s.substring(i+4);
                System.out.println(s);
            }
        }
        if (s.contains("%")){
            //<<--  use pattran matcher

            while (s.contains("%")){
                System.out.println("String in % "+s);
                int i=s.indexOf("%");
                System.out.println("intex of % "+i);
                String str=s.substring(0,i-1);
                System.out.println("start str-"+str);
                int in=lastOurrencOfNonDigit(str);

                System.out.println("last ourrense " + in);
                String strInters = s.substring(in, i);
                BigDecimal inf= new BigDecimal(strInters);
                System.out.println("int"+inf);
                inf=inf.divide(new BigDecimal(100));
                s=s.substring(0,in)+inf+s.substring(i+1);
                System.out.println(s);
            }
        }

        if(s.contains("-") || s.contains("/") || s.contains("*") || s.contains("+")){

        System.out.println(("----------------------->here123"));
       // if(s.matches("[^0-9.]") || s.matches("[^0-9.]")) {
            String[] lis = s.split("[^0-9.]");
           // Collections.reverse(Arrays.asList(lis));
            System.out.println(("-------------------lis "+lis.length));
            for(String str:lis) {
                System.out.println(("-------------------lis "+str));
            }
            System.out.println(("----------------------->here1"));
            String[] oplis = findop(s);
            int lent = oplis.length;
            int lenofsubjects=lis.length;

        for (int i = 0; i < lent; i++) {
            int a = ((lenofsubjects - 1) - i);
            System.out.println(("----------------------->point1"));
            int b = (lent - 1) - (i);
            int c = ((lenofsubjects - 1) - i) - 1;
            System.out.println(("----------------------->point2" + b + " " + a));
            int opration2 = findoprator(oplis[b]);
            ////Toast.makeText(this, "ss- " + opration2, ////Toast.LENGTH_SHORT).show();
            System.out.println(("----------------------->point3"));
            if (opration2 <= 4) {
                if (i == 0) {
                    System.out.println("------------"+"inside main for oprations");

                    Final = doOpration2(new BigDecimal(lis[c]), new BigDecimal(lis[a]), opration2);
                    System.out.println(("----------------------->here12----- a=" + lis[c] + " b=" + lis[a]));

                } else {
                    System.out.println(("----------------------->here123"));
                    Final = doOpration2(new BigDecimal(lis[c]), Final, opration2);
                }
            }
            else if(opration2==6){
                if (i == 0) {
                    System.out.println(("-------------- inside  % opration not done yet" + lis[c] + " b=" + lis[a]));
                    Final = doOpration2(new BigDecimal(lis[c]), BigDecimal.ZERO, opration2);
                    System.out.println(("----------------------->here12----- a=" + lis[c] + " b=" + lis[a]));

                } else {
                    System.out.println(("----------------------->here123"));
                    Final = doOpration2( Final,BigDecimal.ZERO, opration2);
                }
            }

        }

        }
        else {
            System.out.println(("----------------------->here1234"));
            Final=new BigDecimal(s);
        }
        System.out.println(("done calulations"+Final));

        return Final;
    }


    public void showaray(String[] a){
        for( String el:a){
            System.out.println(el);
        }
    }


    public String[] findop(String s){
        Pattern pr = Pattern.compile("[^0-9.]");
        Matcher matcher = pr.matcher(s);
        String A="";
        while (matcher.find()){
            A=A+matcher.group();
        }
        return A.split("");
    }


    public int findsubadd() {
        String str = result.getText().toString();
        if (str.endsWith("+") || str.endsWith("-")){
            chopLast(str);
        }
        int i = -1;
        if (str.contains("+") || str.contains("-")) {

            int i1 = str.lastIndexOf("+");
            int i2 = str.lastIndexOf("-");
            if (i1 > i2) {
                i = i1;
            } else {
                i = i2;
            }

        }
        return i;
    }
    public JSONObject jsoncovert(String resulte,String  Interme) throws Exception{
        JSONObject jo = new JSONObject();
        jo.put("resulte",resulte);
        jo.put("interme",Interme);
        return jo;
    }

    public void jsonhandling (String FN) throws Exception {
       // JSONObject mainj = new JSONObject();
        //JSONObject jo=new JSONObject();
        File f = new File(getApplicationContext().getFilesDir(), FN);
        JSONArray history = new JSONArray();
        try {
            if (f.exists()) {
                StringBuilder strbld = readjson(FN);
                update(strbld, history);
            }
            history.put(jsoncovert(result.getText().toString(), intermRe.getText().toString()));
            System.out.println("-------");
        } catch (JSONException e) {
            e.printStackTrace();
        }
            /*try {
                mainj.put("histroy", history);
            } catch (JSONException ep) {
                ep.printStackTrace();
            }*/
            System.out.println("-------");

            //Files.write(Paths.get(FN),mainj.toString().getBytes());
            try {
                String strjo = history.toString();
                f = new File(getApplicationContext().getFilesDir(), FN);
                FileWriter fw = new FileWriter(f);
                BufferedWriter bfw = new BufferedWriter(fw);
                bfw.write( strjo);
                bfw.close();
            } catch (IOException epp) {
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

    public void update(StringBuilder strbld,JSONArray ja) throws Exception {
        String res = strbld.toString();
        JSONArray js = new JSONArray(res);
        int in = js.length();
        for (int i = 0; i < in; i++) {
            JSONObject jo = js.getJSONObject(i);
            String re = jo.get("resulte").toString();
            String interm = jo.get("interme").toString();
            ja.put(jsoncovert(re, interm));
        }
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
        histroyAdopter hisA=new histroyAdopter(this,listh);
    ListView listView = findViewById(R.id.listv);
    listView.setAdapter(hisA);
    }

}
