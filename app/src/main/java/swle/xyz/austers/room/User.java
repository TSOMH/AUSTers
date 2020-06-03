package swle.xyz.austers.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
*Created by TSOMH on 2020/3/3$
*Description:
*
*/
@Entity(primaryKeys = {"uid","phonenumber"})
public class User{




    private int uid;

    private String student_id;//学号
    @NonNull
    private String phonenumber;
    private String password;
    private String password_jwxt;
    private String organization;
    private String major;
    private String name;  //昵称
    private String true_name;
    private boolean is_student;


    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }


    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_student() {
        return is_student;
    }

    public void setIs_student(boolean is_student) {
        this.is_student = is_student;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPassword_jwxt() {
        return password_jwxt;
    }

    public void setPassword_jwxt(String password_jwxt) {
        this.password_jwxt = password_jwxt;
    }
}
