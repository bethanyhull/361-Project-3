package re;

import fa.nfa.NFA;

public class RE implements REInterface {
	private String regEx;

	public RE(String regEx) {
		this.regEx = regEx;
	}

	public NFA parse () {
		//TODO: flesh out this method
		return null;

	}

	/* Recursive descent parsing internals. */

	/**
	 * 
	 * 
	 * @return 
	 */
	private char peek() {
		return regEx.charAt(0);
	}

	private void eat(char c) {
		if (peek() == c)
			this.regEx = this.regEx.substring(1) ;
		else
			throw new 
			RuntimeException("Expected: " + c + "; got: " + peek()) ;
	}  

	private char next() {
		char c = peek() ;
		eat(c) ;
		return c ;
	}

	private boolean more() {
		return regEx.length() > 0 ;
	}


	/* Regular expression term types. */

	private NFA regex() {
		return null;
		//TODO: flesh out this method
	}

	private NFA term() {
		return null;
		//TODO: flesh out this method
	}

	private NFA factor() {
		NFA baseNFA = base();
		//TODO: Snippet from assignmnt
		// More ccode goes here

		return baseNFA;
	}

	private NFA base() {
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
