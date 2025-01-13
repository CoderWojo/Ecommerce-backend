package pl.wojo.app.ecommerce_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "short_description", nullable = false)
    private String short_description;

    @Column(name = "long_description")
    private String long_description;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToOne(mappedBy = "product", orphanRemoval = true)
    private Inventory inventory;
}
