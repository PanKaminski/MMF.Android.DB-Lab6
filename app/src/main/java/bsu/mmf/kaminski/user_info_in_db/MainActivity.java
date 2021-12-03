package bsu.mmf.kaminski.user_info_in_db;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import bsu.mmf.kaminski.user_info_in_db.dao.UserDatabase;
import bsu.mmf.kaminski.user_info_in_db.entities.User;

public class MainActivity extends AppCompatActivity {

    private TextView totalCount;
    private TextView searchResult;
    private EditText searchRecords;
    private Button addRecordButton;
    private Button searchButton;
    private UserDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = UserDatabase.getDbInstance(getApplicationContext());

        totalCount = findViewById(R.id.text_records_total_value);
        searchResult = findViewById(R.id.text_records_found_value);
        searchRecords = findViewById(R.id.search_records);
        addRecordButton = findViewById(R.id.button_add_record);
        searchButton = findViewById(R.id.button_search_records);

        this.setUsersCount();

        searchButton.setOnClickListener(view -> {
            findCountOfSpecifiedUsers(database, this.searchRecords.getText().toString());
        });

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();

                            String nameString = data.getStringExtra("NAME");
                            String surnameString = data.getStringExtra("SURNAME");
                            String commentString = data.getStringExtra("COMMENT");

                            database.userDao().insertUser(new User(nameString,
                                    surnameString, commentString));
                        }
                    }
                });

        addRecordButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            someActivityResultLauncher.launch(intent);
        });
    }

    private void setUsersCount(){
        database.userDao().getUsers().observe(this, users -> {
            totalCount.setText(String.valueOf(users.size()));
        });
    }

    public void findCountOfSpecifiedUsers(UserDatabase db, String name) {
        if (this.searchRecords.getText().toString() == ""){
            return;
        }
        db.userDao().findUsersByName(name).observe(this, users -> {
            searchResult.setText(String.valueOf(users.size()));
        });
    }
}