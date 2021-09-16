package ir.shaparak.eWallet.repository;

import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.model.PersonalWallet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Repository
public interface PersonalWalletRepository extends CrudRepository<PersonalWallet, Long> {

/*
    @Query("SELECT t FROM PersonalWallet t WHERE t.userId = :userId AND t.walletSerial = :walletSerial ")
    List<PersonalWallet> findAllByUserIdAndWalletSerial(@Param("userId") Integer userId,
                                                        @Param("walletSerial") Long walletSerial);
*/
    //todo: check duplicate by wallet_serial


    @Query("SELECT t FROM PersonalWallet t WHERE t.userId = :userId AND t.walletId = :walletId ")
    PersonalWallet findByUserIdAndWalletId(@Param("userId") Integer userId,
                                           @Param("walletId") String walletId);

    @Query("SELECT t FROM PersonalWallet t WHERE t.userId = :userId AND t.walletSerial = :walletSerial ")
    PersonalWallet findByUserIdAndWalletSerial(@Param("userId") Integer userId,
                                               @Param("walletSerial") Long walletSerial);

    @Query("SELECT count(*)  FROM PersonalWallet t WHERE t.userId = :userId AND t.walletSerial = :walletSerial group by t.userId, t.walletSerial")
    Integer countByUserIdAndWalletSerial(@Param("userId") Integer userId,
                                         @Param("walletSerial") Long walletSerial);

    @Query("SELECT count(*) FROM PersonalWallet t WHERE t.nationalId = :nationalId AND t.nationalType = :nationalType group by t.nationalId , t.nationalType")
    Integer countByNationalIdAndNationalType(@Param("nationalId") String nationalId,
                                             @Param("nationalType") Integer nationalType);

    @Modifying
    @Query("UPDATE PersonalWallet t SET t.riskLevel = :riskLevel, t.riskLevel2_date = :riskLevel2Date, t.lastEditDate = :lastEditDate WHERE t.walletId = :walletId")
    Integer updateRiskLevelById(@Param("riskLevel") Integer riskLevel,
                                @Param("riskLevel2Date") Date riskLevel2Date,
                                @Param("lastEditDate") Timestamp lastEditDate,
                                @Param("walletId") String walletId);

    @Modifying
    @Query("UPDATE PersonalWallet t SET t.status = :status, t.lastEditDate = :lastEditDate WHERE t.walletId = :walletId")
    Integer updateStatusById(@Param("status") Integer status,
                             @Param("lastEditDate") Timestamp lastEditDate,
                             @Param("walletId") String walletId);

    @Modifying
    @Query("UPDATE PersonalWallet t SET t.mobileNumber = :mobileNo, t.lastEditDate = :lastEditDate WHERE t.walletId = :walletId")
    Integer updateMobileNoById(@Param("mobileNo") String mobileNo,
                               @Param("lastEditDate") Timestamp lastEditDate,
                               @Param("walletId") String walletId);

}
