package com.tandon.testbench.nomads.combat.steps;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.testbench.nomads.Events;
import com.tandon.testbench.nomads.Player;
import com.tandon.testbench.nomads.combat.BaseCommand;

public class ActivateAbilities extends BaseCommand {
    public void execute(XCFRequest request) throws XCFException
    {
    	Player attacker = (Player)getProperty("attacker");
    	Player defender = (Player)getProperty("defender");
    	log(request,  "===== DO Activate Abilities for " + attacker.getName());
    	getCardGame(request).postEvent(Events.SelectAbility);
    	
    	request.getContext().putValue("attacker", attacker);
    	request.getContext().putValue("defender", defender);
    }

}
