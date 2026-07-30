package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Activity to display list of doll
public class DollsDetailsActivity extends AppCompatActivity {
    // references
    private SoundEffects mSoundEffects; // field used to play sounds when clicking photo
    private MediaPlayer mMediaPlayer; // field used to play music
    private long mDollId; // field for each doll id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call super onCreate
        super.onCreate(savedInstanceState);
        // set the view layout to display UI from activity_dolls_details
        setContentView(R.layout.activity_dolls_details);
        // gets the object instance of SoundEffects
        mSoundEffects = SoundEffects.getInstance(getApplicationContext());

        // get the DOLL ID from DollsListActivity
        // when a doll button is clicked
        // doll value is associated to a button // for each doll
        // saved in intent object
        Intent intent = getIntent(); // get intent object
        mDollId = intent.getLongExtra("DOLL_ID",1); // extract doll id

    }

    // method invoked when clicking a doll image
    public void AnimateDolls(View view) {

        // if player exists from earlier tap on a photo
        if(mMediaPlayer != null) {
            mMediaPlayer.stop(); // old sound is stopped
            mMediaPlayer.release(); // free the resource
            mMediaPlayer = null; // no player exists again
        }
        // add animation & sound when clicking doll photo:
        // creates a MediaPlayer and loads the raw resource honey.mp3
        mMediaPlayer = MediaPlayer.create(this, R.raw.honey);
        // start playing the audio file
        mMediaPlayer.start();

        // Animate doll image:
        // find image view for doll_image // save as ImageAnim object
        ImageView ImageAnim = findViewById(R.id.doll_image);
        // Load and start the animations in imageanimator.xml from the animator resource
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.imageanimator);
        // set ImageAnim image view as the target object
        animator.setTarget(ImageAnim);
        // start playing the animations in the AnimatorSet
        animator.start();
    }

    // When the activity is paused or stopped
    @Override
    protected void onPause() {
        super.onPause(); // call super

        if(mMediaPlayer != null) {
            // stops playing the audio file
            mMediaPlayer.stop();
            // releases resources associated with the MediaPlayer
            mMediaPlayer.release();
            // allows the MediaPlayer to be garbage collected
            mMediaPlayer = null;
        }
    }

    // When clicking edit button in details screen
    public void editDoll(View view) {
        // Builds intent targeting DollEditActivity
        Intent intent = new Intent(this, DollEditActivity.class);
        // attaches doll id under DOLL_ID
        intent.putExtra("DOLL_ID", mDollId);
        // launches form for editing doll information
        startActivity(intent);
    }

    private void showDoll() {
        // instance (object) of doll name TextView
        // getting the doll_Name_text_view & saving it to the TextView object
        TextView dollName = findViewById(R.id.doll_Name);
        // instance (object) of doll model TextView
        // getting the doll_Model_text_view & saving it to the TextView object
        TextView dollModel = findViewById(R.id.doll_Model);
        // instance (object) of doll year TextView
        // getting the doll_Year_text_view & saving it to the TextView object
        TextView dollYear = findViewById(R.id.doll_Year);
        // instance (object) of doll cost TextView
        // getting the doll_Cost_text_view & saving it to the TextView object
        TextView dollCost = findViewById(R.id.doll_Cost);
        // instance (object) of doll body TextView
        // getting the doll_Body_text_view & saving it to the TextView object
        TextView dollBody = findViewById(R.id.doll_Body);
        // instance (object) of doll wig TextView
        // getting the doll_Wig_text_view & saving it to the TextView object
        TextView dollWig = findViewById(R.id.doll_Wig);
        // instance (object) of doll eyes TextView
        // getting the doll_Eyes_text_view & saving it to the TextView object
        TextView dollEyes = findViewById(R.id.doll_Eyes);
        // Additional Textview objects for doll attributes
        TextView dollCompany = findViewById(R.id.doll_Company);
        TextView dollBrand = findViewById(R.id.doll_Brand);

        // instance (object) of doll image ImageView
        // getting the doll_Image_view  & saving it to the ImageView object
        ImageView dollImage = findViewById(R.id.doll_image);

        // create an instance of ManageDolls
        ManageDollsRepo dolls = ManageDollsRepo.getInstance(this);
        DollDataModel d = dolls.getDoll(mDollId); // return doll // save as d

        // set text inside of text views:
        // get Doll_Name for doll
        // set text as doll name value for dollName TextView
        dollName.setText(d.getDoll_name());
        // get Doll_Model for doll
        // set text as doll model value for dollModel TextView
        dollModel.setText(d.getDoll_model());
        // get Doll_Year for doll
        // set text as doll year string for dollYear TextView
        dollYear.setText(d.getDoll_year());
        // get Doll_Cost for doll
        // set text as doll cost value for dollCost TextView
        dollCost.setText(d.getDoll_cost());
        // get Doll_Body for doll
        // set text as doll body value for dollBody TextView
        dollBody.setText(d.getDoll_body());
        // get Doll_Wig for doll
        // set text as doll wig value for dollWig TextView
        dollWig.setText(d.getDoll_wig());
        // get Doll_Eyes for doll
        // set text as doll eyes value for dollEyes TextView
        dollEyes.setText(d.getDoll_eyes());
        // get doll company for doll
        // set text as doll company value for TextView
        dollCompany.setText(d.getDoll_company());
        // get doll brand for doll
        // set text as doll brand value for TextView
        dollBrand.setText(d.getDoll_brand());

        // fill details screen ImageView with the doll's picture
        DollImageHelper.loadImage(this, dollImage, d.getDoll_image());
    }

    @Override
    protected void onResume() { // when resuming activity
        super.onResume();
        showDoll(); //  re-reads doll from database to repopulate the views
    }

    public void deleteDoll(View view) { // when clicking delete button
        try {
            // gets the repository
            ManageDollsRepo repo = ManageDollsRepo.getInstance(this);
            // fetches model for doll on screen
            DollDataModel doll = repo.getDoll(mDollId);
            // deletes entire doll entry in database
            repo.delete(doll);
            // closes screen to return to list
            finish();
        } catch (Exception e) {
            Toast.makeText(this, R.string.doll_delete_error, Toast.LENGTH_LONG).show();
        }
    }
}