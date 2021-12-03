package bsu.mmf.kaminski.user_info_in_db;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UserActivity extends AppCompatActivity {

    private EditText inputName;
    private EditText inputSurname;
    private EditText inputComment;
    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        inputName = findViewById(R.id.input_name);
        inputSurname = findViewById(R.id.input_surname);
        inputComment = findViewById(R.id.input_comment);
        saveButton = findViewById(R.id.button_save);
        cancelButton = findViewById(R.id.button_cancel);

        saveButton.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("NAME", inputName.getText().toString());
            returnIntent.putExtra("SURNAME", inputSurname.getText().toString());
            returnIntent.putExtra("COMMENT", inputComment.getText().toString());
            this.setResult(Activity.RESULT_OK, returnIntent);
            this.finish();
        });

        cancelButton.setOnClickListener(view -> {
            this.setResult(Activity.RESULT_CANCELED);
            this.finish();
        });
    }
}