package ir.shaparak.eWallet.api.dto.personal;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class WalletRegisterRequestDto /*extends WalletRegisterDto*/ {
    @Size(min = 12, max = 26)
    private String walletSerial;

    private String[] iban;

    private String[] pan;

    //    @Size(min = 10 ,max = 10)
    private String postalCode;

    //    @Max(20)
    private String stan;

    private Integer riskLevel;

    //    @Min(12)
    private String mobileNumber;

    private PersonDto personDto;
}
