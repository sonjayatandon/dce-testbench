package com.tandon.testbench.nomads.combat;

import com.eternal.xcf.core.XCFContext;
import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFLogger;
import com.eternal.xcf.core.XCFLogger.LogTypes;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.testbench.nomads.CardGame;

public class BaseCommand extends PropertyContainer {
	
	public String getString(String propertyName, String defaultValue)  
	{
		Object oprop =  getProperty(propertyName);
		String property = (oprop == null)?null:oprop.toString();
		if (property == null) return defaultValue;
		return property;
	}

	protected void log(XCFRequest request, String message) throws XCFException {
		XCFContext context = request.getContext();
		XCFLogger logger = context.getFacade().getLogManager().getLogger("combat");
		
		logger.logMessage(context, LogTypes.INFO, message);
	}
	
	protected CardGame getCardGame(XCFRequest request) throws XCFException {
		CardGame game = (CardGame)request.getContext().getValue("card-game");
		return game;
	}
	
    /**
     * 
     * @param commandInput
     */
    public void execute(XCFRequest request) throws XCFException
    {

    }

}
