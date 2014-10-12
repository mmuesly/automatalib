/* Copyright (C) 2013-2014 TU Dortmund
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
package net.automatalib.ts.simple;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.automatalib.commons.util.mappings.MapMapping;
import net.automatalib.commons.util.mappings.MutableMapping;

/**
 * A simple transition system. A transition system is a (not necessarily finite) collection
 * of states. For an arbitrary input symbol, each state has a set of successors.
 * 
 * @author Malte Isberner
 *
 * @param <S> state class.
 * @param <I> symbol class.
 */
@ParametersAreNonnullByDefault
public interface SimpleTS<S, I> {
	
	/**
	 * Retrieves the set of initial states of the transition system.
	 * @return the initial states.
	 */
	@Nonnull
	public Set<? extends S> getInitialStates();
	
	/**
	 * Retrieves the set of successors for the given input symbol. 
	 * 
	 * @param state the source state.
	 * @param input the input symbol.
	 * @return the set of successors reachable by this input, or
	 * <code>null</code> if no successor states are reachable by this input.
	 */
	@Nonnull
	public Set<? extends S> getSuccessors(S state, @Nullable I input);
	
	/**
	 * Retrieves the set of successors for the given sequence of input symbols.
	 * 
	 * @param state the source state.
	 * @param input the sequence of input symbols.
	 * @return the set of successors reachable by this input, or
	 * <code>null</code> if no successor states are reachable by this input.
	 */
	@Nonnull
	public default Set<? extends S> getSuccessors(S state, Iterable<? extends I> input) {
		return getSuccessors(Collections.singleton(state), input);
	}
	
	/**
	 * Retrieves the set of all successors that can be reached from any
	 * of the given source states by the specified sequence of input symbols.
	 *  
	 * @param states the source states.
	 * @param input the sequence of input symbols.
	 * @return the set of successors reachable by this input, or <code>null</code>
	 * if no successor states are reachable.
	 */
	@Nonnull
	public default Set<? extends S> getSuccessors(Collection<? extends S> states, Iterable<? extends I> input) {
		Set<S> current = new HashSet<S>(states);
		Set<S> succs = new HashSet<S>();
		
		for(I sym : input) {
			for(S state : current) {
				Set<? extends S> currSuccs = getSuccessors(state, sym);
				succs.addAll(currSuccs);
			}
					
			Set<S> tmp = current;
			current = succs;
			succs = tmp;
			succs.clear();
		}
		
		return current;
	}

	/**
	 * Retrieves the set of all states reachable by the given sequence of input
	 * symbols from an initial state. Calling this method is equivalent to
	 * <code>getSuccessors(getInitialStates(), input)</code>.
	 * 
	 * @param input the sequence of input symbols.
	 * @return the set of states reachable by this input from an initial state,
	 * or <code>null</code> if no successor state is reachable.
	 */
	@Nonnull
	public default Set<? extends S> getStates(Iterable<? extends I> input) {
		return getSuccessors(getInitialStates(), input);
	}
	
	/**
	 * Creates a {@link MutableMapping} allowing to associate arbitrary data
	 * with this transition system's states. The returned mapping is however
	 * only guaranteed to work correctly if the transition system is not
	 * modified.
	 * @return the mutable mapping
	 */
	@Nonnull
	public default <V> MutableMapping<S,V> createStaticStateMapping() {
		return new MapMapping<>(new HashMap<S,V>());
	}
	
	/**
	 * Creates a {@link MutableMapping} allowing to associate arbitrary data
	 * with this transition system's states. The returned mapping maintains
	 * the association even when the transition system is modified.
	 * @return the mutable mapping
	 */
	@Nonnull
	public default <V> MutableMapping<S,V> createDynamicStateMapping() {
		return new MapMapping<>(new HashMap<S,V>());
	}
	
}
