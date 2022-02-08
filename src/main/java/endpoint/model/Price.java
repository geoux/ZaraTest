package endpoint.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "PRICES")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name="BRAND_ID")
    private Integer brandID;

    @Column(name="START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name="END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name="PRICE_LIST")
    private Integer priceList;

    @Column(name="PRODUCT_ID")
    private Integer productID;

    @Column(name="PRIORITY")
    private Integer priority;

    @Column(name="PROD_PRICE")
    private Float prodPrice;

    @Column(name="CURR")
    private String curr;

    @Override
    public String toString() {
        return "Price{" +
                "brandID=" + brandID +
                ", productID=" + productID +
                ", price=" + prodPrice +
                ", curr='" + curr + '\'' +
                '}';
    }
}