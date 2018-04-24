package exampleoncreatingfixedfragment.example.com.androidproject.Doctor;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import exampleoncreatingfixedfragment.example.com.androidproject.CustomToast;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.SingleTon;

public class DoctorProfileActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, View.OnFocusChangeListener {
    Intent intent;
    private Button updateBtn;
    private EditText mFName, mLName, mUserName, mPhoneNumber, mAddress, mPassword;
    int doctor_id;
    String firstname, lastname, username, phonenumber, address, password;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        intent = getIntent();
        defineViews();

        toolbar = (Toolbar) findViewById(R.id.app_bar);


        getIntentValues();
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doctor_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_manage_doctor_profile:
                if(mFName.getFreezesText())
                mFName.setEnabled(true);
                mLName.setEnabled(true);
                mUserName.setEnabled(true);
                mPhoneNumber.setEnabled(true);
                mAddress.setEnabled(true);
                mPassword.setEnabled(true);
                break;


            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIntentValues() {
        doctor_id = intent.getIntExtra("doctor_id", 1);
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");
        username = intent.getStringExtra("username");
        phonenumber = intent.getStringExtra("phonenumber");
        address = intent.getStringExtra("lastname");
        password = intent.getStringExtra("password");

        mFName.setText(firstname);
        mLName.setText(lastname);
        mUserName.setText(username);
        mPhoneNumber.setText(phonenumber);
        mAddress.setText(address);
        mPassword.setText(password);
        mFName.setEnabled(false);
        mLName.setEnabled(false);
        mUserName.setEnabled(false);
        mPhoneNumber.setEnabled(false);
        mAddress.setEnabled(false);
        mPassword.setEnabled(false);


        toolbar.setTitle("Dr "+firstname+ " "+ lastname);

    }

    private void defineViews() {
        updateBtn = (Button) findViewById(R.id.update_btn_doctor_profile);
        updateBtn.setOnClickListener(this);
        mFName = (EditText) findViewById(R.id.f_name_doctor_profile);
        mLName = (EditText) findViewById(R.id.l_name_doctor_profile);
        mPhoneNumber = (EditText) findViewById(R.id.phonenumber_doctor_profile);
        mAddress = (EditText) findViewById(R.id.address_doctor_profile);
        mPassword = (EditText) findViewById(R.id.password_doctor_profile);

        mUserName = (EditText) findViewById(R.id.username_doctor_profile);
        mPhoneNumber = (EditText) findViewById(R.id.phonenumber_doctor_profile);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.update_btn_doctor_profile){

                firstname = mFName.getText().toString();
                lastname = mLName.getText().toString();
                username = mUserName.getText().toString();
                phonenumber = mPhoneNumber.getText().toString();
                address = mAddress.getText().toString();
                password = mPassword.getText().toString();
                String url =   getString(R.string.ip) + "/update/updateDoctoData.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new CustomToast(getApplicationContext()).raiseToast(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("doctor_id", Integer.toString(doctor_id));
                        map.put("fName", firstname);
                        map.put("lName", lastname);
                        map.put("username", username);
                        map.put("phonenumber", phonenumber);
                        map.put("address", address);
                        map.put("password", password);
                        return map;
                    }
                };
                SingleTon.getInstance(this).addRequest(stringRequest);
                return;
        }
        else{
            v.setEnabled(true);
        }
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            new CustomToast(this).raiseToast("have focus");
            v.setEnabled(true);
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
