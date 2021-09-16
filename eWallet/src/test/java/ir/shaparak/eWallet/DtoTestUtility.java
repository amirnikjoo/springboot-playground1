package ir.shaparak.eWallet;

import ir.shaparak.eWallet.api.dto.personal.PersonDto;
import ir.shaparak.eWallet.api.dto.personal.WalletRegisterRequestDto;

import java.sql.Date;

public class DtoTestUtility {
    public static WalletRegisterRequestDto getARegisterDto() {
        WalletRegisterRequestDto requestDto = new WalletRegisterRequestDto();
        requestDto.setWalletSerial("123456789013");
        requestDto.setRiskLevel(1);
        requestDto.setPostalCode(TestConstants.POSTAL_CODE);
        requestDto.setMobileNumber(TestConstants.MOBILE_NO);
        requestDto.setStan("123");
        requestDto.setPan(new String[]{"123"});
        requestDto.setIban(new String[]{"IR01"});
        requestDto.setPersonDto(getAPerson());
        return requestDto;
    }

    public static WalletRegisterRequestDto getARegisterDtoCustom(String walletSerialId, Integer riskLevel, String stan, String[] ibans, String[] pans, String personId, String personType) {
        WalletRegisterRequestDto requestDto = new WalletRegisterRequestDto();
        requestDto.setWalletSerial(walletSerialId);
        requestDto.setRiskLevel(riskLevel);
        requestDto.setPostalCode(TestConstants.POSTAL_CODE);
        requestDto.setMobileNumber(TestConstants.MOBILE_NO);
        requestDto.setStan(stan);
        requestDto.setIban(ibans);
        requestDto.setPan(pans);
        requestDto.setPersonDto(getAPerson(personId, personType));
        return requestDto;
    }

    public static WalletRegisterRequestDto getARegisterDtoCustom(String walletSerialId, Integer riskLevel, String stan, String personId, String personType, String mobileNo) {
        WalletRegisterRequestDto requestDto = new WalletRegisterRequestDto();
        requestDto.setWalletSerial(walletSerialId);
        requestDto.setRiskLevel(riskLevel);
        requestDto.setPostalCode(TestConstants.POSTAL_CODE);
        requestDto.setMobileNumber(mobileNo);
        requestDto.setStan(stan);
        requestDto.setPersonDto(getAPerson(personId, personType));
        return requestDto;
    }

    public static WalletRegisterRequestDto getARegisterDtoCustomV2(String walletSerialId, Integer riskLevel, String stan, String personId, String personType, String mobileNo, String[] ibans, String[] pans) {
        WalletRegisterRequestDto requestDto = new WalletRegisterRequestDto();
        requestDto.setWalletSerial(walletSerialId);
        requestDto.setRiskLevel(riskLevel);
        requestDto.setPostalCode(TestConstants.POSTAL_CODE);
        requestDto.setMobileNumber(mobileNo);
        requestDto.setStan(stan);
        requestDto.setIban(ibans);
        requestDto.setPan(pans);
        requestDto.setPersonDto(getAPerson(personId, personType));
        return requestDto;
    }

    public static PersonDto getAPerson() {
        PersonDto personDto = new PersonDto();
        personDto.setIdentifier(TestConstants.NATIONAL_ID);
        personDto.setIdentifierType(TestConstants.NATIONAL_TYPE);
        personDto.setRegisterDate(new Date(System.currentTimeMillis()));
        return personDto;
    }

    public static PersonDto getAPerson(String nationalId, String idType) {
        PersonDto personDto = new PersonDto();
        personDto.setIdentifier(nationalId);
        personDto.setIdentifierType(idType);
        personDto.setRegisterDate(new Date(System.currentTimeMillis()));
        return personDto;
    }
}
