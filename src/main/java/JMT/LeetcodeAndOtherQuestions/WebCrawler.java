package JMT.LeetcodeAndOtherQuestions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WebCrawler {
    public static interface HtmlParser {
        List<String> getUrls(String url);
    }
    public static class KV implements Comparable<KV>{
        public int k;
        public int v;
        public int compareTo(KV other) {
            return this.k - other.k;
        }
    }
    public static class ResourceExecutor {

    }
    private String getBase(String url) {
        return url.split("/")[2];
    }
    private String baseUrl;
    ReentrantLock lock;
    Condition c1 = lock.newCondition();
    TreeSet<Integer> set = new TreeSet<>();
    Set<Integer> ss = new HashSet<>();

    public List<String> crawl(String start, HtmlParser htmlParser)  {
        ss.toArray(new Integer[0]);
        return null;
    }
}
