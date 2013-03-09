package com.tandon.testbench.nomads.combat.steps;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.testbench.nomads.Player;
import com.tandon.testbench.nomads.combat.BaseCommand;

public class StartRound extends BaseCommand {
    public void execute(XCFRequest request) throws XCFException
    {
    	log(request,  "===== START ROUND " + (11 -getCardGame(request).getNumRounds()) + " ======================== ");
    	Player player = (Player)getProperty("player");
    	Player opponent = (Player)getProperty("opponent");
    	
    	player.setTurns();
    	opponent.setTurns();
    	
    	if (opponent.getSpeed() > player.getSpeed()) {
    		getCardGame(request).loadActiveSteps(opponent.getTurnSteps());
    	} else {
    		getCardGame(request).loadActiveSteps(player.getTurnSteps());
    	}
    }

}
