package ir.shaparak.eWallet.repository;

import ir.shaparak.eWallet.model.User;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {

//    @Cacheable(value ="findUserByUsernameAndPassword" , key = "#username")
    @Query("SELECT u FROM User u WHERE u.username = :username and u.password = :password ")
//    @Query(value = "SELECT * FROM w_user u WHERE u.username = :username and u.password = :password " , nativeQuery = true )
    User findUserByUsernameAndPassword(@Param("username") String username,
                                       @Param("password") String password);


    @Query("SELECT u FROM User u WHERE u.id = :id ")
    User findUserById(@Param("id") Integer id);

}
