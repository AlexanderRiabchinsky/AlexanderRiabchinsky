package main.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "moderation_status")
public class ModerationStatus {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
}