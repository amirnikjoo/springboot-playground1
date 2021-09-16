package ir.shaparak.eWallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "w_user", schema = "wallet", uniqueConstraints = {@UniqueConstraint(columnNames = {"bank_iin", "provider"})})
@NoArgsConstructor
@AllArgsConstructor
@Data
//@SequenceGenerator(sequenceName = "USER_ID_SEQ", name = "USER_ID_SEQ", allocationSize = 100, initialValue = 1)
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "bank_iin")
    private String bankIin;

    @Column(name = "provider")
    private String provider;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "user_type")
    private Integer userType;

}
