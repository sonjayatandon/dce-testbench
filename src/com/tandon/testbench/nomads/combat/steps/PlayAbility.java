package com.tandon.testbench.nomads.combat.steps;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.dce.cards.MODEL_Card;
import com.tandon.dce.combat.UTIL_Helper;
import com.tandon.testbench.nomads.CardGame;
import com.tandon.testbench.nomads.Events;
import com.tandon.testbench.nomads.Player;
import com.tandon.testbench.nomads.combat.BaseCommand;

public class PlayAbility extends BaseCommand {
    public void execute(XCFRequest request) throws XCFException
    {
    	CardGame game = (CardGame)getProperty("game");
    	MODEL_Card card = (MODEL_Card)getProperty("ability");
    	Player attacker = (Player)request.getContext().getValue("attacker");
    	Player defender = (Player)request.getContext().getValue("defender");
    	
    	int attackerEvasion = attacker.getVehicle().getEvasion();
    	int attackerAccuracy = attacker.getVehicle().getAccuracy();
    	
    	int defenderEvasion = defender.getVehicle().getEvasion();
    	int defenderAccuracy = defender.getVehicle().getAccuracy();
    	
    	log(request,  "===== Play " + card.getName());
    	log(request,  " Attacker: " + attacker.getName() + " E" + attackerEvasion + " A" + attackerAccuracy);
    	log(request,  " Defender: " + defender.getName() + " E" + defenderEvasion + " A" + defenderAccuracy);
    	UTIL_Helper.setActiveCard(request, card);
    	card.execute(request);
    	
    	if (attacker.getCombatUnit().isKnockedOut()) {
        	log(request,  "===== " + attacker.getName() + " was destroyed.");
    		game.postEvent(Events.GameOver);
    	} else if (defender.getCombatUnit().isKnockedOut()) {
        	log(request,  "===== " + defender.getName() + " was destroyed.");
    		game.postEvent(Events.GameOver);
    	}
    }

}
