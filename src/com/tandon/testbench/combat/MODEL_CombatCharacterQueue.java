package com.tandon.testbench.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.eternal.xcf.core.XCFException;
import com.tandon.dce.combat.MODEL_CombatCharacter;

public class MODEL_CombatCharacterQueue {
	ArrayList<MODEL_CombatCharacter> queue = new ArrayList<MODEL_CombatCharacter>();

	public void addCharacter(MODEL_CombatCharacter c) {
		queue.add(c);
	}

	public void loadWith(MODEL_CombatCharacterQueue units) {
		queue.addAll(units.queue);
		
	}

	public int size() {
		return queue.size();
	}

	public void sortBy(String attribute, boolean ascending) {
		Collections.sort(queue, new CompareByAttribute(attribute, ascending));
	}
	
	class CompareByAttribute implements Comparator<MODEL_CombatCharacter> {
		boolean ascending;
		String attribute;
		CompareByAttribute(String attribute, boolean ascending) {
			this.ascending = ascending;
			this.attribute = attribute;
		}
		
		@Override
		public int compare(MODEL_CombatCharacter c1, MODEL_CombatCharacter c2) {
			try {
				int v1 = c1.getAttribute(attribute);
				int v2 = c2.getAttribute(attribute);
				
				if (ascending) {
					return (v1 < v2 ? -1 :(v1 == v2 ? 0 : 1));
				} else {
					return (v1 < v2 ? 1 :(v1 == v2 ? 0 : -1));
				}
			} catch (XCFException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
}
