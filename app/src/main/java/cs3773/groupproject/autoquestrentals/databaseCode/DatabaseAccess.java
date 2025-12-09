package cs3773.groupproject.autoquestrentals.databaseCode;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.AccountStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.AdministratorStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.CompanyStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.CustomerStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.RentalStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.RepresentativeStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.ReservationStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.ReviewStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.databaseStructs.TransactionsStruct;
import cs3773.groupproject.autoquestrentals.databaseCode.DatabaseQueries;

/**
 * A class to open and access the database.
 */
public class DatabaseAccess {
    // FIXME: Test the methods
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    /**
     * Private constructor to prevent accidental instantiation.
     * @param context The application context.
     */
    public DatabaseAccess(Context context){
        this.openHelper = new DatabaseHelper(context);
    }

    /**
     * Returns the current DatabaseAccess instance.
     * Creates instance if one is not already created.
     * @param context The application context.
     * @return The DatabaseAccess instance.
     */
    public static DatabaseAccess getInstance(Context context) {
        if(instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Opens the database in writable mode.
     * Allows reading and writing.
     */
    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    /**
     * Closes the database, if database is open.
     */
    public void close(){
        if(db != null){
            this.db.close();
        }
    }

    /**
     * Deletes a company from the Company table of the database.
     * Updates customer data in Customer table to reflect this deletion.
     * @param company The id of the company to delete.
     */
    public void DeleteSingleCompany(int company){
        db.execSQL(DatabaseQueries.DELETE_COMPANY + company);
        db.execSQL(DatabaseQueries.UPDATE_CUSTOMER_COMPANY + company);
    }

    /**
     * Deletes a review from the Review table of the database.
     * @param cus The customer whose review is to be deleted.
     * @param rental The rental that was the target of the review.
     */
    public void DeleteReview(int cus, int rental){
        db.execSQL(DatabaseQueries.DELETE_REVIEW_CUSTOMER + cus + " AND " + DatabaseContract.ReviewTable.RENTAL_ID_COLUMN + "=" + rental);
    }

    /**
     * Deletes a reservation from the Reservation table in the database.
     * @param cus The id of the customer whose reservation is to be deleted.
     */
    public void DeleteReservation(int cus){
        db.execSQL(DatabaseQueries.DELETE_RESERVATION_CUSTOMER + cus);
    }

    /**
     * Deletes a transaction from the Transactions table in the database.
     * @param trans The id of the transaction to be deleted.
     */
    public void DeleteTransaction(int trans){
        db.execSQL(DatabaseQueries.DELETE_TRANSACTION_BYID + trans);
    }

    /**
     * Deletes an account from the Account table in the database.
     * @param account The id of the account to be deleted.
     */
    public void DeleteAccount(int account){
        db.execSQL(DatabaseQueries.DELETE_ACCOUNT + account);
    }

    /**
     * Deletes a representative from the Representative table in the database.
     * Also deletes the rep's account data in the Account table.
     * @param rep The id of the representative to delete.
     */
    public void DeleteRepresentativeData(int rep){
        RepresentativeStruct repData = SearchForRepresentativeByID(rep);
        db.execSQL(DatabaseQueries.DELETE_REPRESENTATIVE + rep);
        DeleteAccount(repData.Acc_ID);
    }

    /**
     * Deletes an administrator from the Administrator table in the database.
     * Also deletes the admin's account data in the Account table.
     * @param admin The id of the administrator to delete.
     */
    public void DeleteAdminData(int admin){
        AdministratorStruct adminData = SearchForAdminByID(admin);
        db.execSQL(DatabaseQueries.DELETE_ADMINISTRATOR + admin);
        DeleteAccount(adminData.Acc_ID);
    }

    /**
     * Deletes a customer from the Customer table in the database.
     * Also deletes the customer data in Reservation table.
     * Also deletes the customer data in Review table.
     * Also deletes the customer data in Transactions table.
     * Also deletes the customer data in Account table.
     * @param cus The if the customer to delete.
     */
    public void DeleteCustomerData(int cus){
        CustomerStruct customerData = SearchForCustomerByID(cus);
        db.execSQL(DatabaseQueries.DELETE_CUSTOMER + cus);
        DeleteAccount(customerData.Acc_ID);
        DeleteReservation(cus);
        db.execSQL(DatabaseQueries.DELETE_REVIEW_CUSTOMER + cus);
        db.execSQL(DatabaseQueries.DELETE_TRANSACTION_CUSTOMER + cus);
    }

    /**
     * Deletes a rental vehicle from the Rental table in the database.
     * Also deletes the rental data in Transactions table.
     * Also deletes the rental data in Review table.
     * Also deletes the rental data in Reservation table.
     * @param rental The id of the rental vehicle to delete.
     */
    public void DeleteRentalData(int rental){
        db.execSQL(DatabaseQueries.DELETE_RENTAL + rental);
        db.execSQL(DatabaseQueries.DELETE_TRANSACTION_RENTAL + rental);
        db.execSQL(DatabaseQueries.DELETE_REVIEW_RENTAL + rental);
        db.execSQL(DatabaseQueries.DELETE_RESERVATION_RENTAL + rental);
    }

    /**
     * Adds a new company into the database.
     * @param id The new company's id number.
     * @param name The new company's name.
     * @param phone The new company's phone number.
     * @param email THe new company's email.
     */
    public void AddCompanyData(int id, String name, String phone, String email) {
        if( id <= 0 || name == null || phone == null || email == null ){
            Log.d("COMPANY:", "id <= 0, or necessary information not provided, could not add company");
            return;
        }
        if( CheckCompanyData(id, name) ){
            Log.d("COMPANY:", "could not add company, already exists");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CompanyTable.ID_COLUMN, id);
        values.put(DatabaseContract.CompanyTable.NAME_COLUMN, name);
        values.put(DatabaseContract.CompanyTable.PHONE_NUM_COLUMN, phone);
        values.put(DatabaseContract.CompanyTable.EMAIL_COLUMN, email);
        db.insert(DatabaseContract.CompanyTable.TABLE_NAME, null, values);
    }

    /**
     * Checks if a company being added already exists in database.
     * @param id The id to check.
     * @param name The name to check.
     * @return true if company exists, false otherwise.
     */
    public boolean CheckCompanyData(int id, String name){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_COMPANY, null);
        while(c.moveToNext()){
            if( id == c.getInt(0) || name.equals(c.getString(1)) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches all of the companies in the database.
     * @return An ArrayList containing all companies in the database.
     */
    public ArrayList<CompanyStruct> FetchCompaniesInDatabase(){
        c = null;
        // Log.d("TEST", "in fetch company data");
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_COMPANY, null);

        ArrayList<CompanyStruct> arrCompany = new ArrayList<>();

        while(c.moveToNext()){
            // get each column individually, assign to object
            // then add object to an ArrayList
            CompanyStruct company = new CompanyStruct();
            company.Company_ID = c.getInt(0); // get company id
            company.Company_Name = c.getString(1); // get company name
            company.Company_Phone = c.getString(2); // get company phone
            company.Company_Email = c.getString(3); // get company email
            arrCompany.add(company);
        }

        return arrCompany;
    }

    /**
     * Searches for a single company in the database.
     * Will return a CompanyStruct containing data on the specified company
     * if found. If company could not be found, it will return a CompanyStruct
     * set to null.
     * @param id The id of the company being searched for.
     * @return A CompanyStruct.
     */
    public CompanyStruct SearchForCompany(int id){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_COMPANY, null);
        CompanyStruct company = null;
        while(c.moveToNext()){
            if( id == c.getInt(0) ){
                company = new CompanyStruct();
                company.Company_ID = c.getInt(0);
                company.Company_Name = c.getString(1);
                company.Company_Phone = c.getString(2);
                company.Company_Email = c.getString(3);
            }
        }
        return company;
    }

    /**
     * Adds a new rental review to the database.
     * @param cus The customer leaving the review.
     * @param rental The rental the review is for.
     * @param rating The review's rating.
     * @param text The text of the review.
     */
    public void AddReviewData(int rental, int cus, int rating, String text){
        if( cus <= 0 || rental <= 0 || rating <= 0 || text == null ){
            Log.d("REVIEW:", "cus, rental, or rating <= 0, or text is null; could not add review");
            return;
        }
        if( CheckReviewData(cus, rental) ){
            Log.d("REVIEW:", "could not add review, customer already left review on rental");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ReviewTable.RENTAL_ID_COLUMN, rental);
        values.put(DatabaseContract.ReviewTable.CUSTOMER_ID_COLUMN, cus);
        values.put(DatabaseContract.ReviewTable.RATING_COLUMN, rating);
        values.put(DatabaseContract.ReviewTable.REVIEW_TEXT_COLUMN, text);
        db.insert(DatabaseContract.ReviewTable.TABLE_NAME, null, values);
    }

    /**
     * Checks if customer already left review on vehicle.
     * @param rental The vehicle the review is for.
     * @param cus The customer leaving the review.
     * @return true if review was already left, false otherwise
     */
    public boolean CheckReviewData(int rental, int cus){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_REVIEW, null);
        while(c.moveToNext()){
            if( rental == c.getInt(0) && cus == c.getInt(1) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches all reviews stored in the database.
     * @return ArrayList containing data on all the reviews.
     */
    public ArrayList<ReviewStruct> FetchReviewsInDatabase(){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_REVIEW, null);
        ArrayList<ReviewStruct> arrReviews = new ArrayList<>();
        while(c.moveToNext()){
            ReviewStruct review = new ReviewStruct();
            review.Rent_ID = c.getInt(0);
            review.Cus_ID = c.getInt(1);
            review.Rating = c.getInt(2);
            review.Review_Text = c.getString(3);
            arrReviews.add(review);
        }
        return arrReviews;
    }

    /**
     * Searches for a single review in the database.
     * If the customer's review is found, returns ReviewStruct with review data.
     * If the customer's review is not found, returns ReviewStruct set to null.
     * @param cus The customer.
     * @param rental The rental.
     * @return A ReviewStruct.
     */
    public ReviewStruct SearchForReview(int rental, int cus){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_REVIEW, null);
        ReviewStruct review = null;
        while(c.moveToNext()){
            if( rental == c.getInt(0) && cus == c.getInt(1) ){
                review = new ReviewStruct();
                review.Rent_ID = c.getInt(0);
                review.Cus_ID = c.getInt(1);
                review.Rating = c.getInt(2);
                review.Review_Text = c.getString(3);
            }
        }
        return review;
    }

    /**
     * Gets all reviews for a specified rental vehicle,
     * and returns them as an ArrayList of ReviewStructs.
     * @param rental The rental id.
     * @return An ArrayList of ReviewStruct containing the reviews for a rental.
     */
    public ArrayList<ReviewStruct> GetRentalReviews(int rental){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_REVIEW, null);
        ArrayList<ReviewStruct> arrReviews = new ArrayList<>();
        while(c.moveToNext()){
            if( rental == c.getInt(0) ){
                ReviewStruct review = new ReviewStruct();
                review.Rent_ID = c.getInt(0);
                review.Cus_ID = c.getInt(1);
                review.Rating = c.getInt(2);
                review.Review_Text = c.getString(3);
                arrReviews.add(review);
            }
        }
        return arrReviews;
    }

    /**
     * Adds a new account to the database.
     * @param id The new account's id.
     * @param email The new account's email address.
     * @param phone The new account's phone number.
     * @param username The new account's username.
     * @param password The new account's password.
     */
    public void AddAccountData(int id, String email, String phone, String username, String password){
        // FIXME: See PasswordEncode class for hashing a password.
        // FIXME: make sure password is encoded / hashed before providing it to this method
        if( id <= 0 || email == null || phone == null || username == null || password == null ){
            Log.d("ACCOUNT:", "could not create account, missing necessary information");
            return;
        }
        if( CheckAccountData(id, email, username) ){
            Log.d("ACCOUNT:", "could not create account, already exists or email or username already used");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AccountTable.ID_COLUMN, id);
        values.put(DatabaseContract.AccountTable.EMAIL_COLUMN, email);
        values.put(DatabaseContract.AccountTable.PHONE_NUM_COLUMN, phone);
        values.put(DatabaseContract.AccountTable.USERNAME_COLUMN, username);
        values.put(DatabaseContract.AccountTable.PASSWORD_COLUMN, password);
        db.insert(DatabaseContract.AccountTable.TABLE_NAME, null, values);
    }

    /**
     * Checks account data being added to database.
     * @param id The account id.
     * @param email The account email.
     * @param username The account username.
     * @return true if account data already exists, false otherwise.
     */
    public boolean CheckAccountData(int id, String email, String username){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_ACCOUNT, null);
        while(c.moveToNext()){
            if( id == c.getInt(0) || email.equals(c.getString(1)) || username.equals(c.getString(3)) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches all accounts stored in database.
     * @return ArrayList containing data on all accounts.
     */
    public ArrayList<AccountStruct> FetchAccountsInDatabase(){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_ACCOUNT, null);
        ArrayList<AccountStruct> arrAccounts = new ArrayList<>();
        while(c.moveToNext()){
            AccountStruct account = new AccountStruct();
            account.Acc_ID = c.getInt(0);
            account.Acc_Email = c.getString(1);
            account.Acc_PhoneNum = c.getString(2);
            account.username = c.getString(3);
            account.password = c.getString(4);
            arrAccounts.add(account);
        }
        return arrAccounts;
    }

    /**
     * Search for a single account by login info.
     * If found, returns AccountStruct containing account data.
     * If not found, returns AccountStruct set to null.
     * @param username The account username. Can be null if email provided.
     * @param password The account password. Must be provided.
     * @param email The account email. Can be null if username provided.
     * @return An AccountStruct.
     */
    public AccountStruct SearchForAccountByLogin(String username, String password, String email){
        // FIXME: factor in password hashing
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_ACCOUNT, null);
        AccountStruct account = null;
        while(c.moveToNext()){
            if( username == null && email != null ){
                if( email.equals(c.getString(1)) && password.equals(c.getString(4)) ){
                    account = new AccountStruct();
                    account.Acc_ID = c.getInt(0);
                    account.Acc_Email = c.getString(1);
                    account.Acc_PhoneNum = c.getString(2);
                    account.username = c.getString(3);
                    account.password = c.getString(4);
                }
            } else if(username != null && email == null ){
                if( username.equals(c.getString(3)) && password.equals(c.getString(4)) ){
                    account = new AccountStruct();
                    account.Acc_ID = c.getInt(0);
                    account.Acc_Email = c.getString(1);
                    account.Acc_PhoneNum = c.getString(2);
                    account.username = c.getString(3);
                    account.password = c.getString(4);
                }
            }
        }
        return account;
    }

    /**
     * Adds a new administrator to the database.
     * @param admin The new admin's id.
     * @param last The new admin's last name.
     * @param first The new admin's first name.
     * @param account The new admin's account id.
     */
    public void AddAdministratorData(int admin, String last, String first, int account){
        if( admin <= 0 || last == null || first == null || account <= 0 ){
            Log.d("ADMINISTRATOR:", "could not add admin, missing necessary data");
            return;
        }
        if( CheckAdministratorData(admin, account) ){
            Log.d("ADMINISTRATOR:", "could not add admin, already exists or has account");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AdministratorTable.ID_COLUMN, admin);
        values.put(DatabaseContract.AdministratorTable.LAST_NAME_COLUMN, last);
        values.put(DatabaseContract.AdministratorTable.FIRST_NAME_COLUMN, first);
        values.put(DatabaseContract.AdministratorTable.ACCOUNT_ID_COLUMN, account);
        db.insert(DatabaseContract.AdministratorTable.TABLE_NAME, null, values);
    }

    /**
     * Checks the administrator data that is being added to the database.
     * Does not check the name since some people share names.
     * @param admin The admin id.
     * @param account The account id.
     * @return true if admin or account id already exists, false otherwise
     */
    public boolean CheckAdministratorData(int admin, int account){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_ADMIN, null);
        while(c.moveToNext()){
            if( admin == c.getInt(0) || account == c.getInt(3) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches data on all Administrators stored in the database.
     * @return ArrayList containing data on all the Administrators.
     */
    public ArrayList<AdministratorStruct> FetchAdministratorsInDatabase(){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_ADMIN, null);
        ArrayList<AdministratorStruct> arrAdmins = new ArrayList<>();
        while(c.moveToNext()){
            AdministratorStruct admin = new AdministratorStruct();
            admin.Admin_ID = c.getInt(0);
            admin.Admin_LName = c.getString(1);
            admin.Admin_FName = c.getString(2);
            admin.Acc_ID = c.getInt(3);
            arrAdmins.add(admin);
        }
        return arrAdmins;
    }

    /**
     * Search for Administrator by Acc_ID.
     * If found, returns AdministratorStruct containing admin data.
     * If not found, returns AdministratorStruct set to null.
     * @param account The account id.
     * @return An AdministratorStruct.
     */
    public AdministratorStruct SearchForAdminByAccount(int account){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_ADMIN, null);
        AdministratorStruct admin = null;
        while(c.moveToNext()){
            if( account == c.getInt(3) ){
                admin = new AdministratorStruct();
                admin.Admin_ID = c.getInt(0);
                admin.Admin_LName = c.getString(1);
                admin.Admin_FName = c.getString(2);
                admin.Acc_ID = c.getInt(3);
            }
        }
        return admin;
    }

    /**
     * Search for Administrator by Admin_ID.
     * If found, returns AdministratorStruct containing admin data.
     * If not found, returns AdministratorStruct set to null.
     * @param id The admin id.
     * @return An AdministratorStruct.
     */
    public AdministratorStruct SearchForAdminByID(int id){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_ADMIN, null);
        AdministratorStruct admin = null;
        while(c.moveToNext()){
            if( id == c.getInt(0) ){
                admin = new AdministratorStruct();
                admin.Admin_ID = c.getInt(0);
                admin.Admin_LName = c.getString(1);
                admin.Admin_FName = c.getString(2);
                admin.Acc_ID = c.getInt(3);
            }
        }
        return admin;
    }

