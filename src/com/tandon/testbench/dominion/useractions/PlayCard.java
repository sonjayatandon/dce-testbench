package com.tandon.testbench.dominion.useractions;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class PlayCard extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent gameComponent) {
		GameComponent card = session.getComponent("selected-card");

		card.execute("on-play");
	}

}
