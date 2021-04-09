package ctlform;

import ctl.Formula;

/**
 * 
 * This class serves as the main class for translating a formula into
 * its positive normal form
 *
 */

public class PositiveNormalForm {
	
	/**
	 * Translate this formula into its positive normal form
	 * @param	form	The {@code Formula} to be translated into positive normal form.
	 * @return	Returns a {@code Formula} in its positive normal form.
	 */
	public static Formula translate(Formula form) {
		return form.positiveNormalForm();
	}

}