    /**
     * Adds a new customer to the database.
     * Payment and company can be null.
     * @param cus The customer id.
     * @param last The customer's last name.
     * @param first The customer's first name.
     * @param payment The customer's payment info.
     * @param account The customer's account id.
     * @param company The customer's company id. 0 for no company.
     */
    public void AddCustomerData(int cus, String last, String first, String payment, int account, int company){
        if( cus <= 0 || last == null || first == null || account <= 0 ){
            Log.d("CUSTOMER:", "could not add customer, missing necessary information");
            return;
        }
        if( CheckCustomerData(cus, account) ){
            Log.d("CUSTOMER:", "could not add customer, already exists");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.CustomerTable.ID_COLUMN, cus);
        values.put(DatabaseContract.CustomerTable.LAST_NAME_COLUMN, last);
        values.put(DatabaseContract.CustomerTable.FIRST_NAME_COLUMN, first);
        values.put(DatabaseContract.CustomerTable.PAYMENT_INFO_COLUMN, payment);
        values.put(DatabaseContract.CustomerTable.ACCOUNT_ID_COLUMN, account);
        values.put(DatabaseContract.CustomerTable.COMPANY_ID_COLUMN, company);
        db.insert(DatabaseContract.CustomerTable.TABLE_NAME, null, values);
    }

