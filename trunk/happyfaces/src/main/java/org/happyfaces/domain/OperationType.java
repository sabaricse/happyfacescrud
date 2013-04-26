package org.happyfaces.domain;

/**
 * Enum for all operation types.
 * 
 * @author Ignas
 * 
 */
public enum OperationType {
    
    CREDIT(1, "operation.credit"), DEBIT(2, "operation.debit");
    
    private Integer id;
    private String label;
    
    private OperationType(Integer id, String label) {
        this.id = id;
        this.label = label;
    }
    
    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}
