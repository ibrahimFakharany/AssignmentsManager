package exampleoncreatingfixedfragment.example.com.androidproject.Models;

/**
 * Created by 450 G1 on 18/05/2017.
 */

public class StudentCourseAssignment extends Course {
    String assignment_id, instruction, description, startTime, endTime, typeOfWork;
    int studentId;
    int seen = 0 ;
    public StudentCourseAssignment(int doctor_id, int studentId, int course_number, String course_name, String course_point, String course_semster, String assignment_id, String instruction, String description, String startTime, String endTime, String typeOfWork, int seen) {
        super(doctor_id, course_number, course_name, course_point, course_semster);
        this.assignment_id = assignment_id;
        this.instruction = instruction;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.typeOfWork = typeOfWork;
        this.seen = seen;
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public int getSeen() {
        return seen;
    }
}
