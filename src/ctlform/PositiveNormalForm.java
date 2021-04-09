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

	/**
	 * Translate a formula into its positive normal form until their are 
	 * no more translations possible
	 * @param	form	The {@code Formula} to be translated into positive normal form.
	 * @return	Returns a {@code Formula} in its positive normal form.
	 */
	public static Formula translateUntilComplete(Formula form) {

		Formula result = form.positiveNormalForm();
		for(int i = 0; i < 50; i++){
			Formula tempResult = result.positiveNormalForm();
			if(tempResult.equals(result)) break;
			result = tempResult;
		}
		return result;

	}
}
