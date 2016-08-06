public class ArrayUtils {

    public static int[] clean(int[] ints, int amount) {
        int[] clean = new int[amount];
        for (int integer : ints) {
            if (integer != 0) {
                clean[--amount] = integer;
            }
        }
        return clean;
    }

    public static int[] removeIfExists(int[] ints, int value) {
        for (int index = 0; index < ints.length; index++) {
            if (ints[index] == value) {
                return removeAtIndex(ints, index);
            }
        }
        return ints;
    }

    public static int[] removeAtIndex(int[] ints, int index) {
        int[] newInts = new int[ints.length - 1];
        System.arraycopy(ints, 0, newInts, 0, index);
        System.arraycopy(ints, index + 1, newInts, index, newInts.length - index);
        return newInts;
    }

    public static int[] clean(int[] ints){
        int amount = 9;
        for (int integer : ints){
            if (integer == 0) amount--;
        }
        return clean(ints, amount);
    }

    public static int[] getCopy(int[] array){
        int[] ints = new int[array.length];
        System.arraycopy(array, 0, ints, 0, array.length);
        return ints;
    }

}
