package com.tandon.testbench.adventure.phases;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameDefinition;
import com.tandon.dce.boardengine.GameSession;

public class InitializeDominionGame extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent sourceComponent) {
		GameSession dominionSession = new GameSession();
		
		session.setProperty("curr-session", dominionSession);
		
		GameDefinition dominionGameDef = (GameDefinition)session.getProperty("definition.dominion-game");
		GameDefinition adventureGameDef = (GameDefinition)session.getProperty("definition.adventure-game");
		
		String adventureSetupId = (String)session.getProperty("curr-adventure");
		GameComponent adventureSetup = adventureGameDef.getGamePiece(adventureSetupId);
		
		Integer numPlayers = (Integer)adventureSetup.getProperty("num-players");
		dominionSession.setProperty("definition.starting-kingdom-cards", adventureSetup.getProperty("starting-kingdom-cards"));
		dominionSession.setProperty("definition.num-players", numPlayers);
		for (int p=0; p<numPlayers;p++) {
			dominionSession.setProperty("definition.player-"+p+"-deck", adventureSetup.getProperty("definition.player-"+p+"-deck"));
		}
		
		dominionGameDef.setupGameBoard(session);
		dominionGameDef.initializeTurnStructure(session);

	}

}
