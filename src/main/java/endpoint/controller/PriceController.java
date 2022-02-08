package endpoint.controller;


import ExceptionsHandler.PriceAPIException;
import ExceptionsHandler.ResourceNotFoundException;
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
    public ResponseEntity<Object> getProductsByDateAndBrand(@RequestParam("appDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime appDate,
                                                                @RequestParam("prod") Integer prod,
                                                                @RequestParam("brand") Integer brand) {

        try {
            Price result = priceService.getProductByDateAndBrand(appDate, prod, brand);
            return (result != null) ? (new ResponseEntity<>((result), HttpStatus.OK)) :
                    (new ResponseEntity<>("Criteria with date: "+
                            appDate.toString()+", and Product ID: "+
                            prod+", and Brand ID: "+
                            brand+", "+
                            "did not produce results", HttpStatus.OK));
        }catch (ResourceNotFoundException ex){
            throw new ResourceNotFoundException("Resource","api",1);
        }catch (PriceAPIException ex){
            throw new PriceAPIException(HttpStatus.BAD_REQUEST,"Malformed URL");
        }
    }
}
