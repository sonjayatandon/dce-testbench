package com.tandon.testbench.combat;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFLogger;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.dce.combat.ICombatFormulas;
import com.tandon.dce.combat.MODEL_CombatCharacter;
import com.tandon.dce.combat.UTIL_Helper;

public class MODEL_CombatResolver {
	XCFFacade facade;
	XCFRequest req;
	Player player;
	Player opponent;
	
	MODEL_CombatCharacterQueue turnQueue;

	public MODEL_CombatResolver(XCFFacade facade) {
		this.facade = facade;
	}
	
	private void addSentries(Player player, Sentry[] sentries) throws XCFException {
		for (Sentry sentry: sentries) {
			MODEL_CombatCharacter unit = player.getUnitByType(sentry.sentryId);
			unit = sentry.getFightingUnit(facade, player.playerId, player.name + " " + sentry.getName(), unit);
			player.setUnitByType(sentry.sentryId, unit);
		}
	}
	
	public void setup(XCFRequest request, ICombatFormulas formulas, String playerAName, Sentry[] playerASentries, String playerBName, Sentry[] playerBSentries) throws XCFException {
		this.req = request;
		
		// create the player objects and set their opponents
		player = new Player(facade, 1, playerAName);
		opponent = new Player(facade, 2, playerBName);
		player.setOpponent(opponent);
		opponent.setOpponent(player);
		
		// add in the sentries
		addSentries(player, playerASentries);
		addSentries(opponent, playerBSentries);
		
		// initialized the player units for combat
		player.initUnits();
		opponent.initUnits();
		
		// add in the mlog and set the formulas
		request.getContext().putValue("match", new MLOG_Combat());
		UTIL_Helper.setFormulas(request, formulas);
		
	}
	
	public boolean doRound() throws XCFException {
		turnQueue = new MODEL_CombatCharacterQueue();
		player.loadUp(turnQueue);
		opponent.loadUp(turnQueue);
		
		turnQueue.sortBy("initiative", true);
		player.initTargetList();
		opponent.initTargetList();
				
		for (MODEL_CombatCharacter unit: turnQueue.queue) {
			// as long as unit not already dead, do the turn
			if (unit.getAttribute("spirit").equals(0)) continue;
			if (doTurn(unit) == false) return false;
		}
		
		if (player.stillStanding && opponent.stillStanding) return true;
		
		return false;
	}
	
	public boolean doTurn(MODEL_CombatCharacter unit) throws XCFException {
		MODEL_CombatCharacter target = unit.getPlayer().getOpponent().getTarget();
		if (target == null) return false;
		
		facade.getLogManager().getLogger("combat").logMessage(req.getContext(), XCFLogger.LogTypes.INFO, "++++++++++++++ START TURN: " + unit.getName());
		req.getContext().putValue("attacker", unit);
		req.getContext().putValue("defender", target);
		req.getContext().putValue("puller", unit.getAlly(0));
		unit.play(req, "attack", "*", null);
		player.bringOutYourDead();
		opponent.bringOutYourDead();
		
		if (player.stillStanding && opponent.stillStanding) return true;

		return false;
	}
	
	public void resolve(int rounds) throws XCFException {
		int round = 0;
		
		facade.getLogManager().getLogger("combat").logMessage(req.getContext(), XCFLogger.LogTypes.INFO, "=============== START ROUND: " + (round + 1));
		while (round < rounds && doRound()){ 
			round++;
			if (round < rounds) {
				facade.getLogManager().getLogger("combat").logMessage(req.getContext(), XCFLogger.LogTypes.INFO, "=============== START ROUND: " + (round + 1));
			}
		}
		
		if (player.stillStanding && opponent.stillStanding) {
			facade.getLogManager().getLogger("combat").logMessage(req.getContext(), XCFLogger.LogTypes.INFO, " The fight was a draw.");
		} else if (!player.stillStanding && !opponent.stillStanding) {
			facade.getLogManager().getLogger("combat").logMessage(req.getContext(), XCFLogger.LogTypes.INFO, " The fight was a draw, no one left standing.");
		} else if (player.stillStanding) {
			facade.getLogManager().getLogger("combat").logMessage(req.getContext(), XCFLogger.LogTypes.INFO, player.name + " won!");
		} else {
			facade.getLogManager().getLogger("combat").logMessage(req.getContext(), XCFLogger.LogTypes.INFO, opponent.name + " won!");
		}	
	}	
}
