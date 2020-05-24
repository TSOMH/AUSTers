package swle.xyz.austers.bean;
/**
*Created by TSOMH on 2020/2/25$
*Description:
*
*/
public class User implements java.io.Serializable{

    private String name;
    private String student_id;
    private String password;
    private String emailaddress;
    private String phonenumber;
    private String qq;
    private String organization;

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }




    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name,String phonenumber,String qq){
        this.name = name;
        this.phonenumber = phonenumber;
        this.qq = qq;
    }
    public User(String name){
        this.name = name;
    }

    public User(String student_id,String password,String student_name,String phonenumber){
        this.student_id = student_id;
        this.student_name = student_name;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    private String student_name;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }



    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }
    public String getStudnet_id() {
        return student_id;
    }

    public void setStudnet_id(String studnet_id) {
        this.student_id = studnet_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
