package bh.h63jav.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    private Button btnSignIn, btnReg;
    private RelativeLayout rootSignIn;
    private FirebaseAuth auth;
    DatabaseReference playersDBref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        playersDBref = FirebaseDatabase.getInstance().getReference("Players");

        btnReg = (Button)findViewById(R.id.btnReg);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        rootSignIn = (RelativeLayout) findViewById(R.id.signInRoot);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegScreen();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginScreen();
            }
        });
    }

    private void showLoginScreen() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sign In");
        dialog.setMessage("Enter username & password below");

        LayoutInflater inflater = LayoutInflater.from(this);
        View loginLayout = inflater.inflate(R.layout.layout_login,null);

        final MaterialEditText txtName = loginLayout.findViewById(R.id.txtName);
        final MaterialEditText txtPassword = loginLayout.findViewById(R.id.txtPassword);

        dialog.setView(loginLayout);

        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                final String name = txtName.getText().toString();
                final String password = txtPassword.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    Snackbar.make(rootSignIn,"No name entered",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6) {
                    Snackbar.make(rootSignIn,"Password too short",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Login
                auth.signInWithEmailAndPassword((name+"@H63JAV.com"),password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        Properties p = (Properties)getApplicationContext();
                        p.setUser(new User(name,password));
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootSignIn,"Error: "+e.getMessage(),Snackbar.LENGTH_SHORT).show();

                    }
                });
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void showRegScreen() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Register");
        dialog.setMessage("Register username & password below");

        LayoutInflater inflater = LayoutInflater.from(this);
        View regLayout = inflater.inflate(R.layout.layout_register,null);

        final MaterialEditText txtName = regLayout.findViewById(R.id.txtName);
        final MaterialEditText txtPassword = regLayout.findViewById(R.id.txtPassword);

        dialog.setView(regLayout);

        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                final String name = txtName.getText().toString();
                final String password = txtPassword.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    Snackbar.make(rootSignIn,"No name entered",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6) {
                    Snackbar.make(rootSignIn,"Password too short",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword((name+"@H63JAV.com"),password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User(name,password);
                        playersDBref.child(name).setValue(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(rootSignIn,"Register successful",Snackbar.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootSignIn,"Error: "+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootSignIn,"Error: "+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
