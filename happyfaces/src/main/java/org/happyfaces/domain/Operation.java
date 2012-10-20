package org.happyfaces.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.happyfaces.domain.base.BaseEntity;

/**
 * @author Ignas
 *
 */
@Entity
@Table(name = "OPERATION")
public class Operation extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "OPERATION_NAME")
    private String operationName;
    
    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;
    
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "OPERATION_TYPE", nullable = false)
    private OperationType operationType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
}
