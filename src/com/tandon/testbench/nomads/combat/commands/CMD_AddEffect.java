package com.tandon.testbench.nomads.combat.commands;

import java.util.Random;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFLogger;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.dce.cards.MODEL_Card;
import com.tandon.dce.combat.CMD_BaseCommand;
import com.tandon.dce.combat.UTIL_Helper;
import com.tandon.testbench.nomads.CardGame;
import com.tandon.testbench.nomads.Events;
import com.tandon.testbench.nomads.Player;
import com.tandon.testbench.nomads.combat.Effect;

public class CMD_AddEffect extends CMD_BaseCommand {
   Random randomGenerator = new Random();
   
   public void execute(XCFRequest request) throws XCFException
    {
	    MODEL_Card card = UTIL_Helper.getActiveCard(request);
		XCFLogger logger = request.getContext().getFacade().getLogManager().getLogger("combat");
		String targetProperty = getString(request, "target", "Add Effect");
		String alterProperty = getString(request, "alter-property", "Add Effect");
		int alterAmount = getInt(request, "alter-amount", "Add Effect");
		int rounds = getInt(request, "rounds", "Add Effect");
    	Player target = (Player)request.getContext().getValue(targetProperty);
    	CardGame game = (CardGame)request.getContext().getValue("card-game");

    	Effect effect = new Effect(card.getName(), rounds, alterProperty, alterAmount);
    	target.getVehicle().addEffect(effect);
    	logger.logMessage(request.getContext(), XCFLogger.LogTypes.INFO, "Add " + card.getName() + " to " + target.getVehicle().getName());
    	game.postEvent(Events.AddEffect);
    }
    

}
