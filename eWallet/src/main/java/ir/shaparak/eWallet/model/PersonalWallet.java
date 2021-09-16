package ir.shaparak.eWallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "w_personal_wallet", schema = "wallet")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@SequenceGenerator(sequenceName = "WALLET_ID_SEQ", name = "WALLET_ID_SEQ", allocationSize = 100, initialValue = 1)
public class PersonalWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WALLET_ID_SEQ")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "wallet_serial")
    private Long walletSerial;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "national_type")
    private Integer nationalType;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "risk_level")
    private Integer riskLevel;

    @Column(name = "risk_level2_date")
    private Date riskLevel2_date;

    @Column(name = "status")
    private Integer status;

    @Column(name = "shaparak_status")
    private Integer shaparakStatus;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_edit_date")
    private Timestamp lastEditDate;

}
