package android.com.visitingpatterns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by srinu on 2/11/2016.
 */
public class AboutActivity extends Activity {

    Button about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);

        about=(Button)findViewById(R.id.btn_back);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this,MainActivity.class));
            }
        });
    }
}
