package com.pawankhandal52.meditime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.pawankhandal52.meditime.R;
import com.pawankhandal52.meditime.session.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginRegisterActivity extends BaseActivity  {
    private static final String TAG = LoginRegisterActivity.class.getSimpleName();
    /*@BindView(R.id.skip_image_button)
        ImageButton mSkipImageButton;*/
    @BindView(R.id.my_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sign_in_button)
    SignInButton mSignInButton;
    @BindView(R.id.fl_progress_bar)
    FrameLayout mProgressbarFrameLayout;
    
    private FirebaseAuth mAuth;
    
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SharedPreference mSharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ButterKnife.bind(this);
        mSharedPreference = new SharedPreference(getApplicationContext());
        setUpUi();
    
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    
    
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_register;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        
    
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        showProgressDialog();
        
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), R.string.authentication_failed, Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        
                        hideProgressDialog();
                    }
                });
    }
    
    private void showProgressDialog() {
        mProgressbarFrameLayout.setVisibility(View.VISIBLE);
    }
    
    private void hideProgressDialog(){
        mProgressbarFrameLayout.setVisibility(View.GONE);
    }
    
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null){
            showToast(getResources().getString(R.string.welcome_msg));
            Log.e(TAG, "updateUI: "+currentUser.getUid() );
            mSharedPreference.setLogin(true);
            showHomeActivity();
        }
       
    }
    
    private void setUpUi(){
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }
    
    private void signIn() {
        Intent signInIntent =mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    
    @OnClick(R.id.skip_image_button)
    public void skipToHomeActivity(View view) {
        showHomeActivity();
    }
    
    private void showHomeActivity(){
        Intent intent = new Intent(LoginRegisterActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    
    
    
    
}
