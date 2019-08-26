package ozd.ouhssain.zak.com.loginserver;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends Activity {

    EditText reset_password1;
    Button btn_reset1;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        firebaseAuth = FirebaseAuth.getInstance();
        reset_password1 = (EditText) findViewById(R.id.reset_password1);
        btn_reset1 = (Button)findViewById(R.id.btn_reset1);

        btn_reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = reset_password1.getText().toString().trim();
                if(mail.equals(""))
                {
                    Toast.makeText(PasswordReset.this,"Veuillez saisir une adresse!",Toast.LENGTH_LONG).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(PasswordReset.this,"Succesful!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordReset.this,Login.class));
                            }
                            else
                            {
                                Toast.makeText(PasswordReset.this,"Error!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
