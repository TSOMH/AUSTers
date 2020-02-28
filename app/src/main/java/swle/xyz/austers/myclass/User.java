package swle.xyz.austers.myclass;
/**
*Created by TSOMH on 2020/2/25$
*Description:
*
*/
public class User implements java.io.Serializable{

    private String student_id;
    private String password;

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
