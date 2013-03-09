package com.tandon.testbench.adventure;

import java.util.ArrayList;
import java.util.HashMap;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameDefinition;
import com.tandon.dce.boardengine.GameSession;
import com.tandon.testbench.adventure.phases.InitializeDominionGame;
import com.tandon.testbench.adventure.phases.PlayDominionGame;

public class AdventureGameDefinition implements GameDefinition {
	private HashMap<String, GameComponent> gamePieces = new HashMap<String, GameComponent>();

	@Override
	public void createGamePieces() {
		GameComponent encounter;
		
		// define zone1adventure1		
		encounter = new GameComponent("adventure1", null, null);
		encounter.setProperty("zap-points", 4);
		encounter.setProperty("num-players", 2);
		encounter.setProperty("player-1-deck", new String[]{"copper","copper","copper","copper","copper","copper","copper","estate","estate","estate"});
		encounter.setProperty("player-2-deck", new String[]{"copper","copper","copper","copper","copper","estate","curse","curse","curse","curse"});
		encounter.setProperty("starting-kingdom-cards", new String[]{"moneylender", "smithy","council-room","laboratory","market","cellar","moat","village","woodcutter","workshop"});
		encounter.setProperty("unlocks", new String[]{"zone1adventure2"});
		gamePieces.put("zone1adventure1", encounter);	

		// define zone1adventure2		
		encounter = new GameComponent("adventure2", null, null);
		encounter.setProperty("zap-points", 2);
		encounter.setProperty("num-players", 2);
		encounter.setProperty("player-1-deck", new String[]{"copper","copper","copper","copper","copper","copper","copper","estate","estate","estate"});
		encounter.setProperty("player-2-deck", new String[]{"copper","copper","copper","copper","copper","copper","estate","estate","estate","estate"});
		encounter.setProperty("starting-kingdom-cards", new String[]{"smithy","spy","council-room","laboratory","market","cellar","moat","village","woodcutter","workshop"});
		encounter.setProperty("unlocks", new String[]{});
		gamePieces.put("zone1adventure2", encounter);	
	}

	@Override
	public void setupGameBoard(GameSession session) {
		
		session.setProperty("definition.adventure-game", this);
		session.setProperty("curr-adventure", "zone1adventure1");
		
	}

	@Override
	public void addPlayer(GameSession session, GameComponent player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initializeTurnStructure(GameSession session) {
		ArrayList<GameAction> phases = new ArrayList<GameAction>();
		phases.add(new InitializeDominionGame());
//		phases.add(new ZapPhase());
		phases.add(new PlayDominionGame());
//		phases.add(new ResolveDominonGame());
//		phases.add(new SelectNextAdventure());
		
		// initialize dominion game session
		// play dominion game
		// resolve game results (which may unlock other adventures
		// 

	}

	@Override
	public GameComponent getGamePiece(String id) {
		return gamePieces.get(id);
	}

}
