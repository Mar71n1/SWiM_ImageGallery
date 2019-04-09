package pl.pwr.imagegallery;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private static final String TAG = "MainActivity";
    private static final int addNewImageRequestCode = 1902;

    private ArrayList<ImageData> images = new ArrayList<>();

    //private ArrayList<String> imageUrls = new ArrayList<>();
    //private ArrayList<String> imageTitles = new ArrayList<>();
    //private ArrayList<String> imageDates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");
        initiateImages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.main_activity_menu_add:
                startActivityForResult(new Intent(this, AddNewImageActivity.class), addNewImageRequestCode);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("images", images);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        images = savedInstanceState.getParcelableArrayList("images");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == addNewImageRequestCode)
            if(resultCode == RESULT_OK){
                String url = data.getStringExtra("url");
                String title = data.getStringExtra("title");
                String date = data.getStringExtra("date");

                images.add(new ImageData(url, title, date));
                adapter.notifyDataSetChanged();
            }
    }

    private void initiateImages() {
        Log.d(TAG, "initiateImages: preparing images.");

        //images.add(new ImageData("https://images-na.ssl-images-amazon.com/images/I/71h-SbGNBPL._SY606_.jpg", "Real Madrid crest", "24.05.2014"));
        //images.add(new ImageData("https://images-na.ssl-images-amazon.com/images/I/61Ap23sZcYL._SX355_.jpg", "FC Barcelona crest", "25.05.2014"));
        //images.add(new ImageData("https://brandingmonitor.pl/wp-content/uploads/2016/12/nowy-herb-atletico-madryt-wypukly.png", "Atletico Madrid crest", "26.05.2014"));
        //images.add(new ImageData("https://i.pinimg.com/originals/29/de/e3/29dee3ccbfbe5400da7d04a665359ad2.jpg", "Sevilla FC crest", "27.05.2014"));
        //images.add(new ImageData("https://upload.wikimedia.org/wikipedia/en/thumb/9/98/Club_Athletic_Bilbao_logo.svg/185px-Club_Athletic_Bilbao_logo.svg.png", "Athletic Bilbao crest", "28.05.2014"));

        initiateRecyclerView();
    }

    private void initiateRecyclerView() {
        Log.d(TAG, "initiateRecyclerView: initiating RecyclerView.");
        recyclerView = findViewById(R.id.main_activity_recycler_view);
        adapter = new RecyclerViewAdapter(this, images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                images.remove(viewHolder.getAdapterPosition());

                //imageUrls.remove(viewHolder.getAdapterPosition());
                //imageTitles.remove(viewHolder.getAdapterPosition());
                //imageDates.remove(viewHolder.getAdapterPosition());

                Toast.makeText(MainActivity.this, "Item removed at position " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
