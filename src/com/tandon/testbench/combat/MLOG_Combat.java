package com.tandon.testbench.combat;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.dce.cards.MODEL_Card;
import com.tandon.dce.combat.ICombatLog;
import com.tandon.dce.combat.MODEL_CombatCharacter;

public class MLOG_Combat implements ICombatLog {

	@Override
	public void logAddCard(XCFRequest request, MODEL_CombatCharacter character,
			String deckName, MODEL_Card card, int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logDamage(XCFRequest request, MODEL_CombatCharacter defender,
			String string, int damageAmount, int fractionalDamage)
			throws XCFException {
		// TODO Auto-generated method stub

	}

	@Override
	public void logDisable(XCFRequest request, MODEL_CombatCharacter character,
			String deckName, int numDraws) throws XCFException {
		// TODO Auto-generated method stub

	}

	@Override
	public void logDiscard(XCFRequest request, MODEL_CombatCharacter target,
			String deckName, MODEL_Card zappedCard) throws XCFException {
		// TODO Auto-generated method stub

	}

	@Override
	public void logEvent(int avatarID, int eventID, int messageID, int[] params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logHit(XCFRequest request) throws XCFException {
		// TODO Auto-generated method stub

	}

	@Override
	public void logInitiative(XCFRequest request,
			MODEL_CombatCharacter nextAttacker) throws XCFException {
		// TODO Auto-generated method stub

	}

	@Override
	public void logOverride(XCFRequest request,
			MODEL_CombatCharacter character, String deckName, int cardId,
			int amount) throws XCFException {
		// TODO Auto-generated method stub

	}

	@Override
	public void logPlayCard(XCFRequest request,
			MODEL_CombatCharacter cardOwner, String deckName, MODEL_Card card)
			throws XCFException {
		// TODO Auto-generated method stub

	}

	@Override
	public void logRecover(XCFRequest request, MODEL_CombatCharacter character,
			String deckName, MODEL_Card recoveredCard) throws XCFException {
		// TODO Auto-generated method stub

	}

}
