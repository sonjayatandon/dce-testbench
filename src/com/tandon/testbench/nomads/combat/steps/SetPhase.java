package com.tandon.testbench.nomads.combat.steps;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.testbench.nomads.Player;
import com.tandon.testbench.nomads.combat.BaseCommand;

public class SetPhase extends BaseCommand {
    public void execute(XCFRequest request) throws XCFException
    {
    	Player owner = (Player)getProperty("phase-owner");
    	String phaseName = (String)getProperty("phase-name");
    	
    	log(request,  "===== " + phaseName + (owner==null?"":" for " + owner.getName()));
    }

}
