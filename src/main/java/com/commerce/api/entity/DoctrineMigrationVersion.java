package com.commerce.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "doctrine_migration_versions")
public class DoctrineMigrationVersion {
    @Id
    @Column(name = "version", nullable = false, length = 191)
    private String version;

    @ColumnDefault("NULL::timestamp without time zone")
    @Column(name = "executed_at")
    private Instant executedAt;

    @Column(name = "execution_time")
    private Integer executionTime;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Instant getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(Instant executedAt) {
        this.executedAt = executedAt;
    }

    public Integer getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Integer executionTime) {
        this.executionTime = executionTime;
    }

}