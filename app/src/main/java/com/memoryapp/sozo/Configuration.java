package com.memoryapp.sozo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class Configuration extends ActionBarActivity {
    public String currentUser;
    public UsuariosSQLiteHelper usdbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        //Get id user
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            String j = (String) b.get("key");
            Log.d("iduser", " valor id user " + j);
        }
        SharedPreferences newUser = getSharedPreferences("newUserData",0);
        currentUser = newUser.getString("iduser", "");
        Log.d("storageiduser", " iduser  "+newUser.getString("iduser", ""));
        //end get id user

        //bd
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 3);

        //SeekBar
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
        final TextView  textView = (TextView) findViewById(R.id.seekbarText);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Hours: " + progress + "/" + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });


        //languages Spinner
        Spinner spinnerLanguage = (Spinner) findViewById(R.id.confPrese);
        ArrayAdapter<CharSequence> adapterl = ArrayAdapter.createFromResource(this,
        R.array.spinnerPresentation, android.R.layout.simple_spinner_item);
        adapterl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapterl);

        //Presentation Spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerLang);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button btnNext = (Button) findViewById(R.id.buttonConfNext);
        btnNext.setOnClickListener(btnListenerNext);

        Button btnBack = (Button) findViewById(R.id.buttonConfBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),NewUser.class);
                startActivity(i);
            }
        });

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButtonSound);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("testing view","is check");
                } else {
                    // The toggle is disabled
                }
            }
        });


    }
    private View.OnClickListener btnListenerNext = new View.OnClickListener() {

        public void onClick(View view) {
            SQLiteDatabase db = usdbh.getWritableDatabase();

            //get values
            Spinner language =(Spinner) findViewById(R.id.spinnerLang);
            String textLang = language.getSelectedItem().toString();
            Spinner confPrese =(Spinner) findViewById(R.id.confPrese);
            String textPrese = confPrese.getSelectedItem().toString();
            //end get values form

            db.execSQL("UPDATE user SET language='"+textLang+"', presentation='"+textPrese+"' WHERE iduser=?", new String[]{currentUser});

            Intent i = new Intent(view.getContext(),MuduleSection.class);
            startActivity(i);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
