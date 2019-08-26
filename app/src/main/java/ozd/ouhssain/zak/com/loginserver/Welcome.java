package ozd.ouhssain.zak.com.loginserver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Welcome extends Activity {

    RelativeLayout first;
    String email_passer;
    EditText textacoder,edit_cle,textadecoder,edit_cle_decodage;
    private static final String REGISTER_URL_1="http://basmatv.ma/Android/data.php";
    private static final String REGISTER_URL="http://basmatv.ma/Android/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textacoder = (EditText)findViewById(R.id.textacoder);
        edit_cle = (EditText)findViewById(R.id.edit_cle);
        textadecoder = (EditText)findViewById(R.id.textadecoder);
        edit_cle_decodage = (EditText)findViewById(R.id.edit_cle_decodage);

        Intent intent_1 = getIntent();
        email_passer = intent_1.getStringExtra(Login.email_utilisateur);

        Button deco = (Button)findViewById(R.id.deco);
        deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goback();
            }
        });

        Button code = (Button)findViewById(R.id.code);
        code.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                affichage_codage();
            }
        });

        Button decode = (Button)findViewById(R.id.decode);
        decode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                affichage_decodage();
            }
        });

        Button valider = (Button)findViewById(R.id.valider);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codage();
            }
        });

        Button decodage = (Button)findViewById(R.id.valider_decodage);
        decodage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decodage();
            }
        });

        //on peut juste copier
        final EditText edittext = (EditText) findViewById(R.id.result);
        edittext.setInputType(InputType.TYPE_NULL);
        edittext.setTextIsSelectable(true);
        edittext.setKeyListener(null);
        //on peut juste copier
        final EditText edittext1 = (EditText) findViewById(R.id.result_decodage);
        edittext1.setInputType(InputType.TYPE_NULL);
        edittext1.setTextIsSelectable(true);
        edittext1.setKeyListener(null);

    }

    void affichage_codage()
    {
        first = (RelativeLayout) findViewById(R.id.first);
        first.setBackgroundColor(Color.parseColor("#335599"));
        TextView text1 = (TextView)findViewById(R.id.text1);
        EditText textacoder = (EditText)findViewById(R.id.textacoder);
        EditText edit_cle = (EditText)findViewById(R.id.edit_cle);
        EditText result = (EditText)findViewById(R.id.result);
        Button code = (Button)findViewById(R.id.valider);
        //********
        EditText textadecoder = (EditText)findViewById(R.id.textadecoder);
        EditText edit_cle_decodage = (EditText)findViewById(R.id.edit_cle_decodage);
        Button valider_decodage = (Button)findViewById(R.id.valider_decodage);
        EditText result_decodage = (EditText)findViewById(R.id.result_decodage);
        //*************
        text1.setText("Text à codé");
        textacoder.setVisibility(View.VISIBLE);
        result.setVisibility(View.VISIBLE);
        edit_cle.setVisibility(View.VISIBLE);
        code.setVisibility(View.VISIBLE);
        //*******
        textadecoder.setVisibility(View.INVISIBLE);
        edit_cle_decodage.setVisibility(View.INVISIBLE);
        valider_decodage.setVisibility(View.INVISIBLE);
        result_decodage.setVisibility(View.INVISIBLE);
    }

    void affichage_decodage()
    {
        first = (RelativeLayout) findViewById(R.id.first);
        first.setBackgroundColor(Color.parseColor("#fbb329"));
        TextView text1 = (TextView)findViewById(R.id.text1);
        EditText textacoder = (EditText)findViewById(R.id.textacoder);
        EditText edit_cle = (EditText)findViewById(R.id.edit_cle);
        EditText result = (EditText)findViewById(R.id.result);
        Button code = (Button)findViewById(R.id.valider);
        //*****
        EditText textadecoder = (EditText)findViewById(R.id.textadecoder);
        EditText edit_cle_decodage = (EditText)findViewById(R.id.edit_cle_decodage);
        Button valider_decodage = (Button)findViewById(R.id.valider_decodage);
        EditText result_decodage = (EditText)findViewById(R.id.result_decodage);
        //*********
        text1.setText("Text à décodé");
        textacoder.setVisibility(View.INVISIBLE);
        result.setVisibility(View.INVISIBLE);
        edit_cle.setVisibility(View.INVISIBLE);
        code.setVisibility(View.INVISIBLE);
        //*******
        textadecoder.setVisibility(View.VISIBLE);
        edit_cle_decodage.setVisibility(View.VISIBLE);
        valider_decodage.setVisibility(View.VISIBLE);
        result_decodage.setVisibility(View.VISIBLE);
    }

    void goback()
    {
        Intent intent = new Intent(Welcome.this,Login.class);
        startActivity(intent);
        this.finish();
    }

    void decodage()
    {
        EditText textadecoder = (EditText)findViewById(R.id.textadecoder);
        EditText edit_cle_decodage = (EditText)findViewById(R.id.edit_cle_decodage);
        EditText result_decodage = (EditText)findViewById(R.id.result_decodage);
        if (edit_cle_decodage.getText().toString().equals(""))
        {
            //Toast.makeText(Welcome.this,"Veuillez Saisir une clée",Toast.LENGTH_LONG).show();
            edit_cle_decodage.setError("Veuillez Saisir une clée");
        }else
        {
            //-----------------------------
            registerdata_decodage();
            //------------------------------
            String newmessage = "";
            char alphabet[] = { 'a','n', 'r', 's', 'b', 'g','B','A','C','E','D','F','H','J','I','G','L','K','M','O','N'
                    ,'P', 'h', 'i','c', 'd', 'e', 'f', 'j',	'k', 'l', 'p', 'q','m',  't',  'o','u', 'v',
                    'w', 'x', 'y', 'z' , ' ','5','6','7','8', '1', 'Q','R','T','S','U','V','X','W','Y','Z','2' ,'3', '4','9'
                    ,'0' ,'?' ,'!' , ',' ,':' , '/' ,'*','-','_' ,'@',
                    '=' ,'+' , '#' ,'.','~' ,'&','é','â','è','\\','^',']','}','|','[','{','(',')','"','\''
                    ,'à','ê','î','ô','<','>'};
            String texte;
            int i, j, key_value = 0, value;
            texte = textadecoder.getText().toString();
            int[] element = new int[texte.length()];
            char[] dechiffre = new char[texte.length()];

            key_value = Integer.parseInt( edit_cle_decodage.getText().toString());

            System.out.println("La clef de déchiffrement vaut " + key_value);
            for (i = 0; i < texte.length(); i++) {
                for (j = 0; j < alphabet.length; j++) {
                    if (texte.charAt(i) == alphabet[j]) {
                        value = j - key_value;
                        if (value < 0) {
                            value = alphabet.length + value;
                        } else {
                            value = value;
                        }
                        if (value >= alphabet.length) {
                            element[i] = value % alphabet.length;
                        } else {
                            element[i] = value;
                        }
                    }
                }
            }
            for (i = 0; i < texte.length(); i++) {
                if (texte.charAt(i) == ' ') {
                    dechiffre[i] = texte.charAt(i);
                }
                for (j = 0; j < alphabet.length; j++) {
                    if (element[i] == j) {
                        dechiffre[i] = alphabet[j];
                        newmessage += dechiffre[i];
                    }
                }
            }
            result_decodage.setText(newmessage);
            Toast.makeText(Welcome.this,"Seccesful",Toast.LENGTH_LONG).show();
        }
    }

    void codage()
    {
        EditText textacoder = (EditText)findViewById(R.id.textacoder);
        EditText edit_cle = (EditText)findViewById(R.id.edit_cle);
        EditText result = (EditText)findViewById(R.id.result);
        String cle_string = edit_cle.getText().toString();
        if ( cle_string.equals(""))
        {
            //Toast.makeText(Welcome.this,"Veuillez Saisir une clée",Toast.LENGTH_LONG).show();
            edit_cle.setError("Veuillez Saisir une clée");
        }
        else {
            /************************************* pour inserer dans la base de donne*******************************************/
            registerdata_codage();
            /********************************************************************************/
            String newmessage1 ="";
            char alphabet[] = { 'a','n', 'r', 's', 'b', 'g','B','A','C','E','D','F','H','J','I','G','L','K','M','O','N'
                    ,'P', 'h', 'i','c', 'd', 'e', 'f', 'j',	'k', 'l', 'p', 'q','m',  't',  'o','u', 'v',
                    'w', 'x', 'y', 'z' , ' ','5','6','7','8', '1', 'Q','R','T','S','U','V','X','W','Y','Z','2' ,'3', '4','9'
                    ,'0' ,'?' ,'!' , ',' ,':' , '/' ,'*','-','_' ,'@',
                    '=' ,'+' , '#' ,'.','~' ,'&','é','â','è','\\','^',']','}','|','[','{','(',')','"','\''
                    ,'à','ê','î','ô','<','>'};
            String texte;
            int i, j, key_value = 0, value;
            texte =  textacoder.getText().toString();
            int[] element = new int[texte.length()];
            char[] dechiffre = new char[texte.length()];
            key_value = Integer.parseInt( edit_cle.getText().toString());
            System.out.println(key_value);
            for (i = 0; i < texte.length(); i++) {
                for (j = 0; j < alphabet.length; j++) {
                    if (texte.charAt(i) == alphabet[j]) {
                        value = j + key_value;
                        if (value < 0) {
                            value = alphabet.length + value;
                        } else {
                            value = value;
                        }
                        if (value >= alphabet.length) {
                            element[i] = value % alphabet.length;
                        } else {
                            element[i] = value;
                        }
                    }
                }
            }
            for (i = 0; i < texte.length(); i++) {
                if (texte.charAt(i) == ' ') {
                    dechiffre[i] = texte.charAt(i);
                }
                for (j = 0; j < alphabet.length; j++) {
                    if (element[i] == j) {
                        dechiffre[i] = alphabet[j];
                        newmessage1 += dechiffre[i];
                    }
                }
            }
            result.setText(newmessage1);
            System.out.println( alphabet.length);
            Toast.makeText(Welcome.this,"Seccesful",Toast.LENGTH_LONG).show();
        }

    }


    //----------------------------------------------------------------------------


    public void registerdata_codage()
    {
        String text = textacoder.getText().toString().trim().toLowerCase();
        String cle = edit_cle.getText().toString().trim().toLowerCase();
        envoyer_codage(email_passer,cle,text);
    }

    public void registerdata_decodage()
    {
        String text = textadecoder.getText().toString().trim().toLowerCase();
        String cle = edit_cle_decodage.getText().toString().trim().toLowerCase();
        envoyer_codage(email_passer,cle,text);
    }

    private void envoyer_codage(String username, String password, String email){
        String urlSuffix = "?username=" + username + "&password=" + password + "&email=" + email;
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Welcome.this, "Please Wait", null, true, true);
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
                    URL url=new URL(REGISTER_URL_1+s);
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
    //-----------------------------------------------------------------------------



}
