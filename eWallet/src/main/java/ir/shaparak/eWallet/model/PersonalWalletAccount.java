package ir.shaparak.eWallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "w_personal_wallet_account", schema = "wallet")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//@SequenceGenerator(sequenceName = "WALLET_ACCOUNT_ID_SEQ", name = "WALLET_ACCOUNT_ID_SEQ", allocationSize = 1, initialValue = 1)
public class PersonalWalletAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WALLET_ACCOUNT_ID_SEQ")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "national_type")
    private Integer nationalType;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "account_type")
    private Integer accountType;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_edit_date")
    private Timestamp lastEditDate;

}
