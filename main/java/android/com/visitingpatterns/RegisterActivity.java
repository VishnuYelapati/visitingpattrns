package android.com.visitingpatterns;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by srinu on 2/11/2016.
 */
public class RegisterActivity extends Activity {

    EditText uname,uid,pwd,phone,loc;
    Button register;
    public   static SharedPreferences registerPrefrences;
    public  SharedPreferences.Editor registerPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        uname=(EditText)findViewById(R.id.et_visit_uname);
        uid=(EditText)findViewById(R.id.et_visit_uid);
        pwd=(EditText)findViewById(R.id.et_visit_pwd);
        phone=(EditText)findViewById(R.id.et_visit_phoneNum);
        loc=(EditText)findViewById(R.id.et_visit_location);
        register=(Button)findViewById(R.id.btn_visit_register);

        registerPrefrences=getSharedPreferences("registerPrefs", Context.MODE_PRIVATE);
        registerPrefEditor=registerPrefrences.edit();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=uname.getText().toString();
                String userId=uid.getText().toString();
                String password=pwd.getText().toString();
                String phoneNum=phone.getText().toString();
                String Loc=loc.getText().toString();

                registerPrefEditor.putString("username",userName);
                registerPrefEditor.putString("userid",userId);
                registerPrefEditor.putString("password",password);
                registerPrefEditor.putString("phonenum",phoneNum);
                registerPrefEditor.putString("location",Loc);
                registerPrefEditor.commit();

                Toast.makeText(getApplicationContext(),"Register sucess..",Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }
}
