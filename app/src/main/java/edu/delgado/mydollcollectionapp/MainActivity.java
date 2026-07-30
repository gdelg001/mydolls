package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // references
    private MediaPlayer mMediaPlayer; // field used to play music
    private Menu mMenu; // to access the menu object at any time
    private SoundEffects mSoundEffects; // field used to play sounds when clicking button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call super onCreate
        super.onCreate(savedInstanceState);
        // set the view layout to display UI from activity_main
        setContentView(R.layout.activity_main);

        // gets the object instance of SoundEffects
        // SoundEffects is implemented as a singleton
        mSoundEffects = SoundEffects.getInstance(getApplicationContext());
    }// end onCreate

    // Method invoked when clicking My Favorite Dolls Button
    public void goToDollList(View view) {
        // Animate button:
        // instance (object) of My Favorite Dolls button
        // get the collection_button view & save it to the button object
        Button button = findViewById(R.id.collection_button);
        // loads animation resource bounce from anim folder
        // returns animation object
        Animation bounceAnim = AnimationUtils.loadAnimation(this,R.anim.bounce);
        // starts the animation object on the button view object
        button.startAnimation(bounceAnim);

        // Move to Dolls List Activity:
        // instantiate intent for starting a new Dolls List Activity
        Intent intent = new Intent(this, DollsListActivity.class);
        // start a new activity with the intent object
        startActivity(intent);

        // Play sprinkle sound when clicking Favorite Dolls Button
        mSoundEffects.playLala();
    }// end goToDollList

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // get inflater object
        getMenuInflater().inflate(R.menu.app_menu, menu); //invoke inflate to display menu
        mMenu = menu; //save menu reference to the object to have access in inflater
        // and any method later on
        return super.onCreateOptionsMenu(menu);  // return menu to super
    }//end onCreateOptionsMenu

    // When the activity is started or resumed
    @Override
    protected void onResume() {
        super.onResume(); // call super

        // creates a MediaPlayer and loads the raw resource pretty.mp3
        mMediaPlayer = MediaPlayer.create(this, R.raw.pretty);
        mMediaPlayer.setLooping(true); // loop music
        // start playing the audio file
        mMediaPlayer.start();
    }

    // When the activity is paused or stopped
    @Override
    protected void onPause() {
        super.onPause(); // call super

        // stops playing the audio file
        mMediaPlayer.stop();
        // releases resources associated with the MediaPlayer
        mMediaPlayer.release();
        // allows the MediaPlayer to be garbage collected
        mMediaPlayer = null;
    }

}// end class