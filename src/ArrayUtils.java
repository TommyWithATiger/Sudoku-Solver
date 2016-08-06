public class ArrayUtils {


    public static int getFirstNonZero(int[] ints) {
        for (int index = 0; index < ints.length; index++) {
            if (ints[index] != 0) return ints[index];
        }
        return 0;
    }

    public static int nonZero(int[] ints) {
        int nonZero = 0;
        for (int index = 0; index < ints.length; index++) {
            if (ints[index] != 0) nonZero++;
        }
        return nonZero;
    }


}
