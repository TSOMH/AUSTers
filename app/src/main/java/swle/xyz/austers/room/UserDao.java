package swle.xyz.austers.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by TSOMH on 2020/3/3$
 * Description:
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * from user where phonenumber = :phonenumber")
    User queryUser(String phonenumber);

    @Query("SELECT true_name from user where phonenumber = :phonenumber")
    String queryTrueName(String phonenumber);
    @Query("SELECT organization from user where phonenumber = :phonenumber")
    String queryOrganization(String phonenumber);

    @Insert(onConflict = REPLACE)
    void InsertUser(User user);

    @Update
    void updateUser(User ... users);
}
