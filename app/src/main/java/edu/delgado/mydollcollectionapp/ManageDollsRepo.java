package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32
import android.content.Context;
import androidx.room.Room;
import java.util.List;

// Singleton Class
public class ManageDollsRepo {
    // references
    private static ManageDollsRepo mDollRepo; // instance of itself // single class instance
    private final DollDao dollDao; // DollDao object to access doll database

    // static method checks if instance of doll repository exists or not
    public static ManageDollsRepo getInstance(Context context) { // pass reference to activity
        // fragment that creates the repository
        if (mDollRepo == null) { // if DollRepo instance does not exist
            mDollRepo = new ManageDollsRepo(context); // invoke constructor
        }
        return mDollRepo; // return DollRepo instance // if instance exists
    }

    // Constructor
    private ManageDollsRepo(Context context) { // takes the context
        // DollDatabase instantiated
        // builder objects stores SQLite database in doll.db
        DollDatabase database = Room.databaseBuilder(context, DollDatabase.class, "doll.db")
                .allowMainThreadQueries() // allows the Room database methods to be called on the main thread
                .fallbackToDestructiveMigration()
                .build(); // creates and initializes the database

        dollDao = database.dollDao(); // call dollDAO to access doll table

        // When table is empty
        // Seeds five starter dolls // sample dataset
        if(getDolls().isEmpty()) {addStartedData();}
    }

    // building an array list of dolls
    // each doll is a data element
    public void addStartedData(){
        // initialize doll data elements
        // create Doll Data object to add doll information to
        // doll element named ala contains id, name, year, cost, model, body, wig, eyes
        DollDataModel ala = new DollDataModel(1,"Ala","2008","10,000 JPY",
                "No.F-588","Type 3","Brown","Brown",
                "Jun Planning","Pullip Family", "ala_2");

        // doll element named eclata contains id, name, year, cost, model, body, wig, eyes
        DollDataModel eclata = new DollDataModel(2,"Eclata","2020","22,000 JPY",
                "No.P-247","Type 4","Black","Gold",
                "Jun Planning", "Pullip Family", "eclata_2");

        // doll element named lala contains id, name, year, cost, model, body, wig, eyes
        DollDataModel lala = new DollDataModel(3,"Lala","2021","27,500 JPY",
                "No.P-278","Type 4","Pink","Pink",
                "Jun Planning", "Pullip Family", "lala_2");

        // doll element named moona contains id, name, year, cost, model, body, wig, eyes
        DollDataModel moona = new DollDataModel(4,"Moona","2023","30,000 JPY",
                "No.P-314","Type 4","Blonde","Pink",
                "Jun Planning", "Pullip Family", "moona_2");

        // doll element named sacralita contains id, name, year, cost, model, body, wig, eyes
        DollDataModel sacralita = new DollDataModel(5,"Sacralita","2004","8,000 JPY",
                "No.F-525","Type 2","Brown","Brown",
                "Jun Planning", "Pullip Family", "sacralita_2");

        dollDao.addDoll(ala); // append doll element ala to dolls database
        dollDao.addDoll(eclata); // append doll element eclata to dolls database
        dollDao.addDoll(lala); // append doll element lala to dolls database
        dollDao.addDoll(moona); // append doll element moona to dolls database
        dollDao.addDoll(sacralita); // append doll element sacralita to dolls database
    }

    // Get all Dolls // Wrapper
    public List<DollDataModel> getDolls() {
        return dollDao.getDolls(); // get dolls from database
    }

    // Get Doll when passing id // Wrapper
    public DollDataModel getDoll(long id) {
        return dollDao.getDoll(id); // get doll from database
    }

    // Passes doll model to DAO's @Insert // returns id Room assigned to new row added
    public long newDoll(DollDataModel model) {
        return dollDao.addDoll(model);
    }

    // Passes doll model to DAO's @Update // returns number of rows changes
    public int updateDoll(DollDataModel model) { return dollDao.update(model);}

    // Passes doll model to DAO's @Delete // returns number of rows removed
    public int delete(DollDataModel model) { return dollDao.delete(model);}
}// end class
