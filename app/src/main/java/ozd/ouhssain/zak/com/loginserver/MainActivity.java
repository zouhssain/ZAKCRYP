package ozd.ouhssain.zak.com.loginserver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    EditText edit_name , edit_email, edit_password,confirm_edit_password,phone_number;
    Button regis;

    AwesomeValidation awesomeValidation;
    private static final String REGISTER_URL="http://basmatv.ma/Android/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        edit_name = (EditText) findViewById(R.id.name);
        edit_email = (EditText) findViewById(R.id.email);
        edit_password = (EditText) findViewById(R.id.pass);
        regis = (Button) findViewById(R.id.register);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TesterAvantEvoyer();
            }
        });
    }

    void TesterAvantEvoyer()
    {
        edit_name = (EditText) findViewById(R.id.name);
        edit_email = (EditText) findViewById(R.id.email);
        edit_password = (EditText) findViewById(R.id.pass);
        confirm_edit_password = (EditText)findViewById(R.id.confirm_pass);
        phone_number = (EditText)findViewById(R.id.phone);

        awesomeValidation.addValidation(MainActivity.this,R.id.name,"[a-zA-Z\\s]+", R.string.err_name);
        awesomeValidation.addValidation(MainActivity.this,R.id.email,android.util.Patterns.EMAIL_ADDRESS, R.string.err_email);
        awesomeValidation.addValidation(MainActivity.this,R.id.phone, RegexTemplate.TELEPHONE, R.string.err_phone);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(MainActivity.this, R.id.pass, regexPassword, R.string.err_password);
        awesomeValidation.addValidation(MainActivity.this, R.id.confirm_pass, R.id.pass, R.string.err_confirm_password);

        if(awesomeValidation.validate())
        {
            registeruser();
            edit_name.setText("");
            edit_email.setText("");
            edit_password.setText("");
            confirm_edit_password.setText("");
            phone_number.setText("");
        }
        else {
            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
        }

    }

    public void registeruser()
    {
        String username = edit_name.getText().toString().trim().toLowerCase();
        String email = edit_email.getText().toString().trim().toLowerCase();
        String password = edit_password.getText().toString().trim().toLowerCase();
        String phone = phone_number.getText().toString().trim().toLowerCase();
        register(username, password, email,phone);
    }

    private void register(String username, String password, String email,String phone){
        String urlSuffix = "?username=" + username + "&password=" + password + "&email=" + email + "&phone=" + phone;
        //String urlSuffix = "?username=" + username + "&password=" + password + "&email=" + email;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferReader=null;
                try {
                    URL url=new URL(REGISTER_URL+s);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferReader.readLine();
                    return  result;

                }catch (Exception e){
                    return null;
                }
            }

        }
        RegisterUser ur=new RegisterUser();
        ur.execute(urlSuffix);
    }
}
