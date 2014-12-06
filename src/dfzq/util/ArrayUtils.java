package dfzq.util;

/**
 * Created with IntelliJ IDEA.
 * User: flai
 * Date: 14-10-29
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
public class ArrayUtils {

    public static String[] removeTheFirst(String[] input) {
        String[] result = new String[input.length - 1];
        for (int i = 1; i < input.length; i++) {
            result[i - 1] = input[i];
        }

        return result;
    }


    public static String[] combineArray(String s, String[] arrays) {
        String[] result = new String[arrays.length + 1];
        result[0] = s;

        for (int i = 0; i < arrays.length; i++) {
            result[i + 1] = arrays[i];

        }

        return result;


    }
}
