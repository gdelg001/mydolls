package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

//access database records
@Dao // interface annotation // manipulates database entities
public interface DollDao {
    // designates database query

    // gets doll description data from the database
    // pass doll id to return doll description for one doll
    @Query("SELECT * FROM DollDataModel WHERE doll_id = :id") // select all the record from the DollDate table
    // where id is the one we're passing
    DollDataModel getDoll(long id); // gives back data for one doll

    // select all the fields from DollDataModel
    // table ordered by doll name // ignore the case
    @Query("SELECT * FROM DollDataModel ORDER BY doll_name COLLATE NOCASE")
    List<DollDataModel> getDolls();

    // insert new entity into the database
    // pass doll object
    // returns a number // id of number inserted
    @Insert(onConflict = OnConflictStrategy.REPLACE) // replace conflicting data
    long addDoll(DollDataModel doll);

    // update existing entry in database
    // pass doll object
    // returns 1 if completed successfully
    @Update
    int update(DollDataModel doll);

    // delete existing entry in database
    // pass doll object
    // returns 1 or 0 // if method is successful
    @Delete
    int delete(DollDataModel doll);

}
