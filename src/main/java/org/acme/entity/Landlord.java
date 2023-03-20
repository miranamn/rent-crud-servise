package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static org.acme.config.Values.*;

/**Class level annotations*/
@Entity
@Getter
@Setter
@ToString
@Table(name = "LANDLORDS")
public class Landlord extends PanacheEntity {
    @Column(name = "first_name", length = 20)
    @NotBlank(message=MESSAGE_FIRST_NAME)
    private String firstName;
    @Column(name = "second_name", length = 20)
    @NotBlank(message=MESSAGE_SECOND_NAME)
    private String secondName;
    @Column(length = 50, unique = true)
    @NotBlank(message=MESSAGE_MAIL_NAME)
    private String mail;
    @Column(length = 20)
    @NotBlank(message=MESSAGE_LOGIN_NAME)
    private String login;
    @Column(length = 20)
    @NotBlank(message=SECURITY_PASSWORD)
    @Size(min=6)
    private String password;
    @Column(length = 11)
    private String phone;

}