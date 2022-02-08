package endpoint.service;

import endpoint.dao.PriceDAO;
import endpoint.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    PriceDAO priceDAO;

    /*****************************************************
     * Filter task implemented in this layer
     * due to an incompatibility of HQL
     * Granmmar Error preparing query when request is made
     * Temporal solution> implement the filter in this layer
    ******************************************************/
    public Price getProductByDateAndBrand(LocalDateTime appDate, Integer prodID, Integer brandID) {
        Price result;
        List<Price> query = priceDAO.findByDateAndProductIDAndBrandID(appDate,prodID,brandID);
        if (query.size() > 1)
            result = query.parallelStream().max(Comparator.comparing(Price::getPriority)).get();
        else
            result = query.get(0);
        return result;
    }
}
