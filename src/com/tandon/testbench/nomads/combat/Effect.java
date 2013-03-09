package com.tandon.testbench.nomads.combat;

import java.util.ArrayList;

public class Effect {
	ArrayList<PropertyDecorator> decorators = new ArrayList<PropertyDecorator>();
	int numRounds = 0;
	public final String name;
	

	public Effect(String name, int rounds, String propertyName, int amount) {
		this.name=name;
		PropertyDecorator decorator = new PropertyDecorator(propertyName, amount, OperatorType.add);
		decorators.add(decorator);
		numRounds=rounds;
	}
	
	public Number getPropertyValue(String propertyName, Number value) {
		for (PropertyDecorator decorator : decorators) {
			value = decorator.getValue(propertyName, value);
		}
		
		return value;
	}
	
	public int decrementRounds(){return --numRounds;}
	
}
