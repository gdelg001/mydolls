package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

// gets resource for an image to load image into a view
public class DollImageHelper {
    // returns drawable name into drawable resource id
    public static int getImageResourceId(Context context, String name) {
        if(name == null || name.trim().isEmpty()) {
            // returns default image // if image has no drawable name
            return R.drawable.outline_add_photo_alternate_24;
        }

        // looks up drawable from string to get the id
        int imageId =  context.getResources().getIdentifier(name.trim(),"drawable", context.getPackageName());

        if(imageId == 0) { // no match found
            // returns placeholder image
            return R.drawable.outline_add_photo_alternate_24;
        }

        return imageId;
    }

    // loads image after deciding how to load
    public static void loadImage(Context context, ImageView view, String imageVal) {
        // When URI:
        // Parses string into Uri and loads it with setImageURI
        if(imageVal != null && imageVal.startsWith("content://")) {
            view.setImageURI(Uri.parse(imageVal));

            if(view.getDrawable() == null) { // if uri not readable
                // sets view with placeholder image
                view.setImageResource(R.drawable.outline_add_photo_alternate_24);
            }

            return;
        }

        // sets image for the view when not a URI
        // when it is a drawable name
        view.setImageResource(getImageResourceId(context, imageVal));
    }
}