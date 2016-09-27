package com.example.aneesh.dictionarygame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    private TextView wordText;
    private TextView pointsText;
    private HashMap<String,String> dictionary = new HashMap<>();
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointsText = (TextView)findViewById(R.id.points);
        pointsText.setText("Score = "+ points);
        //String[] listItems = {"AF", "BG", "CH", "DI", "EJ", "AF", "BG", "CH", "DI", "EJ", "AF", "BG", "CH", "DI", "EJ"};
        populateDictionary();
        createList();

        // parameters passed are ---- Activity(here, this), Layout(simple_list_item_1) and Array(here, listItems)
        // simple_list_item_1 ----> this is how you want the list to be formatted


        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(position));
                        wordText = (TextView)findViewById(R.id.word);
                        pointsText = (TextView)findViewById(R.id.points);

                        String givenWord = wordText.getText().toString();
                        if(item == dictionary.get(givenWord)){
                            points++;
                            Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                            pointsText.setText("Score = " + points);
                        }
                        else{
                            points--;
                            Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                            pointsText.setText("Score = " + points);
                        }
                        createList();
                        //Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    protected void populateDictionary(){
        String line = "";
        String[] lineSplit;

        wordText = (TextView)findViewById(R.id.word);
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.grewords));

        while(scan.hasNextLine()){
            line = scan.nextLine();
            lineSplit = line.split("\t");
            dictionary.put(lineSplit[0], lineSplit[1]);
        }
    }

    protected void createList(){

        String[] listItems = new String[5];
        Random random = new Random();

        List<String> keys = new ArrayList<>(dictionary.keySet());
        int pos = random.nextInt(5);
        for (int i = 0; i < 5; i++){
            String randomKey = keys.get(random.nextInt(keys.size()));
            String correspondingValue = dictionary.get(randomKey);
            listItems[i] = correspondingValue;
            if(i == pos){
                wordText.setText(randomKey);
            }
        }
        //String[] listItems = new String[listArray.size()];
        //listItems = listArray.toArray(listItems);

        ListAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);

        myListView = (ListView)findViewById(R.id.definitions);
        myListView.setAdapter(myAdapter);
    }
}
