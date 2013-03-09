package com.tandon.testbench.combat.config;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.sax.UTIL_Helper;

public class Loader {
	static Loader _s = new Loader();
	public static Loader s() {return _s;}
	
	public void load(XCFFacade facade) throws XCFException {
		
		UTIL_Helper.interpretLocalFile(this, "villager.xml", facade, "facade");
		UTIL_Helper.interpretLocalFile(this, "gaurdian.xml", facade, "facade");
		UTIL_Helper.interpretLocalFile(this, "hunter.xml", facade, "facade");
		UTIL_Helper.interpretLocalFile(this, "healer.xml", facade, "facade");
		UTIL_Helper.interpretLocalFile(this, "enchanter.xml", facade, "facade");
		
		// UTIL_Helper.interpretLocalFile(this, "events.xml", facade, "facade");
	}
	
}
