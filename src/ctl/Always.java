package ctl;

/**
 * 
 * This class represents a path formula that is an always of a state formula. 
 * 
 */

public class Always extends PathFormula {

	StateFormula inner;	// the inner StateFormula that this Always Pathformula refers to
	
	/**
	 * Initializes the {@code inner} formula to the <i>StateFormula</i>
	 * provided to this constructor.
	 * @param	formula		A state sub-formula
	 */
	public Always(StateFormula formula) {
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
		Always other = (Always) obj;
		if (inner == null) {
			if (other.inner != null)
				return false;
		} else if (!inner.equals(other.inner))
			return false;
		return true;
	}

	/**
	 * Returns the {@code hashCode} of this <i>Always</i> formula.
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
	 * Returns a string representation of this <i>Always</i> formula.
	 * @return A pretty print version of this formula. The {@code inner} formula
	 * 		   is enclosed in brackets.
	 */
	@Override
	public String toString() {
		return "G(" + inner.toString() + ")";
	}
	
	/**
	 * Returns a {@code PathFormula} for the existential normal form of <i>Always</i>.
	 * @return PathFormula with translated CTL of <i>Always</i> in existential normal form.
	 */
	@Override
	public PathFormula existentialNormalForm() {
		return new Always(inner.existentialNormalForm());
	}
	
	/**
	 * Returns a {@code PathFormula} for the positive normal form of <i>Always</i>.
	 * @return PathFormula with translated CTL of <i>Always</i> in positive normal form.
	 */
	@Override
	public PathFormula positiveNormalForm() {
		return new Always(inner.positiveNormalForm());
	}
}
