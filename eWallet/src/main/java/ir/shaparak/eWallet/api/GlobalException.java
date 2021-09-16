package ir.shaparak.eWallet.api;

import ir.shaparak.eWallet.api.dto.personal.WalletResponseDto;
import ir.shaparak.eWallet.constants.ResponseConstants;
import ir.shaparak.eWallet.constants.WalletConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                             HttpHeaders headers,
                                                                             HttpStatus status, WebRequest request) {

        WalletResponseDto responseDto = new WalletResponseDto("", "", "", ResponseConstants.EXCEPTION_KEY_VALIDATION,
                ResponseConstants.EXCEPTION_KEY_VALIDATION_DESC, (String) WalletConstants.EXCEPTION_SOURCE_MAP.get(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL));
        return new ResponseEntity<>(responseDto, headers, status);
//        return responseDto;

    }

}
