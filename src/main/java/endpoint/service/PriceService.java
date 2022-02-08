package endpoint.service;

import endpoint.dao.PriceDAO;
import endpoint.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
        List<Price> query = priceDAO.findByDateAndProductIDAndBrandID(appDate,prodID,brandID);
        if(query.isEmpty())
            return null;
        else
            return (query.size() > 1)?query.stream().max(Comparator.comparing(Price::getPriority)).get():query.get(0);
    }
}
