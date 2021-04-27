package re;

import fa.nfa.NFA;

/**
 * @author bethanyhull
 *
 */

public class RE implements REInterface {
	private String regEx;
	private Int state = 0;

	public RE(String regEx) {
		this.regEx = regEx;
	}

	public NFA parse () {
		NFA nfa = new NFA();
	
		//TODO: flesh out this method
		return null;
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


	private Int getState() {
		Int i = state;
		state++;
		return i;
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
	      
	      // TODO: need a method to handle this
	      //Creeate a new NFA that combines the two NFA's in an either/or configuration
	      //return new Choice(term,regex) 
	      return null;
	    } else {
	     return term ;
	    }
		
		//TODO: flesh out this method
	}

	private NFA term() {
		NFA factor = RegEx.blank ;

	    while (more() && peek() != ')' && peek() != '|') {
	      NFA nextFactor = factor();
	      return null;
	      //TODO: Concatination of factor and next factor
	    }

	    return factor ;
	}

	private NFA factor() {
		NFA baseNFA = base();
		//TODO: Snippet from assignmnt
		// More code goes here

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
	        RegEx r = regex() ;  
	        eat(')') ;
	      return r ;

	      case '\\':
	       eat ('\\') ;
	       char esc = next() ;
	      return new Primitive(esc) ;

	      default:
	      return new Primitive(next()) ;
	    }
		return null;
		//TODO: flesh out this method
	}

	@Override
	public NFA getNFA() {
		// TODO
		// Break up the string into manageable sections using http://matt.might.net/articles/parsing-NFA-with-recursive-descent/
		return null;
	}
	
	

}
