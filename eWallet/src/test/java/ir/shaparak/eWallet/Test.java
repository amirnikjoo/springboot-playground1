package ir.shaparak.eWallet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.sql.Date;

public class Test {

    public static void main(String[] args) throws JsonProcessingException {
        String a = "\"bDate\"" + ":" + "\"2006/01/02\"";
        System.out.println(a);
//        AlakiDto dto = new AlakiDto();
//        ObjectMapper mapper = new ObjectMapper();
//        dto = mapper.readValue(a, AlakiDto.class);

//        System.out.println(dto.toString());
    }

}
