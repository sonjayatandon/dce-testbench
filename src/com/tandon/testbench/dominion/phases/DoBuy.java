package com.tandon.testbench.dominion.phases;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class DoBuy extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent gameComponent) {
		System.out.println("DoBuy");


	}

}
