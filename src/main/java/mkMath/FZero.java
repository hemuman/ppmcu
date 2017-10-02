package mkMath;

/**
 * Implements the fzero function from matlab.
 * It uses a mix of bisections and interpolations.
 * The user must give the function {@link NumFunction}
 * in parameter. To find the root of the function,
 * the user can enter an interval of 2 points where the
 * function values at those endpoints $f(x1) \times f(x2) \le 0$
 * or enter only one point and the algorithm will try to find
 * the second point to get an interval.
 */
public class FZero {

   // Tolerance, minimum difference between a point and it's previous point.
   private double tol = 1e-2;   // tolX value from fzero
   private int maxIteration = 500;

   // Find the root of this function
   private NumFunction f = null;

/*   // example
   public static void main (String[] args) {

      if (args.length < 2) {
         System.out.println ("Enter x0 , x1. Where f(x0) * f(x1) <= 0");
         System.exit(-1);
      }

      FZero fz = new FZero(new NumFunctionLD() );

      try {
         double arg1 = Double.parseDouble (args[0]);
         double arg2 = Double.parseDouble (args[1]);

         double root = fz.findRoot(arg1, arg2);
         System.out.println ("\nRoot = " + root + " , function = " + 
            fz.function(root));
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }
*/

   /**
    * Constructor for the root solver with default error tolerance
    * which are 0.01 absolute error tolerance
    * over the root point and 500 iterations.
    * The function must be set with {@link #setFunction}.
    */
    public FZero() {
       this.f = null;
    }

   /**
    * Constructor for the root solver with default error tolerance
    * which are 0.01 absolute error tolerance
    * over the root point and 500 iterations.
    @param f the function to find the root. It must be a single
    * variable function.
    */
   public FZero(NumFunction f) {
      this.f = f;
   }

   /**
    * Constructor for the root solver with inputed tolerance and
    * spotting conditions.
    @param f the function to find the root. It must be a single 
    * variable function.
    @param tol the absolute error tolerance for the difference 
    * between the roots after each iteration (new value vs old value).
    @param maxIteration the root finder will stop after reaching
    * maxIteration count and return the current root found.
    */
   public FZero (NumFunction f, double tol, int maxIteration) {
      this.f = f;
      this.tol = tol;
      this.maxIteration = maxIteration;
   }
   
  /**
   * Set the function where to find the root.
   @param f the function to find the root.
   */
   public void setFunction(NumFunction f) {
      this.f = f;
   }

   /**
    * Evaluates the function given to the root solver.
    @param x the value to give to the function.
    @return the value of the function.
    */
   public double function(double x) {
      return f.eval(x);
   }

   /** Finds a root around point x0.
   @param x0 the starting point of the root search.
   */
   public double findRoot(double x0) {
      double fx = function(x0);
      double dx;

      if (fx == 0)
         return x0;

      if (x0 != 0)
         dx = x0 / 50;
      else
         dx = 1.0 / 50.0;

      // find change of sign
      double twosqrt = Math.sqrt(2);
      double a = x0,
             fa = fx,
             b = x0,
             fb = fx;

      int intervaliter = 0;
      while ((fa * fb) > 0.0) {
         intervaliter++;
         dx = twosqrt * dx;
         a = x0 - dx;
         fa =  function(a);

         b = x0 + dx;
         fb = function(b);

         if ((Math.abs(fa) == Double.POSITIVE_INFINITY) ||
             (Math.abs(fb) == Double.POSITIVE_INFINITY))
            return Double.NaN;
      }

      return findRoot(a, b);
   }
   
   /**
    * Finds a root between x0 and x1. The values of the function
    * at point x0 and x1 must be of opposite signs.
    * \texttt{f(x0) * f(x1) <= 0}.
    @param x0 first value of the interval to search.
    @param x1 second value of the interval to search.
    @return a root of the function between x0 and x1 or
    * \texttt{Double.NaN} if none exists.
    */
   public double findRoot(double x0, double x1) {
  
      double a = x0,
             b = x1,
             fa = function(a),
             fb = function(b);
 
      int count = 0;

      if (fa == 0.0) {
         // System.out.println("Zero find terminated.");
         return a;
      }
      else if (fb == 0.0) {
         // System.out.println ("Zero fin terminated.");
         return b;
      }
      else if ( (fa * fb) > 0 ) {
         // System.err.println ("The function values at the interval " +
         //   "endpoints must differ in sign.");
         return Double.NaN;
      }

      double fc = fb,
             c = 0,
	     d = 0,
             e = 0,
             m = 0,
             toler;

      double p, q, r, s;

      String proc = "none";

      // Main loop
      while (fb != 0.0) {
         // Insure that b is the best result so far. a is the
         // previous value of b, and c is on the opposite of the
         // zero from b.
         if ( (fb * fc) > 0) {
            c = a;
            fc = fa;
            d = b - a;
            e = d;
         }
         if (Math.abs(fc) < Math.abs(fb)) {
            a = b;
            b = c;
            c = a;
            fa = fb;
            fb = fc;
            fc = fa;
         }

         // convergence test and possible exit
         m = 0.5 * (c - b);
         toler = 2.0 * tol * Math.max(Math.abs(b), 1.0);
         if ( (Math.abs(m) <= toler) || (fb == 0.0) ) {
            // System.out.println ("Reached tolerance or fb == 0");
            return b;
         }
         else if ( count > maxIteration) {
            // System.out.println ("Reached max iteration ! count = " + count);
            return b;
         }

         // System.out.println ("Count = " + count + " , b = " + b +
         //   " , fb = " + fb + " , method = " + proc);

         // Choose bisection or interpolation
         if ( (Math.abs(e) < toler) || (Math.abs(fa) <= Math.abs(fb) ) ) {
            // Bisection
            d = m;
            e = m;
            proc = "bisection";
         }
         else {
            // Interpolation
            s = fb / fa;
            if (a == c) {   // Linear interpolation
               p = 2.0 * m * s;
               q = 1.0 - s;
            }
            else {
               q = fa / fc;
               r = fb / fc;
               p = s * (2.0 * m * q * (q - r) - (b - a)*(r - 1.0));
               q = (q - 1.0) * (r - 1.0) * (s - 1.0);
            }
            if (p > 0)
               q = -q;
            else
               p = -p;

            // Is interpolated point acceptable
            if ( ((2.0 * p) < (3.0 * m * q - Math.abs(toler * q))) &&
                 ( p < Math.abs(0.5 * e * q)) ) {
               e = d;
               d = p / q;
               proc = "interpolation";
            }
            else {
               d = m;
               e = m;
               proc = "bisection";
            }
         }

         // Next point
         a = b;
         fa = fb;
         if (Math.abs(d) > toler)
            b = b + d;
         else if (b > c)
            b = b - toler;
         else
            b = b + toler;

         fb = function(b);
         count++;

      }
      return b;   // if exit while() loop
   }
}
