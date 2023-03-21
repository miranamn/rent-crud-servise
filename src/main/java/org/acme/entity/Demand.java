package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.sql.Date;
import java.time.LocalDate;


import static org.acme.config.Values.*;

/**Class level annotations*/
@Entity
@Getter
@Setter
@ToString
@Table(name = "DEMANDS")
public class Demand extends PanacheEntity{
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "advertising_id")
    private Advertising advertising;
    private Date data = Date.valueOf(LocalDate.now());
    private String status;
    @Column(length = 30)
    @NotBlank(message=MESSAGE_TITLE)
    private String title;
    @Column(name = "visitor_count")
    @Min(message=MESSAGE_VISITOR_COUNT, value = 1)
    private int visitorCount;
    private String description;
    @Column(name = "rent_time")
    @Min(message=MESSAGE_TERM, value = 1)
    private int rentTime;
}
