package com.example.calculator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

import java.util.logging.FileHandler;

public class history {


    //FileHandler fileh=new FileHandler();
    private String resulte,interme;
    /*public static void main(String args[]){
        JSONObject mainj=new JSONObject();
        JSONObject jo=new JSONObject();
        JSONArray history = new JSONArray();
        try {
            jo.put("resulte", "56+34*4+56");
            jo.put("interme","3456");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        history.put(jo);
        try {
            mainj.put("histroy", history);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        try{
            FileWriter fw =new FileWriter("E:/outp.json");
            fw.append(jo.toString());
            fw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }*/
    public history(String resulte,String interme){
        this.resulte=resulte;
        this.interme=interme;
    }
    public String getResulte() {
        return resulte;
    }
    public String getInterme() {
        return interme;
    }
    public void setResulte(String str) {
        this.resulte=str;
    }
    public void setInterme(String str) {
        this.interme=str;
    }
}
