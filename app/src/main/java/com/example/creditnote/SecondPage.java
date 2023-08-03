package com.example.creditnote;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SecondPage extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        final ImageView imageView1 = findViewById(R.id.imageView1);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        // Check if the user is already signed in
        if (googleSignInAccount != null) {
            // Open home activity
            openHomeActivity();
            return; // We don't need to set the click listener if the user is already signed in
        }

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Getting signed-in account after the user selects an account from the Google accounts dialog
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleSignInTask(task);
                    }
                });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);
            }
        });
    }

    private void handleSignInTask(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                // Getting account data
                final String getFullName = account.getDisplayName();
                final String getEmail = account.getEmail();
                final Uri getPhotoUrl = account.getPhotoUrl();

                // Opening home activity
                openHomeActivity();
            } else {
                Toast.makeText(this, "Failed to sign in", Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed or Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void openHomeActivity() {
        // Opening home activity
        startActivity(new Intent(SecondPage.this, HomeActivity.class));
        finish();
    }
}
