package pl.pwr.imagegallery;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNewImageActivity extends AppCompatActivity {

    private EditText urlEditText;
    private EditText titleEditText;
    private EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_new_image);

        urlEditText = findViewById(R.id.add_new_image_activity_url_editText);
        titleEditText = findViewById(R.id.add_new_image_activity_title_editText);
        dateEditText = findViewById(R.id.add_new_image_activity_date_editText);
    }

    public void addNewImage(View view) {
        if(urlEditText.getText().toString().equals("") || titleEditText.getText().toString().equals("") || dateEditText.getText().toString().equals("")) {
            if(urlEditText.getText().toString().equals(""))
                urlEditText.setError("Enter some data");

            if(titleEditText.getText().toString().equals(""))
                titleEditText.setError("Enter some data");

            if(dateEditText.getText().toString().equals(""))
                dateEditText.setError("Enter some data");
        } else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("url", urlEditText.getText().toString());
            resultIntent.putExtra("title", titleEditText.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date;
            try {
                date = sdf.parse(dateEditText.getText().toString());
                resultIntent.putExtra("date", sdf.format(date));
                setResult(RESULT_OK, resultIntent);
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Enter the date in the appropriate format", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
