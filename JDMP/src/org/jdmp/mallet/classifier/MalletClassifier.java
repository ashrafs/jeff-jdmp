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

package org.jdmp.mallet.classifier;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.mallet.dataset.DataSet2InstanceList;
import org.jdmp.mallet.matrix.Labeling2Matrix;
import org.jdmp.mallet.sample.Sample2Instance;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

import cc.mallet.classify.AdaBoostM2Trainer;
import cc.mallet.classify.AdaBoostTrainer;
import cc.mallet.classify.BalancedWinnowTrainer;
import cc.mallet.classify.Classification;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.DecisionTreeTrainer;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelAlphabet;

public class MalletClassifier extends AbstractClassifier {
	private static final long serialVersionUID = -1594522916901093940L;

	public enum MalletClassifiers {
		NaiveBayes, AdaBoost, AdaBoostM2, Bagging, BalancedWinnow, C45, ConfidencePredictingClassifier, DecisionTree, MaxEnt, MCMaxEnt, Winnow
	};

	private MalletClassifiers type = null;

	private ClassifierTrainer<?> trainer = null;

	private List<Integer> cumSum = null;

	private cc.mallet.classify.Classifier classifier = null;

	public MalletClassifier(MalletClassifiers classifier) throws Exception {
		super();
		setLabel("Mallet Classifier " + classifier.name());
		this.type = classifier;
		reset();
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		Instance instance = new Sample2Instance(input, null, classifier.getAlphabet(), classifier
				.getLabelAlphabet(), classifier.getInstancePipe(), cumSum);
		Classification c = classifier.classify(instance);
		return new Labeling2Matrix(c.getLabeling());
	}

	@Override
	public void reset() throws Exception {
		switch (type) {
		case NaiveBayes:
			this.trainer = new NaiveBayesTrainer();
			this.classifier = null;
			break;
		case AdaBoost:
			this.trainer = new AdaBoostTrainer(new DecisionTreeTrainer());
			this.classifier = null;
			break;
		case AdaBoostM2:
			this.trainer = new AdaBoostM2Trainer(new DecisionTreeTrainer());
			this.classifier = null;
			break;
		// case Bagging:
		// this.trainer = new BaggingTrainer(new DecisionTreeTrainer());
		// this.classifier = null;
		// break;
		case BalancedWinnow:
			this.trainer = new BalancedWinnowTrainer();
			this.classifier = null;
			break;
		// case ConfidencePredictingClassifier:
		// this.trainer = new ConfidencePredictingClassifierTrainer(new
		// DecisionTreeTrainer());
		// this.classifier = null;
		// break;
		case DecisionTree:
			this.trainer = new DecisionTreeTrainer();
			this.classifier = null;
			break;
		default:
			throw new MatrixException("not implemented");
		}

	}

	@Override
	public void train(RegressionDataSet dataSet) throws Exception {
		Matrix dataSetInput = dataSet.getInputMatrix();

		Matrix max = dataSetInput.max(Ret.NEW, Matrix.ROW);
		cumSum = new ArrayList<Integer>((int) max.getColumnCount());
		int sum = 0;
		cumSum.add(sum);
		for (int i = 0; i < max.getColumnCount(); i++) {
			sum += max.getAsInt(0, i) + 1;
			cumSum.add(sum);
		}

		LabelAlphabet inputAlphabet = new LabelAlphabet();
		for (int i = 0; i < dataSet.getFeatureCount(); i++) {
			// iterate from 1 to max (inclusive!)
			for (int fv = 1; fv < max.getAsDouble(0, i) + 1; fv++) {
				inputAlphabet.lookupIndex("Feature" + i + "=" + fv, true);
			}
		}

		LabelAlphabet targetAlphabet = new LabelAlphabet();
		for (int i = 0; i < dataSet.getTargetMatrix().getColumnCount(); i++) {
			targetAlphabet.lookupIndex("Class" + i, true);
		}

		InstanceList trainingSet = new DataSet2InstanceList((ClassificationDataSet) dataSet,
				inputAlphabet, targetAlphabet, cumSum);

		classifier = trainer.train(trainingSet);
	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new MatrixException("not implemented");
	}

	public Classifier emptyCopy() throws Exception {
		return new MalletClassifier(type);
	}

}
