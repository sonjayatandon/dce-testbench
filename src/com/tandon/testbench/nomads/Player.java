package com.tandon.testbench.nomads;

import java.util.Stack;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.tandon.dce.combat.IPlayer;
import com.tandon.dce.combat.MODEL_CombatCharacter;
import com.tandon.testbench.nomads.combat.BaseCommand;

public class Player implements IPlayer {
	Integer playerId;
	String name;
	Vehicle vehicle;
	XCFFacade facade;
	
	Stack<BaseCommand> turnSteps;
	
	int turn = 1;
	
	Player(XCFFacade facade, Integer id, String name) {
		this.playerId = id;
		this.name = name;
		this.facade = facade;
	}
	
	public String getName() {return name;}
	
	void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public MODEL_CombatCharacter getCombatUnit() {
		return vehicle.getCombatUnit();
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	@Override
	public IPlayer getOpponent() {
		return null;
	}

	@Override
	public MODEL_CombatCharacter getTarget() {
		return null;
	}

	@Override
	public MODEL_CombatCharacter getAlly(
			MODEL_CombatCharacter unit, int allyIndex) {
		return unit;
	}


	@Override
	public MODEL_CombatCharacter getBest(MODEL_CombatCharacter character, String attributeName) throws XCFException {
		
		return character;
	}

	public int getSpeed() throws XCFException {
		return vehicle.getSpeed();
	}

	public int completeTurn() {
		return turn--;
	}

	public void setTurns() throws XCFException {
		int speed = vehicle.getSpeed();
		turn = Math.max(1, speed/10);
		
	}

	public void setTurnSteps(Stack<BaseCommand> steps) {
		turnSteps = steps;
	}
	
	public Stack<BaseCommand> getTurnSteps() {
		return turnSteps;
	}

	public boolean hasTurns() {
		return turn > 0;
	}
}
