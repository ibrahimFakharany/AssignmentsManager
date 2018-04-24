package exampleoncreatingfixedfragment.example.com.androidproject.Doctor;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class QuestionCreation extends AppCompatActivity {
    Toolbar toolbar;
    int num_of_questions, assignment_id, courseNumber;
    Button nextBtnCreate;
    EditText questionEt, choiceAEt, choiceBEt, choiceCEt, choiceDEt, correctEt;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_creation);
         intent = getIntent();
        num_of_questions = getIntent().getIntExtra("number_of_questions", 0);
        assignment_id = intent.getIntExtra("assignment_id", 0);
        new CustomToast(this).raiseToast("ocreaaaatee"+ assignment_id);
        courseNumber = getIntent().getIntExtra("course_number", 0);
        defineView();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private void defineView() {
        questionEt = (EditText) findViewById(R.id.question_body_et);
        choiceAEt = (EditText) findViewById(R.id.a_et);
        choiceBEt = (EditText) findViewById(R.id.b_et);
        choiceCEt = (EditText) findViewById(R.id.c_et);
        choiceDEt = (EditText) findViewById(R.id.d_et);
        correctEt = (EditText) findViewById(R.id.correctEt);
        nextBtnCreate = (Button) findViewById(R.id.next_create_btn);

        nextBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_of_questions--;
                if(num_of_questions != 0){

                    if (choiceAEt.getText().toString().equals("") || choiceAEt.getText().toString().equals("") || choiceCEt.getText().toString().equals("") || choiceDEt.getText().toString().equals("") || correctEt.getText().toString().equals("")) {
                        new CustomToast(getBaseContext()).raiseToast("All fields Required");
                    } else {
                        final String question = questionEt.getText().toString();
                        final String choiceA = choiceAEt.getText().toString();
                        final String choiceB = choiceBEt.getText().toString();
                        final String choiceC = choiceCEt.getText().toString();
                        final String choiceD = choiceDEt.getText().toString();
                        final String correct = correctEt.getText().toString();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip) + "/insert/insertQuestion.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        questionEt.setText("");
                                        choiceAEt.setText("");
                                        choiceBEt.setText("");
                                        choiceCEt.setText("");
                                        choiceDEt.setText("");
                                        correctEt.setText("");
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("question", question);
                                map.put("choiceA", choiceA);
                                map.put("choiceB", choiceB);
                                map.put("choiceC", choiceC);
                                map.put("choiceD", choiceD);
                                map.put("correct", correct);
                                map.put("assignment_id", Integer.toString(assignment_id));
                                map.put("course_number", Integer.toString(courseNumber));
                                return map;
                            }
                        };
                        SingleTon.getInstance(getBaseContext()).addRequest(stringRequest);

                    }
                }else{

                    // open new activity
                    Intent intent1 = new Intent(getBaseContext(), DetailCourseActivity.class);
                    intent1.putExtra("doctorOrStudent" , 1);
                    intent1.putExtras(intent);
                    startActivity(intent1);
                }

            }
        });
    }
}
