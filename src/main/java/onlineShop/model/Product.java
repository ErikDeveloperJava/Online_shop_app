package onlineShop.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private int id;

    private String title;

    private String description;

    private double price;

    private int quantity;

    private String imgUrl;

    private Date createdDate;

    private User user;

    private List<AttributeValue> values;
}
