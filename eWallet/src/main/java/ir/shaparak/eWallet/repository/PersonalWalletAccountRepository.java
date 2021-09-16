package ir.shaparak.eWallet.repository;

import ir.shaparak.eWallet.model.PersonalWalletAccount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface PersonalWalletAccountRepository extends CrudRepository<PersonalWalletAccount, Long> {

    @Modifying
    @Query("UPDATE PersonalWalletAccount t SET t.status = :status, t.lastEditDate = :lastEditDate WHERE t.walletId = :walletId AND t.status = 0 ")
    Integer updatePersonalWalletAccountByWalletId(@Param("status") Integer status,
                                                  @Param("lastEditDate") Timestamp lastEditDate,
                                                  @Param("walletId") String walletId);

    @Query("SELECT t FROM PersonalWalletAccount t WHERE t.walletId = :walletId AND t.accountId = :accountId AND t.status = :status ")
    PersonalWalletAccount findPersonalWalletAccountByWalletIdAndAccountIdAndStatus(@Param("walletId") String walletId,
                                                                                   @Param("accountId") String accountId,
                                                                                   @Param("status") Integer status);

    @Query("SELECT count(1) FROM PersonalWalletAccount t WHERE t.walletId = :walletId AND t.status = :status ")
    Integer findPersonalWalletAccountByWalletIdAndStatus(@Param("walletId") String walletId,
                                                         @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PersonalWalletAccount t SET t.status = :status, t.lastEditDate = :lastEditDate " +
            "WHERE t.walletId = :walletId AND  t.accountId = :accountId AND t.accountType = :accountType ")
    Integer updatePersonalWalletAccountByWalletIdAndAccountIdAndAccountType(@Param("status") Integer status,
                                                                            @Param("lastEditDate") Timestamp lastEditDate,
                                                                            @Param("walletId") String walletId,
                                                                            @Param("accountId") String accountId,
                                                                            @Param("accountType") Integer accountType);

}
