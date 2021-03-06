/*
 * ARX: Powerful Data Anonymization
 * Copyright (C) 2012 - 2014 Florian Kohlmayer, Fabian Prasser
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.deidentifier.arx.gui.view.impl.common.table;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.LayerUtil;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.layer.cell.LayerCell;

/**
 * Adds additional rows at the end that fill up the available height
 * @author Fabian Prasser
 */
public class LayerRowFillLayout extends CTLayer implements IUniqueIndexLayer {

    public LayerRowFillLayout(IUniqueIndexLayer underlyingDataLayer, CTConfiguration config, CTContext context) {
        super(underlyingDataLayer, config, context);
        addConfiguration(new StyleConfigurationFillLayout(config));
    }

    @Override
    public ILayerCell getCellByPosition(int columnPosition, int rowPosition) {
        if (isActive() && isAdditionalRowPosition(rowPosition)) { return new LayerCell(this, columnPosition, rowPosition); }
        return super.getCellByPosition(columnPosition, rowPosition);
    }

    @Override
    public int getColumnPositionByIndex(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < getColumnCount()) {
            return columnIndex;
        } else {
            return -1;
        }
    }

    @Override
    public LabelStack getConfigLabelsByPosition(int columnPosition, int rowPosition) {
        if (isActive() && isAdditionalRowPosition(rowPosition)) {
            return new LabelStack(StyleConfigurationFillLayout.DEFAULT_FILL_LAYOUT_CELL_CONFIG_LABEL);
        } else {
            return super.getConfigLabelsByPosition(columnPosition, rowPosition);
        }
    }

    /**
     * NOTE: Since this is a {@link IUniqueIndexLayer} sitting close to the
     * {@link DataLayer}, columnPosition == columnIndex
     */
    @Override
    public Object getDataValueByPosition(final int columnPosition, final int rowPosition) {
        if (isActive() && isAdditionalRowPosition(rowPosition)) {
            return "";
        } else {
            return super.getDataValueByPosition(columnPosition, rowPosition);
        }
    }

    @Override
    public int getHeight() {
        return isActive() ? super.getHeight() + getGapHeight() : super.getHeight();
    }

    @Override
    public int getPreferredHeight() {
        return isActive() ? super.getPreferredHeight() + getGapHeight() : super.getPreferredHeight();
    }

    @Override
    public int getPreferredRowCount() {
        return getRowCount();
    }

    @Override
    public int getRowCount() {
        return isActive() ? super.getRowCount() + getAdditionalRowCount() : super.getRowCount();
    }

    @Override
    public int getRowHeightByPosition(int rowPosition) {
        if (isActive() && isAdditionalRowPosition(rowPosition)) { return DataLayer.DEFAULT_ROW_HEIGHT; }
        return super.getRowHeightByPosition(rowPosition);
    }

    @Override
    public int getRowIndexByPosition(int rowPosition) {
        if (isActive() && isAdditionalRowPosition(rowPosition)) { return rowPosition; }
        return super.getRowIndexByPosition(rowPosition);
    }

    @Override
    public int getRowPositionByIndex(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < getRowCount()) {
            return rowIndex;
        } else {
            return -1;
        }
    }
    @Override
    public int getRowPositionByY(int y) {
        return LayerUtil.getRowPositionByY(this, y);
    }
    private int getAdditionalRowCount() {
        return getGapHeight()/DataLayer.DEFAULT_ROW_HEIGHT;
    }
    
    private int getGapHeight() {
        NatTable table = getContext().getTable();
        if (table.isDisposed()) return 0;
        return table != null ? table.getSize().y - DataLayer.DEFAULT_ROW_HEIGHT - super.getHeight() : 0;
    }

    private boolean isActive() {
        boolean result = getGapHeight()>0;
        getContext().setRowExpanded(result);
        return result;
    }

    private boolean isAdditionalRowPosition(int rowPosition) {
        return super.getRowIndexByPosition(rowPosition)==-1;
    }
}
