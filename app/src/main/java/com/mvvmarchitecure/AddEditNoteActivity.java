package com.mvvmarchitecure;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.mvvmarchitecure.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.mvvmarchitecure.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.mvvmarchitecure.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.mvvmarchitecure.EXTRA_PRIORITY";

    private EditText title, description;
    private NumberPicker numberPicker;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.titleEditText);
        description = findViewById(R.id.descriptionEditText);
        numberPicker = findViewById(R.id.numberPicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            title.setText(intent.getStringExtra(EXTRA_TITLE));
            description.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }else {
            setTitle("Add Note");
        }

    }

    private void saveNote(){
        String getTitle = title.getText().toString();
        String getDescription = description.getText().toString();
        int getPriority = numberPicker.getValue();

        if (getTitle.trim().isEmpty() || getDescription.trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return; }

        // TO PASS THE DATA FROM THE FIELDS INTO THE MAIN ACTIVITY
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, getTitle);
        data.putExtra(EXTRA_DESCRIPTION, getDescription);
        data.putExtra(EXTRA_PRIORITY,  getPriority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
