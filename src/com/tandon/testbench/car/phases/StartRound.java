package com.tandon.testbench.car.phases;

import java.util.ArrayList;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class StartRound extends GameAction {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(GameSession session, GameComponent sourceComponent) {
		System.out.println("START ROUND");
		
		ArrayList<GameAction> phases = (ArrayList<GameAction>)session.getProperty("definition.player-turn");
		session.pushActionList(phases);
		phases = (ArrayList<GameAction>)session.getProperty("definition.opponent-turn");
		session.pushActionList(phases);
		
		

	}

}
