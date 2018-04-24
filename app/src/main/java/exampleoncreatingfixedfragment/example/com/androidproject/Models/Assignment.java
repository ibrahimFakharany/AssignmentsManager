package exampleoncreatingfixedfragment.example.com.androidproject.Models;

/**
 * Created by 450 G1 on 18/05/2017.
 */

public class Assignment {
    int assignment_id;
    int courseNumber;
    String Instruction;
    String description;
    String starttime;
    String endtime;
    String type_of_work;
    String type;

    public Assignment(int assignment_id, int courseNumber, String instruction, String description, String starttime, String endtime, String type_of_work ) {
        this.assignment_id = assignment_id;
        this.courseNumber = courseNumber;
        this.Instruction = instruction;
        this.description = description;
        this.starttime = starttime;
        this.endtime = endtime;
        this.type_of_work = type_of_work;

    }



    public int getAssignment_id() {
        return assignment_id;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getInstruction() {
        return Instruction;
    }

    public String getDescription() {
        return description;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getType_of_work() {
        return type_of_work;
    }
}
