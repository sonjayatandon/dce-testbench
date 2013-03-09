package com.tandon.testbench.nomads.combat.commands;

import java.util.Random;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFLogger;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.dce.combat.CMD_BaseCommand;
import com.tandon.testbench.nomads.CardGame;
import com.tandon.testbench.nomads.Events;
import com.tandon.testbench.nomads.Player;

public class CMD_Attack extends CMD_BaseCommand {
   Random randomGenerator = new Random();
   
   public void execute(XCFRequest request) throws XCFException
    {
		XCFLogger logger = request.getContext().getFacade().getLogManager().getLogger("combat");
    	Player attacker = (Player)request.getContext().getValue("attacker");
    	Player defender = (Player)request.getContext().getValue("defender");
    	CardGame game = (CardGame)request.getContext().getValue("card-game");

    	int defenderEvasion = defender.getVehicle().getEvasion();
    	int attackerAccuracy = attacker.getVehicle().getAccuracy();
    	
    	int chanceToHit = Math.min(99, Math.max(1, attackerAccuracy-defenderEvasion));
    	int damage = getInt(request, "damage", "Attack");
    	
    	if (randomGenerator.nextInt(100)<=chanceToHit) {
    		defender.getCombatUnit().takeDamage(request, damage, 0);
    	    int defenderHP = defender.getCombatUnit().getHP();
    	    int damageTaken = defender.getCombatUnit().getDamageTaken();
    	    defenderHP = defenderHP-damageTaken;
     		logger.logMessage(request.getContext(), XCFLogger.LogTypes.INFO, defender.getCombatUnit().getName() + " hit for " + damage + " damage.");
     		logger.logMessage(request.getContext(), XCFLogger.LogTypes.INFO, defender.getCombatUnit().getName() + " has " + defenderHP + " armor left.");
    		game.postEvent(Events.AttackHit);
    	} else {
    		logger.logMessage(request.getContext(), XCFLogger.LogTypes.INFO, attacker.getCombatUnit().getName() + " missed!");
    		game.postEvent(Events.AttackMissed);
    	}
    }
    

}
