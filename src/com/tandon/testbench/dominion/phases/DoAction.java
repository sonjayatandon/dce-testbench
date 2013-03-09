package com.tandon.testbench.dominion.phases;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class DoAction extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent gameComponent) {
		System.out.println("DoAction");
		/*
		GameComponent currentPlayer = session.getComponent("current-player");

		Integer actions = (Integer)currentPlayer.getProperty("action");
		if (actions > 0) {
			// session.post event:  play card
			// selectable cards are all cards "kindgom cards" in hand
			System.out.println("Select an action or treasure card to play");
			session.pushAction(this);
		}
		*/
	}

}
