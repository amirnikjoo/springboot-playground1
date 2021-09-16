package ir.shaparak.eWallet.api.dto.personal;

import lombok.Data;

@Data
public class WalletChangeRiskLevelDto {

    private String walletId;

    private String stan;

    private Integer riskLevel;

    private String[] iban;

    private String[] pan;
}
