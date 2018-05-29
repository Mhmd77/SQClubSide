package ir.sq.apps.sqclubside.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sq.apps.sqclubside.R;
import ir.sq.apps.sqclubside.controllers.UrlHandler;
import ir.sq.apps.sqclubside.uiControllers.TypeFaceHandler;

public class SignUpLogin extends AppCompatActivity implements View.OnClickListener {
    private boolean isSigninScreen = true;
    private TextView tvSignupInvoker;
    private LinearLayout llSignup;
    private TextView tvSigninInvoker;
    private LinearLayout llSignin;
    private Button signUpbutton;
    private Button signInbutton;
    private TextInputEditText[] signUPviews;
    private TextInputEditText[] signInViews;

    @BindView(R.id.userName_textview)
    TextInputEditText userName_textview;
    @BindView(R.id.passWord_textview)
    TextInputEditText passWord_textview;
    @BindView(R.id.email_textview)
    TextInputEditText email_textview;
    @BindView(R.id.userName_signin)
    TextInputEditText userName_signin;
    @BindView(R.id.passWord_signin)
    TextInputEditText passWord_signin;

    LinearLayout llsignin, llsignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);

        ButterKnife.bind(this);

        llSignin = (LinearLayout) findViewById(R.id.llSignin);
        llSignin.setOnClickListener(this);
        llsignup = (LinearLayout) findViewById(R.id.llSignup);
        llsignup.setOnClickListener(this);
        tvSignupInvoker = (TextView) findViewById(R.id.tvSignupInvoker);
        tvSigninInvoker = (TextView) findViewById(R.id.tvSigninInvoker);

        signUpbutton = (Button) findViewById(R.id.signUP_button);
        signInbutton = (Button) findViewById(R.id.signIn_button);

        llSignup = (LinearLayout) findViewById(R.id.llSignup);
        llSignin = (LinearLayout) findViewById(R.id.llSignin);

        tvSignupInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = false;
                showSignupForm();
            }
        });

        tvSigninInvoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSigninScreen = true;
                showSigninForm();
            }
        });
        showSigninForm();

        signUpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
                if (isSigninScreen)
                    signUpbutton.startAnimation(clockwise);
            }
        });
        setFonts();
        setviews();
    }

    private void setviews() {
        signUPviews = new TextInputEditText[3];
        signUPviews[0] = userName_textview;
        signUPviews[1] = passWord_textview;
        signUPviews[2] = email_textview;

        signInViews = new TextInputEditText[2];
        signInViews[0] = userName_signin;
        signInViews[1] = passWord_signin;
    }

    private void setFonts() {
        tvSigninInvoker.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        tvSignupInvoker.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        signInbutton.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
        signUpbutton.setTypeface(TypeFaceHandler.getInstance(this).getFa_bold());
    }

    private void showSignupForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.15f;
//        for (EditText e : signInViews) {
//                e.setError(null);
//        }
        llSignin.requestLayout();


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.85f;
        llSignup.requestLayout();

        tvSignupInvoker.setVisibility(View.GONE);
        tvSigninInvoker.setVisibility(View.VISIBLE);
        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right_to_left);
        llSignup.startAnimation(translate);

        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_left);
        signUpbutton.startAnimation(clockwise);

    }

    private void showSigninForm() {
        PercentRelativeLayout.LayoutParams paramsLogin = (PercentRelativeLayout.LayoutParams) llSignin.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoLogin = paramsLogin.getPercentLayoutInfo();
        infoLogin.widthPercent = 0.85f;
        llSignin.requestLayout();
//        for (EditText e : signUPviews) {
//                e.setError(null);
//        }


        PercentRelativeLayout.LayoutParams paramsSignup = (PercentRelativeLayout.LayoutParams) llSignup.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo infoSignup = paramsSignup.getPercentLayoutInfo();
        infoSignup.widthPercent = 0.15f;
        llSignup.requestLayout();

        Animation translate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left_to_right);
        llSignin.startAnimation(translate);

        tvSignupInvoker.setVisibility(View.VISIBLE);
        tvSigninInvoker.setVisibility(View.GONE);
        Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left_to_right);
        signInbutton.startAnimation(clockwise);
    }

    private Boolean checkEmptyFieldsSignUp() {
        Boolean flag = true;
        for (EditText e : signUPviews) {
            if (e.getText().toString().length() == 0) {
                e.setError(getString(R.string.empty_field_error_meesage));
                flag = false;
            }
        }
        return flag;
    }

    private Boolean checkEmptyFieldsSignIn() {
        Boolean flag = true;
        for (EditText e : signInViews) {
            if (e.getText().toString().length() == 0) {
                e.setError(getString(R.string.empty_field_error_meesage));
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(SignUpLogin.this, "HERE", Toast.LENGTH_LONG).show();
        switch (view.getId()) {
            case R.id.signIn_button:
                if (checkEmptyFieldsSignIn()) {
                    signIn();
                }
                break;
            case R.id.signUP_button:
                if (checkEmptyFieldsSignUp()) {
                    signUp();
                }
                break;
        }

    }

    private void signIn() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", userName_signin.toString());
            jsonObject.put("passWord", passWord_signin.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(UrlHandler.signInUserURL.toString())
                .addJSONObjectBody(jsonObject)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if(status == 1)
                            Log.e("SIGNIN", "Done");
                         else
                        Log.e("SIGNIN", "FUCKED");
                }
                         catch(JSONException e){
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    private void signUp() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", userName_textview.toString());
            jsonObject.put("passWord", passWord_textview.toString());
            jsonObject.put("email", email_textview.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(UrlHandler.signUpUserURL.toString())

                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == 1) {
                                Log.e("SIGNUP", "Done");
                            } else
                                Log.e("SIGNUP", "FUCKED");
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

    }


}

