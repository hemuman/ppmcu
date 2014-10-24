/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkMath;

/**
 *
 * @author PDI
 */
public class GrahamScan {
    /***
     * import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
 
public class GrahamScan {
 
    public static ArrayList&lt;Point&gt; grahamScan(ArrayList&lt;Point&gt; polygon) {
        int min = 0;
        int length = polygon.size();
        for (int i = 1; i &lt; length; i++) {
            Point get = polygon.get(i);
            if (get.y &lt; polygon.get(min).y) {
                min = i;
            } else {
                if (get.y == polygon.get(min).y) {
                    if (get.x &lt; polygon.get(min).x) {
                        min = i;
                    }
                }
            }
        }
        final Point pivot = polygon.get(min);
        ArrayList&lt;Point&gt; sorted = (ArrayList&lt;Point&gt;) polygon.clone();
        Collections.sort(sorted, new Comparator&lt;Point&gt;() {
 
            public int compare(Point o1, Point o2) {
                if (o1.equals(o2)) {
                    return 0;
                }
                if (angle_cmp(pivot, o1, o2)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        sorted.add(0, pivot);
        Stack&lt;Point&gt; stack = new Stack&lt;Point&gt;();
        stack.push(sorted.get(length - 1));
        stack.push(pivot);
        int i = 1;
        while (i &lt; length) {
            Point pt1 = stack.pop();
            Point pt2 = stack.peek();
            stack.push(pt1);
            if (isLeftTurn(pt1, pt2, sorted.get(i))) {
                stack.push(sorted.get(i));
                i++;
            } else {
                stack.pop();
            }
        }
        ArrayList&lt;Point&gt; convex = new ArrayList&lt;Point&gt;();
        while (!stack.isEmpty()) {
            convex.add(stack.pop());
        }
        convex.remove(convex.size() - 1);
        return convex;
    }
 
    private static int distance(Point a, Point b) {
        int dx = a.x - b.x, dy = a.y - b.y;
        return dx * dx + dy * dy;
    }
 
    private static int area(Point a, Point b, Point c) {
        return a.x * b.y - a.y * b.x + b.x * c.y - b.y * c.x + c.x * a.y - c.y * a.y;
    }
 
    private static boolean angle_cmp(Point pivot, Point a, Point b) {
        if (area(pivot, a, b) == 0) {
            return distance(pivot, a) &lt; distance(pivot, b);
        }
        int d1x = a.x - pivot.x, d1y = a.y - pivot.y;
        int d2x = b.x - pivot.x, d2y = b.y - pivot.y;
        return (Math.atan2((double) d1y, (double) d1x) - Math.atan2((double) d2y, (double) d2x)) &lt; 0;
    }
 
    private static int turnTest(Point p, Point q, Point r) {
        int result = (r.x - q.x) * (p.y - q.y) - (r.y - q.y) * (p.x - q.x);
        if (result &lt; 0) {
            return -1;
        }
        if (result &gt; 0) {
            return 1;
        }
        return 0;
    }
 
    private static boolean isLeftTurn(Point p, Point q, Point r) {
        return turnTest(p, q, r) &gt; 0;
    }
}
     */
}
