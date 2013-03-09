package com.tandon.testbench.dominion;

import java.util.ArrayList;
import java.util.HashMap;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameDefinition;
import com.tandon.dce.boardengine.GameSession;
import com.tandon.testbench.dominion.commands.AddToProperty;
import com.tandon.testbench.dominion.commands.DrawIntoHand;
import com.tandon.testbench.dominion.phases.Cleanup;
import com.tandon.testbench.dominion.phases.DoAction;
import com.tandon.testbench.dominion.phases.DoBuy;
import com.tandon.testbench.dominion.phases.EndGame;
import com.tandon.testbench.dominion.phases.EndTurn;
import com.tandon.testbench.dominion.phases.StartGame;
import com.tandon.testbench.dominion.phases.StartTurn;

public class DominionGameDefinition implements GameDefinition {
	private HashMap<String, GameComponent> gamePieces = new HashMap<String, GameComponent>();

	@Override
	public void createGamePieces() {
		GameAction action;
		
		// define estate card
		GameComponent card = new GameComponent("estate", null, null);
		card.setProperty("victory-points", 1);
		card.setProperty("cost", 2);
		card.setProperty("keywords", "victory-card");
		gamePieces.put("estate", card);
		
		// define duchy card
		card = new GameComponent("duchy", null, null);
		card.setProperty("victory-points", 3);
		card.setProperty("cost", 5);
		card.setProperty("keywords", "victory-card");
		gamePieces.put("duchy", card);
		
		// define province card
		card = new GameComponent("province", null, null);
		card.setProperty("victory-points", 6);
		card.setProperty("cost", 8);
		card.setProperty("keywords", "victory-card");
		gamePieces.put("province", card);		
		
		// define copper card
		card = new GameComponent("copper", null, null);
		card.setProperty("cost", 0);
		card.setProperty("keywords", "treasure-card");
		
		action = new AddToProperty();
		action.setProperty("propertyName", "current-player.coins");
		action.setProperty("amount", 1);
		
		card.addAction("on-play", action);
		
		gamePieces.put("copper", card);
		
		// define silver card
		card = new GameComponent("silver", null, null);
		card.setProperty("cost", 3);
		card.setProperty("keywords", "treasure-card");
		
		action = new AddToProperty();
		action.setProperty("propertyName", "current-player.coins");
		action.setProperty("amount", 2);
		card.addAction("on-play", action);
		
		gamePieces.put("silver", card);		
		
		// define gold card
		card = new GameComponent("gold", null, null);
		card.setProperty("cost", 6);
		card.setProperty("keywords", "treasure-card");
		
		action = new AddToProperty();
		action.setProperty("propertyName", "current-player.coins");
		action.setProperty("amount", 3);	
		card.addAction("on-play", action);
		
		gamePieces.put("gold", card);		
		
		// add market card (+1 card, +1 action, +1 buy, +1 coin)
		card = new GameComponent("market", null, null);
		card.setProperty("cost", 5);
		card.setProperty("keywords", "kingdom-card");
		
		action = new DrawIntoHand();
		card.addAction("on-play", action);
		
		action = new AddToProperty();
		action.setProperty("propertyName", "current-player.action");
		action.setProperty("amount", 1);		
		card.addAction("on-play", action);
		
		action = new AddToProperty();
		action.setProperty("propertyName", "current-player.buy");
		action.setProperty("amount", 1);		
		card.addAction("on-play", action);
		
		action = new AddToProperty();
		action.setProperty("propertyName", "current-player.coins");
		action.setProperty("amount", 1);		
		card.addAction("on-play", action);
		
		gamePieces.put("market", card);		
		
	}

	@Override
	public void setupGameBoard(GameSession session) {
		String[] kindomCardIds = (String[])session.getProperty("definition.starting-kingdom-cards");
		Integer numPlayers = (Integer)session.getProperty("definition.num-players");
		Integer numVictoryCards = 12;
		
		if (numPlayers == 2) {
			numVictoryCards = 8;
		}
		
		
		// add victory cards to game board
		session.addNewToComponentStack("victory-cards.estates", gamePieces.get("estate"), numVictoryCards);
		session.addNewToComponentStack("victory-cards.duchies", gamePieces.get("duchy"), numVictoryCards);
		session.addNewToComponentStack("victory-cards.province", gamePieces.get("province"), numVictoryCards);
		
		// add treasure cards to game board
		session.addNewToComponentStack("treasure-cards.copper", gamePieces.get("copper"), 60);
		session.addNewToComponentStack("treasure-cards.copper", gamePieces.get("silver"), 40);
		session.addNewToComponentStack("treasure-cards.copper", gamePieces.get("gold"), 30);
		
		// TODO add curse cards
		
		// add kingdom cards to game board
		int slotNum=1;
		for (String cardId: kindomCardIds) {
			Integer numCards = 10;
			if (cardId.equals("garden")) numCards = numVictoryCards;
			
			session.addNewToComponentStack("kingdom-cards.slot"+slotNum, gamePieces.get(cardId), numCards);
			slotNum++;
		}
		
	}

	@Override
	public void addPlayer(GameSession session, GameComponent player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeTurnStructure(GameSession session) {
		ArrayList<GameAction> phases = new ArrayList<GameAction>();
		phases.add(new StartTurn());
		phases.add(new DoAction());
		phases.add(new DoBuy());
		phases.add(new Cleanup());
		phases.add(new EndTurn());
		
		session.setProperty("phases", phases);
		
		session.queueAction(new StartGame());
		session.queueAction(new EndGame());
	}

	@Override
	public GameComponent getGamePiece(String id) {
		return gamePieces.get(id);
	}


}
