package ir.shaparak.eWallet;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeAccountDto;
import ir.shaparak.eWallet.api.dto.personal.WalletInquiryDto;
import ir.shaparak.eWallet.api.dto.personal.WalletResponseDto;
import ir.shaparak.eWallet.constants.ResponseConstants;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.util.MyUtility;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EWalletPersonalChangeAccountTestCases {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void add_account_pan_success_test() throws URISyntaxException, InterruptedException {
        //issue > inquiry > change level
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRestV2(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_2, TestConstants.MOBILE_NO, new String[]{}, new String[]{"3333331234567890"});

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String stan1 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan1);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //------add account
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeAccountDto inputDto = new WalletChangeAccountDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setAccountType(WalletConstants.ACCOUNT_TYPE_PAN_STRING);
        inputDto.setAccountId("4444441234567890");
        result = PersonalWalletRestCalls.callAddAccountRest(restTemplate, randomServerPort, inputDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void add_account_pan_invalid_risk_level_fail_test() throws URISyntaxException, InterruptedException {
        //issue > inquiry > change level
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String stan1 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan1);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //------add account
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeAccountDto inputDto = new WalletChangeAccountDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setAccountType(WalletConstants.ACCOUNT_TYPE_PAN_STRING);
        inputDto.setAccountId("12344");
        result = PersonalWalletRestCalls.callAddAccountRest(restTemplate, randomServerPort, inputDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("110", result.getBody().getResponseCode());

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("110", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void add_account_pan_invalid_pan_fail_test() throws URISyntaxException, InterruptedException {
        //issue > inquiry > change level
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRestV2(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_2, TestConstants.MOBILE_NO, new String[]{}, TestConstants.TWO_PAN);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String stan1 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan1);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //------add account
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeAccountDto inputDto = new WalletChangeAccountDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setAccountType(WalletConstants.ACCOUNT_TYPE_PAN_STRING);
        inputDto.setAccountId("12344");
        result = PersonalWalletRestCalls.callAddAccountRest(restTemplate, randomServerPort, inputDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("104", result.getBody().getResponseCode());

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("104", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void add_account_pan_already_exist_fail_test() throws URISyntaxException, InterruptedException {
        //issue > inquiry > change level
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRestV2(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_2, TestConstants.MOBILE_NO, new String[]{}, TestConstants.TWO_PAN);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String stan1 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan1);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //------add account
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeAccountDto inputDto = new WalletChangeAccountDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setAccountType(WalletConstants.ACCOUNT_TYPE_PAN_STRING);
        inputDto.setAccountId("1111111111111111");
        result = PersonalWalletRestCalls.callAddAccountRest(restTemplate, randomServerPort, inputDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("109", result.getBody().getResponseCode());

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("109", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void remove_pan_account_success_test() throws URISyntaxException, InterruptedException {
        //issue > inquiry > change level
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRestV2(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_2, TestConstants.MOBILE_NO, new String[]{}, TestConstants.TWO_PAN);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String stan1 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan1);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //remove account
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeAccountDto inputDto = new WalletChangeAccountDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setAccountType(WalletConstants.ACCOUNT_TYPE_PAN_STRING);
        inputDto.setAccountId(TestConstants.PAN1);
        result = PersonalWalletRestCalls.callRemoveAccountRest(restTemplate, randomServerPort, inputDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void remove_pan_account_not_found_pan_fail_test() throws URISyntaxException, InterruptedException {
        //issue > inquiry > change level
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRestV2(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_2, TestConstants.MOBILE_NO, new String[]{}, TestConstants.TWO_PAN);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String stan1 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan1);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //------remove account
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeAccountDto inputDto = new WalletChangeAccountDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setAccountType(WalletConstants.ACCOUNT_TYPE_PAN_STRING);
        inputDto.setAccountId("9999999999999999");
        result = PersonalWalletRestCalls.callRemoveAccountRest(restTemplate, randomServerPort, inputDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("108", result.getBody().getResponseCode());

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("108", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void remove_pan_last_pan_fail_test() throws URISyntaxException, InterruptedException {
        //issue > inquiry > change level
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRestV2(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_2, TestConstants.MOBILE_NO, new String[]{}, TestConstants.ONE_PAN);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String stan1 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan1);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //remove account
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeAccountDto inputDto = new WalletChangeAccountDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setAccountType(WalletConstants.ACCOUNT_TYPE_PAN_STRING);
        inputDto.setAccountId(TestConstants.PAN1);
        result = PersonalWalletRestCalls.callRemoveAccountRest(restTemplate, randomServerPort, inputDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("113", result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("113", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }


}