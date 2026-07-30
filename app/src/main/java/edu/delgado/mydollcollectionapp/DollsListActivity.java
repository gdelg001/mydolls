package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DollsListActivity extends AppCompatActivity {
    // reference
    // field to hold sound
    private SoundEffects mSoundEffects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call super onCreate
        super.onCreate(savedInstanceState);
        // set the view layout to display UI from activity_dolls_list
        setContentView(R.layout.activity_dolls_list);

        // Gets app's shared SoundEffects object and stores in field
        mSoundEffects = SoundEffects.getInstance(getApplicationContext());
    }

    public void addDoll(View view) { // click handler for FAB
        // Builds Intent targeting DollEditActivity
        Intent intent = new Intent(this, DollEditActivity.class);
        // launches form
        startActivity(intent);
    }

    private void loadDolls() { // populates doll list on screen and respective buttons
        // get container
        LinearLayout container = findViewById(R.id.doll_container);
        // remove all views
        container.removeAllViews();
        // get doll list
        List<DollDataModel> list = ManageDollsRepo.getInstance(this).getDolls();
        // inflate layout
        LayoutInflater inflater = getLayoutInflater();
        // loop over the dolls
        for (DollDataModel doll :  list) {
            // inflate item doll
            View view = inflater.inflate(R.layout.item_doll,container,false);
            // get button from layout
            Button button = view.findViewById(R.id.doll_button);
            // set button text
            button.setText(doll.getDoll_name());
            // doll id
            final long id = doll.getDoll_id();
            // register listener
            // when clicking a doll button
            button.setOnClickListener(v -> {
                // Builds intent targeting DollsDetailsActivity
                Intent intent = new Intent(this, DollsDetailsActivity.class);
                // attach doll id under DOLL_ID
                intent.putExtra("DOLL_ID", id);
                // plays a sparkle sound
                mSoundEffects.lookupSound(doll.getDoll_name());
                // launches details screen
                startActivity(intent);
            });

            // view for doll image
            ImageView imageView = view.findViewById(R.id.doll_image);

            // hands imageView  and doll's stored image to helper method
            // picks by URI or Resource
            DollImageHelper.loadImage(this, imageView, doll.getDoll_image());
            // adds image view to the button
            container.addView(view);
        }
    }

    @Override
    protected void onResume() { // when the activity resumes
        super.onResume();
        // load all dolls
        loadDolls();
    }
}// end class