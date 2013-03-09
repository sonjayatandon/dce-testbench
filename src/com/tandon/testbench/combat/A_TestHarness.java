package com.tandon.testbench.combat;

import com.eternal.xcf.core.XCFException;
import com.eternal.xcf.core.XCFRequest;
import com.eternal.xcf.core.XCFLogger.LogTypes;
import com.eternal.xcf.core.loggers.LOGGER_Console;
import com.eternal.xcf.core.loggers.LOGGER_Null;
import com.tandon.dce.DCEInterface;
import com.tandon.dce.characterclass.SERVICE_StyleManager;
import com.tandon.dce.combat.REQUEST_Simple;
import com.tandon.testbench.combat.config.Loader;

public class A_TestHarness extends DCEInterface {
	FORMULAS_Combat formulas;
	SERVICE_StyleManager sentryMgr;
	
	
	public A_TestHarness() {
		super();
		
		try {
			Loader.s().load(facade);
			formulas = new FORMULAS_Combat();
			facade.getLogManager().setLogger(LogTypes.DEBUG, new LOGGER_Null());
			sentryMgr = (SERVICE_StyleManager)facade.getService(SERVICE_StyleManager.XCF_TAG);
		} catch (XCFException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		A_TestHarness harness = new A_TestHarness();

		try {
			harness.facade.getLogManager().registerLogger("combat", new LOGGER_Console());
			harness.runTestCases();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	public void runTestCases() throws XCFException {
		TestCase[] testCases = new TestCase[] {
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 2, 1, 1)},  // 1G1 vs. 1H1
						     new Sentry[]{new Sentry(sentryMgr, 3, 1, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 2, 1, 1)},  // 1G1 vs. 2H1
					     	 new Sentry[]{new Sentry(sentryMgr, 3, 1, 2)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),	// 1G1,1H1 vs 1H2
										  new Sentry(sentryMgr, 3, 1, 1)},  
							 new Sentry[]{new Sentry(sentryMgr, 3, 2, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),	// 1G1,1H1 vs 2H1
						  	 			  new Sentry(sentryMgr, 3, 1, 1)},  
						  	 new Sentry[]{new Sentry(sentryMgr, 3, 2, 2)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 2, 2, 1),	// 1G2, 2H1 vs 1G1, 1G2, 1H2
		  	 			  	 			  new Sentry(sentryMgr, 3, 1, 2)},  
		  	 			  	 new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),
										  new Sentry(sentryMgr, 2, 2, 1),
										  new Sentry(sentryMgr, 3, 2, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),	// 1G1, 1G2, 1G3 vs 1H1, 1H2, 1H3
			  	 			  			  new Sentry(sentryMgr, 2, 2, 1),
			  	 			  			  new Sentry(sentryMgr, 2, 3, 1)},  
			  	 			 new Sentry[]{new Sentry(sentryMgr, 3, 1, 1),
						  				  new Sentry(sentryMgr, 3, 2, 1),
						  				  new Sentry(sentryMgr, 3, 3, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 1, 1, 40)},	// 40V1 vs 1H1, 1H2, 1H3
							 new Sentry[]{new Sentry(sentryMgr, 3, 1, 1),
		  				  				  new Sentry(sentryMgr, 3, 2, 1),
		  				  				  new Sentry(sentryMgr, 3, 3, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 1, 1, 30)},	// 30V1 vs 1H1, 1H2, 1H3
						 	 new Sentry[]{new Sentry(sentryMgr, 3, 1, 1),
	  				  				  	  new Sentry(sentryMgr, 3, 2, 1),
	  				  				  	  new Sentry(sentryMgr, 3, 3, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 1, 1, 30)},	// 30V1 vs 1G1, 1G2, 1G3
					 	 	 new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),
 				  				  	  	  new Sentry(sentryMgr, 2, 2, 1),
 				  				  	  	  new Sentry(sentryMgr, 2, 3, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 1, 1, 20)},	// 20V1 vs 1H1, 1H2, 1H3
					 	 				  new Sentry[]{new Sentry(sentryMgr, 3, 1, 1),
 				  				  	  	  new Sentry(sentryMgr, 3, 2, 1),
 				  				  	      new Sentry(sentryMgr, 3, 3, 1)}),
 				new TestCase(new Sentry[]{new Sentry(sentryMgr, 1, 1, 20)},	// 20V1 vs 1G1, 1G2, 1G3
				 	 	 				  new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),
			  				  	  	  	  new Sentry(sentryMgr, 2, 2, 1),
			  				  	  	  	  new Sentry(sentryMgr, 2, 3, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 4, 1, 1)},  // 1Hlr1 vs. 1H1
										  new Sentry[]{new Sentry(sentryMgr, 3, 1, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 4, 1, 1)},  // 1Hlr1 vs. 1G1
										  new Sentry[]{new Sentry(sentryMgr, 2, 1, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),	// 1G1, 1G2, 1G3, 1Hlr3 vs 1H1, 1H2, 1H3, 1Hlr3
 			  			  				  new Sentry(sentryMgr, 2, 2, 1),
 			  			  				  new Sentry(sentryMgr, 2, 3, 1),
 			  			  				  new Sentry(sentryMgr, 4, 3, 1)},  
 			  			  	 new Sentry[]{new Sentry(sentryMgr, 3, 1, 1),
		  				  				  new Sentry(sentryMgr, 3, 2, 1),
		  				  				  new Sentry(sentryMgr, 3, 3, 1),
		  				  				  new Sentry(sentryMgr, 4, 3, 1)}),
		  		new TestCase(new Sentry[]{new Sentry(sentryMgr, 5, 1, 1)},  // 1E1 vs. 1G1
							 new Sentry[]{new Sentry(sentryMgr, 2, 1, 1)}),
				new TestCase(new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),	// 1G1, 1G2, 1G3, 1Hlr3, 1E3 vs 1H1, 1H2, 1H3, 1Hlr3, 1E3
			  			  				  new Sentry(sentryMgr, 2, 2, 1),
			  			  				  new Sentry(sentryMgr, 2, 3, 1),
			  			  				  new Sentry(sentryMgr, 4, 3, 1),
			  			  				  new Sentry(sentryMgr, 5, 3, 1)},  
			  			  	 new Sentry[]{new Sentry(sentryMgr, 3, 1, 1),
		  				  				  new Sentry(sentryMgr, 3, 2, 1),
		  				  				  new Sentry(sentryMgr, 3, 3, 1),
		  				  				  new Sentry(sentryMgr, 4, 3, 1),
		  				  				  new Sentry(sentryMgr, 5, 3, 1)}),
		  		new TestCase(4, new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),	// 1G123, 1H123, 1Hlr3, 1E3 vs 1G123, 1H123, 1Hlr3, 1E3
					  			  		  new Sentry(sentryMgr, 2, 2, 1),
					  			  		  new Sentry(sentryMgr, 2, 3, 1),
					  			  		  new Sentry(sentryMgr, 3, 1, 1),
					  			  		  new Sentry(sentryMgr, 3, 2, 1),
					  			  		  new Sentry(sentryMgr, 3, 3, 1),
					  			  		  new Sentry(sentryMgr, 4, 3, 1),
					  			  		  new Sentry(sentryMgr, 5, 3, 1)},  
					  		 new Sentry[]{new Sentry(sentryMgr, 2, 1, 1),
				  				  		  new Sentry(sentryMgr, 2, 2, 1),
				  				  		  new Sentry(sentryMgr, 2, 3, 1),
					  			  		  new Sentry(sentryMgr, 3, 1, 1),
					  			  		  new Sentry(sentryMgr, 3, 2, 1),
					  			  		  new Sentry(sentryMgr, 3, 3, 1),
				  				  		  new Sentry(sentryMgr, 4, 3, 1),
				  				  		  new Sentry(sentryMgr, 5, 3, 1)})
		};

		for (TestCase test: testCases) {
			System.out.println("===========================================================================");
			System.out.println(test.getDescription());
			
			MODEL_CombatResolver resolver = new MODEL_CombatResolver(facade);
			XCFRequest req = new REQUEST_Simple("", "");
			req.setContext(cmgr.getNewContext());
			
			resolver.setup(req, formulas, "Player A", test.playerSentries, "Player B", test.opponentSentries);
			resolver.resolve(test.rounds);
		
		}
	}
	
	class TestCase {
		int rounds = 3;
		Sentry[] playerSentries;
		Sentry[] opponentSentries;
		
		TestCase(Sentry[] playerSentries, Sentry[] opponentSentries) {
			this.playerSentries = playerSentries;
			this.opponentSentries = opponentSentries;
		}
		
		TestCase(int rounds, Sentry[] playerSentries, Sentry[] opponentSentries) {
			this.playerSentries = playerSentries;
			this.opponentSentries = opponentSentries;
			this.rounds = rounds;
		}
		
		String getDescription() throws XCFException {
			StringBuffer b = new StringBuffer();
			String sep = "";
			for (Sentry sentry: playerSentries) {
				b.append(sep);
				b.append(sentry.count);
				b.append(" L");
				b.append(sentry.level);
				b.append(" ");
				b.append(sentry.getName());
				sep = ", ";
			}
			b.append(" VERSUS ");
			sep = "";
			for (Sentry sentry: opponentSentries) {
				b.append(sep);
				b.append(sentry.count);
				b.append(" L");
				b.append(sentry.level);
				b.append(" ");
				b.append(sentry.getName());
				sep = ",";
			}
			return b.toString();
		}
	}
}
