package com.tandon.testbench.car;

import java.util.ArrayList;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameComponentStack;
import com.tandon.dce.boardengine.GameDefinition;
import com.tandon.dce.boardengine.GameSession;
import com.tandon.testbench.car.phases.ActivateAbilities;
import com.tandon.testbench.car.phases.EndTurn;
import com.tandon.testbench.car.phases.SetPhase;
import com.tandon.testbench.car.phases.StartRound;
import com.tandon.testbench.car.phases.Upkeep;

public class CarGameDefinition implements GameDefinition {

	@Override
	public void createGamePieces() {
		GameComponent vehicle;
		GameComponentStack weapons;
		GameComponent weapon;
		
		vehicle = new GameComponent("vehicle", null, null);
		vehicle.setProperty("speed", 30);
		vehicle.setProperty("evasion", 21);
		vehicle.setProperty("accuracy", 65);
		vehicle.setProperty("armor", 48);
		
		weapons = new GameComponentStack("weapons", null, null);
		
		weapon = new GameComponent("battering-ram", null, null);
		
	}

	@Override
	public void setupGameBoard(GameSession session) {
		session.setProperty("definition.num-rounds", 2);
		
	}

	@Override
	public void addPlayer(GameSession session, GameComponent player) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initializeTurnStructure(GameSession session) {
		
		GameComponent player = session.getComponent("player");
		GameComponent opponent = session.getComponent("opponent");
		
		ArrayList<GameAction> phases = new ArrayList<GameAction>();
		GameAction action;
				
		// Define start round actions
		action = new SetPhase();
		action.setProperty("phase-name", "START ROUND");
		phases.add(action);
		
		action = new Upkeep();
		phases.add(action);
		
		action = new StartRound();
		phases.add(action);
		session.setProperty("definition.start-round", phases);
		
		// Define player turn (setphase, activate, end turn)
		phases = new ArrayList<GameAction>();
		action = new SetPhase();
		action.setProperty("phase-name", "START TURN");
		action.setProperty("phase-owner", player);
		action.setProperty("attacker", player);
		action.setProperty("defender", opponent);
		phases.add(action);
		
		action = new ActivateAbilities();
		phases.add(action);
		
		action = new EndTurn();
		phases.add(action);
		
		session.setProperty("definition.player-turn", phases);
		
		// Define opponent turn (setphase, activate, end turn)
		phases = new ArrayList<GameAction>();
		action = new SetPhase();
		action.setProperty("phase-name", "START TURN");
		action.setProperty("phase-owner", opponent);
		action.setProperty("attacker", opponent);
		action.setProperty("defender", player);
		phases.add(action);
		
		action = new ActivateAbilities();
		phases.add(action);
		
		action = new EndTurn();
		phases.add(action);
		
		session.setProperty("definition.opponent-turn", phases);
		
		// now load up start round
		session.pushActionList((ArrayList<GameAction>)session.getProperty("definition.start-round"));
	}

	@Override
	public GameComponent getGamePiece(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
