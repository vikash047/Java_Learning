package Adobe;

public class Adobe3 {
    /*
      Library rand() -> random boolean generator true/false
      another myfunc(int min, int max) -> generator number between min/max
      int diff = max - min + 1;
       9
       1 1 1 1 -> [1 - 8]
       true set a bit
       false 0 bit
       min + value -> return
       0 ->
       T/F  --> [min, max]
       8
       sum -> [1/0]
       diff = max - min + 1 => 8
       cake

       1 -> 2
       2 -> 4


     */
    public static boolean rand() {
        return true;
    }


    public int myFunc(int min, int max) {
        if(min > max) {
            throw new RuntimeException();
        }
        int diff = max - min + 1;
        int temp = diff;
        int cnt = 0;
        while (temp > 0) {
            cnt++;
            temp = temp/2;
        }
        int index = 0;
        int ret = 0;
        while (true) {
            boolean value = rand();
            if(value && index <= cnt) {
                ret |= (1 << index);
            }
            index++;
            if(index >= cnt && ret >= 1 && ret <= diff) {
                return min + ret;
            } else if(index >= cnt && (ret > diff || ret == 0)) {
                index = 0;
                ret = 0;
            }
        }
    }
}
