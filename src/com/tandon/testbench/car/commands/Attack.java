package com.tandon.testbench.car.commands;

import java.util.Random;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class Attack extends GameAction {
	
	Random randomGenerator = new Random();

	@Override
	public void execute(GameSession session, GameComponent sourceComponent) {
		Integer damage = (Integer)getProperty("damage");
		String  targetName = (String)getProperty("target");
		Integer weaponAccuracy = (Integer)container.getProperty("accuracy");
		
		GameComponent attackerVehicle = session.getComponent("definition.attacker.vehicle");
		GameComponent defenderVehicle = session.getComponent("definition."+targetName+".vehicle");
		
    	int defenderEvasion = (Integer)defenderVehicle.getProperty("evasion");
    	int attackerAccuracy = (Integer)attackerVehicle.getProperty("accuracy") + (weaponAccuracy==null?0:weaponAccuracy);
		
    	int chanceToHit = Math.min(99, Math.max(1, attackerAccuracy-defenderEvasion));

    	if (randomGenerator.nextInt(100)<=chanceToHit) {
    		// they hit
    		Integer armor = (Integer)defenderVehicle.getProperty("armor");
    		armor = Math.max(0, armor - damage);
    		defenderVehicle.setProperty("armor", armor);
    		
    	} else {
    		// they missed
    	}
	}

}
