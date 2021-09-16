package ir.shaparak.eWallet.api.dto.personal;

import lombok.Data;

@Data
public class WalletChangeAccountDto {

    private String walletId;

    private String stan;

    private String accountType;

    private String accountId;

}
