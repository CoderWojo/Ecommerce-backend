package pl.wojo.app.ecommerce_backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // usuń encję Inventory, gdy Product stracił referencję do niego
    // każdy produkt występuje 1 raz i jest unikatowy
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, optional = false) 
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