    /**
     * Checks the customer data being added.
     * Does not check customer name or payment info.
     * Does not check company. Customers may share name
     * or companies. Payment is something not worth
     * worrying about at the moment.
     * @param cus The customer id.
     * @param account The account id.
     * @return true if customer or account id already exits, false otherwise.
     */
    public boolean CheckCustomerData(int cus, int account){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_CUSTOMER, null);
        while(c.moveToNext()){
            if( cus == c.getInt(0) || account == c.getInt(4) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches all customers stored in the database.
     * @return ArrayList containing data on the customers in the database.
     */
    public ArrayList<CustomerStruct> FetchCustomersInDatabase(){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_CUSTOMER, null);
        ArrayList<CustomerStruct> arrCustomers = new ArrayList<>();
        while(c.moveToNext()){
            CustomerStruct customer = new CustomerStruct();
            customer.Cus_ID = c.getInt(0);
            customer.Cus_LName = c.getString(1);
            customer.Cus_FName = c.getString(2);
            customer.Payment_Info = c.getString(3);
            customer.Acc_ID = c.getInt(4);
            customer.Company_ID = c.getInt(5);
            arrCustomers.add(customer);
        }
        return arrCustomers;
    }

    /**
     * Search for customer by account id.
     * If found, returns CustomerStruct containing data on the customer.
     * If not found, returns CustomerStruct set to null.
     * @param account The account id.
     * @return A CustomerStruct.
     */
    public CustomerStruct SearchForCustomerByAccount(int account){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_CUSTOMER, null);
        CustomerStruct customer = null;
        while(c.moveToNext()){
            if( account == c.getInt(4) ){
                customer = new CustomerStruct();
                customer.Cus_ID = c.getInt(0);
                customer.Cus_LName = c.getString(1);
                customer.Cus_FName = c.getString(2);
                customer.Payment_Info = c.getString(3);
                customer.Acc_ID = c.getInt(4);
                customer.Company_ID = c.getInt(5);
            }
        }
        return customer;
    }

