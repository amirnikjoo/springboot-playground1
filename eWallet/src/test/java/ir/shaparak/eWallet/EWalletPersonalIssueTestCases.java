package ir.shaparak.eWallet;

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
class EWalletPersonalIssueTestCases {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void register_success_test() throws URISyntaxException, InterruptedException {
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan =  MyUtility.getRandomNumber(6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId,
                WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        Thread.sleep(TestConstants.JOB_INTERVAL);

        // call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());
    }

    @Test
    public void register_duplicate_serial_fail_test() throws URISyntaxException, InterruptedException {
        String walletSerialId = MyUtility.getRandomNumber(12);
        String stan1 = MyUtility.getRandomNumber(walletSerialId.length() - 6);
        String nationalId = MyUtility.getRandomNumber(10);

        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan1, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId, WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        Thread.sleep(TestConstants.JOB_INTERVAL);

        // call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan1);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //call register
        String stan2 = MyUtility.getRandomNumber(6);
        result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan2, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId, WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("094", result.getBody().getResponseCode());

        // call inquiry
        inquiryDto = new WalletInquiryDto();
        String newStan1 = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan1);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("094", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void register_3wallet_fail_test() throws URISyntaxException, InterruptedException {
        //call register1
        String walletSerialId = MyUtility.getRandomNumber(12);
        String stan1 = walletSerialId.substring(walletSerialId.length() - 6);
        String nationalId = MyUtility.getRandomNumber(10);

        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan1, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId, WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);
        // call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan1);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        // call register2
        String stan2 = MyUtility.getRandomNumber(6);
        walletSerialId = MyUtility.getRandomNumber(12);
        result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan2, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId, WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        Thread.sleep(TestConstants.JOB_INTERVAL);

        // call inquiry
        inquiryDto = new WalletInquiryDto();
        newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //call register3
        String stan3 = MyUtility.getRandomNumber(6);
        walletSerialId = MyUtility.getRandomNumber(12);
        result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan3, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId, WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());

        Thread.sleep(TestConstants.JOB_INTERVAL);
        // call inquiry
        inquiryDto = new WalletInquiryDto();
        newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan2);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(ResponseConstants.APPROVED_RESPONSE_CODE, result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

        //call register4
        String stan4 = MyUtility.getRandomNumber(6);
        walletSerialId = MyUtility.getRandomNumber(12);

        result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan4, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId, WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("065", result.getBody().getResponseCode());

        // call inquiry
        inquiryDto = new WalletInquiryDto();
        newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan4);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("065", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());

    }

    @Test
    public void register_invalid_risk_level_fail_Test() throws URISyntaxException {
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = walletSerialId.substring(walletSerialId.length() - 6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId, WalletConstants.WALLET_RISK_LEVEL_3, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("110", result.getBody().getResponseCode());

        // call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("110", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());
    }

    @Test
    public void register_invalid_mobile_no_fail_Test() throws URISyntaxException {
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = walletSerialId.substring(walletSerialId.length() - 6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan, WalletConstants.IDENTIFIER_TYPE_REAL_PERSON, nationalId, WalletConstants.WALLET_RISK_LEVEL_1, "1234");

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("111", result.getBody().getResponseCode());

        // call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("111", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());
    }

    @Test
    public void register_invalid_person_type_fail_Test() throws URISyntaxException {
        String walletSerialId = MyUtility.getRandomNumber(12);
        String nationalId = MyUtility.getRandomNumber(10);
        String stan = walletSerialId.substring(walletSerialId.length() - 6);
        ResponseEntity<WalletResponseDto> result = PersonalWalletRestCalls.callRegisterRest(restTemplate, randomServerPort,
                walletSerialId, stan, "ALAKI", nationalId, WalletConstants.WALLET_RISK_LEVEL_1, TestConstants.MOBILE_NO);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("103", result.getBody().getResponseCode());

        // call inquiry
        WalletInquiryDto inquiryDto = new WalletInquiryDto();
        String newStan = MyUtility.getRandomNumber(6);
        inquiryDto.setStan(newStan);
        inquiryDto.setOriginalStan(stan);
        result = PersonalWalletRestCalls.callInquiryRest(restTemplate, randomServerPort, inquiryDto);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("103", result.getBody().getResponseCode());
        System.out.println("result = " + result.getBody().toString());
    }

}