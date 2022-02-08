package endpoint.dao;

import endpoint.model.Price;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PriceDAOTest {
    @Autowired
    private PriceDAO repository;

    @SneakyThrows
    @Test
    public void whenSearchProductBrandByRange() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = "2020-06-14 00:00:00";
        LocalDateTime startDT = LocalDateTime.parse(str, formatter);
        List<Price> result = repository.findByDateAndProductIDAndBrandID(startDT,35455,1);
        assertEquals(1, result.size());
        assertEquals(1,result.get(0).getBrandID());
    }

    @SneakyThrows
    @Test
    public void whenSearchProductBrandByOneDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = "2021-09-15 16:00:00";
        LocalDateTime startDT = LocalDateTime.parse(str, formatter);
        List<Price> result = repository.findByDateAndProductIDAndBrandID(startDT,35455,1);
        assertEquals(0, result.size());
    }
}