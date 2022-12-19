import java.lang.reflect.Type;
import java.util.*;


/*

A = {
 "a" : {
     "b":"str",
     "c": ["a", "b"],
     "d": 123,
     "e": true,
     "f" : { "qw": "123",
       "er": 123
       }
  }
}

 */
public class Interview {
    interface Json {
    }
    class JArray implements Json {
        List<Json> array = new ArrayList<>();
    }
    class JMap implements Json {
        Map<String, Json> map = new HashMap<>();
    }
    class JBoolean implements Json {
        Boolean bool;
    }
    class JNumber implements Json {
        long number;

        public JNumber(int i) {
            this.number = i;
        }
    }
    class JString implements Json {
        String str;
        public JString(String str) {
            this.str = str;
        }
    }
    //public String getRoot(Json )
    public String getType(Json json) {
        List<String> types = new ArrayList<>();
        if(json instanceof JMap) {
            for(Map.Entry entry : ((JMap) json).map.entrySet()) {
                String res =  entry.getKey().toString() + ":";
                res += getType((Json) entry.getValue());
                types.add(res);
            }
            return "struct<" + String.join(",", types) + ">";
        } else if(json instanceof JArray) {
            JArray jArray = (JArray) json;
            String res = getType(jArray.array.get(0));
            return "array<" + res + ">";
        } else if( json instanceof JString) {
            return "String";
        } else if(json instanceof JBoolean) {
            return "Boolean";
        } else if(json instanceof  JNumber) {
            return "Number";
        }
        return "null";
    }

    public void Sample() {
        JMap json = new JMap();
        JMap j1 = new JMap();
        j1.map.put("b", new JString("str"));
        JArray array = new JArray();
        array.array.add(new JString("str1"));
        array.array.add(new JString("123"));
        j1.map.put("c", array);
        j1.map.put("d", new JNumber(123));

        json.map.put("a", j1);

        /*
        {a : {
        b : "str",
        c : ["str", "123"],
        d : 123
        }}
        <
         */
        System.out.println("type is " + getType(json));
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparing(e -> e));
    }

}
