package com.tandon.testbench.dominion.phases;

import java.util.ArrayList;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class StartGame extends GameAction {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(GameSession session, GameComponent gameComponent) {
		System.out.println("Start game");
		
		ArrayList<GameAction> phases = (ArrayList<GameAction>)session.getProperty("phases");
		session.pushActionList(phases);
	}

}
