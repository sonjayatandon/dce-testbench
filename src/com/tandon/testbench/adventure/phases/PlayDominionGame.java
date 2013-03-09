package com.tandon.testbench.adventure.phases;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class PlayDominionGame extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent sourceComponent) {
		GameSession dominionSession = (GameSession)session.getProperty("curr-session");
		
		dominionSession.doNextAction();
		
		if (!dominionSession.isGameOver()) {
			session.queueAction(this);
		}
	}

}
