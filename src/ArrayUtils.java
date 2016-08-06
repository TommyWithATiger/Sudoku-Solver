public class ArrayUtils {


    public static int getFirstNonZero(int[] ints) {
        for (int index = 0; index < 9; index++) {
            if (ints[index] != 0) return ints[index];
        }
        return 0;
    }

    public static int nonZero(int[] ints) {
        int nonZero = 0;
        for (int index = 0; index < 9; index++) {
            if (ints[index] != 0) ++nonZero;
        }
        return nonZero;
    }

    public static int nonZero2(int[] ints) {
        int nonZero = 0;
        for (int index = 0; index < 9; index++) {
            if (ints[index] != 0) nonZero++;
        }
        return nonZero;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1, 2, 0, 0, 0, 0, 7, 8, 0};

        for (int i = 0; i < 100000000; i++){
            int[] k = new int[100];
        }

        long time = System.nanoTime();
        for (int i = 0; i < 100000000; i++){
            int k = nonZero(a);
        }
        System.out.println(System.nanoTime() - time);

        time = System.nanoTime();
        for (int i = 0; i < 100000000; i++){
            int k = nonZero2(a);
        }
        System.out.println(System.nanoTime() - time);
    }

}
