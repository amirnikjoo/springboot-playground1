package ir.shaparak.eWallet.api;

import ir.shaparak.eWallet.api.dto.personal.*;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping(value = WalletConstants.BASE_URL_PERSONAL)
public class PersonalWalletApi {
    protected final static Logger log = LoggerFactory.getLogger(PersonalWalletApi.class);

    private final IHandler registerFlow;

    private final IHandler changeRiskLevelFlow;

    private final IHandler changeStatusFlow;

    private final IHandler changeMobileNoFlow;

    private final IHandler changeAccountFlow;

    private final IHandler inquiryFlow;

    public PersonalWalletApi(@Qualifier("registerFlow") IHandler registerFlow,
                             @Qualifier("changeRiskLevelFlow") IHandler changeRiskLevelFlow,
                             @Qualifier("changeStatusFlow") IHandler changeStatusFlow,
                             @Qualifier("changeMobileNoFlow") IHandler changeMobileNoFlow,
                             @Qualifier("changeAccountFlow") IHandler changeAccountFlow,
                             @Qualifier("inquiryFlow") IHandler inquiryFlow
    ) {
        this.registerFlow = registerFlow;
        this.changeRiskLevelFlow = changeRiskLevelFlow;
        this.changeStatusFlow = changeStatusFlow;
        this.changeMobileNoFlow = changeMobileNoFlow;
        this.changeAccountFlow = changeAccountFlow;
        this.inquiryFlow = inquiryFlow;
    }

    @PostMapping(value = WalletConstants.URL_REGISTER,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public @ResponseBody
    WalletResponseDto register(@Valid @RequestBody WalletRegisterRequestDto inputDto,
                               @RequestHeader Map<String, String> headers) {

        log.debug("request_body: {} ", inputDto.toString());
        Map map = new HashMap();
        map.put(WalletConstants.REQUEST_OBJECT, inputDto);
        map.put(WalletConstants.HEADERS, headers);
        map.put(WalletConstants.REQUEST_WALLET_ID, inputDto.getWalletSerial());

        try {
            registerFlow.process(map);
        } catch (Exception e) {
            log.error(e.getMessage());
//            result = e.getMessage() + "|" + e.toString();
        }

        WalletResponseDto responseDto = (WalletResponseDto) map.remove(WalletConstants.WALLET_RESPONSE_OBJECT);
        return responseDto;
    }

    @PostMapping(value = WalletConstants.URL_CHANGE_MOBILE_NO,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public @ResponseBody
    WalletResponseDto changeMobileNo(@RequestBody WalletChangeMobileNoDto inputDto,
                                     @RequestHeader Map<String, String> headers) {

        log.debug("request_body: {} ", inputDto);
        Map map = new HashMap();
        map.put(WalletConstants.REQUEST_OBJECT, inputDto);
        map.put(WalletConstants.HEADERS, headers);
        map.put(WalletConstants.REQUEST_WALLET_ID, inputDto.getWalletId());

        try {
            changeMobileNoFlow.process(map);
        } catch (Exception e) {
            log.error(e.getMessage());
//            result = e.getMessage() + "|" + e.toString();
        }

        WalletResponseDto responseDto = (WalletResponseDto) map.remove(WalletConstants.WALLET_RESPONSE_OBJECT);
        return responseDto;
    }

    @PostMapping(value = WalletConstants.URL_CHANGE_RISK_LEVEL,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    WalletResponseDto changeRiskLevel(@RequestBody WalletChangeRiskLevelDto inputDto,
                                      @RequestHeader Map<String, String> headers) {

        log.debug("request_body: {} ", inputDto);
        Map map = new HashMap();
        map.put(WalletConstants.REQUEST_OBJECT, inputDto);
        map.put(WalletConstants.HEADERS, headers);
        map.put(WalletConstants.REQUEST_WALLET_ID, inputDto.getWalletId());

        try {
            changeRiskLevelFlow.process(map);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        WalletResponseDto responseDto = (WalletResponseDto) map.remove(WalletConstants.WALLET_RESPONSE_OBJECT);
        return responseDto;
    }

    @PostMapping(value = WalletConstants.URL_CHANGE_STATUS,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    WalletResponseDto changeStatus(@RequestBody WalletChangeStatusDto inputDto,
                                   @RequestHeader Map<String, String> headers) {

        log.info("request_body: {} ", inputDto);
        Map map = new HashMap();
        map.put(WalletConstants.HEADERS, headers);
        map.put(WalletConstants.REQUEST_OBJECT, inputDto);
        map.put(WalletConstants.REQUEST_WALLET_ID, inputDto.getWalletId());

        try {
            changeStatusFlow.process(map);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        WalletResponseDto responseDto = (WalletResponseDto) map.remove(WalletConstants.WALLET_RESPONSE_OBJECT);
        return responseDto;
    }

    @PostMapping(value = WalletConstants.URL_ADD_ACCOUNT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public @ResponseBody
    WalletResponseDto addAccount(@RequestBody WalletChangeAccountDto inputDto,
                                 @RequestHeader Map<String, String> headers) {
        return changeAccount(inputDto, headers, WalletConstants.SERVICE_ID_ADD_ACCOUNT);
    }

    @PostMapping(value = WalletConstants.URL_REMOVE_ACCOUNT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public @ResponseBody
    WalletResponseDto removeAccount(@RequestBody WalletChangeAccountDto inputDto,
                                    @RequestHeader Map<String, String> headers) {
        return changeAccount(inputDto, headers, WalletConstants.SERVICE_ID_REMOVE_ACCOUNT);
    }

    private WalletResponseDto changeAccount(WalletChangeAccountDto inputDto, Map<String, String> headers, Integer accountServiceType) {
        log.info("request_body: {} ", inputDto);
        Map map = new HashMap();
        map.put(WalletConstants.REQUEST_OBJECT, inputDto);
        map.put(WalletConstants.REQUEST_WALLET_ID, inputDto.getWalletId());
        map.put(WalletConstants.HEADERS, headers);
        map.put(WalletConstants.METHOD_ACCOUNT_TYPE, accountServiceType);

        try {
            changeAccountFlow.process(map);
        } catch (Exception e) {
            log.error(e.getMessage());
//            result = e.getMessage() + "|" + e.toString(); //todo
        }

        return (WalletResponseDto) map.remove(WalletConstants.WALLET_RESPONSE_OBJECT);

    }

    @PostMapping(value = WalletConstants.URL_INQUIRY,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public @ResponseBody
    WalletResponseDto inquiry(@RequestBody WalletInquiryDto inputDto,
                              @RequestHeader Map<String, String> headers) {
        log.info("request_body: {} ", inputDto);
        String result = "";
        Map map = new HashMap();
        map.put(WalletConstants.REQUEST_OBJECT, inputDto);
        map.put(WalletConstants.HEADERS, headers);

        try {
            inquiryFlow.process(map);
        } catch (Exception e) {
            log.error(e.getMessage());
//            result = e.getMessage() + "|" + e.toString();
        }

        return (WalletResponseDto) map.remove(WalletConstants.WALLET_RESPONSE_OBJECT);

    }
}
