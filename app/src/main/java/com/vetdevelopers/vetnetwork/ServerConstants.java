package com.vetdevelopers.vetnetwork;

public class ServerConstants
{

    public static final String DB_URL = "http://vetdevelopers.com/app_connect/";
    public static final String REGISTER_URL = DB_URL + "register.php";
    public static final String LOGIN_URL = DB_URL + "login.php";
    public static final String UPDATE_URL = DB_URL + "update.php";
    public static final String GET_ADMIN_EMAIL_URL = DB_URL + "get_admin_email.php";
    public static final String UPDATE_CURRENT_ADMIN_EMAIL = DB_URL + "update_current_adminEmail.php";
    public static final String DOCTOR_POSTING_AREA = DB_URL + "doctor_posting_area.php";
    public static final String SEARCH_URL = DB_URL + "search.php";
    public static final String SEARCH_FOR_DISPLAY_PROFILE = DB_URL + "searchForDisplayProfile.php";
    public static final String FORGOT_PASSWORD = DB_URL + "forgotPassword.php";
    public static final String CHANGE_PASSWORD = DB_URL + "change_password.php";
    public static final String SEARCH_FOR_DELETE_URL = DB_URL + "search_for_delete.php";
    public static final String PENDING_USER_REQUEST_URL = DB_URL + "pending_user_request.php";
    public static final String ADMIN_VIEW_URL = DB_URL + "admin_view.php";
    public static final String ADMIN_ACCEPT_URL = DB_URL + "admin_accept.php";
    public static final String ADMIN_REJECT_URL = DB_URL + "admin_reject.php";
    public static final String DELETE_ACCOUNT_URL = DB_URL + "delete_account.php";
    public static final String ADMIN_DELETE_URL = DB_URL + "admin_delete.php";
    public static final String REPORT_BROWSERS_URL = DB_URL + "report_browsers.php";
    public static final String REPORT_USERS_URL = DB_URL + "report_users.php";
    public static final String REPORT_ADMIN_URL = DB_URL + "report_admin.php";


    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PREV_PHONE = "prevPhone";
    public static final String KEY_BVC_REG = "bvc_reg";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_UNIVERSITY = "university";
    public static final String KEY_DESIGNATION = "designation";
    public static final String KEY_POSTING_AREA = "posting_area";
    public static final String KEY_DISTRICT = "district";
    public static final String KEY_DIVISION = "division";
    public static final String KEY_EMAIL_CONFIRM = "email_confirm";
    public static final String KEY_RAND_CODE = "rand_code";
    public static final String KEY_USER_REQUEST = "user_request";
    public static final String KEY_USER_TYPE = "user_type";

    //for adminEmail Table
    public static final String KEY_ADMIN_NAME = "admin_name";
    public static final String KEY_ADMIN_PHONE = "admin_phone";
    public static final String KEY_ADMIN_EMAIL = "admin_email";

    //adminPanel
    public static final String KEY_SELECTED_RADIO_BUTTON = "radio_button";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_ALL_USER_PHONE = "all_user_phone";

    //report
    public static final String KEY_TOPIC = "topic";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
}
