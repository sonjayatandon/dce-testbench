package com.tandon.testbench.dominion.commands;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class DrawIntoHand extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent gameComponent) {
		/*
		GameComponent deck = session.getComponent("current-player.deck");
		
		if (deck.size() == 0) {
			GameComponent discard = session.getComponent("current-player.discard");
			session.setComponent("current-player.deck", discard);
			session.setComponent("current-player.discard", deck);
			
			deck = discard;
			deck.shuffle();
		}
		
		GameComponent card = deck.pop();
		session.addComponent("current-player.hand", card);
		
		// TODO create event to update display of hand
		*/
	}

}
