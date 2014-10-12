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
package net.automatalib.automata;

import java.util.Collection;

import net.automatalib.automata.graphs.TransitionEdge;
import net.automatalib.automata.graphs.UniversalAutomatonGraphView;
import net.automatalib.graphs.UniversalGraph;
import net.automatalib.ts.UniversalTransitionSystem;

/**
 * A universal automaton is a generalized representation of automata, with a unified
 * access to the properties of states and transitions. See {@link UniversalTransitionSystem}
 * for a further explanation of this concept.
 * 
 * @author Malte Isberner 
 *
 * @param <S> state class
 * @param <I> input symbol class
 * @param <T> transition class
 * @param <SP> state property class
 * @param <TP> transition property class
 */
public interface UniversalAutomaton<S, I, T, SP, TP> extends
		Automaton<S, I, T>, UniversalTransitionSystem<S, I, T, SP, TP> {
	
	@Override
	public default UniversalGraph<S,TransitionEdge<I,T>,SP,TransitionEdge.Property<I,TP>>
	transitionGraphView(Collection<? extends I> inputs) {
		return UniversalAutomatonGraphView.create(this, inputs);
	}

}
