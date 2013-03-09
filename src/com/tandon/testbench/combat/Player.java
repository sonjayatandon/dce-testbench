package com.tandon.testbench.combat;

import java.util.ArrayList;
import java.util.HashMap;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFLogger;
import com.tandon.dce.combat.IPlayer;
import com.tandon.dce.combat.MODEL_CombatCharacter;

class Player implements IPlayer {
	Integer playerId;
	String name;
	boolean stillStanding = true;
	MODEL_CombatCharacterQueue units;
	MODEL_CombatCharacterQueue targetList;
	HashMap<Integer, MODEL_CombatCharacter> unitsByClassId;

	Player opponent;
	XCFFacade facade;
	
	Player(XCFFacade facade, Integer id, String name) {
		this.playerId = id;
		this.name = name;
		this.facade = facade;
		this.unitsByClassId = new HashMap<Integer, MODEL_CombatCharacter>();
		this.units = new MODEL_CombatCharacterQueue();
	}
	
	void initUnits() {
		for (MODEL_CombatCharacter unit: unitsByClassId.values() ) {
			unit.setPlayer(this);
			unit.shuffle();
			units.addCharacter(unit);
		}
	}
	
	void setOpponent(Player opponent){this.opponent = opponent;}
	
	void initTargetList() {
		targetList = new MODEL_CombatCharacterQueue();
		
		targetList.loadWith(units);
		targetList.sortBy("pull", false);
	}
	
	void loadUp(MODEL_CombatCharacterQueue turnQueue) {
		for (MODEL_CombatCharacter c: units.queue) {
			turnQueue.addCharacter(c);
		}
	}
	
	void bringOutYourDead() throws XCFException {
		ArrayList<MODEL_CombatCharacter> killList = new ArrayList<MODEL_CombatCharacter>();
		// resolve damage, destroy those units with 0 or less spirit
		for (MODEL_CombatCharacter unit: units.queue) {
			int damage = unit.getDamageTaken();
			int spirit = unit.getAttribute("spirit");
			spirit = Math.max(0, spirit-damage);
			unit.setAttribute("spirit", spirit);
			if (spirit == 0) killList.add(unit);
			unit.setDamageTaken(0);
		}
		
		// we have the dead, get rid of them
		for (MODEL_CombatCharacter unit: killList) {
			facade.getLogManager().getLogger("combat").logMessage(null, XCFLogger.LogTypes.INFO, unit.getName() + " has been vanquished in battle!");
			targetList.queue.remove(unit);
			units.queue.remove(unit);
		}
		
		if (units.size() == 0) stillStanding = false;
	}

	@Override
	public IPlayer getOpponent() {
		return opponent;
	}

	@Override
	public MODEL_CombatCharacter getTarget() {
		return targetList.queue.get(0);
	}

	@Override
	public MODEL_CombatCharacter getAlly(
			MODEL_CombatCharacter unit, int allyIndex) {
		if (allyIndex < targetList.size()) return targetList.queue.get(allyIndex);
		return unit;
	}

	public MODEL_CombatCharacter getUnitByType(int classId) {
		return unitsByClassId.get(classId);
	}

	public void setUnitByType(int classId, MODEL_CombatCharacter unit) {
		unitsByClassId.put(classId, unit);
	}

	@Override
	public MODEL_CombatCharacter getBest(MODEL_CombatCharacter character, String attributeName) throws XCFException {
		int bestValue = character.getAttribute(attributeName);
		
		for (MODEL_CombatCharacter unit: units.queue) {
			int value = unit.getAttribute(attributeName);
			if (value > bestValue) {
				bestValue = value;
				character = unit;
			}
		}
		return character;
	}
}
