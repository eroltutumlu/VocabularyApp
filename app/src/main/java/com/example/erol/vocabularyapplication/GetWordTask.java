package com.example.erol.vocabularyapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class GetWordTask extends AsyncTask<String, Void, String>{

    TextView textView;
    boolean isCheckDefinition, isCheckSynonym, isCheckExample;
    public GetWordTask(TextView textView, boolean isCheckDefinition, boolean isCheckSynonym, boolean isCheckExample) {
        super();
        this.textView = textView;
        this.isCheckDefinition = isCheckDefinition;
        this.isCheckSynonym = isCheckSynonym;
        this.isCheckExample = isCheckExample;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        textView.setText(string);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... strings) {
        String word = "UNDEFINED";
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("X-Mashape-Key","KEY_HERE_");


            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder jsonStringBuilder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(inputString);
            }

            JSONObject jsonObject = new JSONObject(jsonStringBuilder.toString());
            String _word = jsonObject.getString("word");

            JSONArray resultsJsonArray = jsonObject.getJSONArray("results");
            JSONObject resultsJsonObject = resultsJsonArray.getJSONObject(0);

            String _definition = resultsJsonObject.getString("definition");
            String _partOfSpeech = resultsJsonObject.getString("partOfSpeech");

            JSONArray synonymsJsonArray = resultsJsonObject.getJSONArray("synonyms");
            String _synonyms = synonymsJsonArray.getString(0);

            //JSONArray examplesJsonArray = resultsJsonObject.getJSONArray("examples");
            //String _examples = examplesJsonArray.getString(0);


            word = "WORD " + "\n" + "   " + _word + " ( "+ _partOfSpeech +" )"+"\n" + "\n" + "DEFINITION " + "\n" + "   " + _definition + "\n\n" + "EXAMPLE " + "\n" + "   " ;




            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return word;
    }
}
