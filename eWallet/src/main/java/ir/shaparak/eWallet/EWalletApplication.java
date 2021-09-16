package ir.shaparak.eWallet;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ImportResource({"classpath:applicationContext.xml"})
@EnableJpaRepositories
@EnableScheduling
//@EnableCaching
//@Import({JAVACacheConfig.class})
//@EnableJpaRepositories(basePackages="ir.shaparak.eWallet", entityManagerFactoryRef="entityManagerFactory")
//@ComponentScan(basePackages = "ir.shaparak.eWallet")
public class EWalletApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(EWalletApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.findUserById(1);
        if (user == null) {
            userRepository.save(new User(1, "admin", "admin", "111111", "222", "shaparak", "shaparak1", WalletConstants.USER_TYPE_ADMIN));
            System.out.println("admin user insert successfully");

            userRepository.save(new User(2, "shap", "shap", "222222", "222", "shaparak", "shaparak2", WalletConstants.USER_TYPE_SHAPARAK_OPERATION));
            System.out.println("shaparak user insert successfully");

            userRepository.save(new User(3, "user", "user", "333333", "222", "saman", "sep", WalletConstants.USER_TYPE_BANK_OPERATOR));
            System.out.println("bank-operator user insert successfully");
        }
    }
}
