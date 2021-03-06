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

package org.deidentifier.arx.metric.v2;

import org.deidentifier.arx.metric.InformationLoss;

/**
 * This class implements an information loss which can be represented as a
 * decimal number per quasi-identifier
 * 
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
class ILMultiDimensionalSum extends AbstractILMultiDimensionalReduced {

    /** SVUID */
    private static final long serialVersionUID = 6456243227046629659L;

    /**
     * Creates a new instance
     * 
     * @param values
     * @param weights
     */
    ILMultiDimensionalSum(final double[] values, final double[] weights) {
        super(values, weights);
    }

    @Override
    public InformationLoss<double[]> clone() {
        return new ILMultiDimensionalSum(getValues(),
                                         getWeights());
    }

    @Override
    protected double getAggregate() {
        double[] values = getValues();
        double[] weights = getWeights();
        double result = 0d;
        for (int i = 0; i < values.length; i++) {
            result += values[i] * weights[i];
        }
        return result;
    }
}
