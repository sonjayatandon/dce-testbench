package com.tandon.testbench.combat;

import java.util.ArrayList;

import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFRequest;
import com.eternal.xcf.core.XCFLogger.LogTypes;
import com.eternal.xcf.core.loggers.LOGGER_Console;
import com.eternal.xcf.core.loggers.LOGGER_Null;
import com.eternal.xcf.node.SERVICE_NodeContextManager;
import com.tandon.dce.cards.SERVICE_CardManager;
import com.tandon.dce.characterclass.SERVICE_StyleManager;
import com.tandon.dce.combat.MODEL_CombatCharacter;
import com.tandon.dce.combat.REQUEST_Simple;
import com.tandon.dce.combat.UTIL_Helper;
import com.tandon.testbench.combat.config.Loader;

public class TestQuestEvents {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			XCFFacade facade = new XCFFacade();
			FORMULAS_Event formulas;
			SERVICE_NodeContextManager cmgr = new SERVICE_NodeContextManager();
			SERVICE_CardManager cardManager = new SERVICE_CardManager();
			facade.putService("context-manager", cmgr);
			facade.putService(SERVICE_CardManager.XCF_TAG, cardManager);

			facade.getLogManager().setLogger(LogTypes.DEBUG, new LOGGER_Console());
			facade.getLogManager().registerLogger("combat", new LOGGER_Console());
			
			Loader.s().load(facade);
			
			formulas = new FORMULAS_Event();
			facade.getLogManager().setLogger(LogTypes.DEBUG, new LOGGER_Null());
			XCFRequest req = new REQUEST_Simple("", "");
			req.setContext(cmgr.getNewContext());
			
			int numRounds = 10;
			
			int numVillagers = 10;
			int numWood = 10;
			int numStone = 10;
			
			double difficulty = .7;
			
			
			int villagerDamage =Math.max   (1  ,  (int)Math.floor(((3 * difficulty) * numVillagers/numRounds)));
			int woodDamage =Math.max   (1  ,  (int)Math.floor(((3 * difficulty) * numWood/numRounds)));
			int stoneDamage =Math.max   (1  ,  (int)Math.floor(((3 * difficulty) * numStone/numRounds)));
			
			MODEL_CombatCharacter environment = new MODEL_CombatCharacter(facade, 2, "ENVIRONMENT");
			environment.setContext(req.getContext());
			
			MODEL_CombatCharacter player = new MODEL_CombatCharacter(facade, 1, "PLAYER");
			player.setAttribute("villagers", numVillagers);
			player.setAttribute("wood", numWood);
			player.setAttribute("stone", numStone);
			player.setAttribute("villager_damage", villagerDamage);
			player.setAttribute("wood_damage", woodDamage);
			player.setAttribute("stone_damage", stoneDamage);
			player.setContext(req.getContext());

			// build the event deck
			environment.addCard("attack", 1000001);
			environment.addCard("attack", 1000002);
			environment.addCard("attack", 1000003);
			environment.addCard("attack", 1000004);
			environment.addCard("attack", 1000005);
			environment.addCard("attack", 1000001);
			environment.addCard("attack", 1000002);
			environment.addCard("attack", 1000003);
			environment.addCard("attack", 1000004);
			environment.addCard("attack", 1000005);
			
			// build the player deck
			player.addCard("defense", 2000001);
			
			// setup context
			ArrayList<String> messages = new ArrayList<String>();
			UTIL_Helper.setFormulas(req, formulas);
			req.getContext().putValue("match",  new MLOG_Combat()); 
			req.getContext().putValue("text-log", messages);
			
			// setup attacker/defender
			req.getContext().putValue("attacker", environment);
			req.getContext().putValue("defender", player);
			
			// setup the friend array
			ArrayList<String> friends = new ArrayList<String>();
			friends.add("Helper dude");
			req.getContext().putValue("friend", friends);
			
			for (int round=0; round< numRounds; round++) {
				System.out.println("============================================================");
				environment.play(req, "attack",  "*", null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
