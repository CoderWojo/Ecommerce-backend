package pl.wojo.app.ecommerce_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "address")
@Data
public class Address {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "address_line1", nullable = false, length = 512)
    private String addressLine1;

    @Column(name = "address_line_2", length = 512)
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 180)
    private String city;

    @Column(name = "country", nullable = false, length = 75)
    private String country;

    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;

    @ManyToOne(optional = false)    // relacja wymagana, Address musi mieć obiekt użytkownika w kodzie Javy
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;
}
