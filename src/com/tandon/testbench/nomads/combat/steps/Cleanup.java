package com.tandon.testbench.nomads.combat.steps;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.testbench.nomads.combat.BaseCommand;

public class Cleanup extends BaseCommand {
    public void execute(XCFRequest request) throws XCFException
    {
    	log(request,  "===== DO Cleanup");
    }

}
