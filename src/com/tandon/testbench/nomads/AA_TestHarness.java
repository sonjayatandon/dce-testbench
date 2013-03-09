package com.tandon.testbench.nomads;

import java.util.Random;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFRequest;
import com.eternal.xcf.core.XCFLogger.LogTypes;
import com.eternal.xcf.core.loggers.LOGGER_Console;
import com.eternal.xcf.core.loggers.LOGGER_Null;
import com.eternal.xcf.node.SERVICE_NodeContextManager;
import com.tandon.dce.cards.MODEL_Card;
import com.tandon.dce.cards.SERVICE_CardManager;
import com.tandon.dce.characterclass.MODEL_Class;
import com.tandon.dce.characterclass.MODEL_Template;
import com.tandon.dce.characterclass.SERVICE_StyleManager;
import com.tandon.dce.combat.MODEL_CombatCharacter;
import com.tandon.dce.combat.MODEL_CombatDeck;
import com.tandon.dce.combat.REQUEST_Simple;
import com.tandon.dce.combat.UTIL_Helper;
import com.tandon.testbench.combat.FORMULAS_Combat;
import com.tandon.testbench.combat.MLOG_Combat;
import com.tandon.testbench.nomads.combat.config.Loader;

public class AA_TestHarness {
	protected XCFFacade facade = new XCFFacade();
	protected SERVICE_NodeContextManager cmgr = new SERVICE_NodeContextManager();
	protected SERVICE_StyleManager styleManager = new SERVICE_StyleManager();
	protected SERVICE_CardManager cardManager = new SERVICE_CardManager();
	Random randomGenerator = new Random();
	FORMULAS_Combat formulas = new FORMULAS_Combat();;

	public AA_TestHarness() {
		 try {
			facade.putService("context-manager", cmgr);
			facade.putService(SERVICE_CardManager.XCF_TAG, cardManager);
			facade.putService(SERVICE_StyleManager.XCF_TAG, styleManager);
			facade.getLogManager().setLogger(LogTypes.DEBUG, new LOGGER_Console());
			// facade.getLogManager().setLogger(LogTypes.DEBUG, new LOGGER_Null());
			Loader.s().load(facade);
			facade.getLogManager().setLogger(LogTypes.DEBUG, new LOGGER_Null());
		 } catch (XCFException e) {
		 	e.printStackTrace();
		 }
	}
	
	public static void main(String[] args) {
		AA_TestHarness harness = new AA_TestHarness();

		try {
			harness.facade.getLogManager().registerLogger("combat", new LOGGER_Console());
			harness.runTestCases();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void runTestCases() throws XCFException {
		runTestCase(6,7);
		
		/*
		int numTemplates = 5;
		for (int p=1; p<=numTemplates; p++) {
			for (int o=1; o<=numTemplates; o++) {
				runTestCase(p,o);
			}
		}
		*/
		
	}
	
	public void runTestCase(int playerTemplateId, int opponentTemplateId) throws XCFException {
		boolean gameOver = false;
		
		System.out.println("==================== GENTLEMEN, START YORU ENGINES =====================");
		XCFRequest req = new REQUEST_Simple("", "");
		req.setContext(cmgr.getNewContext());
		req.getContext().putValue("match", new MLOG_Combat());
		UTIL_Helper.setFormulas(req, formulas);
		
		MODEL_Class car = styleManager.getStyle(1);
		
		
		Player player = new Player(facade, 1, "Joe Fataboomboom");
		player.setVehicle(createVehicle(car, facade, player.getName() + "'s " + car.getName(), 1, playerTemplateId));
		Player opponent = new Player(facade, 2, "Niko Blaster");
		opponent.setVehicle(createVehicle(car, facade, opponent.getName() + "'s " + car.getName(), 2, opponentTemplateId));
		
		CardGame game = new CardGame(facade, req, player, opponent);
		game.setup();
		try {
			do {
				game.doNextSteps();
				for (Events event : game.events) {
					System.out.println("Client event: " + event);
					switch (event) {
						case GameOver:
							gameOver = true;
							break;
						case SelectAbility:
							Player attacker = (Player)req.getContext().getValue("attacker");
							MODEL_Card card = selectAbility(attacker.getCombatUnit());
							if (card != null) game.playCard(card);
							break;
					case AddEffect:
					case AttackHit:
					case AttackMissed:
					default:
						break;
					}
				}  
			} while (!gameOver);
			
		} catch (XCFException e) {e.printStackTrace();}
		
		System.out.println("==================== GAME OVER, MAN =====================");
	}
	
	MODEL_Card selectAbility(MODEL_CombatCharacter attacker) throws XCFException {
		MODEL_CombatDeck deck = attacker.getDecks().get("component");
		int size = deck.size("ability", null);
		int index = randomGenerator.nextInt(size);
		int i=0;
		MODEL_Card ability = null;
		
		for (MODEL_Card card:deck.getCards()) {
			if (i > index) break;
			if (card.matches("ability")) {
				ability = card;
				i++;
			}
		}
		
		return ability;
	}
	
	Vehicle createVehicle(MODEL_Class vehicleType, XCFFacade facade, String name, Integer id, int templateId) throws XCFException {
		Integer numHardPoints = vehicleType.getAttributeValue("4");
		Integer numStaff = vehicleType.getAttributeValue("5");
		Vehicle v = new Vehicle(facade, name, id, numHardPoints, numStaff);
		MODEL_Template carBuild = vehicleType.getTemplate(templateId);
		System.out.println("==================== Create " + name);
		System.out.println("=========== Build " + carBuild.getName());
		for (MODEL_Card card: carBuild.getDeck("component").getCards()) {
			v.addCard(card.getID());
		}
		v.setup();
		return v;
	}
}
