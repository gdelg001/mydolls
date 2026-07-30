package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32
import androidx.room.Database;
import androidx.room.RoomDatabase;

// designates the Room database class
// pass the database entities
@Database(entities = {DollDataModel.class}, version = 2) // Doll data table

public abstract class DollDatabase extends RoomDatabase {

    // create abstract methods to return the DAO
    public abstract DollDao dollDao(); // give back DollDao object to access Doll database
}
