package bsu.mmf.kaminski.user_info_in_db.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import bsu.mmf.kaminski.user_info_in_db.entities.User;

@Database(entities = {User.class}, version  = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase INSTANCE;

    public abstract UserDAO userDao();

    public static UserDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_info_db")
                    .allowMainThreadQueries()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            AsyncTask.execute(() -> {
                                INSTANCE.userDao().insertUsers(setPrimeData());
                            });
                        }
                    })
                    .build();

        }

        return INSTANCE;
    }

    private static User[] setPrimeData(){
        return new User[] {
                new User("First person name", "First person surname", "Some text"),
                new User("Second person name", "Second person surname", "Another text"),
                new User("Third person name", "Third person surname", "Third comment")
        };
    }
}