    /**
     * Search for customer by customer id.
     * If found, returns CustomerStruct containing customer data.
     * If not found, returns CustomerStruct set to null.
     * @param id The customer id.
     * @return A CustomerStruct.
     */
    public CustomerStruct SearchForCustomerByID(int id){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_CUSTOMER, null);
        CustomerStruct customer = null;
        while(c.moveToNext()){
            if( id == c.getInt(0) ){
                customer = new CustomerStruct();
                customer.Cus_ID = c.getInt(0);
                customer.Cus_LName = c.getString(1);
                customer.Cus_FName = c.getString(2);
                customer.Payment_Info = c.getString(3);
                customer.Acc_ID = c.getInt(4);
                customer.Company_ID = c.getInt(5);
            }
        }
        return customer;
    }

    /**
     * Adds a new rental to the database.
     * @param rental The rental id.
     * @param price The price of the rental. Cannot be <= 0.
     * @param rentType The vehicle type of the rental.
     * @param desc The description of the rental.
     * @param history The vehicle history, for accidents. May be null.
     * @param features Features that the rental comes with. (CSV)
     */
    public void AddRentalData(int rental, int price, String rentType, String desc, String history, String features){
        if( rental <= 0 || price <= 0 || rentType == null || desc == null || features == null ){
            Log.d("RENTAL:", "could not add rental, missing necessary data");
            return;
        }
        if( CheckRentalData(rental) ){
            Log.d("RENTAL:", "could not add rental, already exists");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.RentalTable.ID_COLUMN, rental);
        values.put(DatabaseContract.RentalTable.PRICE_COLUMN, price);
        values.put(DatabaseContract.RentalTable.TYPE_COLUMN, rentType);
        values.put(DatabaseContract.RentalTable.DESCRIPTION_COLUMN, desc);
        values.put(DatabaseContract.RentalTable.HISTORY_COLUMN, history);
        values.put(DatabaseContract.RentalTable.FEATURES_COLUMN, features);
        db.insert(DatabaseContract.RentalTable.TABLE_NAME, null, values);
    }

    /**
     * Checks the rental data being added.
     * Only checks id, as service may offer multiple vehicles of the same type.
     * @param rental The rental id.
     * @return true if rental already exists, false otherwise.
     */
    public boolean CheckRentalData(int rental){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_RENTAL, null);
        while(c.moveToNext()){
            if( rental == c.getInt(0) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches all rental vehicle data stored in the database.
     * @return ArrayList containing data on all rental vehicles.
     */
    public ArrayList<RentalStruct> FetchRentalsInDatabase(){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_RENTAL, null);
        ArrayList<RentalStruct> arrRentals = new ArrayList<>();
        while(c.moveToNext()){
            RentalStruct rental = new RentalStruct();
            rental.Rent_ID = c.getInt(0);
            rental.Rent_Price = c.getInt(1);
            rental.Rent_Type = c.getString(2);
            rental.Rent_Description = c.getString(3);
            rental.Rent_History = c.getString(4);
            rental.Rent_Features = c.getString(5);
            arrRentals.add(rental);
        }
        return arrRentals;
    }

    /**
     * Search for rental in database.
     * If found, returns RentalStruct containing data on rental.
     * If not found, returns RentalStruct set to null.
     * @param id The rental id.
     * @return A RentalStruct.
     */
    public RentalStruct SearchForRental(int id){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_RENTAL, null);
        RentalStruct rental = null;
        while(c.moveToNext()){
            if( id == c.getInt(0) ){
                rental = new RentalStruct();
                rental.Rent_ID = c.getInt(0);
                rental.Rent_Price = c.getInt(1);
                rental.Rent_Type = c.getString(2);
                rental.Rent_Description = c.getString(3);
                rental.Rent_History = c.getString(4);
                rental.Rent_Features = c.getString(5);
            }
        }
        return rental;
    }

    /**
     * Adds a new representative to the database.
     * @param rep The representative id.
     * @param last The rep's last name.
     * @param first The rep's first name.
     * @param account The rep's account id.
     */
    public void AddRepresentativeData(int rep, String last, String first, int account){
        if( rep <= 0 || last == null || first == null || account <= 0 ){
            Log.d("REPRESENTATIVE:", "could not add representative, missing necessary information");
            return;
        }
        if( CheckRepresentativeData(rep, account) ){
            Log.d("REPRESENTATIVE:", "could not add representative, rep or account id already exists");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.RepresentativeTable.ID_COLUMN, rep);
        values.put(DatabaseContract.RepresentativeTable.LAST_NAME_COLUMN, last);
        values.put(DatabaseContract.RepresentativeTable.FIRST_NAME_COLUMN, first);
        values.put(DatabaseContract.RepresentativeTable.ACCOUNT_ID_COLUMN, account);
        db.insert(DatabaseContract.RepresentativeTable.TABLE_NAME, null, values);
    }

    /**
     * Checks the representative data being added.
     * Only checks rep and account id, as reps may share names.
     * @param rep The representative id.
     * @param account The account id.
     * @return true if already exists, false otherwise.
     */
    public boolean CheckRepresentativeData(int rep, int account){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_REPRESENTATIVE, null);
        while(c.moveToNext()){
            if( rep == c.getInt(0) || account == c.getInt(3) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches all Representatives stored in the database.
     * @return An ArrayList containing data on all the representatives.
     */
    public ArrayList<RepresentativeStruct> FetchRepresentativesInDatabase(){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_REPRESENTATIVE, null);
        ArrayList<RepresentativeStruct> arrReps = new ArrayList<>();
        while(c.moveToNext()){
            RepresentativeStruct rep = new RepresentativeStruct();
            rep.Rep_ID = c.getInt(0);
            rep.Rep_LName = c.getString(1);
            rep.Rep_FName = c.getString(2);
            rep.Acc_ID = c.getInt(3);
            arrReps.add(rep);
        }
        return arrReps;
    }

    /**
     * Search for representative by account id.
     * If found, returns RepresentativeStruct containing data on representative.
     * If not found, returns RepresentativeStruct set to null.
     * @param account The account id.
     * @return A RepresentativeStruct.
     */
    public RepresentativeStruct SearchForRepresentativeByAccount(int account){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_REPRESENTATIVE, null);
        RepresentativeStruct rep = null;
        while(c.moveToNext()){
            if( account == c.getInt(3) ){
                rep = new RepresentativeStruct();
                rep.Rep_ID = c.getInt(0);
                rep.Rep_LName = c.getString(1);
                rep.Rep_FName = c.getString(2);
                rep.Acc_ID = c.getInt(3);
            }
        }
        return rep;
    }

    /**
     * Search for representative by rep id.
     * If found, returns RepresentativeStruct containing date on representative.
     * If not found, returns RepresentativeStruct set to null.
     * @param id The rep's id.
     * @return A RepresentativeStruct.
     */
    public RepresentativeStruct SearchForRepresentativeByID(int id){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_REPRESENTATIVE, null);
        RepresentativeStruct rep = null;
        while(c.moveToNext()){
            if( id == c.getInt(0) ){
                rep = new RepresentativeStruct();
                rep.Rep_ID = c.getInt(0);
                rep.Rep_LName = c.getString(1);
                rep.Rep_FName = c.getString(2);
                rep.Acc_ID = c.getInt(3);
            }
        }
        return rep;
    }

    /**
     * Adds a new reservation to the database.
     * @param cus The customer id. The one reserving.
     * @param rental The rental id. THe vehicle being reserved.
     */
    public void AddReservationData(int cus, int rental){
        if( cus <= 0 || rental <= 0 ){
            Log.d("RESERVATION:", "could not add reservation, customer or rental id <= 0");
            return;
        }
        if( CheckReservationData(cus, rental) ){
            Log.d("RESERVATION:", "could not add reservation, customer or vehicle already reserved");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ReservationTable.CUSTOMER_ID_COLUMN, cus);
        values.put(DatabaseContract.ReservationTable.RENTAL_ID_COLUMN, rental);
        db.insert(DatabaseContract.ReservationTable.TABLE_NAME, null, values);
    }

    /**
     * Checks the reservation being added.
     * @param cus The customer id.
     * @param rental The rental id.
     * @return true if already reserved, false otherwise.
     */
    public boolean CheckReservationData(int cus, int rental){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_RESERVATION, null);
        while(c.moveToNext()){
            if( cus == c.getInt(0) || rental == c.getInt(1) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches all reservations stored in the database.
     * @return An ArrayList containing data on all reservations.
     */
    public ArrayList<ReservationStruct> FetchReservationsInDatabase(){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_RESERVATION, null);
        ArrayList<ReservationStruct> arrReserves = new ArrayList<>();
        while(c.moveToNext()){
            ReservationStruct reservation = new ReservationStruct();
            reservation.Cus_ID = c.getInt(0);
            reservation.Rent_ID = c.getInt(1);
            arrReserves.add(reservation);
        }
        return arrReserves;
    }

    /**
     * Search for reservation in the database.
     * If found, returns ReservationStruct containing data on the reservation.
     * If not found, returns ReservationStruct set to null.
     * @param cus The customer id.
     * @param rental The rental id.
     * @return A ReservationStruct.
     */
    public ReservationStruct SearchForReservation(int cus, int rental){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_RESERVATION, null);
        ReservationStruct reservation = null;
        while(c.moveToNext()){
            if( cus == c.getInt(0) && rental == c.getInt(1) ){
                reservation = new ReservationStruct();
                reservation.Cus_ID = c.getInt(0);
                reservation.Rent_ID = c.getInt(1);
            }
        }
        return reservation;
    }

    /**
     * Adds a new transaction to the database.
     * @param trans The id of the transaction.
     * @param cus The customer id. The one making the purchase.
     * @param rental The rental id. The vehicle being rented.
     * @param dateRented The date the vehicle was rented.
     * @param dateReturned The date the vehicle was returned. Can be null.
     * @param paid The amount the customer paid.
     */
    public void AddTransactionsData(int trans, int cus, int rental, String dateRented, String dateReturned, int paid){
        if( trans <= 0 || cus <= 0 || rental <= 0 || dateRented == null ){
            Log.d("TRANSACTIONS:", "could not add transaction, missing necessary information");
            return;
        }
        if( dateReturned == null && paid > 0 ){
            Log.d("TRANSACTIONS:", "could not add transaction, cannot have amount paid if rental is not returned yet");
        }
        if( CheckTransactionsData(trans) ){
            Log.d("TRANSACTIONS:", "could not add transaction, trans id already exists");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TransactionTable.ID_COLUMN, trans);
        values.put(DatabaseContract.TransactionTable.CUSTOMER_ID_COLUMN, cus);
        values.put(DatabaseContract.TransactionTable.RENTAL_ID_COLUMN, rental);
        values.put(DatabaseContract.TransactionTable.DATE_RENTED_COLUMN, dateRented);
        values.put(DatabaseContract.TransactionTable.DATE_RETURNED_COLUMN, dateReturned);
        values.put(DatabaseContract.TransactionTable.AMOUNT_PAID_COLUMN, paid);
        db.insert(DatabaseContract.TransactionTable.TABLE_NAME, null, values);
    }

    /**
     * Checks transaction being added.
     * @param trans The transaction id.
     * @return true if trans id already exists, false otherwise.
     */
    public boolean CheckTransactionsData(int trans){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_TRANSACTIONS, null);
        while(c.moveToNext()){
            if( trans == c.getInt(0) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches all transactions stored in the database.
     * @return An ArrayList containing data on the all the transactions.
     */
    public ArrayList<TransactionsStruct> FetchTransactionsInDatabase(){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_TRANSACTIONS, null);
        ArrayList<TransactionsStruct> arrTrans = new ArrayList<>();
        while(c.moveToNext()){
            TransactionsStruct transaction = new TransactionsStruct();
            transaction.Trans_ID = c.getInt(0);
            transaction.Cus_ID = c.getInt(1);
            transaction.Rent_ID = c.getInt(2);
            transaction.Date_Rented = c.getString(3);
            transaction.Date_Returned = c.getString(4);
            transaction.Amount_Paid = c.getInt(5);
            arrTrans.add(transaction);
        }
        return arrTrans;
    }

    /**
     * Search for transaction by its id.
     * If found, returns TransactionsStruct containing data on transaction.
     * If not found, returns TransactionsStruct set to null.
     * @param id The transaction id.
     * @return A TransactionsStruct.
     */
    public TransactionsStruct SearchForTransactionByID(int id){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_TRANSACTIONS, null);
        TransactionsStruct transaction = null;
        while(c.moveToNext()){
            if( id == c.getInt(0) ){
                transaction = new TransactionsStruct();
                transaction.Trans_ID = c.getInt(0);
                transaction.Cus_ID = c.getInt(1);
                transaction.Rent_ID = c.getInt(2);
                transaction.Date_Rented = c.getString(3);
                transaction.Date_Returned = c.getString(4);
                transaction.Amount_Paid = c.getInt(5);
            }
        }
        return transaction;
    }

    /**
     * Searches for a transaction by a specified customer and rental.
     * If found, returns TransactionsStruct containing data on transaction.
     * If not found, returns TransactionsStruct set to null.
     * @param cus The customer id.
     * @param rental The rental id.
     * @return A TransactionsStruct.
     */
    public TransactionsStruct SearchForTransactionByCustomerAndRental(int cus, int rental){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_TRANSACTIONS, null);
        TransactionsStruct transaction = null;
        while(c.moveToNext()){
            if( cus == c.getInt(1) && rental == c.getInt(2) ){
                transaction = new TransactionsStruct();
                transaction.Trans_ID = c.getInt(0);
                transaction.Trans_ID = c.getInt(0);
                transaction.Cus_ID = c.getInt(1);
                transaction.Rent_ID = c.getInt(2);
                transaction.Date_Rented = c.getString(3);
                transaction.Date_Returned = c.getString(4);
                transaction.Amount_Paid = c.getInt(5);
            }
        }
        return transaction;
    }

    /**
     * Gets all transactions of a specified customer, and
     * returns an ArrayList of TransactionsStruct containing
     * data on the customer transactions.
     * Useful for viewing a customer's transaction history.
     * @param cus The customer id.
     * @return An ArrayList of TransactionsStruct.
     */
    public ArrayList<TransactionsStruct> GetAllCustomerTransactions(int cus){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_TRANSACTIONS, null);
        ArrayList<TransactionsStruct> arrTrans = new ArrayList<>();
        while(c.moveToNext()){
            if( cus == c.getInt(1) ){
                TransactionsStruct transaction = new TransactionsStruct();
                transaction.Trans_ID = c.getInt(0);
                transaction.Trans_ID = c.getInt(0);
                transaction.Cus_ID = c.getInt(1);
                transaction.Rent_ID = c.getInt(2);
                transaction.Date_Rented = c.getString(3);
                transaction.Date_Returned = c.getString(4);
                transaction.Amount_Paid = c.getInt(5);
                arrTrans.add(transaction);
            }
        }
        return arrTrans;
    }

    /**
     * Gets all transactions involving a specified rental vehicle.
     * Returns an ArrayList of TransactionsStruct containing data
     * on the rental vehicle transactions.
     * @param rental The rental id.
     * @return An ArrayList of TransactionsStruct.
     */
    public ArrayList<TransactionsStruct> GetAllRentalTransactions(int rental){
        c = null;
        c = db.rawQuery(DatabaseQueries.SELECT_ALL_TRANSACTIONS, null);
        ArrayList<TransactionsStruct> arrTrans = new ArrayList<>();
        while(c.moveToNext()){
            if( rental == c.getInt(2) ){
                TransactionsStruct transaction = new TransactionsStruct();
                transaction.Trans_ID = c.getInt(0);
                transaction.Trans_ID = c.getInt(0);
                transaction.Cus_ID = c.getInt(1);
                transaction.Rent_ID = c.getInt(2);
                transaction.Date_Rented = c.getString(3);
                transaction.Date_Returned = c.getString(4);
                transaction.Amount_Paid = c.getInt(5);
                arrTrans.add(transaction);
            }
        }
        return arrTrans;
    }
}
