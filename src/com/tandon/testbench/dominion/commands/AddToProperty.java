package com.tandon.testbench.dominion.commands;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class AddToProperty extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent gameComponent) {
		
		String propertyName = (String)properties.get("propertyName"); 
		Integer propertyValue = (Integer)session.getProperty(propertyName);
		Integer amount = (Integer)properties.get("amount");
		
		propertyValue += amount;
		
		session.setProperty(propertyName, propertyValue);	
		
		// TODO create update property display event
	}


}
