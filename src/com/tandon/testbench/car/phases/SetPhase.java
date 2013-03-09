package com.tandon.testbench.car.phases;

import com.tandon.dce.boardengine.GameAction;
import com.tandon.dce.boardengine.GameComponent;
import com.tandon.dce.boardengine.GameSession;

public class SetPhase extends GameAction {

	@Override
	public void execute(GameSession session, GameComponent sourceComponent) {
		String phaseName = (String)getProperty("phase-name");
		GameComponent owner = (GameComponent)getProperty("phase-owner");

		System.out.println("========== ======================================");
		System.out.print(phaseName);
		if (owner != null) {
			System.out.print(": " + owner.getProperty("avatar-name"));
		}
		System.out.println("");
		System.out.println("========== ======================================");
	}

}
