package com.memoryapp.sozo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import java.sql.ResultSet;


public class NewUser extends ActionBarActivity {
    public UsuariosSQLiteHelper usdbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        Button btnConfiguration = (Button) findViewById(R.id.button4);
        btnConfiguration.setOnClickListener(BotonListener);

        //return home view
        Button btnReturnHome = (Button) findViewById(R.id.button3);
        btnReturnHome.setOnClickListener(BotonListenerReturnHome);

        //storage
        SharedPreferences newUser = getSharedPreferences("newUserData",0);
        Log.d("storage values", "valor de la variable guardada  "+newUser.getString("newUser",""));
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 3);



    }
    private OnClickListener BotonListenerReturnHome = new OnClickListener(){

        public void onClick(View view) {

                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);

        }
    };
    private OnClickListener BotonListener = new OnClickListener(){

        public void onClick(View view) {

            EditText et = (EditText) findViewById(R.id.editText);
            EditText et2 = (EditText) findViewById(R.id.editText2);
            EditText et3 = (EditText) findViewById(R.id.editText3);
            if(!et2.getText().toString().equals(et3.getText().toString())){
                Toast.makeText(view.getContext(),R.string.passwordError,Toast.LENGTH_SHORT).show();
            }else {

                SharedPreferences newUser = getSharedPreferences("newUserData", 0);
                SharedPreferences.Editor editNewUser = newUser.edit();
                editNewUser.putString("newUser",et.getText().toString());
                editNewUser.putString("newPassword",et2.getText().toString());
                editNewUser.commit();

                //sqLite
                SQLiteDatabase db = usdbh.getWritableDatabase();
                db.execSQL("INSERT INTO user (name,password,language,presentation,sound,time) VALUES ('"+et.getText().toString()+"', '"+et2.getText().toString()+"','spanish','directo','1','45')");


                SQLiteDatabase mydatabase = openOrCreateDatabase("DBUsuarios",MODE_PRIVATE,null);
                Cursor resultSet = mydatabase.rawQuery("Select * from user",null);
                resultSet.moveToFirst();
                String username = resultSet.getString(1);

                while (resultSet.moveToNext()) {
                    Log.d("sqlite", "valor id  "+resultSet.getString(0));
                    Log.d("sqlite", "valor name  "+resultSet.getString(1));
                    Log.d("sqlite", "valor password  "+resultSet.getString(2));
                }
                mydatabase.close();



                //end sqlite

                Intent i = new Intent(view.getContext(), Configuration.class);
                startActivity(i);
            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
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
}
