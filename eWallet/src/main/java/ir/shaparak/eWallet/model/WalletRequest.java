package ir.shaparak.eWallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "w_wallet_request")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@SequenceGenerator(sequenceName = "WALLET_REQUEST_ID_SEQ", name = "WALLET_REQUEST_ID_SEQ", allocationSize = 100, initialValue = 1)
public class WalletRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WALLET_REQUEST_ID_SEQ")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "stan")
    private String stan;

    @Column(name = "original_stan")
    private String originalStan;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "original_reference_number")
    private String originalReferenceNumber;

    @Column(name = "national_type")
    private Integer nationalType;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "register_date")
    private Date registerDate;

    @Column(name = "risk_level")
    private Integer riskLevel;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "wallet_status")
    private String walletStatus;

    @Column(name = "ibans")
    private String ibans;

    @Column(name = "pans")
    private String pans;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "response_code")
    private Integer responseCode;

    @Column(name = "handled")
    private Integer handled;

    @Column(name = "step")
    private Integer step = 0;

    @Column(name = "action_codes")
    private String actionCodes;

    @Column(name = "last_exception_source")
    private Integer lastExceptionSource = 0;

    @Column(name = "last_action_code")
    private Integer lastActionCode;

    @Column(name = "try_count")
    private Integer tryCount;

    @Column(name = "max_try_count")
    private Integer maxTryCount;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_edit_date")
    private Timestamp lastEditDate;


}
