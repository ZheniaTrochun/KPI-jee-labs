package com.yevhenii.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.MONTHS;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "tokenHash" })
})
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String tokenHash;

    private String description;

    private String ipAddress;

    private Instant created;
    private Instant expiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Token(Long id, String tokenHash, String description, String ipAddress,
                 Instant created, Instant expiration, User user) {
        this.id = id;
        this.tokenHash = tokenHash;
        this.description = description;
        this.ipAddress = ipAddress;
        this.created = created;
        this.expiration = expiration;
        this.user = user;
    }

    public Token() {
    }

    @PrePersist
    public void  generateExpiration() {
        created = Instant.now();

        if (expiration == null)
            expiration = created.plus(1, MONTHS);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
