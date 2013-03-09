package com.tandon.testbench.combat;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.tandon.dce.cards.MODEL_Card;
import com.tandon.dce.characterclass.MODEL_Template;
import com.tandon.dce.characterclass.MODEL_Class;
import com.tandon.dce.characterclass.SERVICE_StyleManager;
import com.tandon.dce.combat.MODEL_CombatCharacter;
import com.tandon.dce.combat.MODEL_CombatDeck;

public class Sentry {
	int sentryId;
	int level;
	int count;
	SERVICE_StyleManager sentryMgr;
	
	public Sentry(SERVICE_StyleManager sentryMgr, int sentryId, int level, int count) {
		this.sentryId = sentryId;
		this.level = level;
		this.count = count;
		this.sentryMgr = sentryMgr;
	}
	
	public MODEL_CombatCharacter getFightingUnit(XCFFacade facade, int id, String name, MODEL_CombatCharacter unit) throws XCFException {
		
		if (unit == null) {
			unit = new MODEL_CombatCharacter(facade, id, name);
			unit.setAttribute("initiative", 0);
			unit.setAttribute("attack", 0);
			unit.setAttribute("defense", 0);
			unit.setAttribute("spirit", 0);
			unit.setAttribute("pull", 0);
			unit.setDefaultDraw("attack", 1);
		}
		
		for (int i=0; i < count; i++) { 
			// get the sentry definition
			MODEL_Class sentry = sentryMgr.getStyle(sentryId);
			
			// set the attribues
			for (String attributeId:sentry.getAttributeIDS()) {
				int value = unit.getAttribute(sentry.getAttributeName(attributeId)) + sentry.getAttributeValue(attributeId);
				unit.setAttribute(sentry.getAttributeName(attributeId), value);
			}
			
			// get the cards for the level
			MODEL_Template levelInfo = sentry.getTemplate(level);
			for (String deckName:levelInfo.getDeckNames()) {
				MODEL_CombatDeck deck = levelInfo.getDeck(deckName);
				for (MODEL_Card card:deck.getCards()) {
					unit.addCard(deckName, card);
				}
			}
		}
		
		return unit;
	}
	
	public String getName() throws XCFException {
		MODEL_Class sentry = sentryMgr.getStyle(sentryId);
		return sentry.getName();
	}
}
