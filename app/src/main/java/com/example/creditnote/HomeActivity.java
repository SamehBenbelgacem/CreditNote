package com.example.creditnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView emailTextView = findViewById(R.id.EmailId);
        TextView fullNameTextView = findViewById(R.id.FullName);
        AppCompatButton signOutBtn = findViewById(R.id.SignOutBtn);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (googleSignInAccount != null) {
            // Get details for signed-in user
            String fullName = googleSignInAccount.getDisplayName();
            String email = googleSignInAccount.getEmail();

            String emailLabel = getString(R.string.email_label, email);
            String fullNameLabel = getString(R.string.full_name_label, fullName);

            emailTextView.setText(emailLabel);
            fullNameTextView.setText(fullNameLabel);
        }

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out
                googleSignInClient.signOut();
                // Opening MainActivity to sign in again
                startActivity(new Intent(HomeActivity.this, SecondPage.class));
                finish();
            }
        });
    }
}
