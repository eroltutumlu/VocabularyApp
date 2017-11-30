package com.example.erol.vocabularyapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class GetWordTask extends AsyncTask<String, Void, HashMap<String,String>>{

    TextView tvWord, tvDefinition, tvSynonym, tvExample;
    Context mainActivity;
    public GetWordTask(Context mainActivity,TextView tvWord, TextView tvDefinition,TextView tvSynonym,TextView tvExample) {
        super();
        this.tvWord = tvWord;
        this.tvDefinition = tvDefinition;
        this.tvSynonym = tvSynonym;
        this.tvExample = tvExample;
        this.mainActivity = mainActivity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(HashMap<String,String> string) {
        super.onPostExecute(string);

        if(string.get("WORD") != null){
            tvWord.setText((String)string.get("WORD"));
        }else{
            tvWord.setText("-");
        }
        if(string.get("SYNONYMS") != null){
            tvSynonym.setText((String)string.get("SYNONYMS"));
        }else{
            tvSynonym.setText("-");
        }
        if(string.get("DEFINITION") != null){
            tvDefinition.setText((String)string.get("DEFINITION"));
        }else{
            tvDefinition.setText("-");
        }
        if(string.get("EXAMPLE") != null){
            tvExample.setText((String)string.get("EXAMPLE"));
        }else{
            tvExample.setText("-");
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected HashMap<String,String> doInBackground(String... strings) {
        HashMap<String,String> hashMap = new HashMap<String, String>();

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("X-Mashape-Key","---");

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder jsonStringBuilder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(inputString);
            }

            String _examples = null, _definition = null, _partOfSpeech = null, _synonyms = null ;
            JSONObject jsonObject = new JSONObject(jsonStringBuilder.toString());

            String _word = jsonObject.getString("word");



            if(!jsonObject.isNull("results")){
                JSONArray resultsJsonArray = jsonObject.getJSONArray("results");
                JSONObject resultsJsonObject = resultsJsonArray.getJSONObject(0);

                _definition = resultsJsonObject.getString("definition");
                _partOfSpeech = resultsJsonObject.getString("partOfSpeech");


                if(!resultsJsonObject.isNull("synonyms")){
                    JSONArray synonymsJsonArray = resultsJsonObject.getJSONArray("synonyms");
                    _synonyms = synonymsJsonArray.getString(0);
                }

                if(!resultsJsonObject.isNull("examples")){
                    JSONArray examplesJsonArray = resultsJsonObject.getJSONArray("examples");
                    _examples = examplesJsonArray.getString(0);
                }
            }

            hashMap.put("DEFINITION",_definition);
            hashMap.put("SYNONYMS",_synonyms);
            hashMap.put("EXAMPLE",_examples);
            hashMap.put("WORD", _word + " ( "+ _partOfSpeech +" )");

            urlConnection.disconnect();

        } catch (IOException | JSONException E) {
            hashMap.put("DEFINITION",null);
            hashMap.put("SYNONYMS",null);
            hashMap.put("EXAMPLE",null);
            hashMap.put("WORD", null);

            //e.printStackTrace();
        }

        return hashMap;
    }
}
