package com.tandon.testbench.nomads.combat.steps;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.testbench.nomads.Player;
import com.tandon.testbench.nomads.combat.BaseCommand;

public class Upkeep extends BaseCommand {
    public void execute(XCFRequest request) throws XCFException
    {
    	log(request,  "===== DO UPKEEP");
    	Player player = (Player)getProperty("player");
    	Player opponent = (Player)getProperty("opponent");
    	
    	player.getVehicle().doUpkeep();
    	opponent.getVehicle().doUpkeep();
   }

}
