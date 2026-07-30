package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// Activity to display the form for inserting and editing Doll Info
public class DollEditActivity extends AppCompatActivity {
    // references
    // fields for all editable doll attributes
    private EditText mDollName;
    private EditText mDollModel;
    private EditText mDollCompany;
    private EditText mDollBrand;
    private EditText mDollYear;
    private EditText mDollCost;
    private EditText mDollBody;
    private EditText mDollWig;
    private EditText mDollEye;
    private String mDollImage = "";
    private long mDollId;

    // field for result of the photo picker
    ActivityResultLauncher<String[]> mPhotoPicker;

    // field for view that will show an image
    private ImageView mDollImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // calls super
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // set the view layout to display UI from activity_doll_edit
        setContentView(R.layout.activity_doll_edit);

        // instance (object) of EditText for each doll attribute
        // gets the view & saves it to the EditText object
        mDollName = findViewById(R.id.edit_doll_name);
        mDollModel = findViewById(R.id.edit_doll_model);
        mDollCompany = findViewById(R.id.edit_doll_company);
        mDollBrand = findViewById(R.id.edit_doll_brand);
        mDollYear = findViewById(R.id.edit_doll_year);
        mDollCost = findViewById(R.id.edit_doll_cost);
        mDollBody = findViewById(R.id.edit_doll_type);
        mDollWig = findViewById(R.id.edit_doll_wig_color);
        mDollEye = findViewById(R.id.edit_doll_eye_color);

        // field pulls long value stored in doll id
        mDollId = getIntent().getLongExtra("DOLL_ID", 0);

        // gets view for the doll image and saves to Imageview object
        mDollImageView = findViewById(R.id.doll_image);

        // when user picks an image
        // call lambda with uri or null if backed out
        // lambda stores URI string in mDollImage
        // shows it in mDollImageView
        mPhotoPicker = registerForActivityResult(new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if(uri == null) {return;}

                    try {
                        getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (SecurityException e) {
                        Toast.makeText(this,R.string.image_access_error, Toast.LENGTH_LONG).show();
                    }

                    mDollImage = uri.toString();

                    mDollImageView.setImageURI(uri);
                });

        if(mDollId != 0) { // if doll id key is in the Intent
            // Get data repository
            ManageDollsRepo repo = ManageDollsRepo.getInstance(this);
            // get model for the doll // by id
            DollDataModel model = repo.getDoll(mDollId);
            // Get view for title in the form
            TextView textView = findViewById(R.id.edit_title);
            // set text for title TextView as the doll name
            textView.setText(model.getDoll_name());
            // set the text for each of the EditText fields
            mDollName.setText(model.getDoll_name());
            mDollModel.setText(model.getDoll_model());
            mDollCompany.setText(model.getDoll_company());
            mDollBrand.setText(model.getDoll_brand());
            mDollYear.setText(model.getDoll_year());
            mDollCost.setText(model.getDoll_cost());
            mDollBody.setText(model.getDoll_body());
            mDollWig.setText(model.getDoll_wig());
            mDollEye.setText(model.getDoll_eyes());
            mDollImage = model.getDoll_image(); // get doll image
            // load doll image
            DollImageHelper.loadImage(this, mDollImageView, mDollImage);
        }
    }

    public void saveDoll(View view) { // when save button is clicked
        if (!validateForm()) {// call form (fields) validation before saving
            return;
        }
        // from the EditText field hold string values
        // for each doll attribute
        String nameCol = mDollName.getText().toString().trim();
        String modelCol = mDollModel.getText().toString().trim();
        String companyCol = mDollCompany.getText().toString().trim();
        String brandCol = mDollBrand.getText().toString().trim();
        String yearCol = mDollYear.getText().toString().trim();
        String costCol = mDollCost.getText().toString().trim();
        String bodyCol = mDollBody.getText().toString().trim();
        String wigCol = mDollWig.getText().toString().trim();
        String eyeCol = mDollEye.getText().toString().trim();

        // Create a DollDataModel
        // pass the string values as arguments
        DollDataModel model = new DollDataModel(mDollId, nameCol, yearCol, costCol, modelCol,
                bodyCol, wigCol, eyeCol, companyCol, brandCol, mDollImage);

        // Get the data repository
        ManageDollsRepo repo = ManageDollsRepo.getInstance(this);

        try {
            if (mDollId == 0) { // when form opens by FAB click
                repo.newDoll(model); // insert doll & attributes to database
            } else { // id exists for the doll
                repo.updateDoll(model); // update Doll Model info
            }
            finish(); // exit the form
        }
        catch (Exception e) {
            Toast.makeText(this, R.string.save_doll_error, Toast.LENGTH_LONG).show();
        }

    }


    // exits edit form when clicking cancel button
    public void cancelEdit(View view) {
        finish();
    }

    // launches photo picker when clicking the choose photo button
    public void choosePhoto(View view) {
        mPhotoPicker.launch(new String[]{"image/*"});
    }

    // Validate for valid alphanumeric characters
    private boolean isValidText(String input) {
        if (input == null) {
            return true;
        }

        return input.matches("[A-Za-z0-9 .,/-]*");
    }

    // Check for required fields and alphanumberic characters
    private boolean checkField(EditText editText) {
        String cleanInput = editText.getText().toString().trim();

        if (cleanInput.isEmpty()) {
            editText.setError("Required");
            return false;
        }

        if (!isValidText(cleanInput)) {
            editText.setError("Use alphanumeric characters only.");
            return false;
        }

        editText.setError(null);

        return true;
    }

    // Validates all the fields in the form
    private boolean validateForm() {
        boolean valid = true;

        if (!checkField(mDollName) ) {
            valid = false;
        }
        if (!checkField(mDollModel) ) {
            valid = false;
        }
        if (!checkField(mDollCompany) ) {
            valid = false;
        }
        if (!checkField(mDollBrand) ) {
            valid = false;
        }
        if (!checkField(mDollYear) ) {
            valid = false;
        }
        if (!checkField(mDollCost) ) {
            valid = false;
        }
        if (!checkField(mDollBody) ) {
            valid = false;
        }
        if (!checkField(mDollWig) ) {
            valid = false;
        }
        if (!checkField(mDollEye) ) {
            valid = false;
        }

        return valid;
    }

}