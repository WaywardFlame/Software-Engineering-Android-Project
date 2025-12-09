package cs3773.groupproject.autoquestrentals.databaseCode;

public final class DatabaseQueries {
    // for SELECT queries
    public static final String SELECT_ALL_COMPANY = "SELECT * FROM " + DatabaseContract.CompanyTable.TABLE_NAME;
    public static final String SELECT_ALL_REVIEW = "SELECT * FROM " + DatabaseContract.ReviewTable.TABLE_NAME;
    public static final String SELECT_ALL_ACCOUNT = "SELECT * FROM " + DatabaseContract.AccountTable.TABLE_NAME;
    public static final String SELECT_ALL_ADMIN = "SELECT * FROM " + DatabaseContract.AdministratorTable.TABLE_NAME;
    public static final String SELECT_ALL_REPRESENTATIVE = "SELECT * FROM " + DatabaseContract.RepresentativeTable.TABLE_NAME;
    public static final String SELECT_ALL_CUSTOMER = "SELECT * FROM " + DatabaseContract.CustomerTable.TABLE_NAME;
    public static final String SELECT_ALL_TRANSACTIONS = "SELECT * FROM " + DatabaseContract.TransactionTable.TABLE_NAME;
    public static final String SELECT_ALL_RESERVATION = "SELECT * FROM " + DatabaseContract.ReservationTable.TABLE_NAME;
    public static final String SELECT_ALL_RENTAL = "SELECT * FROM " + DatabaseContract.RentalTable.TABLE_NAME;

    // for DELETE queries
    public static final String DELETE_COMPANY = "DELETE FROM " + DatabaseContract.CompanyTable.TABLE_NAME + " WHERE " + DatabaseContract.CompanyTable.ID_COLUMN + "=";
    public static final String DELETE_REVIEW_CUSTOMER = "DELETE FROM " + DatabaseContract.ReviewTable.TABLE_NAME + " WHERE " + DatabaseContract.ReviewTable.CUSTOMER_ID_COLUMN + "=";
    public static final String DELETE_REVIEW_RENTAL = "DELETE FROM " + DatabaseContract.ReviewTable.TABLE_NAME + " WHERE " + DatabaseContract.ReviewTable.RENTAL_ID_COLUMN + "=";
    public static final String DELETE_RESERVATION_CUSTOMER = "DELETE FROM " + DatabaseContract.ReservationTable.TABLE_NAME + " WHERE " + DatabaseContract.ReservationTable.CUSTOMER_ID_COLUMN + "=";
    public static final String DELETE_RESERVATION_RENTAL = "DELETE FROM " + DatabaseContract.ReservationTable.TABLE_NAME + " WHERE " + DatabaseContract.ReservationTable.RENTAL_ID_COLUMN + "=";
    public static final String DELETE_TRANSACTION_BYID = "DELETE FROM " + DatabaseContract.TransactionTable.TABLE_NAME + " WHERE " + DatabaseContract.TransactionTable.ID_COLUMN + "=";
    public static final String DELETE_TRANSACTION_CUSTOMER = "DELETE FROM " + DatabaseContract.TransactionTable.TABLE_NAME + " WHERE " + DatabaseContract.TransactionTable.CUSTOMER_ID_COLUMN + "=";
    public static final String DELETE_TRANSACTION_RENTAL = "DELETE FROM " + DatabaseContract.TransactionTable.TABLE_NAME + " WHERE " + DatabaseContract.TransactionTable.RENTAL_ID_COLUMN + "=";
    public static final String DELETE_RENTAL = "DELETE FROM " + DatabaseContract.RentalTable.TABLE_NAME + " WHERE " + DatabaseContract.RentalTable.ID_COLUMN + "=";
    public static final String DELETE_CUSTOMER = "DELETE FROM " + DatabaseContract.CustomerTable.TABLE_NAME + " WHERE " + DatabaseContract.CustomerTable.ID_COLUMN + "=";
    public static final String DELETE_REPRESENTATIVE = "DELETE FROM " + DatabaseContract.RepresentativeTable.TABLE_NAME + " WHERE " + DatabaseContract.RepresentativeTable.ID_COLUMN + "=";
    public static final String DELETE_ADMINISTRATOR = "DELETE FROM " + DatabaseContract.AdministratorTable.TABLE_NAME + " WHERE " + DatabaseContract.AdministratorTable.ID_COLUMN + "=";
    public static final String DELETE_ACCOUNT = "DELETE FROM " + DatabaseContract.AccountTable.TABLE_NAME + " WHERE " + DatabaseContract.AccountTable.ID_COLUMN + "=";

    // for update queries
    public static final String UPDATE_CUSTOMER_COMPANY = "UPDATE " + DatabaseContract.CustomerTable.TABLE_NAME + " SET " + DatabaseContract.CustomerTable.COMPANY_ID_COLUMN + " = " + null + " WHERE " + DatabaseContract.CustomerTable.COMPANY_ID_COLUMN + "=";
}
