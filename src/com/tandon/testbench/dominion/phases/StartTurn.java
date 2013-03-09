package com.tandon.testbench.dominion.phases;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class StartTurn extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent gameComponent) {
		System.out.println("Start Turn");

		/*
		GameComponent player = session.getComponent("current-player");

		player.setProperty("action", 1);
		player.setProperty("buy", 1);
		player.setProperty("coins", 0);
		*/
	}

}
