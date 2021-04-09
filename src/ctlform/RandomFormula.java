package ctlform;

import ctl.*;

import java.util.Random;

/**
 * 
 * This class serves as the main class for generating random formulas
 *
 */

public class RandomFormula {

	
	/**
	 * Generate a random untranslatable {@code Formula} of user inputed depth.
	 * @param	depth	The depth of the random {@code Formula}.
	 * @return	Returns a randomly generated {@code Formula}.
	 */
    public static Formula UntranslatableRandomFormula(int depth) {
        Random r = new Random();

        if (depth == 0)
        	if(r.nextBoolean())
            return generateUntranslatableStateFormula(0);
        	else return generateUntranslatablePathFormula(0);
        else if (r.nextBoolean())
            return generateUntranslatablePathFormula(depth - 1);
        else
            return generateUntranslatableStateFormula(depth - 1);


    }

    /**
	 * Generate a random untranslatable {@code StateFormula} of user inputed depth.
	 * @param	depth	The depth of the random {@code StateFormula}.
	 * @return	Returns a randomly generated {@code StateFormula}.
	 */
    public static StateFormula generateUntranslatableStateFormula(int depth) {
        Random r = new Random();
        if (depth <= 0) {
            if (r.nextBoolean() && r.nextBoolean())
                return new False();
            else return new True();

        } else {
            int curr = r.nextInt(3) + 1;
            switch (curr) {
                case 1:
                    return new And(generateUntranslatableStateFormula(depth - 1), generateUntranslatableStateFormula(depth - 1));
                case 2:
                    return new Or(generateUntranslatableStateFormula(depth - 1), generateUntranslatableStateFormula(depth - 1));
                case 3:
                    return new Implies(generateUntranslatableStateFormula(depth - 1), generateUntranslatableStateFormula(depth - 1));
                default:
                    return new Iff(generateUntranslatableStateFormula(depth - 1), generateUntranslatableStateFormula(depth - 1));
            }
        }

    }
    
    /**
   	 * Generate a random untranslatable {@code PathFormula} of user inputed depth.
   	 * @param	depth	The depth of the random {@code PathFormula}.
   	 * @return	Returns a randomly generated {@code PathFormula}.
   	 */
    public static PathFormula generateUntranslatablePathFormula(int depth) {
        Random r = new Random();
        int curr = r.nextInt(5) + 1;
        if (depth <= 0) {

            switch (curr) {
                case 1:
                    return new Always(generateUntranslatableStateFormula(0));
                case 2:
                    return new Eventually(generateUntranslatableStateFormula(0));
                case 3:
                    return new Next(generateUntranslatableStateFormula(0));
                case 4:
                    return new WeakUntil(generateUntranslatableStateFormula(0), generateUntranslatableStateFormula(0));
                default:
                    return new Until(generateUntranslatableStateFormula(0), generateUntranslatableStateFormula(0));
            }

        } else {

            switch (curr) {
                case 1:
                    return new Always(generateUntranslatableStateFormula(depth - 1));
                case 2:
                    return new Eventually(generateUntranslatableStateFormula(depth - 1));
                case 3:
                    return new Next(generateUntranslatableStateFormula(depth - 1));
                case 4:
                    return new WeakUntil(generateUntranslatableStateFormula(depth - 1), generateUntranslatableStateFormula(depth - 1));
                default:
                    return new Until(generateUntranslatableStateFormula(depth - 1), generateUntranslatableStateFormula(depth - 1));
            }
        }
    }


   
    
    /**
   	 * Generate a random PNF translatable PNF translatable {@code Formula} of user inputed depth.
   	 * @param	depth	The depth of the random PNF translatable {@code Formula}.
   	 * @return	Returns a randomly generated PNF translatable {@code Formula}.
   	 */
    public static Formula translatablePNFRandomFormula(int depth) {
        Random r = new Random();

        if (depth == 0)
            if (r.nextBoolean())
                return generateTranslatablePositiveStateFormula(0);
            else return UntranslatableRandomFormula(0);


        else
            return generateTranslatablePositiveStateFormula(depth - 1);



    }


    /**
   	 * Helper function to produce a random PNF translatable {@code StateFormula} of inputed depth.
   	 * @param	depth	The depth of the random PNF translatable {@code StateFormula}.
   	 * @return	Returns a randomly generated PNF translatable {@code StateFormula}.
   	 */
    public static StateFormula generateTranslatablePositiveStateFormula(int depth) {
        Random r = new Random();
        if (depth <= 0) {
            if (r.nextBoolean() && r.nextBoolean())
                return new Not(new False());
            else return new Not(new True());

        } else {

            int curr = r.nextInt(5) + 1;
            switch (curr) {
                case 1:
                    return new Not(new And(generateTranslatablePositiveStateFormula(depth - 1), generateTranslatablePositiveStateFormula(depth - 1)));
                case 2:
                    return new Not(new ForAll(new Next(generateTranslatablePositiveStateFormula(depth - 1))));
                case 3:
                    return new Not(new ForAll(new Until(generateTranslatablePositiveStateFormula(depth - 1), generateTranslatablePositiveStateFormula(depth - 1))));
				case 4:
					return new Not(new Exists(new Until(generateTranslatablePositiveStateFormula(depth - 1),generateTranslatablePositiveStateFormula(depth - 1))));
				default:
					return new Not(new Exists(new Next(generateTranslatablePositiveStateFormula(depth - 1))));
            }
        }

    }

    /**
   	 * Function to produce a random ENF translatable {@code Formula} of inputed depth.
   	 * @param	depth	The depth of the random ENF translatable {@code Formula}.
   	 * @return	Returns a randomly generated ENF translatable {@code Formula}.
   	 */
	public static Formula translatableENFRandomFormula(int depth) {
		Random r = new Random();

		if (depth == 0)
			if (r.nextBoolean())
				return generateTranslatableExistentialStateFormula(0);
			else return UntranslatableRandomFormula(0);


		else
			return generateTranslatableExistentialStateFormula(depth - 1);



	}


	/**
   	 * Helper function to produce a random ENF translatable {@code StateFormula} of inputed depth.
   	 * @param	depth	The depth of the random ENF translatable {@code StateFormula}.
   	 * @return	Returns a randomly generated ENF translatable {@code StateFormula}.
   	 */
	public static StateFormula generateTranslatableExistentialStateFormula(int depth) {
		Random r = new Random();
		if (depth <= 0) {
			if (r.nextBoolean() && r.nextBoolean())
				return new AtomicProposition("ap");
			else return new True();
		} else {
			int curr = r.nextInt(4) + 1;
			switch (curr) {
				case 1:
					return new ForAll(new Next(generateTranslatableExistentialStateFormula(depth - 1)));
				case 2:
					return new ForAll(new Until(generateTranslatableExistentialStateFormula(depth - 1), generateTranslatableExistentialStateFormula(depth - 1)));
				case 3:
					return new ForAll(new Until(generateUntranslatableStateFormula(depth - 1), generateTranslatableExistentialStateFormula(depth - 1)));
				default:
					return new ForAll(new Next(generateUntranslatableStateFormula(depth - 1)));
			}
		}

	}
}
