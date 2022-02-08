package endpoint.dao;

import endpoint.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceDAO extends JpaRepository<Price, Long> {

    @Query(value = "SELECT * FROM PRICES p WHERE p.PRODUCT_ID = ?2 AND p.BRAND_ID = ?3 AND p.START_DATE <= ?1 AND p.END_DATE >= ?1", nativeQuery=true)
    List<Price> findByDateAndProductIDAndBrandID(@Param("appDate") LocalDateTime appDate,
                                                 @Param("productID") Integer productID,
                                                 @Param("brandID") Integer brandID);
}
