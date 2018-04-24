package exampleoncreatingfixedfragment.example.com.androidproject.Student;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import exampleoncreatingfixedfragment.example.com.androidproject.CustomToast;
import exampleoncreatingfixedfragment.example.com.androidproject.Models.Assignment;
import exampleoncreatingfixedfragment.example.com.androidproject.Models.QuestionMultiChoice;
import exampleoncreatingfixedfragment.example.com.androidproject.R;
import exampleoncreatingfixedfragment.example.com.androidproject.SingleTon;

public class QuestionsMultiActivity extends AppCompatActivity {
    Intent intent;
    int assignment_id, studentId;
    Toolbar toolbar;
    TextView questionBody;
    RadioButton choiceA, choiceB, choiceC, choiceD;
    RadioGroup radioGroup;
    Button mNextBtn;
    int courseNumber;
    int counter = 0;
    int degree = 0;
    ArrayList<QuestionMultiChoice> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_multi);
        intent = getIntent();
        getIntentValues();
        defineValues();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("Assignment");
        setSupportActionBar(toolbar);
        getQuestions();

    }

    private void defineValues() {
        questionBody = (TextView) findViewById(R.id.question_body);
        choiceA = (RadioButton) findViewById(R.id.a);
        choiceB = (RadioButton) findViewById(R.id.b);
        choiceC = (RadioButton) findViewById(R.id.c);
        choiceD = (RadioButton) findViewById(R.id.d);
        mNextBtn = (Button) findViewById(R.id.nextBtn);
        radioGroup = (RadioGroup) findViewById(R.id.radio_answers);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.a:
                        choiceA.setTextColor(getColor(R.color.white));
                        choiceD.setTextColor(getColor(android.R.color.black));
                        choiceC.setTextColor(getColor(android.R.color.black));
                        choiceB.setTextColor(getColor(android.R.color.black));
                        break;
                    case R.id.b:
                        choiceB.setTextColor(getColor(R.color.white));
                        choiceA.setTextColor(getColor(android.R.color.black));
                        choiceD.setTextColor(getColor(android.R.color.black));
                        choiceC.setTextColor(getColor(android.R.color.black));
                        break;
                    case R.id.c:
                        choiceC.setTextColor(getColor(R.color.white));
                        choiceB.setTextColor(getColor(android.R.color.black));
                        choiceA.setTextColor(getColor(android.R.color.black));
                        choiceD.setTextColor(getColor(android.R.color.black));
                        break;
                    case R.id.d:
                        choiceD.setTextColor(getColor(R.color.white));
                        choiceB.setTextColor(getColor(android.R.color.black));
                        choiceA.setTextColor(getColor(android.R.color.black));
                        choiceC.setTextColor(getColor(android.R.color.black));
                        break;
                }
            }
        });
    }

    private void getQuestions() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip) + "/retrieve/getMultiQuestions.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        counter = 0;
                        degree = 0;
                        list = new ArrayList<>();
                        try {

                            JSONObject root = new JSONObject(response);
                            int state = root.getInt("success");
                            if (state == 1) {
                                System.out.println("success = 1");
                                JSONArray array = root.getJSONArray("message");
                                JSONObject jsonObjectI = null;
                                for (int i = 0; i < array.length(); i++) {
                                    jsonObjectI = array.getJSONObject(i);
                                    list.add(new QuestionMultiChoice(
                                            jsonObjectI.getString("question"),
                                            jsonObjectI.getString("a"),
                                            jsonObjectI.getString("b"),
                                            jsonObjectI.getString("c"),
                                            jsonObjectI.getString("d"),
                                            jsonObjectI.getString("correct")));
                                }
                                questionBody.setText(list.get(counter).getmQuestionBody());
                                choiceA.setText(list.get(counter).getmChoiceA());
                                choiceB.setText(list.get(counter).getmChoiceB());
                                choiceC.setText(list.get(counter).getmChoiceC());
                                choiceD.setText(list.get(counter).getmChoiceD());
                                mNextBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String anwser = list.get(counter).getmCorrectAnswer();
                                        if (anwser.equals("a")) {
                                            if (radioGroup.getCheckedRadioButtonId() == R.id.a) {
                                                degree++;
                                            }
                                        } else if (anwser.equals("b")) {
                                            if (radioGroup.getCheckedRadioButtonId() == R.id.b) {
                                                degree++;
                                            }
                                        } else if (anwser.equals("c")) {
                                            if (radioGroup.getCheckedRadioButtonId() == R.id.c) {
                                                degree++;
                                            }
                                        } else if (anwser.equals("d")) {
                                            if (radioGroup.getCheckedRadioButtonId() == R.id.d) {
                                                degree++;
                                            }
                                        }


                                        counter++;
                                        if (counter >= list.size()) {
                                            //store student result in database
                                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, getString(R.string.ip) + "/insert/insertuserdegree.php", new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Intent intent = new Intent(getBaseContext(), AssignmentsActivity.class);
                                                    intent.putExtra("course_number", courseNumber);
                                                    intent.putExtra("student_id", studentId);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    HashMap<String, String> map = new HashMap<>();
                                                    map.put("student_id", Integer.toString(studentId));
                                                    map.put("assignment_id", Integer.toString(assignment_id));
                                                    map.put("degree", Integer.toString(degree));
                                                    map.put("course_number", Integer.toString(courseNumber));
                                                    return map;
                                                }
                                            };
                                            SingleTon.getInstance(getBaseContext()).addRequest(stringRequest1);
                                        } else {
                                            if ((counter + 1) == list.size())
                                                mNextBtn.setText("FINISH");
                                                mNextBtn.setBackgroundColor(Color.RED);
                                            questionBody.setText(list.get(counter).getmQuestionBody());
                                            choiceA.setText(list.get(counter).getmChoiceA());
                                            choiceB.setText(list.get(counter).getmChoiceB());
                                            choiceC.setText(list.get(counter).getmChoiceC());
                                            choiceD.setText(list.get(counter).getmChoiceD());
                                        }
                                    }
                                });
                            } else {
                                System.out.println("success = zero");
                            }
                        } catch (JSONException ex) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("assignment_id", Integer.toString(assignment_id));
                return map;
            }
        };
        SingleTon.getInstance(this).addRequest(stringRequest);
    }

    private void getIntentValues() {
        assignment_id = intent.getIntExtra("assignment_id", 0);
        new CustomToast(this).raiseToast("assignment_Id      " + assignment_id);
        studentId = intent.getIntExtra("student_id", 0);
        courseNumber = intent.getIntExtra("course_number", 0);
    }


}
