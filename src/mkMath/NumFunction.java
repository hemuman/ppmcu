package mkMath;

/**
 * Interface representing a function. The usage of a function is
 * to take some variable arguments and return a value.
 */
public interface NumFunction {

   /**
    * Evaluates the function.
    @param x The array of variables of the function.
    @return the value of the function for the variables \texttt{x[]}.
    */
   double eval (double x);

}