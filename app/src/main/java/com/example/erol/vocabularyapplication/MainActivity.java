package com.example.erol.vocabularyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etWord;
    private TextView tvWord, tvGeneric;
    private CheckBox checkDefinition, checkSynonym, checkExample;
    private Button btnFromText, btnRandomly, btnSave, btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetViewElements();
        SetOnClickListeners();
    }

    @Override
    public void onClick(View view) {

        boolean isCheckDefinition = checkDefinition.isChecked();
        boolean isCheckSynonym = checkSynonym.isChecked();
        boolean isCheckExample = checkExample.isChecked();
        String text  = etWord.getText().toString();
        switch (view.getId()){
            case R.id.btnFromText:
                String url = String.format("https://wordsapiv1.p.mashape.com/words/" + text + "/");
                GetWordTask task = new GetWordTask(tvGeneric, isCheckDefinition, isCheckSynonym, isCheckExample);
                task.execute(url);
                break;
            case R.id.btnRandomly:

                break;
            case R.id.btnSave:

                break;
            case R.id.btnList:
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void SetOnClickListeners(){
        btnFromText.setOnClickListener(this);
        btnRandomly.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    private void SetViewElements(){
        etWord = (EditText)findViewById(R.id.etWord);
        tvWord = (TextView)findViewById(R.id.tvWord);
        tvGeneric = (TextView)findViewById(R.id.tvGeneric);
        checkDefinition = (CheckBox)findViewById(R.id.checkDefinition);
        checkSynonym = (CheckBox)findViewById(R.id.checkSynonym);
        checkExample = (CheckBox)findViewById(R.id.checkExample);
        btnFromText = (Button)findViewById(R.id.btnFromText);
        btnRandomly = (Button)findViewById(R.id.btnRandomly);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnList = (Button)findViewById(R.id.btnList);
    }
}
