package ozd.ouhssain.zak.com.loginserver;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

    public static final String LOGIN_URL="https://basmatv.ma/Android/login.php";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";
    public static final String LOGIN_SUCCESS="success";
    public static final String SHARED_PREF_NAME="tech";
    public static final String EMAIL_SHARED_PREF="email";
    public static final String email_utilisateur ="ozd.ouhssain.zak.com.loginserver";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    private EditText editTextEmail;
    private EditText editTextPassword;
    TextView reset_password_label;
    private Button BtnLogin;
    private boolean loggedIn=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityregister();
            }
        });


        editTextEmail=(EditText)findViewById(R.id.login);
        editTextPassword=(EditText)findViewById(R.id.password);
        BtnLogin=(Button)findViewById(R.id.btn_login);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        reset_password_label = (TextView)findViewById(R.id.reset_password_label);
        reset_password_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,PasswordReset.class));
            }
        });


    }

    private void login() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    EditText lgn = (EditText)findViewById(R.id.login);
                        EditText pass = (EditText)findViewById(R.id.password);
                        lgn.setError(null);
                        if(pass.getText().toString().equals(""))
                        {
                            pass.setError(getString(R.string.error_incorrect_password));
                        }
                        if(lgn.getText().toString().equals(""))
                        {
                            lgn.setError(getString(R.string.error_incorrect_login));
                        }
                        if(response.trim().equalsIgnoreCase(LOGIN_SUCCESS)){

                            SharedPreferences sharedPreferences = Login.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                            editor.putString(EMAIL_SHARED_PREF, email);

                            editor.commit();

                            Intent intent = new Intent(Login.this, Welcome.class);
                            intent.putExtra(email_utilisateur,lgn.getText().toString().trim());
                            startActivity(intent);
                            finish();
                        }else{
                            if(!pass.getText().toString().equals("") && !lgn.getText().toString().equals("")){
                                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prams = new HashMap<>();
                prams.put(KEY_EMAIL, email);
                prams.put(KEY_PASSWORD, password);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        if(loggedIn){
            Intent intent = new Intent(Login.this, Welcome.class);
            startActivity(intent);
        }
    }*/

    public void activityregister()
    {
        Intent intent = new Intent(Login.this,MainActivity.class);
        startActivity(intent);
    }
}
