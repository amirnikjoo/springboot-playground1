package ir.shaparak.eWallet.constants;

import ir.shaparak.eWallet.api.dto.ResponseCodeDto;

import java.util.HashMap;
import java.util.Map;

public class ResponseConstants {
    public final static String APPROVED_RESPONSE_CODE = "000";
    public final static Integer APPROVED_RESPONSE_CODE_INT = 0;
    public final static String APPROVED_RESPONSE_DESC = "درخواست با موفقیت ثبت شد";
    public final static String APPROVED_RESPONSE_DESC_INQUIRY = "درخواست اصلی با موفقیت انجام شده است";
    public final static String EXCEPTION_KEY_IN_PROGRESS = "RequestInProgressException";
    public final static String EXCEPTION_KEY_DATA_ACCESS = "DataAccessException";
    public final static String EXCEPTION_KEY_VALIDATION = "InputValidationException";
    public final static String EXCEPTION_KEY_VALIDATION_DESC = "ورودی نامعتبر است";

    public static final Map responseMap = new HashMap();

    static {
        responseMap.put(EXCEPTION_KEY_IN_PROGRESS, new ResponseCodeDto("009", "درخواست در صف پردازش است"));
        responseMap.put("009", new ResponseCodeDto(EXCEPTION_KEY_IN_PROGRESS, "درخواست در صف پردازش است"));

        responseMap.put("FormatErrorException", new ResponseCodeDto("030", "فرمت داده های ارسالی نامعتبر است"));
        responseMap.put("030", new ResponseCodeDto("FormatErrorException", "فرمت داده های ارسالی نامعتبر است"));

        responseMap.put("SecurityViolationException", new ResponseCodeDto("063", "خطای امنیتی"));
        responseMap.put("063", new ResponseCodeDto("SecurityViolationException", "خطای امنیتی"));

        responseMap.put("WalletNotFoundException", new ResponseCodeDto("056", "شناسه کیف نامعتبر است"));
        responseMap.put("056", new ResponseCodeDto("WalletNotFoundException", "شناسه کیف نامعتبر است"));

        responseMap.put("MaxWalletCountExceededException", new ResponseCodeDto("065", "تعداد کیف بیشتر از حد مجاز است"));
        responseMap.put("065", new ResponseCodeDto("MaxWalletCountExceededException", "تعداد کیف بیشتر از حد مجاز است"));

        responseMap.put(EXCEPTION_KEY_DATA_ACCESS, new ResponseCodeDto("087", "خطای داخلی پایگاه داده"));
        responseMap.put("087", new ResponseCodeDto(EXCEPTION_KEY_DATA_ACCESS, "خطای داخلی پایگاه داده"));

        responseMap.put("SystemInternalError", new ResponseCodeDto("088", "خطای داخلی"));
        responseMap.put("088", new ResponseCodeDto("SystemInternalError", "خطای داخلی"));

        responseMap.put("DuplicateTransmissionException", new ResponseCodeDto("094", "درخواست تکراری است"));
        responseMap.put("094", new ResponseCodeDto("DuplicateTransmissionException", "درخواست تکراری است"));

        responseMap.put("InvalidUsernameOrPasswordException", new ResponseCodeDto("100", "شناسه کاربری یا رمز عبور نامعتبر است"));
        responseMap.put("100", new ResponseCodeDto("InvalidUsernameOrPasswordException", "شناسه کاربری یا رمز عبور نامعتبر است"));

        responseMap.put("InactiveWalletException", new ResponseCodeDto("101", "کیف پول در وضعیت فعال نمی باشد"));
        responseMap.put("101", new ResponseCodeDto("InactiveWalletException", "کیف پول در وضعیت فعال نمی باشد"));

        responseMap.put("UnsupportedRequestException", new ResponseCodeDto("102", "درخواست پشتیبانی نمی گردد"));
        responseMap.put("102", new ResponseCodeDto("UnsupportedRequestException", "درخواست پشتیبانی نمی گردد"));

        responseMap.put("InvalidIdentifierTypeException", new ResponseCodeDto("103", "نوع شخصیت نامعتبر است"));
        responseMap.put("103", new ResponseCodeDto("InvalidIdentifierTypeException", "نوع شخصیت نامعتبر است"));

        responseMap.put("InvalidAccountException", new ResponseCodeDto("104", "اطلاعات حساب نامعتبر است"));
        responseMap.put("104", new ResponseCodeDto("InvalidAccountException", "اطلاعات حساب نامعتبر است"));

        responseMap.put("RequestNotFoundException", new ResponseCodeDto("105", "درخواست یافت نشد"));
        responseMap.put("105", new ResponseCodeDto("RequestNotFoundException", "درخواست یافت نشد"));

        responseMap.put("InvalidStatusRequestException", new ResponseCodeDto("106", "وضعیت کیف پول درخواستی نامعتبر است"));
        responseMap.put("106", new ResponseCodeDto("InvalidStatusRequestException", "وضعیت کیف پول درخواستی نامعتبر است"));

        responseMap.put("BlockedWalletException", new ResponseCodeDto("107", "وضعیت کیف پول راکد است"));
        responseMap.put("107", new ResponseCodeDto("BlockedWalletException", "وضعیت کیف پول راکد است"));

        responseMap.put("AccountNotFoundException", new ResponseCodeDto("108", "اطلاعات حساب یافت نشد"));
        responseMap.put("108", new ResponseCodeDto("AccountNotFoundException", "اطلاعات حساب یافت نشد"));

        responseMap.put("AccountAlreadyExistsException", new ResponseCodeDto("109", "حساب قبلا تعریف شده است"));
        responseMap.put("109", new ResponseCodeDto("AccountAlreadyExistsException", "حساب قبلا تعریف شده است"));

        responseMap.put("InvalidRequestException", new ResponseCodeDto("110", "درخواست نامعتبر است"));
        responseMap.put("110", new ResponseCodeDto("InvalidRequestException", "درخواست نامعتبر است"));

        responseMap.put(EXCEPTION_KEY_VALIDATION, new ResponseCodeDto("111", EXCEPTION_KEY_VALIDATION_DESC));
        responseMap.put("111", new ResponseCodeDto(EXCEPTION_KEY_VALIDATION, EXCEPTION_KEY_VALIDATION_DESC));

        responseMap.put("MaxAccountCountExceededException", new ResponseCodeDto("112", "تعداد حساب بیشتر از حد مجاز است"));
        responseMap.put("112", new ResponseCodeDto("MaxAccountCountExceededException", "تعداد حساب بیشتر از حد مجاز است"));

        responseMap.put("MinAccountCountExceededException", new ResponseCodeDto("113", "تعداد حساب کمتر از حد مجاز است"));
        responseMap.put("113", new ResponseCodeDto("MinAccountCountExceededException", "تعداد حساب کمتر از حد مجاز است"));


    }

}
