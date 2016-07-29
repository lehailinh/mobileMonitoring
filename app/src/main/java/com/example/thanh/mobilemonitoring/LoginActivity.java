package com.example.thanh.mobilemonitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.realtime.util.StringListReader;

import org.w3c.dom.Text;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPass;
    Button btnDangNhap, btnDangKy;

    Firebase root;
    String databaseLink = "https://giamsatdienthoai.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);
        root = new Firebase(databaseLink);

        edtUsername = (EditText)findViewById(R.id.editTextUsername);
        edtPass = (EditText) findViewById(R.id.editTextPass);

        btnDangKy = (Button)findViewById(R.id.buttonSignUp);
        btnDangNhap = (Button)findViewById(R.id.buttonSignIn);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.createUser(edtUsername.getText().toString(), edtPass.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        Toast.makeText(getApplicationContext(),
                                "Successfully created user account with uid: " + result.get("uid"),
                                Toast.LENGTH_LONG
                        ).show();

                        String firebase_ref = databaseLink + result.get("uid").toString();
                        root.child(result.get("uid").toString()).child("UserName").setValue(edtUsername.getText().toString());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle send = new Bundle();
                        send.putString("firebase_ref", firebase_ref);
                        intent.putExtras(send);
                        startActivity(intent);
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // there was an error
                        Toast.makeText(getApplicationContext(),
                                firebaseError.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.authWithPassword(edtUsername.getText().toString(), edtPass.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Toast.makeText(getApplicationContext(),
                                "User ID: " + authData.getUid() + ", Provider: " + authData.getProvider(),
                                Toast.LENGTH_LONG
                        ).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        String userData = databaseLink + authData.getUid();
                        Bundle send = new Bundle();
                        send.putString("userID", userData);
                        intent.putExtras(send);
                        startActivity(intent);
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // there was an error
                        Toast.makeText(getApplicationContext(),
                                firebaseError.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }
        });
    }

}