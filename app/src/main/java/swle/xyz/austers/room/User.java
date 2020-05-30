package swle.xyz.austers.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
*Created by TSOMH on 2020/3/3$
*Description:
*
*/
@Entity
public class User{


    @PrimaryKey(autoGenerate = true)
    int uid;

    public String student_id;
    public String phonenumber;
    public String password;
    public String organization;

    public User(String student_id, String phonenumber, String password, String organization) {
        this.student_id = student_id;
        this.phonenumber = phonenumber;
        this.password = password;
        this.organization = organization;
    }
}
