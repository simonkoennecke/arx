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

import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.DataDefinition;
import org.deidentifier.arx.framework.check.groupify.HashGroupifyEntry;
import org.deidentifier.arx.framework.check.groupify.IHashGroupify;
import org.deidentifier.arx.framework.data.Data;
import org.deidentifier.arx.framework.data.GeneralizationHierarchy;
import org.deidentifier.arx.framework.lattice.Node;
import org.deidentifier.arx.metric.InformationLoss;

import com.carrotsearch.hppc.IntIntOpenHashMap;

/**
 * This class provides an implementation of the non-uniform entropy
 * metric. TODO: Add reference
 * 
 * @author Fabian Prasser
 * @author Florian Kohlmayer
 */
public class MetricMDNUNMEntropyPrecomputed extends MetricMDNUEntropyPrecomputed {

    /** SVUID*/
    private static final long serialVersionUID = -7428794463838685004L;

    /**
     * Creates a new instance
     */
    protected MetricMDNUNMEntropyPrecomputed() {
        super(false, false, AggregateFunction.SUM);
    }
    
    /**
     * Creates a new instance
     * @param function
     */
    protected MetricMDNUNMEntropyPrecomputed(AggregateFunction function){
        super(false, false, function);
    }
    
    @Override
    public InformationLoss<?> createMaxInformationLoss() {
        // TODO: Implement
        return super.createMaxInformationLoss();
    }
    
    @Override
    public InformationLoss<?> createMinInformationLoss() {
        // TODO: Implement
        return super.createMinInformationLoss();
    }

    @Override
    public String toString() {
        return "Non-monotonic non-uniform entropy";
    }


    @Override
    protected ILMultiDimensionalWithBound getInformationLossInternal(final Node node, final IHashGroupify g) {
        
        // Compute non-uniform entropy
        ILMultiDimensionalWithBound loss = super.getInformationLossInternal(node, g);
        
        // Compute loss induced by suppression
        double[] result = loss.getInformationLoss().getValue();
        double suppressed = 0;
        final IntIntOpenHashMap[] original = new IntIntOpenHashMap[node.getTransformation().length];
        for (int i = 0; i < original.length; i++) {
            original[i] = new IntIntOpenHashMap();
        }

        // Compute counts for suppressed values in each column 
        // m.count only counts tuples from the research subset
        HashGroupifyEntry m = g.getFirstEntry();
        while (m != null) {
            if (!m.isNotOutlier && m.count > 0) {
                suppressed += m.count;
                for (int i = 0; i < original.length; i++) {
                    original[i].putOrAdd(m.key[i], m.count, m.count);
                }
            }
            m = m.nextOrdered;
        }

        // Evaluate non-uniform entropy for suppressed tuples
        if (suppressed != 0){
            for (int i = 0; i < original.length; i++) {
                IntIntOpenHashMap map = original[i];
                for (int j = 0; j < map.allocated.length; j++) {
                    if (map.allocated[j]) {
                        double count = map.values[j];
                        result[i] -= count * log2(count / suppressed);
                    }
                }
            }
        }
        
        // Return
        return new ILMultiDimensionalWithBound(createInformationLoss(result),
                                               createInformationLoss(result));
    }

    @Override
    protected AbstractILMultiDimensional getLowerBoundInternal(Node node) {
        return super.getLowerBoundInternal(node);
    }

    @Override
    protected AbstractILMultiDimensional getLowerBoundInternal(Node node,
                                                       IHashGroupify groupify) {
        return super.getLowerBoundInternal(node);
    }

    @Override
    protected void initializeInternal(DataDefinition definition,
                                      Data input,
                                      GeneralizationHierarchy[] hierarchies,
                                      ARXConfiguration config) {
        
        super.initializeInternal(definition, input, hierarchies, config);
    }
}
