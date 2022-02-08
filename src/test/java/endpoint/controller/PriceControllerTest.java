package endpoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import endpoint.model.Price;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /*****************************
     * Test that the response is a message indicating non existing product
     * whereas the response status in OK.
     * @throws Exception
     *****************************/
    @Test
    public void testInexistentRow() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/zara/rangeandbrand")
                        .param("appDate","2022-09-14T10:00:00")
                        .param("prod","32559")
                        .param("brand","1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("did not produce results"));
    }

    @Test
    public void testBadRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/zara/rangeandbrand")
                        .param("appDate","")
                        .param("prod","")
                        .param("brand","")
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(400,mvcResult.getResponse().getStatus());
    }

    /**************************************
     * ParameterizedTest to run 5 possible scenarios
     * @param appDate
     * @param prodID
     * @param brandID
     * @param expectedID
     * @throws Exception
     *
     * Scenario 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     * Expected Response:
     * [{"id":1,"brandID":1,"startDate":"2020-06-14","endDate":"2020-12-31","priceList":1,"productID":35455,"priority":0,"prodPrice":35.5,"curr":"EUR"}]
     *
     * Scenario 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     * Expected Response:
     * [{"id":1,"brandID":1,"startDate":"2020-06-14","endDate":"2020-12-31","priceList":1,"productID":35455,"priority":0,"prodPrice":35.5,"curr":"EUR"},
     * {"id":2,"brandID":1,"startDate":"2020-06-14","endDate":"2020-06-14","priceList":1,"productID":35455,"priority":1,"prodPrice":25.45,"curr":"EUR"}]
     * Selected id=2 with higher priority
     *
     * Scenario 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
     * Expected Response:
     * [{"id":1,"brandID":1,"startDate":"2020-06-14","endDate":"2020-12-31","priceList":1,"productID":35455,"priority":0,"prodPrice":35.5,"curr":"EUR"}]
     *
     * Scenario 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
     * Expected Response:
     * [{"id":1,"brandID":1,"startDate":"2020-06-14","endDate":"2020-12-31","priceList":1,"productID":35455,"priority":0,"prodPrice":35.5,"curr":"EUR"},
     * {"id":3,"brandID":1,"startDate":"2020-06-15","endDate":"2020-06-15","priceList":3,"productID":35455,"priority":1,"prodPrice":30.5,"curr":"EUR"}]
     * Selected id=3 with higher priority
     *
     * Scenario 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)
     * Expected Response:
     * [{"id":1,"brandID":1,"startDate":"2020-06-14","endDate":"2020-12-31","priceList":1,"productID":35455,"priority":0,"prodPrice":35.5,"curr":"EUR"},
     * {"id":4,"brandID":1,"startDate":"2020-06-15","endDate":"2020-12-31","priceList":4,"productID":35455,"priority":1,"prodPrice":38.95,"curr":"EUR"}]
     * Selected id=4 with higher priority
     */
    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00,35455,1,1",
            "2020-06-14T16:00:00,35455,1,2",
            "2020-06-14T21:00:00,35455,1,1",
            "2020-06-15T10:00:00,35455,1,3",
            "2020-06-16T21:00:00,35455,1,4"
    })
    public void testScenariosForRequest(String appDate, String prodID, String brandID, String expectedID) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/zara/rangeandbrand")
                        .param("appDate",appDate)
                        .param("prod",prodID)
                        .param("brand",brandID)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Price response = objectMapper.readValue(content, Price.class);

        assertEquals(expectedID, response.getId().toString());
    }

}