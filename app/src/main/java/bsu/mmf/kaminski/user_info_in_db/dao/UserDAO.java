package bsu.mmf.kaminski.user_info_in_db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import bsu.mmf.kaminski.user_info_in_db.entities.User;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User user);

    @Insert
    void insertUsers(User... users);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM user WHERE first_name LIKE :search || '%' OR last_name LIKE :search || '%'")
    LiveData<List<User>> findUsersByName(String search);
}