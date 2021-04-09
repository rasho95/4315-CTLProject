package ctlform;

import ctl.Formula;

/**
 * 
 * This class serves as the main class for translating a formula into
 * its existential normal form
 *
 */


public class ExistentialNormalForm {
	
	/**
	 * Translate this formula into its existential normal form
	 * @param	form	The {@code Formula} to be translated into existential normal form.
	 * @return	Returns a {@code Formula} in its existential normal form.
	 */
	public static Formula translate(Formula form) {
		return form.existentialNormalForm();
	}

}
