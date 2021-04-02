package com.example.calculator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.math.MathContext;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class histroyAdopter extends ArrayAdapter<history> {
    Context context;
    List<history> list = new ArrayList<>();
    public  histroyAdopter(Context context_a,List<history> list_a){
            super(context_a,0,list_a);
            context=context_a;
            list=list_a;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.itemcontent, parent, false);
        }
        history his = getItem(position);
        assert his != null;
        final TextView resulte = item.findViewById(R.id.listre);
        final TextView interme = item.findViewById(R.id.lisinterme);
        resulte.setText(his.getResulte());
        interme.setText(his.getInterme());
        /*resulte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context," yes",Toast.LENGTH_SHORT).show();
                startactivity(resulte.getText().toString(), interme.getText().toString());

            }
        });
        interme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context," yes",Toast.LENGTH_SHORT).show();
                startactivity(resulte.getText().toString(), interme.getText().toString());
            }
        });*/
        return item;
    }
    public void startactivity(String res,String interm){
        Intent intetmain = new Intent(context,MainActivity.class);
       intetmain.putExtra("resulte",res);
        intetmain.putExtra("interme",interm);
        context.startActivity(intetmain);
    }

}
