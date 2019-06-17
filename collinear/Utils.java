/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Utils {

    public void requiresNonNullAndUniqueValues(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            requiresNonNull(points[i], "Null value in given points.");

            for (int j = i + 1; j < points.length; j++) {
                requiresNonNull(points[j], "Null value in given points.");

                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }

    public void requiresNonNull(Object object, String msg) {
        if (object == null) {
            throw new IllegalArgumentException(msg);
        }
    }
}