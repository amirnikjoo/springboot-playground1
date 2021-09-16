package ir.shaparak.eWallet;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeRiskLevelDto;
import ir.shaparak.eWallet.api.dto.personal.WalletChangeStatusDto;
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
class EWalletPersonalChangeStatusTestCases {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void change_status_from_active_2_block_success_test() throws URISyntaxException, InterruptedException {
        //issue > inquiry > change status
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);
        //inquiry register
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        String walletId = result.getBody().getWalletId();
        System.out.println("result = " + result.getBody().toString());

        // change status
        String newStan2 = MyUtility.getRandomNumber(6);
        WalletChangeStatusDto inputDto = new WalletChangeStatusDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(newStan2);
        inputDto.setStatus(WalletConstants.WALLET_STATUS_BLOCK_TEXT);
        inputDto.setDescription(TestConstants.CHANGE_STATUS_DESC);
        result = PersonalWalletRestCalls.callChangeStatusRest(restTemplate, randomServerPort, inputDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);

        //inquiry change status
        inquiryDto = new WalletInquiryDto();
        String newStan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan3);
        inquiryDto.setOriginalStan(newStan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }


    @Test
    public void change_status_wallet_not_found_fail_test() throws URISyntaxException, InterruptedException {

        // change status
        String newStan1 = MyUtility.getRandomNumber(6);
        WalletChangeStatusDto inputDto = new WalletChangeStatusDto();
        String s = "alaki";
        inputDto.setWalletId(s);
        inputDto.setStan(newStan1);
        inputDto.setStatus(WalletConstants.WALLET_STATUS_BLOCK_TEXT);
        inputDto.setDescription(TestConstants.CHANGE_STATUS_DESC);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callChangeStatusRest(restTemplate, randomServerPort, inputDto);

        Thread.sleep(TestConstants.JOB_INTERVAL);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("056", result.getBody().getResponseCode());

        //inquiry change status
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String newStan2 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan2);
        inquiryDto.setOriginalStan(newStan1);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("056", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }
}