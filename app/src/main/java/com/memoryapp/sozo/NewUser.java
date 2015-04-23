package com.memoryapp.sozo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NewUser extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        Button btnConfiguration = (Button) findViewById(R.id.button4);
        btnConfiguration.setOnClickListener(BotonListener);
    }

    private OnClickListener BotonListener = new OnClickListener(){

        public void onClick(View view) {

            EditText et = (EditText) findViewById(R.id.editText);
            EditText et2 = (EditText) findViewById(R.id.editText2);
            EditText et3 = (EditText) findViewById(R.id.editText3);
            if(!et2.getText().toString().equals(et3.getText().toString())){
                Toast.makeText(view.getContext(),R.string.passwordError,Toast.LENGTH_SHORT).show();
            }else {

                SharedPreferences newUser = getSharedPreferences("newUserData",MODE_PRIVATE);
                Editor editNewUser = newUser.edit();
                editNewUser.putString("newUser",et.getText().toString());
                editNewUser.putString("newPassword",et2.getText().toString());
                editNewUser.commit();

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
