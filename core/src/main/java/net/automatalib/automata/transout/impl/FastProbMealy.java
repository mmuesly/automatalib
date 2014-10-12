/* Copyright (C) 2013 TU Dortmund
 * This file is part of AutomataLib, http://www.automatalib.net/.
 * 
 * AutomataLib is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 3.0 as published by the Free Software Foundation.
 * 
 * AutomataLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with AutomataLib; if not, see
 * http://www.gnu.de/documents/lgpl.en.html.
 */
package net.automatalib.automata.transout.impl;

import net.automatalib.automata.base.fast.FastMutableNondet;
import net.automatalib.automata.transout.probabilistic.MutableProbabilisticMealy;
import net.automatalib.automata.transout.probabilistic.ProbabilisticOutput;
import net.automatalib.words.Alphabet;

public class FastProbMealy<I, O>
		extends
		FastMutableNondet<FastProbMealyState<O>, I, ProbMealyTransition<FastProbMealyState<O>, O>, Void, ProbabilisticOutput<O>>
		implements
		MutableProbabilisticMealy<FastProbMealyState<O>, I, ProbMealyTransition<FastProbMealyState<O>, O>, O> {

	public FastProbMealy(Alphabet<I> inputAlphabet) {
		super(inputAlphabet);
	}

	@Override
	public FastProbMealyState<O> getSuccessor(
			ProbMealyTransition<FastProbMealyState<O>, O> transition) {
		return transition.getSuccessor();
	}



	@Override
	public O getTransitionOutput(
			ProbMealyTransition<FastProbMealyState<O>, O> transition) {
		return transition.getOutput();
	}


	@Override
	public Void getStateProperty(FastProbMealyState<O> state) {
		return null;
	}

	@Override
	public ProbabilisticOutput<O> getTransitionProperty(
			ProbMealyTransition<FastProbMealyState<O>, O> transition) {
		return new ProbabilisticOutput<O>(transition.getProbability(), transition.getOutput());
	}

	@Override
	public void setTransitionOutput(
			ProbMealyTransition<FastProbMealyState<O>, O> transition, O output) {
		transition.setOutput(output);
	}

	@Override
	public void setTransitionProbability(
			ProbMealyTransition<FastProbMealyState<O>, O> transition,
			float probability) {
		transition.setProbability(probability);
	}

	@Override
	public float getTransitionProbability(
			ProbMealyTransition<FastProbMealyState<O>, O> transition) {
		return transition.getProbability();
	}

	@Override
	public void setStateProperty(FastProbMealyState<O> state, Void property) {
	}

	@Override
	public void setTransitionProperty(
			ProbMealyTransition<FastProbMealyState<O>, O> transition,
			ProbabilisticOutput<O> property) {
		float prob;
		O output;
		if(property == null) {
			prob = 0.0f;
			output = null;
		}
		else {
			prob = property.getProbability();
			output = property.getOutput();
		}
		transition.setProbability(prob);
		transition.setOutput(output);
	}

	@Override
	public ProbMealyTransition<FastProbMealyState<O>, O> createTransition(
			FastProbMealyState<O> successor, ProbabilisticOutput<O> properties) {
		float prob;
		O output;
		if(properties == null) {
			prob = 0.0f;
			output = null;
		}
		else {
			prob = properties.getProbability();
			output = properties.getOutput();
		}
		return new ProbMealyTransition<FastProbMealyState<O>, O>(successor, output, prob);
	}


	@Override
	protected FastProbMealyState<O> createState(Void property) {
		return new FastProbMealyState<O>(inputAlphabet.size());
	}

	
	public void addTransition(FastProbMealyState<O> src, I input, FastProbMealyState<O> successor, O output, float prob) {
		addTransition(src, input, successor, new ProbabilisticOutput<O>(prob, output));
	}

}
