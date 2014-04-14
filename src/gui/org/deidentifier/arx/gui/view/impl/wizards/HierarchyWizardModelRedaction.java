package org.deidentifier.arx.gui.view.impl.wizards;

import org.deidentifier.arx.DataType;
import org.deidentifier.arx.aggregates.HierarchyBuilder;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased.Order;

public class HierarchyWizardModelRedaction<T> extends HierarchyWizardModelAbstract<T> {

    private Order redactionOrder = Order.RIGHT_TO_LEFT;
    private Order alignmentOrder = Order.LEFT_TO_RIGHT;
    private char  paddingCharacter = ' ';
    private char  redactionCharacter = '*';
    public HierarchyWizardModelRedaction(DataType<T> dataType, String[] data) {
        super(data);
        this.update();
    }

    public Order getAlignmentOrder() {
        return alignmentOrder;
    }

    @Override
    public HierarchyBuilderRedactionBased<T> getBuilder() {
        return HierarchyBuilderRedactionBased.create(  alignmentOrder, 
                                                       redactionOrder, 
                                                       paddingCharacter, 
                                                       redactionCharacter);
    }

    public char getPaddingCharacter() {
        return paddingCharacter;
    }

    public char getRedactionCharacter() {
        return redactionCharacter;
    }

    public Order getRedactionOrder() {
        return redactionOrder;
    }

    @Override
    public void parse(HierarchyBuilder<T> _builder) {
        
        if (!(_builder instanceof HierarchyBuilderRedactionBased)) {
            return;
        }
        HierarchyBuilderRedactionBased<T> builder = (HierarchyBuilderRedactionBased<T>)_builder; 
        this.redactionOrder = builder.getRedactionOrder();
        this.alignmentOrder = builder.getAligmentOrder();
        this.redactionCharacter = builder.getRedactionCharacter();
        this.paddingCharacter = builder.getPaddingCharacter();
        this.update();
    }

    public void setAlignmentOrder(Order alignmentOrder) {
        if (alignmentOrder != this.alignmentOrder) {
            this.alignmentOrder = alignmentOrder;
            this.update();
        }
    }

    public void setPaddingCharacter(char paddingCharacter) {
        if (this.paddingCharacter != paddingCharacter){
            this.paddingCharacter = paddingCharacter;
            this.update();
        }
    }

    public void setRedactionCharacter(char redactionCharacter) {
        if (this.redactionCharacter != redactionCharacter){
            this.redactionCharacter = redactionCharacter;
            this.update();
        }
    }

    public void setRedactionOrder(Order redactionOrder) {
        if (redactionOrder != this.redactionOrder){
            this.redactionOrder = redactionOrder;
            this.update();
        }
    }

    @Override
    protected void internalUpdate() {
        super.hierarchy = null;
        super.error = null;
        super.groupsizes = null;
        
        HierarchyBuilderRedactionBased<T> builder = getBuilder();
        try {
            super.groupsizes = builder.prepare(data);
        } catch(Exception e){
            super.error = "Unknown error";
            return;
        }
        
        try {
            super.hierarchy = builder.build();
        } catch(Exception e){
            super.error = "Unknown error";
            return;
        }
    }
}