package edu.cnm.deepdive.codebreaker.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

@Entity
public class Game {

  public static final int MAX_CODE_LENGTH = 12;
  private static final int MAX_POOL_LENGTH = 20;

  @NonNull
  @Id
  @GeneratedValue
  @Column(name = "game_id", nullable = false, updatable = false)
  private Long id;

  @NonNull
  @Column(name = "external_key", nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  private UUID key;

  @NonNull
  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Instant created;

  @Column(nullable = false, updatable = false)
  private int length;

  @NonNull
  @Column(nullable = false, updatable = false, length = MAX_POOL_LENGTH)
  private String pool;

  @NonNull
  @Column(nullable = false, updatable = false, length = MAX_CODE_LENGTH) // TODO: 2024-02-28 Investigate how database determines lengths.
  private String secretCode;

  @NonNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @NonNull
  public Long getId() {
    return id;
  }

  @NonNull
  public UUID getKey() {
    return key;
  }

  @NonNull
  public Instant getCreated() {
    return created;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  @NonNull
  public String getPool() {
    return pool;
  }

  public void setPool(@NonNull String pool) {
    this.pool = pool;
  }

  @NonNull
  public String getSecretCode() {
    return secretCode;
  }

  public void setSecretCode(@NonNull String secretCode) {
    this.secretCode = secretCode;
  }

  @NonNull
  public User getUser() {
    return user;
  }

  public void setUser(@NonNull User user) {
    this.user = user;
  }

  @PrePersist
  private void generateKey() {
    key = UUID.randomUUID();
  }

}