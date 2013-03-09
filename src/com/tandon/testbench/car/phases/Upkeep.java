package com.tandon.testbench.car.phases;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class Upkeep extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent sourceComponent) {
		System.out.println("UPKEEP");

	}

}
