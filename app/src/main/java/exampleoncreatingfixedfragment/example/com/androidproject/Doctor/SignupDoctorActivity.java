package exampleoncreatingfixedfragment.example.com.androidproject.Doctor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.ArrayList;

import exampleoncreatingfixedfragment.example.com.androidproject.CustomToast;
import exampleoncreatingfixedfragment.example.com.androidproject.Params;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.VolleyCall;

public class SignupDoctorActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, View.OnFocusChangeListener {
    private Button mSignupBtn;
    private EditText mFName, mLName, mUserName, mPhoneNumber, mAddress, mPassword;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_doctor);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onStart() {
        super.onStart();
        defineViews();
    }

    public void defineViews(){
        mSignupBtn = (Button) findViewById(R.id.signup_btn_doctor_activity);
        mSignupBtn.setOnClickListener(this);

        mFName = (EditText) findViewById(R.id.f_name);
        mLName = (EditText) findViewById(R.id.l_name);
        mPhoneNumber = (EditText) findViewById(R.id.phonenumber);
        mAddress = (EditText) findViewById(R.id.address);
        mPassword = (EditText) findViewById(R.id.password);

        mUserName = (EditText) findViewById(R.id.username);
        mUserName.setOnTouchListener(this);
        mUserName.setOnFocusChangeListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup_btn_doctor_activity:
                new CustomToast(this).raiseToast("sign up btn");
                storeDoctorData();
                return;
        }
    }

    private boolean storeDoctorData() {
        String fName = mFName.getText().toString();
        String lName = mLName.getText().toString();
        String username = mUserName.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        String address = mAddress.getText().toString();
        String password = mPassword.getText().toString();
        if(!(fName.equals("")||lName.equals("")||username.equals("")||phoneNumber.equals("")||address.equals("")||password.equals(""))){
            ArrayList<Params> list = new ArrayList<>();
            list.add(new Params("fName", fName));
            list.add(new Params("lName",lName));
            list.add(new Params("username", username));
            list.add(new Params("phonenumber", phoneNumber));
            list.add(new Params("address", address));
            list.add(new Params("password", password));
            VolleyCall volleyCall = new VolleyCall(this, "/insert/insertdoctordata.php", list);
            String response = volleyCall.getVolleyCall();

            new CustomToast(this).raiseToast(response);
        }else {
            new CustomToast(this).raiseToast("All Fields is required");
        }


        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        String username = mFName.getText().toString() + mLName.getText().toString();
        mUserName.setText(username);
        new CustomToast(this).raiseToast("oh touch it");
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            new CustomToast(this).raiseToast("didn't have focus :(");
        }
    }

}
