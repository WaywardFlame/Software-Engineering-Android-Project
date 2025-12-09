package cs3773.groupproject.autoquestrentals.databaseCode;

import android.provider.BaseColumns;

/**
 * A class called a 'Contract'.
 * Designed to store various constants related to
 * the names of the tables and table columns in the
 * database. Makes refactoring SQL code easier.
 */
public final class DatabaseContract {
    /**
     * The constructor for the class.
     * Set to private to prevent accidental instantiation.
     */
    private DatabaseContract() {}

    /**
     * Inner class called AccountTable.
     * Holds constants for the ACCOUNT database table.
     */
    public static class AccountTable implements BaseColumns {
        public static final String TABLE_NAME = "ACCOUNT";
        public static final String ID_COLUMN = "Acc_ID";
        public static final String EMAIL_COLUMN = "Acc_Email";
        public static final String PHONE_NUM_COLUMN = "Acc_PhoneNum";
        public static final String USERNAME_COLUMN = "username";
        public static final String PASSWORD_COLUMN = "password";
    }

    /**
     * Inner class called AdministratorTable.
     * Holds constants for the ADMINISTRATOR database table.
     */
    public static class AdministratorTable implements BaseColumns {
        public static final String TABLE_NAME = "ADMINISTRATOR";
        public static final String ID_COLUMN = "Admin_ID";
        public static final String LAST_NAME_COLUMN = "Admin_LName";
        public static final String FIRST_NAME_COLUMN = "Admin_FName";
        public static final String ACCOUNT_ID_COLUMN = "Acc_ID";
    }

    /**
     * Inner class called RepresentativeTable.
     * Holds constants for the REPRESENTATIVE database table.
     */
    public static class RepresentativeTable implements BaseColumns {
        public static final String TABLE_NAME = "REPRESENTATIVE";
        public static final String ID_COLUMN = "Rep_ID";
        public static final String LAST_NAME_COLUMN = "Rep_LName";
        public static final String FIRST_NAME_COLUMN = "Rep_FName";
        public static final String ACCOUNT_ID_COLUMN = "Acc_ID";
    }

    /**
     * Inner class called CompanyTable.
     * Holds constants for the COMPANY database table.
     */
    public static class CompanyTable implements BaseColumns {
        public static final String TABLE_NAME = "COMPANY";
        public static final String ID_COLUMN = "Company_ID";
        public static final String NAME_COLUMN = "Company_Name";
        public static final String PHONE_NUM_COLUMN = "Company_Phone";
        public static final String EMAIL_COLUMN = "Company_Email";
    }

    /**
     * Inner class called CustomerTable.
     * Holds constants for the CUSTOMER database table.
     */
    public static class CustomerTable implements BaseColumns {
        public static final String TABLE_NAME = "CUSTOMER";
        public static final String ID_COLUMN = "Cus_ID";
        public static final String LAST_NAME_COLUMN = "Cus_LName";
        public static final String FIRST_NAME_COLUMN = "Cus_FName";
        public static final String PAYMENT_INFO_COLUMN = "Payment_Info";
        public static final String ACCOUNT_ID_COLUMN = "Acc_ID";
        public static final String COMPANY_ID_COLUMN = "Company_ID";
    }

    /**
     * Inner class called RentalTable.
     * Holds constants for the RENTAL database table.
     */
    public static class RentalTable implements BaseColumns {
        public static final String TABLE_NAME = "RENTAL";
        public static final String ID_COLUMN = "Rent_ID";
        public static final String PRICE_COLUMN = "Rent_Price";
        public static final String TYPE_COLUMN = "Rent_Type";
        public static final String DESCRIPTION_COLUMN = "Rent_Description";
        public static final String HISTORY_COLUMN = "Rent_History";
        public static final String FEATURES_COLUMN = "Rent_Features";
    }

    /**
     * Inner class called TransactionTable.
     * Holds constants for the TRANSACTION database table.
     */
    public static class TransactionTable implements BaseColumns {
        public static final String TABLE_NAME = "TRANSACTIONS";
        public static final String ID_COLUMN = "Trans_ID";
        public static final String CUSTOMER_ID_COLUMN = "Cus_ID";
        public static final String RENTAL_ID_COLUMN = "Rent_ID";
        public static final String DATE_RENTED_COLUMN = "Date_Rented";
        public static final String DATE_RETURNED_COLUMN = "Date_Returned";
        public static final String AMOUNT_PAID_COLUMN = "Amount_Paid";
    }

    /**
     * Inner class called ReservationTable.
     * Holds constants for the RESERVATION database table.
     */
    public static class ReservationTable implements BaseColumns {
        public static final String TABLE_NAME = "RESERVATION";
        public static final String CUSTOMER_ID_COLUMN = "Cus_ID";
        public static final String RENTAL_ID_COLUMN = "Rent_ID";
    }

    /**
     * Inner class called ReviewTable.
     * Holds constants for the REVIEW database table.
     */
    public static class ReviewTable implements BaseColumns {
        public static final String TABLE_NAME = "REVIEW";
        public static final String RENTAL_ID_COLUMN = "Rent_ID";
        public static final String CUSTOMER_ID_COLUMN = "Cus_ID";
        public static final String RATING_COLUMN = "Rating";
        public static final String REVIEW_TEXT_COLUMN = "Review_Text";
    }
}
