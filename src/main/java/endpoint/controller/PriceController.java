package endpoint.controller;

import endpoint.dao.PriceDAO;
import endpoint.model.Price;
import endpoint.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/zara")
public class PriceController {
    @Autowired
    PriceService priceService;

    @GetMapping("/rangeandbrand")
    public ResponseEntity<Price> getProductsByDateAndBrand(@RequestParam("appDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime appDate,
                                                                @RequestParam("prod") Integer prod,
                                                                @RequestParam("brand") Integer brand) {

        try{
            Price result = priceService.getProductByDateAndBrand(appDate, prod, brand);
            return (new ResponseEntity<>((result), HttpStatus.OK));
        }catch (RuntimeException e){
            throw new RuntimeException("Se ha producido el siguiente error recuperando los datos: " + e.getMessage());
        }

    }
}
