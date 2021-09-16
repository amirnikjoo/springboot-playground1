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
class EWalletPersonalChangeRiskLevelTestCases {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void risk_level_from1_to_2_success_test() throws URISyntaxException, InterruptedException {
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

        //------change level
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeRiskLevelDto inputDto = new WalletChangeRiskLevelDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setRiskLevel(WalletConstants.WALLET_RISK_LEVEL_2);
        inputDto.setPan(new String[]{"1234567890123456"});
        inputDto.setIban(new String[]{});
        result = PersonalWalletRestCalls.callChangeRiskLevelRest(restTemplate, randomServerPort, inputDto);

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
        Assert.assertEquals(walletId, result.getBody().getWalletId());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void risk_level_from1_to_3_fail_test() throws URISyntaxException, InterruptedException {
        //register > inquiry > change level
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

        //------change level
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeRiskLevelDto inputDto = new WalletChangeRiskLevelDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setRiskLevel(WalletConstants.WALLET_RISK_LEVEL_3);
        inputDto.setPan(new String[]{"1234567890123456"});
        inputDto.setIban(new String[]{});
        result = PersonalWalletRestCalls.callChangeRiskLevelRest(restTemplate, randomServerPort, inputDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("102", result.getBody().getResponseCode());
        Thread.sleep(TestConstants.JOB_INTERVAL);

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("102", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void risk_level_from2_to_1_success_test() throws URISyntaxException, InterruptedException {
        //issue > inquiry > change level
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRestV2(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_2, TestConstants.MOBILE_NO, TestConstants.IBANS, new String[]{});

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

        //------change level
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeRiskLevelDto inputDto = new WalletChangeRiskLevelDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setRiskLevel(WalletConstants.WALLET_RISK_LEVEL_1);
        inputDto.setPan(new String[]{});
        inputDto.setIban(new String[]{});
        result = PersonalWalletRestCalls.callChangeRiskLevelRest(restTemplate, randomServerPort, inputDto);

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
        Assert.assertEquals(walletId, result.getBody().getWalletId());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void risk_level_in_block_status_fail_test() throws URISyntaxException, InterruptedException {
        //register > inquiry > change level > inquiry
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

        Thread.sleep(TestConstants.JOB_INTERVAL);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        //inquiry change status
        inquiryDto = new WalletInquiryDto();
        String newStan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan3);
        inquiryDto.setOriginalStan(newStan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //------change level
        String stan3 = MyUtility.getRandomNumber(6);
        WalletChangeRiskLevelDto changeRiskLevelDto = new WalletChangeRiskLevelDto();
        changeRiskLevelDto.setWalletId(walletId);
        changeRiskLevelDto.setStan(stan3);
        changeRiskLevelDto.setRiskLevel(WalletConstants.WALLET_RISK_LEVEL_2);
        changeRiskLevelDto.setPan(new String[]{"1234567890123456"});
        changeRiskLevelDto.setIban(new String[]{});
        result = PersonalWalletRestCalls.callChangeRiskLevelRest(restTemplate, randomServerPort, changeRiskLevelDto);
        Thread.sleep(TestConstants.JOB_INTERVAL);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("101", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void risk_level_request_in_progress_fail_test() throws URISyntaxException, InterruptedException {
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

        //------change level
        String stan2 = MyUtility.getRandomNumber(6);
        String walletId = result.getBody().getWalletId();
        WalletChangeRiskLevelDto inputDto = new WalletChangeRiskLevelDto();
        inputDto.setWalletId(walletId);
        inputDto.setStan(stan2);
        inputDto.setRiskLevel(WalletConstants.WALLET_RISK_LEVEL_2);
        inputDto.setPan(new String[]{"1234567890123456"});
        inputDto.setIban(new String[]{});
        result = PersonalWalletRestCalls.callChangeRiskLevelRest(restTemplate, randomServerPort, inputDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
//        Thread.sleep(TestConstants.JOB_INTERVAL);  should be comment in this test

        //call inquiry
        inquiryDto = new WalletInquiryDto();
        String stan3 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(stan3);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("009", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

}