package ir.shaparak.eWallet.repository;

import ir.shaparak.eWallet.model.WalletRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WalletRequestRepository extends CrudRepository<WalletRequest, Long> {

    @Query(value = "SELECT w FROM WalletRequest w " +
            "WHERE w.handled = :handled " +
            "and w.serviceId = :serviceId ")
    List<WalletRequest> getWalletRequestByHandledAndServiceId(
            @Param("handled") Integer handled,
            @Param("serviceId") Integer serviceId);

    @Query(value = "SELECT w FROM WalletRequest w " +
            "WHERE w.userId = :userId " +
            "and w.referenceNumber = :referenceNumber ")
    WalletRequest findByUserIdAndRefNo(
            @Param("userId") Integer userId,
            @Param("referenceNumber") String referenceNumber);

    @Query(value = "SELECT w FROM WalletRequest w " +
            "WHERE w.userId = :userId " +
            "and w.stan = :stan ")
    WalletRequest findByUserIdAndStan(
            @Param("userId") Integer userId,
            @Param("stan") String stan);

    @Transactional
    @Modifying
    @Query(value = "UPDATE w_wallet_request w " +
            " SET w.handled = :handled " +
            " ,w.try_count = :tryCount" +
            " ,w.last_edit_date = :editDate" +
            " WHERE w.id = :id ", nativeQuery = true)
    Integer updateWalletRequestById(
            @Param("handled") Integer handled,
            @Param("tryCount") Integer tryCount,
            @Param("editDate") Timestamp editDate,
            @Param("id") Long id);

    @Modifying()
    @Query(value = "UPDATE WalletRequest w " +
            " SET w.handled = :handled, " +
            " w.tryCount = :tryCount," +
            " w.lastEditDate = :lastEditDate" +
            " WHERE w.id = :id ")
    Integer updateWalletRequestById2(
            @Param("handled") Integer handled,
            @Param("tryCount") Integer tryCount,
            @Param("lastEditDate") Timestamp lastEditDate,
            @Param("id") Long id);

    @Modifying()
    @Query(value = "UPDATE WalletRequest w " +
            " SET w.step = :step, " +
            " w.lastEditDate = :lastEditDate" +
            " WHERE w.id = :id ")
    Integer incrementWalletRequestStepById(
            @Param("step") Integer step,
            @Param("lastEditDate") Timestamp lastEditDate,
            @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE w_wallet_request w " +
            " SET w.handled = :handled " +
            " ,w.try_count = :tryCount" +
            " ,w.action_codes = :actionCodes" +
            " ,w.last_edit_date = :editDate" +
            " WHERE w.id = :id ", nativeQuery = true)
    Integer updateHandleWalletRequestById(
            @Param("handled") Integer handled,
            @Param("tryCount") Integer tryCount,
            @Param("actionCodes") String actionCodes,
            @Param("editDate") Timestamp editDate,
            @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE WalletRequest w " +
            " SET w.handled = :handled " +
            " ,w.tryCount = :tryCount" +
            " ,w.actionCodes = :actionCodes" +
            " ,w.lastActionCode = :lastActionCode" +
            " ,w.lastExceptionSource = :lastExceptionSource" +
            " ,w.lastEditDate = :editDate" +
            " WHERE w.id = :id ")
    Integer changeWalletRequestByIdV2(
            @Param("handled") Integer handled,
            @Param("tryCount") Integer tryCount,
            @Param("actionCodes") String actionCodes,
            @Param("lastActionCode") Integer lastActionCode,
            @Param("lastExceptionSource") Integer lastExceptionSource,
            @Param("editDate") Timestamp editDate,
            @Param("id") Long id);

}
