package exampleoncreatingfixedfragment.example.com.androidproject.Models;

/**
 * Created by 450 G1 on 19/05/2017.
 */

public class QuestionMultiChoice {
    private int questionNumber;
    private String mQuestionBody , mChoiceA, mChoiceB, mChoiceC, mChoiceD, mCorrectAnswer;

    public QuestionMultiChoice( String mQuestionBody, String mChoiceA, String mChoiceB, String mChoiceC, String mChoiceD, String mCorrectAnswer) {

        this.mQuestionBody = mQuestionBody;
        this.mChoiceA = mChoiceA;
        this.mChoiceB = mChoiceB;
        this.mChoiceC = mChoiceC;
        this.mChoiceD = mChoiceD;
        this.mCorrectAnswer = mCorrectAnswer;
    }



    public String getmQuestionBody() {
        return mQuestionBody;
    }

    public String getmChoiceA() {
        return mChoiceA;
    }

    public String getmChoiceB() {
        return mChoiceB;
    }

    public String getmChoiceC() {
        return mChoiceC;
    }

    public String getmChoiceD() {
        return mChoiceD;
    }

    public String getmCorrectAnswer() {
        return mCorrectAnswer;
    }
}
