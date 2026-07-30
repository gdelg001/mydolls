package edu.delgado.mydollcollectionapp;

// Author:Geraldine Delgado
// Date:7/26/2026
// Phone: Pixel 6 API 32
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Class Model for Doll Collection
@Entity // class annotation // defines keys & values of database table
public class DollDataModel {
    // Doll Table
    @PrimaryKey(autoGenerate = true) // designates doll id as primary key field for table
    // automatically generate id in order
    @ColumnInfo(name = "doll_id") // column name for doll id field
    private long doll_id; // uniquely identifies the doll

    @NonNull // doll name field should not be null
    @ColumnInfo(name = "doll_name") // column name for doll name field
    private String doll_name; // variable to hold doll name

    @ColumnInfo(name = "doll_year") // column name for doll year field
    private String doll_year; // variable to hold year of release

    @ColumnInfo(name = "doll_cost") // column name for doll cost field
    private String doll_cost; // variable to hold cost of doll

    @ColumnInfo(name = "doll_model") // column name for doll model field
    private String doll_model; // variable to hold model tag

    @ColumnInfo(name = "doll_body") // column name for doll body field
    private String doll_body; // variable to hold body type

    @ColumnInfo(name = "doll_wig") // column name for doll wig field
    private String doll_wig; // variable to hold wig color

    @ColumnInfo(name = "eyes") // column name for doll eyes field
    private String doll_eyes; // variable to hold eye color

    @ColumnInfo(name = "doll_company") //column name for doll company field
    private String doll_company; // variable holds company

    @ColumnInfo(name = "doll_brand") //column name for doll brand field
    private String doll_brand; // variable holds brand

    @ColumnInfo(name = "doll_image") //column name for doll image field
    private String doll_image; // variable holds doll image

    // class constructor
    // pass arguments: id, name, year, cost, model, body type, wig color, eye color,
    // company, brand, image
    public DollDataModel(long doll_id, String doll_name, String doll_year, String doll_cost,
                         String doll_model, String doll_body, String doll_wig, String doll_eyes,
                         String doll_company, String doll_brand, String doll_image) {
        //assign arguments passed to variables
        this.doll_id = doll_id; // assign doll id
        this.doll_name = doll_name; // assign doll name
        this.doll_year = doll_year; // assign  doll year
        this.doll_cost = doll_cost; // assign doll cost
        this.doll_model = doll_model; // assign doll model
        this.doll_body = doll_body; // assign doll body
        this.doll_wig = doll_wig; // assign doll wig
        this.doll_eyes = doll_eyes; // assign doll eyes
        this.doll_company = doll_company; // assign doll company
        this.doll_brand = doll_brand; // assign doll brand
        this.doll_image = doll_image; // assign doll image
    }

    // getter or accessor that returns doll id value
    public long getDoll_id() {
        return doll_id;
    }

    // setter or mutator that sets the doll id property
    public void setDoll_id(long doll_id) {
        this.doll_id = doll_id;
    }

    // getter or accessor that returns doll name value
    public String getDoll_name() {
        return doll_name;
    }

    // setter or mutator that sets the doll name property
    public void setDoll_name(String doll_name) {
        this.doll_name = doll_name;
    }

    // getter or accessor that returns doll year value
    public String getDoll_year() {
        return doll_year;
    }

    // setter or mutator that sets the doll year property
    public void setDoll_year(String doll_year) {
        this.doll_year = doll_year;
    }

    // getter or accessor that returns doll cost value
    public String getDoll_cost() {
        return doll_cost;
    }

    // setter or mutator that sets the doll cost property
    public void setDoll_cost(String doll_cost) {
        this.doll_cost = doll_cost;
    }

    // getter or accessor that returns doll model value
    public String getDoll_model() {
        return doll_model;
    }

    // setter or mutator that sets the doll model property
    public void setDoll_model(String doll_model) {
        this.doll_model = doll_model;
    }

    // getter or accessor that returns doll body value
    public String getDoll_body() {
        return doll_body;
    }

    // setter or mutator that sets the doll body property
    public void setDoll_body(String doll_body) {
        this.doll_body = doll_body;
    }

    // getter or accessor that returns doll wig value
    public String getDoll_wig() {
        return doll_wig;
    }

    // setter or mutator that sets the doll wig property
    public void setDoll_wig(String doll_wig) {
        this.doll_wig = doll_wig;
    }

    // getter or accessor that returns doll eye value
    public String getDoll_eyes() {
        return doll_eyes;
    }

    // setter or mutator that sets the doll eye property
    public void setDoll_eyes(String doll_eyes) {
        this.doll_eyes = doll_eyes;
    }

    // getter that returns doll company value
    public String getDoll_company() {
        return doll_company;
    }

    // setter sets doll company property
    public void setDoll_company(String doll_company) {
        this.doll_company = doll_company;
    }

    // getter returns doll brand value
    public String getDoll_brand() {
        return doll_brand;
    }

    // setter sets doll brand property
    public void setDoll_brand(String doll_brand) {
        this.doll_brand = doll_brand;
    }

    // getter returns doll image value
    public String getDoll_image() {
        return doll_image;
    }

    // setter sets doll image property
    public void setDoll_image(String doll_image) {
        this.doll_image = doll_image;
    }

}



