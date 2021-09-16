package ir.shaparak.eWallet;

import ir.shaparak.eWallet.api.dto.personal.*;
import ir.shaparak.eWallet.constants.WalletConstants;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

public class PersonalWalletRestCalls {

    public static ResponseEntity<WalletResponseDto> callRegisterRest(TestRestTemplate restTemplate, int randomServerPort, String walletSerialId,
                                                                     String stan, String nationalType, String nationalId, Integer riskLevel, String mobileNo) throws URISyntaxException {
        return restTemplate.withBasicAuth(TestConstants.USER_BANK_OPERATOR, TestConstants.USER_BANK_OPERATOR_PASS).postForEntity(
                new URI(TestConstants.LOCAL_HOST + randomServerPort + WalletConstants.BASE_URL_PERSONAL + WalletConstants.URL_REGISTER)
                , DtoTestUtility.getARegisterDtoCustom(walletSerialId, riskLevel, stan, nationalId, nationalType, mobileNo),
                WalletResponseDto.class);
    }
    public static ResponseEntity<WalletResponseDto> callRegisterRestV2(TestRestTemplate restTemplate, int randomServerPort, String walletSerialId,
                                                                     String stan, String nationalType, String nationalId, Integer riskLevel, String mobileNo, String[] ibans,String[] pans) throws URISyntaxException {
        return restTemplate.withBasicAuth(TestConstants.USER_BANK_OPERATOR, TestConstants.USER_BANK_OPERATOR_PASS).postForEntity(
                new URI(TestConstants.LOCAL_HOST + randomServerPort + WalletConstants.BASE_URL_PERSONAL + WalletConstants.URL_REGISTER)
                , DtoTestUtility.getARegisterDtoCustomV2(walletSerialId, riskLevel, stan, nationalId, nationalType, mobileNo, ibans, pans),
                WalletResponseDto.class);
    }

    public static ResponseEntity<WalletResponseDto> callChangeRiskLevelRest(TestRestTemplate restTemplate, int randomServerPort, WalletChangeRiskLevelDto inputDto) throws URISyntaxException {
        return restTemplate.withBasicAuth(TestConstants.USER_BANK_OPERATOR, TestConstants.USER_BANK_OPERATOR_PASS).postForEntity(
                new URI(TestConstants.LOCAL_HOST + randomServerPort + WalletConstants.BASE_URL_PERSONAL + WalletConstants.URL_CHANGE_RISK_LEVEL)
                , inputDto, WalletResponseDto.class);
    }

    public static ResponseEntity<WalletResponseDto> callChangeStatusRest(TestRestTemplate restTemplate, int randomServerPort, WalletChangeStatusDto inputDto) throws URISyntaxException {
        return restTemplate.withBasicAuth(TestConstants.USER_BANK_OPERATOR, TestConstants.USER_BANK_OPERATOR_PASS).postForEntity(
                new URI(TestConstants.LOCAL_HOST + randomServerPort + WalletConstants.BASE_URL_PERSONAL + WalletConstants.URL_CHANGE_STATUS)
                , inputDto, WalletResponseDto.class);
    }

    public static ResponseEntity<WalletResponseDto> callChangeMobileNoRest(TestRestTemplate restTemplate, int randomServerPort, WalletChangeMobileNoDto inputDto) throws URISyntaxException {
        return restTemplate.withBasicAuth(TestConstants.USER_BANK_OPERATOR, TestConstants.USER_BANK_OPERATOR_PASS).postForEntity(
                new URI(TestConstants.LOCAL_HOST + randomServerPort + WalletConstants.BASE_URL_PERSONAL + WalletConstants.URL_CHANGE_MOBILE_NO)
                , inputDto, WalletResponseDto.class);
    }

    public static ResponseEntity<WalletResponseDto> callAddAccountRest(TestRestTemplate restTemplate, int randomServerPort, WalletChangeAccountDto inputDto) throws URISyntaxException {
        return restTemplate.withBasicAuth(TestConstants.USER_BANK_OPERATOR, TestConstants.USER_BANK_OPERATOR_PASS).postForEntity(
                new URI(TestConstants.LOCAL_HOST + randomServerPort + WalletConstants.BASE_URL_PERSONAL + WalletConstants.URL_ADD_ACCOUNT)
                , inputDto, WalletResponseDto.class);
    }

    public static ResponseEntity<WalletResponseDto> callRemoveAccountRest(TestRestTemplate restTemplate, int randomServerPort, WalletChangeAccountDto inputDto) throws URISyntaxException {
        return restTemplate.withBasicAuth(TestConstants.USER_BANK_OPERATOR, TestConstants.USER_BANK_OPERATOR_PASS).postForEntity(
                new URI(TestConstants.LOCAL_HOST + randomServerPort + WalletConstants.BASE_URL_PERSONAL + WalletConstants.URL_REMOVE_ACCOUNT)
                , inputDto, WalletResponseDto.class);
    }

    public static ResponseEntity<WalletResponseDto> callInquiryRest(TestRestTemplate restTemplate, int randomServerPort, WalletInquiryDto inquiryDto) throws URISyntaxException {
        return restTemplate.withBasicAuth(TestConstants.USER_BANK_OPERATOR, TestConstants.USER_BANK_OPERATOR_PASS).postForEntity(
                new URI(TestConstants.LOCAL_HOST + randomServerPort + WalletConstants.BASE_URL_PERSONAL + WalletConstants.URL_INQUIRY)
                , inquiryDto, WalletResponseDto.class);
    }
}
