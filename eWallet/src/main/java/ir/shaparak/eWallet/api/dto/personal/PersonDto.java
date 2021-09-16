package ir.shaparak.eWallet.api.dto.personal;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class PersonDto {

    @NotNull
    private String identifierType;

    @NotNull
    private String identifier;

    @NotNull
    private Date registerDate;

}
