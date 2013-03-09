package com.tandon.testbench.nomads.combat.steps;

import java.util.Stack;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.testbench.nomads.Player;
import com.tandon.testbench.nomads.combat.BaseCommand;

public class EndTurn extends BaseCommand {
    @SuppressWarnings("unchecked")
	public void execute(XCFRequest request) throws XCFException
    {
    	Player attacker = (Player)getProperty("attacker");
    	Player defender = (Player)getProperty("defender");
    	Stack<BaseCommand> startRoundSteps = (Stack<BaseCommand>)getProperty("start-round-steps"); 
    	
    	attacker.completeTurn();
    	
    	if (defender.hasTurns()) {
    		getCardGame(request).loadActiveSteps(defender.getTurnSteps());
    	} else if (attacker.hasTurns()) {
    		getCardGame(request).loadActiveSteps(attacker.getTurnSteps());
    	} else {
    		getCardGame(request).endRound();
    		if (getCardGame(request).getNumRounds() > 0) {
        		getCardGame(request).loadActiveSteps(startRoundSteps);
    		}
    	}
    	log(request,  "===== END TURN for " + attacker.getName());
    }

}
