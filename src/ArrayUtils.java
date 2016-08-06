public class ArrayUtils {

    public static int[] getCopy(int[] array) {
        int[] ints = new int[array.length];
        System.arraycopy(array, 0, ints, 0, array.length);
        return ints;
    }

    public static int getFirstNonZero(int[] ints){
        for (int index = 0; index < ints.length; index++){
            if(ints[index] != 0) return ints[index];
        }
        return 0;
    }

    public static int firstNonZeroIndex(int[] ints){
        for (int index = 0; index < ints.length; index++){
            if(ints[index] != 0) return index;
        }
        return -1;
    }

    public static int nonZero(int[] ints){
        int nonZero = 0;
        for (int index = 0; index < ints.length; index++){
            if(ints[index] != 0) nonZero++;
        }
        return nonZero;
    }

    public static int nonZero(int [] ints, int firstNonZeroIndex){
        int nonZero = 0;
        for (int index = firstNonZeroIndex; index < ints.length; index++){
            if(ints[index] != 0) nonZero++;
        }
        return nonZero;
    }

}
