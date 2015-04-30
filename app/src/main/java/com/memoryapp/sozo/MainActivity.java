package com.memoryapp.sozo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


public class MainActivity extends ActionBarActivity {
    public UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsers", null, 5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnNewUser = (Button) findViewById(R.id.btnNewUser);
        btnNewUser.setOnClickListener(BotonListener);
        //start
        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(BotonListenerStart);
        //end start
    }
    private OnClickListener BotonListenerStart = new OnClickListener(){
        @Override
        public void onClick(View view) {

            EditText userlogin = (EditText) findViewById(R.id.loginame);
            EditText passwordlogin = (EditText) findViewById(R.id.loginpassword);


            SQLiteDatabase db = usdbh.getWritableDatabase();
           // Toast.makeText(view.getContext(), "dsa", Toast.LENGTH_SHORT).show();


            SQLiteDatabase mydatabase = openOrCreateDatabase("DBUsers",MODE_PRIVATE,null);
            Cursor resultSet = mydatabase.rawQuery("Select * from user WHERE name = ? AND password = ?",new String[] {userlogin.getText().toString(),passwordlogin.getText().toString()});
            //Cursor resultSet = mydatabase.rawQuery("Select * from user WHERE iduser=?", new String[]{"23"});
            boolean found = resultSet.moveToFirst();
            if (found) {
                String username = resultSet.getString(0);
                Toast.makeText(view.getContext(), "usuario id: "+username, Toast.LENGTH_SHORT).show();
                Log.d("usernamelogin", "username value " + username);
            }else{
                Toast.makeText(view.getContext(), "El usuario o password son incorrectos", Toast.LENGTH_SHORT).show();
            }

            mydatabase.close();





        }
    };

    private OnClickListener BotonListener = new OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(),NewUser.class);
            startActivity(i);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
