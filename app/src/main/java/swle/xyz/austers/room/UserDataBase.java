package swle.xyz.austers.room;
import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
*Created by TSOMH on 2020/3/3$
*Description:
*
*/
@Database(entities = {User.class,Trip.class},version = 1,exportSchema = false)
public abstract class UserDataBase extends RoomDatabase{
    public abstract UserDao getUserDao();
    public abstract TripsDao getTripDao();
}
