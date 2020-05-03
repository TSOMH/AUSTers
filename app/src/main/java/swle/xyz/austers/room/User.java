package swle.xyz.austers.room;

import androidx.room.ColumnInfo;
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
    public int uid;

    @ColumnInfo
    private String student_id;

    @ColumnInfo
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

}
