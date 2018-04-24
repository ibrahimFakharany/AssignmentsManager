package exampleoncreatingfixedfragment.example.com.androidproject.Models;

/**
 * Created by 450 G1 on 12/05/2017.
 */
public class Course {
    private int doctor_id;
    private int course_number;
    private String course_name;
    private String course_point;
    private String course_semster;
    private int student_id;
    public Course( int doctor_id, int course_number, String course_name, String course_point, String course_semster) {
        this.doctor_id = doctor_id;
        this.course_number = course_number;
        this.course_name = course_name;
        this.course_point = course_point;
        this.course_semster = course_semster;
    }
    public Course( int doctor_id,int student_id, int course_number, String course_name, String course_point, String course_semster) {
        this.doctor_id = doctor_id;
        this.course_number = course_number;
        this.course_name = course_name;
        this.course_point = course_point;
        this.course_semster = course_semster;
        this.student_id = student_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public int getCourse_number() {
        return course_number;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_point() {
        return course_point;
    }

    public String getCourse_semster() {
        return course_semster;
    }
}
