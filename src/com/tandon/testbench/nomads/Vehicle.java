package com.tandon.testbench.nomads;

import java.util.ArrayList;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.tandon.dce.cards.MODEL_Card;
import com.tandon.dce.combat.MODEL_CombatCharacter;
import com.tandon.testbench.nomads.combat.Effect;


public class Vehicle {
	String name;
	int numHardPoints;
	int numStaff;
	XCFFacade facade;
	ArrayList<Effect> effects = new ArrayList<Effect>();
	
	MODEL_CombatCharacter combatUnit;
	
	public Vehicle(XCFFacade facade, String name, Integer id, int numHardPoints, int numStaff) throws XCFException {
		this.name = name;
		this.numHardPoints = numHardPoints;
		this.numStaff = numStaff;
		this.facade = facade;
		combatUnit = new MODEL_CombatCharacter(facade, id, name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public MODEL_CombatCharacter getCombatUnit() {
		return combatUnit;
	}

	public int getNumHardPoints() {
		return numHardPoints;
	}

	public void setNumHardPoints(int numHardPoints) {
		this.numHardPoints = numHardPoints;
	}


	public int getNumStaff() {
		return numStaff;
	}

	public void setNumStaff(int numStaff) {
		this.numStaff = numStaff;
	}
	
	public void addCard(Integer cardId) throws XCFException {
		combatUnit.addCard("component", cardId);
	}
	
	public void addEffect(Effect effect) {
		effects.add(effect);
	}
	
	
	public void doUpkeep() {
		ArrayList<Effect> updatedEffects = new ArrayList<Effect>();
		for (Effect effect: effects) {
			if (effect.decrementRounds() > 0) updatedEffects.add(effect); 
			else {
				System.out.println(effect.name + " removed from " + name);
			}
		}
		
		effects = updatedEffects;
	}
	
	public Integer getPropertyValue(String propertyName, Integer value) throws XCFException {
		for (Effect effect: effects) {
			value = (Integer)effect.getPropertyValue(propertyName, value);
		}
		
		return value;
	}
	
	public int getAccuracy() throws XCFException {
		Integer value =  combatUnit.getAttribute("accuracy");
		value = getPropertyValue("accuracy", value); 
		return value;
	}
	
	public int getEvasion() throws XCFException {
		Integer value =  combatUnit.getAttribute("evasion");
		value = getPropertyValue("evasion", value); 
		return value;
	}
	
	public void setup() throws XCFException {
		int armor = 0;
		int speed = 0;
		int evasion = 5;
		int accuracy = 65;
		
		ArrayList<MODEL_Card> components = combatUnit.getDecks().get("component").getCards();
		for (MODEL_Card component: components) {
			armor = armor + getInt(component, "armor");
			speed = speed + getInt(component, "speed");
			evasion = evasion + getInt(component, "evasion");
			accuracy = accuracy + getInt(component, "accuracy");
		}
		
		combatUnit.setAttribute("armor", armor);
		combatUnit.setAttribute("speed", speed);
		combatUnit.setAttribute("evasion", evasion);
		combatUnit.setAttribute("accuracy", accuracy);
		combatUnit.setAttribute("defense",0);
		
		System.out.println("Armor: " + combatUnit.getAttribute("armor"));
		System.out.println("Speed: " + combatUnit.getAttribute("speed"));
		System.out.println("Evasion: " + combatUnit.getAttribute("evasion"));
		System.out.println("Accuracy: " + combatUnit.getAttribute("accuracy"));
		
		combatUnit.setHP(armor);
	}
	
	int getInt(MODEL_Card component, String property) {
		String sValue = component.getProperty(property);
		
		if (sValue == null || sValue.trim().length() == 0) return 0;
		return Integer.parseInt(sValue);
	}

	public int getSpeed() throws XCFException {
		return combatUnit.getAttribute("speed");
	}
		
}
