package cs3773.groupproject.autoquestrentals.Model;

import androidx.annotation.NonNull;

// FIXME: CustomerStruct already exists in databaseCode. Stores information on customer. Can use database functions to get data on customer.
public class Customer {

    @NonNull
    private int customerID;

    private String firstName;
    private String middleName = ""; // NOTE: A customer's middle name is not stored in the database.
    private String lastName;
    private String email;
    private String password;
    private String title; //MR or MS NOTE: A customer's title is not stored in the database.

    public Customer(int customerID,
                    String firstName,  String middleName,      String lastName,
                    String email,      String password){

        this.customerID = customerID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public String toString(){

        return  "\n"+
                "CustomerID:        " + customerID + "\n" +
                "First Name:        " + firstName + "\n" +
                "Middle Name:       " + middleName + "\n" +
                "Last Name:         " + lastName + "\n" +
                "Email:             " + email + "\n" +
                "Password:          " + password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName(){
        return title + ". " + firstName + " " + lastName;
    }
}