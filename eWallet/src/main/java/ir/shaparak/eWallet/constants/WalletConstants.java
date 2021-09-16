package ir.shaparak.eWallet.constants;

import java.util.HashMap;
import java.util.Map;

public class WalletConstants {
    public final static String BASE_URL_PERSONAL = "/api/wallet/personal";
    public final static String URL_REGISTER = "/register";
    public final static String URL_CHANGE_RISK_LEVEL = "/change/riskLevel";
    public final static String URL_CHANGE_STATUS = "/change/state";
    public final static String URL_CHANGE_MOBILE_NO = "/change/mobileNumber";
    public final static String URL_ADD_ACCOUNT = "/account/add";
    public final static String URL_REMOVE_ACCOUNT = "/account/remove";
    public final static String URL_INQUIRY = "/inquiry";

    public final static String GENERATED_WALLET_ID = "GENERATED_WALLET_ID";

    public final static String METHOD_ACCOUNT_TYPE = "METHOD_ACCOUNT_TYPE";

    public final static String AUTHORIZATION_HEADER = "authorization";

    public final static String JSON_BODY_STRING = "JSON_BODY_STRING";
    public final static String HEADERS = "HEADERS";
    public final static String REQUEST_OBJECT = "REQUEST_OBJECT";
    public final static String REQUEST_TIME = "REQUEST_TIME";

    public final static String REFERENCE_NUMBER = "REFERENCE_NUMBER";

    public static final String EXCEPTION_CLASS = "Exception_Class";
    public static final String EXCEPTION_CLASS_NAME = "Exception_Class_Name";
    public static final String EXCEPTION_CLASS_OBJECT = "Exception-Class_Object";

    public static final String USER_OBJECT = "User-Object";
    public static final String WALLET_REQUEST_OBJECT = "Wallet_Request_Object";
    public static final String PERSONAL_WALLET_OBJECT = "Personal_Wallet_Object";

    public static final String WALLET_RESPONSE_OBJECT = "Wallet_Response_Object";
    public static final String REQUEST_WALLET_ID = "REQUEST_WALLET_ID";

    public static final Integer TRY_COUNT = 0;
    public static final Integer MAX_TRY_COUNT = 2;

    public static final String PERSONAL_WALLET_ACCOUNT_OBJECT_LIST = "PERSONAL_WALLET_ACCOUNT_OBJECT_LIST";
    public static final String PERSONAL_WALLET_ACCOUNT_OBJECT = "PERSONAL_WALLET_ACCOUNT_OBJECT";

    //todo: make better
    public static final Integer WALLET_STATUS_ACTIVE = 0;
    public static final Integer WALLET_STATUS_ACTIVE_WARNING = 10;
    public static final Integer WALLET_STATUS_BLOCK_DEPOSIT = 20;
    public static final Integer WALLET_STATUS_BLOCK_WITHDRAW = 30;
    public static final Integer WALLET_STATUS_BLOCK = 40;
    public static final Integer WALLET_STATUS_CLOSE_TEMP = 50;
    public static final Integer WALLET_STATUS_CLOSE_WARNING = 60;
    public static final Integer WALLET_STATUS_CLOSE = 70;

    public static final String REQUEST_SERVICE_ID = "REQUEST_SERVICE_ID";
    public static final String ORIGINAL_SERVICE_ID = "ORIGINAL_SERVICE_ID";
    public static final Integer SERVICE_ID_REGISTER = 1;
    public static final Integer SERVICE_ID_CHANGE_RISK_LEVEL = 2;
    public static final Integer SERVICE_ID_CHANGE_STATUS = 3;
    public static final Integer SERVICE_ID_CHANGE_MOBILE_NO = 4;
    public static final Integer SERVICE_ID_ADD_ACCOUNT = 5;
    public static final Integer SERVICE_ID_REMOVE_ACCOUNT = 6;
    public static final Integer SERVICE_ID_INQUIRY = 7;

    public static final String WALLET_TYPE_PERSONAL = "1";
    public static final String WALLET_TYPE_COMMERCIAL = "2";

    public static final String EXCEPTION_SOURCE = "بدون خطا";
    public static final String EXCEPTION_SOURCE_ID = "EXCEPTION_SOURCE_ID";
    public static final Integer EXCEPTION_SOURCE_ID_UNKNOWN = -1;
    public static final Integer EXCEPTION_SOURCE_ID_NO_EXCEPTION = 0;
    public static final Integer EXCEPTION_SOURCE_ID_WALLET_CHANNEL = 1;
    public static final Integer EXCEPTION_SOURCE_ID_SABTE_AHVAL = 2;
    public static final Integer EXCEPTION_SOURCE_ID_SHAHKAR = 3;
    public static final Integer EXCEPTION_SOURCE_ID_SIAM = 4;
    public static final Integer EXCEPTION_SOURCE_ID_SABTE_ASNAD = 5;
    public static final Integer EXCEPTION_SOURCE_ID_MANA = 6;

    public static final Integer HANDLE = 0;
    public static final Integer HANDLED_FINE = 1;
    public static final Integer HANDLED_WITH_ERROR = 2;
    public static final Integer HANDLE_NOT = 9;

    public static final String EMPTY_STRING = " ";
    public static final String START_TIME = "START_TIME";

    public static final Integer MAX_WALLET_COUNT = 3;
    public static final Integer MAX_VALID_ACCOUNTS = 5;

