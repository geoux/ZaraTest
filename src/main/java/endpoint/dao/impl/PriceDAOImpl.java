package endpoint.dao.impl;

import endpoint.dao.PriceDAO;
import endpoint.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public abstract class PriceDAOImpl implements PriceDAO {
    @Override
    public List<Price> findByDateAndProductIDAndBrandID(LocalDateTime appDate, Integer productID, Integer brandID) {
        return null;
    }
}
