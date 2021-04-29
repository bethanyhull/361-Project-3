package re;

import java.util.Set;

import fa.State;
import fa.nfa.NFA;
import fa.nfa.NFAState;

/**
 * @author bethanyhull
 *
 */

public class RE implements REInterface {
	private String regEx;
	private Integer state = 0;

	public RE(String regEx) {
		this.regEx = regEx;
	}

	
	/* Recursive descent parsing internals. */

	/**
	 * returns the next item of input without consuming it;
	 * 
	 * @return next char in the regex string
	 */
	private char peek() {
		return regEx.charAt(0);
	}

	
	/**
	 * consumes the next item of input, failing if not equal to c.
	 * 
	 * @param c - char to be removed from the front of the regex string
	 */
	private void eat(char c) {
		if (peek() == c)
			this.regEx = this.regEx.substring(1) ;
		else
			throw new 
			RuntimeException("Expected: " + c + "; got: " + peek()) ;
	}  
	
	
	/**
	 * returns the next item of input and consumes it
	 * 
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
	 * @return
	 */
	private NFA regex() {
		NFA term = term();

	    if (more() && peek() == '|') {
	      eat ('|') ;
	      NFA regex = regex();

	      NFA combined = new NFA();
	      String newStart = getState();
	      combined.addStartState(newStart);
	      Set<State> termStates = term.getStates();
	      for(State s : termStates) {
	    	  if(s.getName().isBlank()) {
	    		  termStates.remove(s);
	    		  System.out.println("Found null");
	    	  }
	      }
	      combined.addNFAStates(termStates);
	      combined.addNFAStates(regex.getStates());
	      combined.addTransition(newStart, 'e', term.getStartState().getName());
	      combined.addTransition(newStart, 'e', regex.getStartState().getName());
	      
	      System.out.println("term states: " + term.toString());
	      System.out.println("regex states: " + regex.toString());
	      System.out.println("combined states: " + combined.getStates().toString());
	      // add the alphabet to the new language
	      combined.addAbc(term.getABC());
	      combined.addAbc(regex.getABC());

	      
	      // TODO: need a method to handle this
	      //Create a new NFA that combines the two NFA's in an either/or configuration
	      //return new Choice(term,regex) 
//	  	  System.out.println("\nRegex");
//	    	System.out.println(combined.toString());
	      return combined;
	    } 
//	    	  System.out.println("\nRegex");
//	    	  	System.out.println(term.toString());
	     return term ;
	    
		
		//TODO: flesh out this method
	}

	private NFA term() {
		// start with empty NFA
		NFA factor = new NFA();
		

		// if factor is empty NFA, factor = nextFactor
		// then as while loop continues each new "nextFactor" is concatenated to factor to make a bigger NFA
		// for addTransition try using forEach loop like on line 136
	    while (more() && peek() != ')' && peek() != '|') {
	      NFA nextFactor = factor();
//	  	  System.out.println("\nDEBUG: Next factor");
//	    	System.out.println(nextFactor.toString());
	      if(factor.getStates().isEmpty()) {
	    	  factor = nextFactor;
	      }else {
		      //NFAState from = new NFAState("?"); I was thinking there would only be a transition on the last state, so how would we find that
	    	  //So if we are instead taking the final states from before, do we now need to make sure they are no longer final?
	    	  
	    	  //Set of old final states
	    	  Set<State> oldFinals = factor.getFinalStates();
	    	  
	    	  //add states and alphabet to the NFA
		      factor.addNFAStates(nextFactor.getStates());
		      factor.addAbc(nextFactor.getABC());
		      
		      // for final states that are in th old liist
		      // make them not final and link them to the nextFactor start state.
		      for(State f : factor.getFinalStates()) {
		    	  for (State o : oldFinals) {
		    		  if (f.getName().equals(o.getName())) {
		    			  ((NFAState) f).setNonFinal();
					      factor.addTransition(f.getName(), 'e', nextFactor.getStartState().getName());
		    			  
		    		  }
		    	  }
		    	  
		    

//			      NFAState oldFinal = (NFAState)f;
//			      //oldFinal.setNonFinal();
//			      Set<NFAState> newFinal = factor.eClosure(oldFinal);
//			      System.out.println("new finals: " + newFinal.toString());
//			      for(NFAState s : newFinal) {
//			    	  if(s.equals(oldFinal)) {
//				    	  s.setNonFinal();
//			    	  }else {
//			    		  s.setFinal();
//			    	  }
			      //}
		      }
//		      System.out.println("Final states " + factor.getFinalStates().toString());

	      }
	     
	      //TODO: Concatenation of factor and next factor
	    }
	    
//	  	  System.out.println("\nTerm");
//	    	System.out.println(factor.toString());
//	    	System.out.println("Final states " + factor.getFinalStates().toString());
	    return factor ;
	}

	private NFA factor() {
		NFA baseNFA = base();
	    
	    while (more() && peek() == '*') {
	      eat('*') ;
	      for (State f : baseNFA.getFinalStates())  {
	    	  baseNFA.addTransition(f.getName(), 'e', baseNFA.getStartState().getName());
	    	  baseNFA.addTransition(baseNFA.getStartState().getName(), 'e', f.getName());
	      }
      
	    }

//  	  System.out.println("\nFactor");
//  	System.out.println(baseNFA.toString());
		return baseNFA;
	}

	/**
	 * Most basic of the levels parses "()" and individual chars in the language
	 * 
	 * @return basic
	 */
	private NFA base() {
		switch (peek()) {
	      case '(':
	        eat('(') ;
	        NFA r = regex() ;  
	        eat(')') ;
//	    	  System.out.println("\nBase");
//	    	System.out.println(r.toString());
	      return r ;

	      default:
	    	  NFA n = new NFA();
	    	  String s = getState();
	    	  String f = getState();
	    	  n.addStartState(s);
	    	  n.addFinalState(f);
	    	  n.addTransition(s, next(), f);
//	    	  System.out.println("\nBase");
//	    	System.out.println(n.toString());
	      return n;
	    }

	}

	@Override
	public NFA getNFA() {
		// TODO
		// Break up the string into manageable sections using http://matt.might.net/articles/parsing-NFA-with-recursive-descent/
		return regex();
	}
	
	

}