    public static final String SPLIT_PIPE = "\\|";
    public static final String SEPARATOR_PIPE = "|";
    public static final String SEPARATOR_COMMA = ",";
    public static final String SEPARATOR_DASH = "-";

    public static final Integer USER_TYPE_ADMIN = 1;
    public static final Integer USER_TYPE_SHAPARAK_OPERATION = 2;
    public static final Integer USER_TYPE_BANK_OPERATOR = 3;

    public static final Integer INIT_STEP = 0;
    public static final Integer ZERO = 0;

    public static final String WALLET_IBAN = "WALLET_IBAN";
    public static final String WALLET_PAN = "WALLET_PAN";

    public static final Integer ACCOUNT_TYPE_IBAN = 1;
    public static final Integer ACCOUNT_TYPE_PAN = 2;

    public static final String ACCOUNT_TYPE_IBAN_STRING = "IBAN";
    public static final String ACCOUNT_TYPE_PAN_STRING = "PAN";


    public static final Integer WALLET_ACCOUNT_STATUS_ACTIVE = 0;
    public static final Integer WALLET_ACCOUNT_STATUS_DISABLED = 1;

    public static final Integer WALLET_RISK_LEVEL_1 = 1;
    public static final Integer WALLET_RISK_LEVEL_2 = 2;
    public static final Integer WALLET_RISK_LEVEL_3 = 3;

    public static final String  WALLET_STATUS_ACTIVE_TEXT = "ACTIVE";
    public static final String  WALLET_STATUS_ACTIVE_WARNING_TEXT = "ACTIVE_WARNING";
    public static final String  WALLET_STATUS_BLOCK_DEPOSIT_TEXT = "BLOCK_DEPOSIT";
    public static final String  WALLET_STATUS_BLOCK_WITHDRAW_TEXT = "BLOCK_WITHDRAW";
    public static final String  WALLET_STATUS_BLOCK_TEXT = "BLOCK";
    public static final String  WALLET_STATUS_CLOSE_TEMP_TEXT= "CLOSE_TEMP";
    public static final String  WALLET_STATUS_CLOSE_WARNING_TEXT = "CLOSE_WARNING";
    public static final String  WALLET_STATUS_CLOSE_TEXT = "CLOSE";

    public static final Map WALLET_STATUS_TO_WEIGHT_MAP = new HashMap();
    static {
        WALLET_STATUS_TO_WEIGHT_MAP.put(WALLET_STATUS_ACTIVE_TEXT, 0);
        WALLET_STATUS_TO_WEIGHT_MAP.put(WALLET_STATUS_ACTIVE_WARNING_TEXT, 10);
        WALLET_STATUS_TO_WEIGHT_MAP.put(WALLET_STATUS_BLOCK_DEPOSIT_TEXT, 20);
        WALLET_STATUS_TO_WEIGHT_MAP.put(WALLET_STATUS_BLOCK_WITHDRAW_TEXT, 30);
        WALLET_STATUS_TO_WEIGHT_MAP.put(WALLET_STATUS_BLOCK_TEXT, 40);
        WALLET_STATUS_TO_WEIGHT_MAP.put(WALLET_STATUS_CLOSE_TEMP_TEXT, 50);
        WALLET_STATUS_TO_WEIGHT_MAP.put(WALLET_STATUS_CLOSE_WARNING_TEXT, 60);
        WALLET_STATUS_TO_WEIGHT_MAP.put(WALLET_STATUS_CLOSE_TEXT, 70);
    }
    public static final Map EXCEPTION_SOURCE_MAP = new HashMap();
    static {
        EXCEPTION_SOURCE_MAP.put(EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "سامانه پایش کیف پول");
        EXCEPTION_SOURCE_MAP.put(EXCEPTION_SOURCE_ID_SABTE_AHVAL, "ثبت احوال");
        EXCEPTION_SOURCE_MAP.put(EXCEPTION_SOURCE_ID_SHAHKAR, "شاهکار");
        EXCEPTION_SOURCE_MAP.put(EXCEPTION_SOURCE_ID_SIAM, "سیام");
        EXCEPTION_SOURCE_MAP.put(EXCEPTION_SOURCE_ID_SABTE_ASNAD, "ثبت اسناد");
        EXCEPTION_SOURCE_MAP.put(EXCEPTION_SOURCE_ID_MANA, "مانا");
    }

    public static final String IDENTIFIER_TYPE_REAL_PERSON = "REAL_PERSON";
    public static final String IDENTIFIER_TYPE_CORPORATE_PERSON = "CORPORATE_PERSON";
    public static final String IDENTIFIER_TYPE_FOREIGN_REAL_PERSON = "FOREIGN_REAL_PERSON";
    public static final Map IDENTIFIER_TYPE_MAP = new HashMap();
    static {
        IDENTIFIER_TYPE_MAP.put(IDENTIFIER_TYPE_REAL_PERSON, 1);
        IDENTIFIER_TYPE_MAP.put(IDENTIFIER_TYPE_CORPORATE_PERSON, 2);
        IDENTIFIER_TYPE_MAP.put(IDENTIFIER_TYPE_FOREIGN_REAL_PERSON, 3);
    }

    public static final Map ACCOUNT_TYPE_MAP = new HashMap();
    static {
        ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_IBAN_STRING, ACCOUNT_TYPE_IBAN);
        ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_PAN_STRING, ACCOUNT_TYPE_PAN);
    }


}

