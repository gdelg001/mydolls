package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32

// import classes needed
import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import java.util.ArrayList;

// Singleton Class
public class SoundEffects { // sounds when clicking a doll button
    private static SoundEffects mSoundEffects; // instance of singleton

    // attributes
    private SoundPool mSoundPool; // reference to play short sounds when doll button selected
    private final ArrayList<Integer> mDollSoundIds; // array list to collect sound IDs
    private int mDollSoundIndex; // index to keep track which sound it is in the array

    private int mLoadedCount = 0;

    // Constructor
    private SoundEffects(Context context) {
        // collection of attributes describing information about an audio stream
        AudioAttributes attributes = new AudioAttributes.Builder()
                // audio's purpose
                .setUsage(AudioAttributes.USAGE_MEDIA) // media
                // audio's type
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) // music
                .build(); // build object
        mSoundPool = new SoundPool.Builder() // create a SoundPool instance
                // set attributes
                // define information about the audio files the SoundPool is going to play
                .setAudioAttributes(attributes)
                .build(); // build object

        // instantiate empty array list
        // keep track of sound IDs
        mDollSoundIds = new ArrayList<>();

        mSoundPool.setOnLoadCompleteListener(((soundPool, sampleId, status) -> {
            if (status == 0) {
                mLoadedCount++; // increment the pool counter
            }
        }));

        // loads an audio from raw resource into the SoundPool and return a sound ID:
        // add Sound ID to array
        mDollSoundIds.add(mSoundPool.load(context, R.raw.ala, 1)); // ID:0
        mDollSoundIds.add(mSoundPool.load(context, R.raw.eclata, 1)); // ID:1
        mDollSoundIds.add(mSoundPool.load(context, R.raw.lala, 1)); // ID:2
        mDollSoundIds.add(mSoundPool.load(context, R.raw.moona, 1)); // ID:3
        mDollSoundIds.add(mSoundPool.load(context, R.raw.sacralita, 1)); // ID:4
    }

    // method for getting the object instance
    public static SoundEffects getInstance(Context context) {
        if (mSoundEffects == null) { // if instance doesn't exist
            mSoundEffects = new SoundEffects(context); // create a new instance // reference to itself
        }
        return mSoundEffects; // return the instance if it exists
    }

    // Play sound:
    // get index // for each sound in the array
    // play Ala button sound // ID:0
    public void playAla() {
        playSound(0);
    }

    // play Eclata button sound // ID:1
    public void playEclata() {
        playSound(1);
    }

    // play Lala button sound // ID:2
    public void playLala() {
        playSound(2);
    }

    // play Moona button sound // ID:3
    public void playMoona() {
        playSound(3);
    }

    // play Sacralita button sound // ID:4
    public void playSacralita() {
        playSound(4);
    }

    // When an app no longer needs a SoundPool
    public void release() {
        mSoundPool.release(); // release the SoundPool resources
        mSoundPool = null; //  allows the SoundPool to be garbage collected
    }

    // Map doll name to sound file
    public void lookupSound(String name) {
        if(name == null) { // no doll name
            playLala();
            return;
        }

        // Play a specific sparkled sound
        // based on the doll's name
        if(name.equalsIgnoreCase("Ala")){
            playAla();
        } else if(name.equalsIgnoreCase("Eclata")) {
            playEclata();
        } else if(name.equalsIgnoreCase("Moona")){
            playMoona();
        } else if (name.equalsIgnoreCase("Sacralita")) {
            playSacralita();
        } else { // plays Lala sparkle sound for Lala
            // and all other dolls not from the sample starter dataset
            playLala();
        }
    }

    // play sound for a given index in a sound pool
    private void playSound(int index) {
        if (mLoadedCount < mDollSoundIds.size()) {
            return;
        }
        mSoundPool.play(mDollSoundIds.get(index),1,1,1,0,1);
    }

}// end class
