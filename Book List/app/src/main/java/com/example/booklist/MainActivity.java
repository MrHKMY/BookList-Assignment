package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);

        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<String> colorList = new ArrayList<>();

        //access json file and store in arrayList
        try {
            JSONArray jsonArray = new JSONArray(loadJSONassets());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                titleList.add(object.getString("title"));
                colorList.add(object.getString("cover"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MainViewAdapter mAdapter = new MainViewAdapter(titleList, colorList, this);
        recyclerView.setAdapter(mAdapter);
    }

    //accessing data.json file
    private String loadJSONassets() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}