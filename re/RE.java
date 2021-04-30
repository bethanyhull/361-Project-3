package re;

import java.util.Set;

import fa.State;
import fa.nfa.NFA;
import fa.nfa.NFAState;

/**
 * Generate an NFA that corresponds to a given regular expression
 * @author Bethany Hull and Marie Phelan
 */

public class RE implements REInterface {
	private String regEx;
	private Integer state = 0; //Keeps track of next NFAState name as an integer value

	public RE(String regEx) {
		this.regEx = regEx;
	}

	
	/* Recursive descent parsing internals. */

	/**
	 * returns the next item of input without consuming it; 
	 * @return next char in the regex string
	 */
	private char peek() {
		return regEx.charAt(0);
	}

	
	/**
	 * consumes the next item of input, failing if not equal to c.
	 * @param c - char to be removed from the front of the regex string
	 */
	private void eat(char c) {
		//Check that the next char is correct and remove it from the regular expression
		if (peek() == c)
			this.regEx = this.regEx.substring(1) ;
		else
			throw new 
			RuntimeException("Expected: " + c + "; got: " + peek()) ;
	}  
	
	
	/**
	 * returns the next item of input and consumes it
	 * @return next char in the regex string
	 */
	private char next() {
		char c = peek() ;
		eat(c) ;
		return c ;
	}

	/**
	 * Checks if more input is available
	 * 
	 * @return - true if there are more then 0 chars left in regex string
	 */
	private boolean more() {
		return regEx.length() > 0 ;
	}

	/**
	 * Keeps track of the number of NFA states, so the next new one can be named appropriately
	 * @return a string value of the next state's name (which is a number)
	 */
	private String getState() {
		Integer i = state;
		state++;
		return i.toString();
	}
	
	
	/* Regular expression term types. */

	/**
	 * Handles the "|" char
	 * Takes a term and regex NFA and concats them and returns them
	 * Or if no | returns the term NFA
	 * @return NFA - either combined through "|" or the term() NFA
	 */
	private NFA regex() {
		NFA term = term();

	    if (more() && peek() == '|') {
	      eat ('|') ;
	      NFA regex = regex();

	      //Create a new NFA to combine term and regex in an either/or configuration
	      NFA combined = new NFA();
	      String newStart = getState();
	      combined.addStartState(newStart);
	      Set<State> termStates = term.getStates();
	      //Check that no blank states are added
	      for(State s : termStates) {
	    	  if(s.getName().isBlank()) {
	    		  termStates.remove(s);
	    	  }
	      }
	      combined.addNFAStates(termStates);
	      combined.addNFAStates(regex.getStates());
	      combined.addTransition(newStart, 'e', term.getStartState().getName());
	      combined.addTransition(newStart, 'e', regex.getStartState().getName());
	      
	      // add the alphabet to the new language
	      combined.addAbc(term.getABC());
	      combined.addAbc(regex.getABC());
	      
	      return combined;
	    } 
	    //If there is no "|" character, there is no need to combine, so return term
	    return term;		
	}
	
	/**
	 * Concatenate terms together as an NFA
	 * @return NFA of concatenated previous and next terms
	 */
	private NFA term() {
		// start with empty NFA
		NFA factor = new NFA();
		
		// if factor is empty NFA, factor = nextFactor then as while loop continues each new "nextFactor" is concatenated to factor to make a bigger NFA
	    while (more() && peek() != ')' && peek() != '|') {
	      NFA nextFactor = factor();
	      if(factor.getStates().isEmpty()) {
	    	  factor = nextFactor;
	      }else { 
	    	  //Set of old final states
	    	  Set<State> oldFinals = factor.getFinalStates();
	    	  
	    	  //add states and alphabet to the NFA
		      factor.addNFAStates(nextFactor.getStates());
		      factor.addAbc(nextFactor.getABC());
		      
		      // for final states that are in the old list make them not final and link them to the nextFactor start state.
		      for(State f : factor.getFinalStates()) {
		    	  for (State o : oldFinals) {
		    		  if (f.getName().equals(o.getName())) {
		    			  ((NFAState) f).setNonFinal();
					      factor.addTransition(f.getName(), 'e', nextFactor.getStartState().getName());
		    			  
		    		  }
		    	  }
		      }
	      }
	    }
	    return factor ;
	}

	/**
	 * Handle the "*" char
	 * @return NFA that corresponds to the "*" operator
	 */
	private NFA factor() {
		NFA baseNFA = base();
	    
	    while (more() && peek() == '*') {
	      eat('*') ;
	      //Add transitions that implement "*"
	      for (State f : baseNFA.getFinalStates())  {
	    	  baseNFA.addTransition(f.getName(), 'e', baseNFA.getStartState().getName());
	    	  baseNFA.addTransition(baseNFA.getStartState().getName(), 'e', f.getName());
	      }
	    }
		return baseNFA;
	}

	/**
	 * Most basic of the levels parses "()" and individual chars in the language
	 * 
	 * @return basic NFA
	 */
	private NFA base() {
		switch (peek()) {
	      case '(':
	        eat('(') ;
	        NFA r = regex() ;  
	        eat(')') ;
	      return r ;

	      default:
	    	  NFA n = new NFA();
	    	  String s = getState();
	    	  String f = getState();
	    	  n.addStartState(s);
	    	  n.addFinalState(f);
	    	  n.addTransition(s, next(), f);
	      return n;
	    }

	}

	@Override
	public NFA getNFA() {
		return regex();
	}
	
}
