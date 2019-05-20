package pl.pwr.imagegallery;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNewImageActivity extends AppCompatActivity {

    private EditText urlEditText;
    private EditText titleEditText;
    private EditText dateEditText;
    private TextView tagsTextView;
    private ArrayList<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add_new_image);

        urlEditText = findViewById(R.id.add_new_image_activity_url_editText);
        titleEditText = findViewById(R.id.add_new_image_activity_title_editText);
        dateEditText = findViewById(R.id.add_new_image_activity_date_editText);
        tagsTextView = findViewById(R.id.add_new_image_activity_tags_textView);
        tags = new ArrayList<>();
    }

    public void labelImage(View view) {
        Toast.makeText(this, "Wait for tagging to be processed", Toast.LENGTH_SHORT).show();

        Picasso.get().load(urlEditText.getText().toString()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                tagImage(bitmap);
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) { }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }

    public void addNewImage(View view) {
        if(urlEditText.getText().toString().equals("") || titleEditText.getText().toString().equals("") || dateEditText.getText().toString().equals("")) {
            if(urlEditText.getText().toString().equals("")) urlEditText.setError("Enter some data");
            if(titleEditText.getText().toString().equals("")) titleEditText.setError("Enter some data");
            if(dateEditText.getText().toString().equals("")) dateEditText.setError("Enter some data");
        } else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("url", urlEditText.getText().toString());
            resultIntent.putExtra("title", titleEditText.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date;
            try {
                date = sdf.parse(dateEditText.getText().toString());
                resultIntent.putExtra("date", sdf.format(date));

            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Enter the date in the appropriate format", Toast.LENGTH_SHORT).show();
            }
            resultIntent.putStringArrayListExtra("tags", tags);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private void tagImage(Bitmap bitmap) {
        FirebaseVisionImage visionImage = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();
        labeler.processImage(visionImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                        StringBuilder sb = new StringBuilder();
                        for(FirebaseVisionImageLabel label : firebaseVisionImageLabels) {
                            tags.add(label.getText());
                            sb.append(label.getText() + ", ");
                        }
                        tagsTextView.setText(sb.toString().substring(0, sb.length() - 2));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.wtf("LAB", e);
                    }
                });
    }
}
