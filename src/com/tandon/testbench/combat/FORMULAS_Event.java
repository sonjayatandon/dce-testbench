package com.tandon.testbench.combat;

import com.eternal.xcf.core.XCFException;
import com.tandon.dce.combat.ICombatFormulas;
import com.tandon.dce.combat.MODEL_CombatCharacter;

public class FORMULAS_Event implements ICombatFormulas {

	@Override
	public int[] getDamage(MODEL_CombatCharacter attacker,
			MODEL_CombatCharacter defender, int damage, int fatigue)
			throws XCFException {
		return new int[]{0, 0};
	}

	@Override
	public int getDeckID(String deckName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDeckName(Integer deckId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHP(MODEL_CombatCharacter character) throws XCFException {
		return 0;
	}

	@Override
	public int getMaxPropertyID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPropertyID(String propertyName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPropertyName(Integer propertyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int resovleFatigue(MODEL_CombatCharacter character, int fatigueDamage)
			throws XCFException {
		return 0;
	}

}
