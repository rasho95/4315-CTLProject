package ctl;

/**
 * 
 * This class represents a universal quantification of a path formula.
 * 
 */

public class ForAll extends StateFormula {

	PathFormula inner;	// the inner PathFormula that this ForAll Stateformula refers to
	
	/**
	 * Initializes the {@code inner} formula to the <i>PathFormula</i>
	 * provided to this constructor.
	 * @param	formula		A path formula
	 */
	public ForAll(PathFormula formula) {
		this.inner = formula;
	}

	/**
	 * Checks if this Formula is equal another object. The checks adhere to the
	 * Equals contract in Java.
	 * @param	obj		The other object that this Formula is being compared to.
	 * @return	Returns <i>true</i> if the two objects are equal, and <i>false</i>
	 * 			if otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForAll other = (ForAll) obj;
		if (inner == null) {
			if (other.inner != null)
				return false;
		} else if (!inner.equals(other.inner))
			return false;
		return true;
	}

	/**
	 * Returns the {@code hashCode} of this <i>ForAll</i> formula.
	 * @return	An integer value representing this object's {@code hashCode}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inner == null) ? 0 : inner.hashCode());
		return result;
	}

	/**
	 * Returns a string representation of this <i>ForAll</i> formula.
	 * @return A pretty print version of this formula. The {@code inner} formula
	 * 		   is enclosed in brackets.
	 */
	@Override
	public String toString() {
		return "A(" + inner.toString() + ")";
	}
	
	/**
	 * Returns the {@code inner} PathFormula of this <i>ForAll</i> formula. This was
	 * added in consequence of the changes made to the Parser in Project 1.
	 * @return	The {@code inner} field of this formula.
	 */
	public PathFormula getInner() {
		return this.inner;
	}
	
	
	/**
	 * Returns a {@code StateFormula} using the Duality Law is which is applied to 
	 * transform the <i>ForAll</i> into its existential normal Form.
	 * @return	The {@code StateFormula} with translated CTL of <i>ForAll</i> in existential normal form.
	 */
	@Override
	public StateFormula existentialNormalForm() {
		if(inner instanceof Next){
			Next next = (Next) inner;
			return new Not(new Exists(new Next(new Not(next.inner.existentialNormalForm()))));
		}else if(inner instanceof Until){

			Until current = (Until) inner;
			StateFormula left = current.left;
			StateFormula right = current.right;

			StateFormula leftTranslated = left.existentialNormalForm();
			StateFormula rightTranslated = right.existentialNormalForm();
			
			StateFormula leftInnerAnd = new And(new Not(leftTranslated), new Not(rightTranslated));

			Until leftUntil = new Until(new Not(rightTranslated), leftInnerAnd);

			StateFormula leftPart = new Not(new Exists(leftUntil));

			StateFormula rightPart = new Not(new Exists(new Always(new Not(rightTranslated))));

			StateFormula result = new And(leftPart.existentialNormalForm(),rightPart.existentialNormalForm());

			return result;
		}else
		return new ForAll(inner.existentialNormalForm());
	}
	
	/**
	 * Returns a {@code StateFormula} for the positive normal form of <i>ForAll</i>
	 * @return {@code StateFormula} with translated CTL of <i>ForAll</i> in positive normal form.
	 */
	@Override
	public StateFormula positiveNormalForm() {
		return new ForAll(inner.positiveNormalForm());
	}
}
