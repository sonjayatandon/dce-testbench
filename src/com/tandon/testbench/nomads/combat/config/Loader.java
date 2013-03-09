package com.tandon.testbench.nomads.combat.config;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.sax.UTIL_Helper;

public class Loader {
	static Loader _s = new Loader();
	public static Loader s() {return _s;}
	
	public void load(XCFFacade facade) throws XCFException {
		
		UTIL_Helper.interpretLocalFile(this, "baseset.xml", facade, "facade");
	}
	
}
