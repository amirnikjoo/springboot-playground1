package ir.shaparak.eWallet.api.dto.personal;

import lombok.Data;

@Data
public class WalletChangeStatusDto {

    private String walletId;

    private String stan;

    private String status;

    private String description;

}
