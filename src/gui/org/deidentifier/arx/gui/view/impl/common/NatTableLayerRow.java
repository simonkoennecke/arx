//package org.deidentifier.arx.gui.view.impl.common;
//
//import org.eclipse.nebula.widgets.nattable.NatTable;
//import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
//import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
//import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
//import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
//import org.eclipse.nebula.widgets.nattable.layer.LayerUtil;
//import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
//import org.eclipse.nebula.widgets.nattable.layer.cell.LayerCell;
//import org.eclipse.nebula.widgets.nattable.summaryrow.SummaryRowLayer;
//
///**
// * Adds additional rows at the end that fill up the available height
// */
//public class NatTableLayerRow extends AbstractLayerTransform implements IUniqueIndexLayer {
//
//    private NatTableContext context;
//    
//    public NatTableLayerRow(NatTableContext context, IUniqueIndexLayer underlyingDataLayer) {
//        super(underlyingDataLayer);
//        this.context = context;
//        addConfiguration(new NatTableLayerConfiguration());
//    }
//
//    @Override
//    public ILayerCell getCellByPosition(int columnPosition, int rowPosition) {
//        if (isActive() && isAdditionalRowPosition(rowPosition)) { return new LayerCell(this, columnPosition, rowPosition); }
//        return super.getCellByPosition(columnPosition, rowPosition);
//    }
//
//    @Override
//    public int getColumnPositionByIndex(int columnIndex) {
//        if (columnIndex >= 0 && columnIndex < getColumnCount()) {
//            return columnIndex;
//        } else {
//            return -1;
//        }
//    }
//
//    @Override
//    public LabelStack getConfigLabelsByPosition(int columnPosition, int rowPosition) {
//        if (isActive() && isAdditionalRowPosition(rowPosition)) { 
//           return new LabelStack(SummaryRowLayer.DEFAULT_SUMMARY_COLUMN_CONFIG_LABEL_PREFIX +
//                                                                       columnPosition, SummaryRowLayer.DEFAULT_SUMMARY_ROW_CONFIG_LABEL); 
//           }
//        return super.getConfigLabelsByPosition(columnPosition, rowPosition);
//    }
//
//    /**
//     * NOTE: Since this is a {@link IUniqueIndexLayer} sitting close to the
//     * {@link DataLayer}, columnPosition == columnIndex
//     */
//    @Override
//    public Object getDataValueByPosition(final int columnPosition, final int rowPosition) {
//        if (isActive() && isAdditionalRowPosition(rowPosition)) {
//            return "";
//        }
//        return super.getDataValueByPosition(columnPosition, rowPosition);
//    }
//
//    @Override
//    public int getHeight() {
//        return isActive() ? super.getHeight() + getGapHeight() : super.getHeight();
//    }
//
//    @Override
//    public int getPreferredHeight() {
//        return isActive() ? super.getPreferredHeight() + getGapHeight() : super.getPreferredHeight();
//    }
//
//    @Override
//    public int getPreferredRowCount() {
//        return getRowCount();
//    }
//
//    @Override
//    public int getRowCount() {
//        return isActive() ? super.getRowCount() + getAdditionalRowCount() : super.getRowCount();
//    }
//
//    @Override
//    public int getRowHeightByPosition(int rowPosition) {
//        if (isActive() && isAdditionalRowPosition(rowPosition)) { return DataLayer.DEFAULT_ROW_HEIGHT; }
//        return super.getRowHeightByPosition(rowPosition);
//    }
//
//    @Override
//    public int getRowIndexByPosition(int rowPosition) {
//        if (isActive() && isAdditionalRowPosition(rowPosition)) { return rowPosition; }
//        return super.getRowIndexByPosition(rowPosition);
//    }
//
//    @Override
//    public int getRowPositionByIndex(int rowIndex) {
//        if (rowIndex >= 0 && rowIndex < getRowCount()) {
//            return rowIndex;
//        } else {
//            return -1;
//        }
//    }
//    @Override
//    public int getRowPositionByY(int y) {
//        return LayerUtil.getRowPositionByY(this, y);
//    }
//    private int getAdditionalRowCount() {
//        return (int)Math.floor((double)getGapHeight()/(double)DataLayer.DEFAULT_ROW_HEIGHT);
//    }
//    
//    private int getGapHeight() {
//        NatTable table = this.context.getTable();
//        return table != null ? table.getSize().y - super.getHeight() : 0;
//    }
//
//    private boolean isActive() {
//        return getGapHeight()>0;
//    }
//
//    private boolean isAdditionalRowPosition(int rowPosition) {
//        return super.getRowIndexByPosition(rowPosition)==-1;
//    }
//}
