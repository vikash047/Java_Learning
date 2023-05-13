package Even;

import java.util.ArrayList;
import java.util.List;

public class Main {

    /*
     11010001 00000110
     128 + 64 + 16 + 1

     -1
     -1 1 -> array
      2 -> 3 4
     */
    static class Data {
        long value;
        int index;

        public Data(long value, int index) {
            this.value = value;
            this.index = index;
        }
    }
    public static  Data extract(Byte[] arr, int start) {
        int limit = 128;
        List<Integer> list = new ArrayList<>();
        int shift = 0;
        int index = -1;
        for(int i = start; i < arr.length; i++) {
           // System.out.println(Byte.toUnsignedInt(e));
            var value = Byte.toUnsignedInt(arr[i]);
            if(value < limit) {
                list.add(value);
                index = i + 1;
                break;
            } else {
                list.add(value - limit);
            }
            shift++;
        }
        long ans = 0;
        for(var e : list) {
            //System.out.println(e);
            long leftShift = shift*7;
            ans += ((long)e << leftShift);
           // System.out.println(ans);
            shift--;
        }

        if(ans%2 == 0) {
            return new Data(ans/2, index);
        }
        ans = (ans + 1)/2;
        return new Data(-ans, index);
    }

    class Record {
        int type;
        int dtype;

        int size;
        List<Integer> data;

    }
    public static void extractData(Byte[] arr, int start) {
            Data d = null;
            d = extract(arr, 0);
            System.out.println(" type " + d.value);
            d = extract(arr, d.index);
            System.out.println(" dtype " + d.value);
            d = extract(arr, d.index);
            long size = d.value;
            System.out.println(" size " + d.value);
             while (size != 0) {
                 d = extract(arr, d.index);
                 System.out.println(" data " + d.value);
                 size--;
             }
    }

    public static void main(String[] args) {
        Byte[] data = {1,  2,  6,  8,  6, (byte) 145, (byte) 239, (byte) 237, (byte) 185,  2 };
        extractData(data, 0);
        /*int index = 0;
        var d = extract(data, 0);
        System.out.println(d.value);
        d = extract(data, d.index);
        System.out.println(d.value);*/
    }
}
