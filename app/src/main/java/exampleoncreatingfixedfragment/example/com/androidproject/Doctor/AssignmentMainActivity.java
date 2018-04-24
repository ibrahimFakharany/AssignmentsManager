package exampleoncreatingfixedfragment.example.com.androidproject.Doctor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.sax.StartElementListener;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import exampleoncreatingfixedfragment.example.com.androidproject.CustomToast;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.SingleTon;

public class AssignmentMainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Intent intent;
    private Calendar calendar;
    private int mCourseNumebr, mDoctor_id, year_x, month_x, day_x;
    private String date;
    private TextView mStartTimeTv, mEndTimeTv;
    private EditText instructionET, descriptionET;
    private DatePickerDialog date_picker, date_picker2;
    private Button createBtn;
    ImageButton mStartTimeImgBtn, mEndTimeImgBtn;
    private Spinner typeSpinner, numOfQuestionsSpinner;
    String typeSpinnerStr, numOfQuestionsSpinnerStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_main);
        intent = getIntent();
        getIntentValues();
        defineView();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Add assignment");
        setSupportActionBar(toolbar);
        Calendar cal = Calendar.getInstance();
// start time date picker
        date_picker = new DatePickerDialog(this, null, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        date_picker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                String cheapDate = (m + 1) + "/" + d + "/" + y;
                mStartTimeTv.setText(cheapDate);

            }
        });
        mStartTimeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_picker.show();
            }
        });

// end time date picker
        date_picker2 = new DatePickerDialog(this, null, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        date_picker2.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                String cheapDate = (m + 1) + "/" + d + "/" + y;
                mEndTimeTv.setText(cheapDate);

            }
        });
        mEndTimeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_picker2.show();
            }
        });

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void defineView() {
        //text view for date
        mStartTimeTv = (TextView) findViewById(R.id.start_time_tv);
        mStartTimeTv.setText(DateFormat.getDateInstance().format(new Date()));
        mEndTimeTv = (TextView) findViewById(R.id.end_time_tv);

        //edit text
        instructionET = (EditText) findViewById(R.id.instruction);
        descriptionET = (EditText) findViewById(R.id.description);

        //img buttons
        mStartTimeImgBtn = (ImageButton) findViewById(R.id.start_time_btn);
        mEndTimeImgBtn = (ImageButton) findViewById(R.id.end_time_btn);

        //spinners
        typeSpinner = (Spinner) findViewById(R.id.type_of_group_spn);
        numOfQuestionsSpinner = (Spinner) findViewById(R.id.num_of_question_spn);

        //create btn
        createBtn = (Button) findViewById(R.id.create_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save assignment data in database
                if ((mStartTimeTv.getText().toString().equals("") || mEndTimeTv.getText().toString().equals("") || instructionET.getText().toString().equals("") || descriptionET.getText().toString().equals(""))) {
                    new CustomToast(getBaseContext()).raiseToast("All Fields Required");
                } else {

                    final String startTime = mStartTimeTv.getText().toString();
                    final String endTime = mEndTimeTv.getText().toString();
                    final String instruction = instructionET.getText().toString();
                    final String description = descriptionET.getText().toString();

                    typeSpinnerStr = typeSpinner.getSelectedItem().toString();
                    numOfQuestionsSpinnerStr = numOfQuestionsSpinner.getSelectedItem().toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip) + "/insert/insertAssignment.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equals("notinserted")) {
                                new CustomToast(getBaseContext()).raiseToast(response);
                                Intent intent1 = new Intent(getBaseContext(), QuestionCreation.class);
                                intent1.putExtra("number_of_questions", Integer.parseInt(numOfQuestionsSpinnerStr));
                                intent1.putExtra("assignment_id", Integer.parseInt(response));
                                intent1.putExtra("course_number", mCourseNumebr);
                                intent1.putExtras(intent);
                                startActivity(intent1);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("starttime", startTime);
                            map.put("endtime", endTime);
                            map.put("instruction", instruction);
                            map.put("description", description);
                            map.put("type_of_work", typeSpinnerStr);
                            map.put("course_number", Integer.toString(mCourseNumebr));
                            return map;
                        }
                    };
                    SingleTon.getInstance(getBaseContext()).addRequest(stringRequest);
                }


            }
        });


    }


    private void getIntentValues() {
        mCourseNumebr = getIntent().getIntExtra("course_number", 0);
        mDoctor_id = getIntent().getIntExtra("doctor_id", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
