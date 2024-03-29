/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.core.algorithm.classification.mlp;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.core.algorithm.basic.Clone;
import org.jdmp.core.algorithm.basic.LogisticFunction;
import org.jdmp.core.algorithm.basic.Mean;
import org.jdmp.core.algorithm.basic.Sum;
import org.jdmp.core.algorithm.basic.Tanh;
import org.jdmp.core.algorithm.basic.TanhPlusOne;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.Aggregation;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.BiasType;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.Transfer;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;

public class NetworkLayerForward extends AbstractAlgorithm {
	private static final long serialVersionUID = -2738909213636005084L;

	public static final String INPUT = "Input";

	public static final String WEIGHT = "Weight";

	public static final String WEIGHTINGFUNCTION = "WeightingFunction";

	public static final String WEIGHTEDINPUT = "WeightedInput";

	public static final String AGGREGATIONFUNCTION = "AggregationFunction";

	public static final String NETINPUT = "NetInput";

	public static final String TRANSFERFUNCTION = "TransferFunction";

	public static final String OUTPUT = "Output";

	public NetworkLayerForward(BiasType biasType) {
		this(Aggregation.MEAN, Transfer.TANH, biasType);
	}

	public NetworkLayerForward(Aggregation aggregationFunction, Transfer transferFunction,
			BiasType biasType) {
		setDescription("One layer of a multi-layer network, forward path");

		setVariable(WEIGHTEDINPUT, VariableFactory.labeledVariable("Weighted Input"));
		setVariable(NETINPUT, VariableFactory.labeledVariable("Net Input"));

		Weighting aw = new Weighting(biasType);
		aw.setVariable(Weighting.TARGET, getWeightedInputVariable());
		setAlgorithm(WEIGHTINGFUNCTION, aw);

		setAggregationFunction(aggregationFunction);
		setTransferFunction(transferFunction);
	}

	@Override
	public Map<Object, Object> calculateObjects(Map<Object, Object> input) throws Exception {
		Map<Object, Object> result = new HashMap<Object, Object>();

		Algorithm weightingFunction = getWeightingFunction();
		weightingFunction.calculate();

		Algorithm aggregationFunction = getAggregationFunction();
		aggregationFunction.calculate();

		Algorithm transferFunction = getTransferFunction();
		transferFunction.calculate();

		return result;
	}

	public Matrix getInputMatrix() {
		return getMatrixFromVariable(INPUT);
	}

	public Variable getInputVariable() {
		Variable v = getVariables().get(INPUT);
		return v;
	}

	public void setInputVariable(Variable v) {
		setVariable(INPUT, v);
		getWeightingFunction().setVariable(AlgorithmTwoSources.SOURCE2, v);
	}

	public Matrix getWeightMatrix() {
		return getMatrixFromVariable(WEIGHT);
	}

	public Variable getWeightVariable() {
		Variable v = getVariables().get(WEIGHT);
		return v;
	}

	public void setWeightVariable(Variable v) {
		setVariable(WEIGHT, v);
		getWeightingFunction().setVariable(AlgorithmTwoSources.SOURCE1, v);
	}

	public Matrix getWeightedInputMatrix() {
		return getMatrixFromVariable(WEIGHTEDINPUT);
	}

	public Variable getWeightedInputVariable() {
		Variable v = getVariables().get(WEIGHTEDINPUT);
		return v;
	}

	public void setAggregationFunction(Aggregation aggregationFunction) {
		Algorithm a = null;
		switch (aggregationFunction) {
		case MEAN:
			a = new Mean(Matrix.COLUMN);
			break;
		case SUM:
			a = new Sum(Matrix.COLUMN);
		}
		a.setVariable(SOURCE, getWeightedInputVariable());
		a.setVariable(TARGET, getNetInputVariable());
		setAlgorithm(AGGREGATIONFUNCTION, a);
	}

	public void setTransferFunction(Transfer transferFunction) {
		Algorithm a = null;
		switch (transferFunction) {
		case TANH:
			a = new Tanh();
			break;
		case LINEAR:
			a = new Clone();
			break;
		case TANHPLUSONE:
			a = new TanhPlusOne();
			break;
		case SIGMOID:
			a = new LogisticFunction();
			break;
		}
		a.setVariable(SOURCE, getNetInputVariable());
		setAlgorithm(TRANSFERFUNCTION, a);
	}

	public void setWeightedInputVariable(Variable v) {
		setVariable(WEIGHTEDINPUT, v);
	}

	public Algorithm getWeightingFunction() {
		Algorithm a = getAlgorithms().get(WEIGHTINGFUNCTION);
		return a;
	}

	public Algorithm getAggregationFunction() {
		Algorithm a = getAlgorithms().get(AGGREGATIONFUNCTION);
		return a;
	}

	public Algorithm getTransferFunction() {
		Algorithm a = getAlgorithms().get(TRANSFERFUNCTION);
		return a;
	}

	public Variable getNetInputVariable() {
		Variable v = getVariables().get(NETINPUT);
		return v;
	}

	public Matrix getNetInputMatrix() {
		return getMatrixFromVariable(NETINPUT);
	}

	public void setNetInputVariable(Variable v) {
		setVariable(NETINPUT, v);
	}

	public Variable getOutputVariable() {
		Variable v = getVariables().get(OUTPUT);
		return v;
	}

	public Matrix getOutputMatrix() {
		return getMatrixFromVariable(OUTPUT);
	}

	public void setOutputVariable(Variable v) {
		setVariable(OUTPUT, v);
		getAlgorithms().get(TRANSFERFUNCTION).setVariable(TARGET, v);
	}

	// public static void main(String[] args) throws Exception {
	// AlgorithmNetworkLayerForward a = new
	// AlgorithmNetworkLayerForward(Aggregation.MEAN, Transfer.TANH);
	// Variable input = new DefaultVariable("Input");
	// Matrix x = Matrix.fromArray(new double[][] { { 1 }, { 2 }, { 3 }, { 4 }
	// });
	// input.addMatrix(x);
	// a.setInputVariable(input);
	// Variable weight = new DefaultVariable("Weight");
	// Matrix w = Matrix.fromArray(new double[][] { { 0.1, 0.2, 0.3, 0.4 }, {
	// 0.1, 0.2, 0.3, 0.4 },
	// { 0.1, 0.2, 0.3, 0.4 }, { 0.1, 0.2, 0.3, 0.4 }, { 0.1, 0.2, 0.3, 0.4 }, {
	// 0.1, 0.2, 0.3, 0.4 } });
	// weight.addMatrix(w);
	// a.setWeightVariable(weight);
	// System.out.println(a.calculate());
	// }

	public void setLayer(int nr) {
		setLabel("Network Layer Forward (" + nr + ")");
	}

}
