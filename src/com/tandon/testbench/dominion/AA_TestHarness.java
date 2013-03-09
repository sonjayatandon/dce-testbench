package com.tandon.testbench.dominion;

import java.util.ArrayList;
import java.util.HashMap;

import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameDefinition;
import com.tandon.dce.boardengine.GameEvent;
import com.tandon.dce.boardengine.GameSession;
import com.tandon.testbench.adventure.AdventureGameDefinition;
import com.tandon.testbench.car.CarGameDefinition;

public class AA_TestHarness {
	static HashMap<String, GameComponent> gamePieces = new HashMap<String, GameComponent>();
	static public GameDefinition dominionGameDef = new DominionGameDefinition();
	static public GameDefinition adventureGameDef = new AdventureGameDefinition();
	static public GameDefinition carGameDef = new CarGameDefinition();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		dominionGameDef.createGamePieces();		
		adventureGameDef.createGamePieces();
		
		AA_TestHarness tc = new AA_TestHarness();
		tc.runCarGame();
		
	}
	
	void runOneDominionGame() {
		GameSession session = new GameSession();
		
		session.setProperty("definition.starting-kingdom-cards", new String[]{"market"});
		
		dominionGameDef.setupGameBoard(session);
		dominionGameDef.initializeTurnStructure(session);
				
		// add curse cards to game board
		
		// for each player (make the first client player)
		//   create player
		//   creating starting deck
		
		
		// run the game
		ArrayList<GameEvent> events;
		do {
			events = session.doNextAction();
			
			// handle events
			
		} while (!events.isEmpty());
		
	}
	
	void runAdventureGame() {
		GameSession adventureSession = new GameSession();
		
		adventureSession.setProperty("definition.dominion-game", dominionGameDef);
		
		dominionGameDef.setupGameBoard(adventureSession);
		dominionGameDef.initializeTurnStructure(adventureSession);
		
		// run the game
		ArrayList<GameEvent> events;
		do {
			events = adventureSession.doNextAction();
			
			// handle events
			
		} while (!events.isEmpty());
	}
	
	void runCarGame() {
		GameSession carGameSession = new GameSession();
		carGameSession.setProperty("definition.game", carGameDef);
		
		carGameSession.setProperty("player.avatar-name", "Niko Blaster");
		carGameSession.setProperty("opponent.avatar-name", "Joe Fattaboomboom");
		
		carGameDef.setupGameBoard(carGameSession);
		carGameDef.initializeTurnStructure(carGameSession);
		
		// run the game
		ArrayList<GameEvent> events;
		do {
			events = carGameSession.doNextAction();
			
			// handle events
			
		} while (!events.isEmpty());
	}

}
