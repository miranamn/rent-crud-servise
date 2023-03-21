package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalDate;


import static org.acme.config.Values.*;

/**Class level annotations*/
@Entity
@Getter
@Setter
@ToString
@Table(name = "ADVERTISING")
public class Advertising extends PanacheEntity{
    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;
    @Column(length = 30)
    @NotBlank(message=MESSAGE_TITLE)
    private String title;
    @Column(length = 50)
    @NotBlank(message=MESSAGE_ADDRESS)
    private String address;
    @Min(message=MESSAGE_CAPACITY, value = 1)
    private int capacity;
    private String description;
    @Column(name = "payment_per_hour")
    @NotNull(message=MESSAGE_PAYMENT)
    private int paymentPerHour;
    private Date data = Date.valueOf(LocalDate.now());
    @Column(name = "rent_date")
    private String rentDate;
    @Column(name = "maximum_term")
    @Min(message=MESSAGE_TERM, value = 1)
    private int maxTerm;
}
