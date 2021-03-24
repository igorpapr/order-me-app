package com.orderme.ordermebackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"}) //https://question-it.com/questions/533939/ne-najden-serializator-dlja-klassa-orghibernateproxypojobytebuddybytebuddyinterceptor-i-ne-najdeny-svojstva-dlja-sozdanija-beanserializer
public class User implements AbstractEntity<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(unique = true, updatable = false, nullable = false)
    private UUID userId;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private Set<Order> createdOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "processingBy")
    private Set<Order> processingOrders;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(UUID userId,  String email, String password, String firstName, String lastName, Set<Order> createdOrders, Set<Order> processingOrders, Role role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdOrders = createdOrders;
        this.processingOrders = processingOrders;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Order> getCreatedOrders() {
        return createdOrders;
    }

    public void setCreatedOrders(Set<Order> createdOrders) {
        this.createdOrders = createdOrders;
    }

    public Set<Order> getProcessingOrders() {
        return processingOrders;
    }

    public void setProcessingOrders(Set<Order> processingOrders) {
        this.processingOrders = processingOrders;
    }

}
