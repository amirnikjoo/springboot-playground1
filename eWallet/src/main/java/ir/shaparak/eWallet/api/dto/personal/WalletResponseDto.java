package ir.shaparak.eWallet.api.dto.personal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletResponseDto {
    private String stan;

    private String referenceNumber;

    private String walletId;

    private String responseCode;

    private String description;

    private String exceptionSource;
}
