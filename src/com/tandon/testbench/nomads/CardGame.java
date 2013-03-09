package com.tandon.testbench.nomads;

import java.util.ArrayList;
import java.util.Stack;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFFacade;
import com.eternal.xcf.core.XCFRequest;
import com.tandon.dce.cards.MODEL_Card;
import com.tandon.testbench.nomads.combat.BaseCommand;
import com.tandon.testbench.nomads.combat.steps.ActivateAbilities;
import com.tandon.testbench.nomads.combat.steps.Cleanup;
import com.tandon.testbench.nomads.combat.steps.EndTurn;
import com.tandon.testbench.nomads.combat.steps.PlayAbility;
import com.tandon.testbench.nomads.combat.steps.SetPhase;
import com.tandon.testbench.nomads.combat.steps.StartRound;
import com.tandon.testbench.nomads.combat.steps.Upkeep;

public class CardGame { 
	XCFFacade facade;
	XCFRequest req;
	Stack<BaseCommand> startRound = new Stack<BaseCommand>();
	Stack<BaseCommand> playerTurn = new Stack<BaseCommand>();
	Stack<BaseCommand> opponentTurn = new Stack<BaseCommand>();
	
	Stack<BaseCommand> activeSteps = new Stack<BaseCommand>();
	
	Player player; 
	Player opponent;
	
	int numRounds = 10;
	
	public ArrayList<Events> events = null;
	
	public CardGame(XCFFacade facade, XCFRequest req, Player player, Player opponent) {
		this.facade = facade;
		this.req = req;
		this.player = player;
		this.opponent = opponent;
		
		try {
			req.getContext().putValue("card-game", this);
		} catch (XCFException e) {e.printStackTrace();}
	}
	
	public void setup() {
		BaseCommand step;
		
		// store all the 
	
		// start round phase
		step = new SetPhase();
		step.setProperty("phase-name", "START ROUND");
		startRound.push(step);
		step = new Upkeep();
		step.setProperty("player", player);
		step.setProperty("opponent", opponent);
		startRound.push(step);
		step = new Cleanup();
		startRound.push(step);
		step = new StartRound();  // determine who goes first and loads that players steps
		step.setProperty("player", player);
		step.setProperty("opponent", opponent);
		startRound.push(step);
		
		// always end with transition to next phase
		
		// player turn
		step = new SetPhase();
		step.setProperty("phase-name", "START TURN");
		step.setProperty("phase-owner", player);
		playerTurn.push(step);
		step = new ActivateAbilities();
		step.setProperty("attacker", player);
		step.setProperty("defender", opponent);
		playerTurn.push(step);
		step = new Cleanup();
		playerTurn.push(step);
		step = new EndTurn();   // decrements turn count, either loads another turn, new round, or ends game
		step.setProperty("attacker", player);
		step.setProperty("defender", opponent);
		step.setProperty("start-round-steps", startRound);
		playerTurn.push(step);
		
		player.setTurnSteps(playerTurn);

		// opponent turn
		step = new SetPhase();
		step.setProperty("phase-name", "START TURN");
		step.setProperty("phase-owner", opponent);
		opponentTurn.push(step);
		step = new ActivateAbilities();
		step.setProperty("attacker", opponent);
		step.setProperty("defender", player);
		opponentTurn.push(step);
		step = new Cleanup();
		opponentTurn.push(step);
		step = new EndTurn();   // either loads another turn, new round, or ends game
		step.setProperty("attacker", opponent);
		step.setProperty("defender", player);
		step.setProperty("start-round-steps", startRound);
		opponentTurn.push(step);

		opponent.setTurnSteps(opponentTurn);

		// cool, now that all that meta data is setup, how about
		// we actually load up some steps
		loadActiveSteps(startRound);
	}
	
	public void doNextSteps() throws XCFException {
		events = new ArrayList<Events>();
		
		while (!activeSteps.isEmpty() && events.isEmpty()) {
			BaseCommand step = activeSteps.pop();
			step.execute(req);
		}
		
		if (activeSteps.isEmpty()) events.add(Events.GameOver);
	}
	
	public void playCard(MODEL_Card ability) throws XCFException {
		BaseCommand step = new PlayAbility();
		step.setProperty("ability", ability);
		step.setProperty("game", this);
		activeSteps.push(step);
	}
	
	public void loadActiveSteps(Stack<BaseCommand> stepsToLoad) {
		@SuppressWarnings("unchecked")
		Stack<BaseCommand> source = (Stack<BaseCommand>) stepsToLoad.clone();
		while (!source.isEmpty()) {
			activeSteps.push(source.pop());
		}
	}
	
	public void postEvent(Events event) {
		events.add(event);
	}
	
	public int getNumRounds() {return numRounds;}
	public void endRound() {numRounds--;}
	
	
}
