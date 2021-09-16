package ir.shaparak.eWallet;

import ir.shaparak.eWallet.api.dto.personal.*;
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
class EWalletPersonalChangeMobileNoTestCases {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void change_mobile_no_success_test() throws URISyntaxException, InterruptedException {
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

        //------change mobile no
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeMobileNoDto inputDto = new WalletChangeMobileNoDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setMobileNumber(TestConstants.MOBILE_NO2);
        result = PersonalWalletRestCalls.callChangeMobileNoRest(restTemplate, randomServerPort, inputDto);
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
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void change_mobile_no_invalid_input_fail_test() throws URISyntaxException, InterruptedException {
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

        //------change mobile no
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeMobileNoDto inputDto = new WalletChangeMobileNoDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setMobileNumber("1233"); //invalid mobile no
        result = PersonalWalletRestCalls.callChangeMobileNoRest(restTemplate, randomServerPort, inputDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("111", result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("111", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

}