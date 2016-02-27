package android.com.visitingpatterns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by srinu on 2/11/2016.
 */
public class LoginActivity extends Activity {

    EditText userid,password;
    Button login,reset;
    String uid,pwd;

    public  static SharedPreferences loginpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latout_login);

        userid=(EditText)findViewById(R.id.et_login_uid);
        password=(EditText)findViewById(R.id.et_login_pwd);
        login=(Button)findViewById(R.id.btn_visit_login);
        reset=(Button)findViewById(R.id.btn_visit_reset);

        loginpref=getSharedPreferences("registerPrefs", Context.MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId=userid.getText().toString();
                String Pwd=password.getText().toString();

                uid=loginpref.getString("userid","");
                pwd=loginpref.getString("password","");

                if(userId.equals(uid)&&Pwd.equals(pwd))
                {
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Login Fails",Toast.LENGTH_LONG).show();
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid.setText("");
                password.setText("");
            }
        });
    }
}
