package basic.collection;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap：使用 object中的hashcode 和equals 方法来保证key 的一致性。结构 Node<K,V>[] + 链表 + 红黑树（只有left 和 right）
 * ConcurrentHashMap（线程安全）：丢弃Segment(继承 ReentrantLock) 使用 synchronized 和 sun.misc.Unsafe(内存相关操作,**不建议使用) 来进行数据操作
 * Hashtable(线程安全)： 直接使用 synchronized 对方法进行上锁保证线程安全  继承于Dictionary 抽象类 并实现了Map接口
 * TreeMap：可进行比较排序Key值的 Map 实现类
 * LinkHashMap（记录插入顺序）：继承于HashMap
 * <p>
 * 使用的版本 java version "1.8.0_112"
 */
public class MyMap {
    public static void main(String[] args) {
        HashMap<String, Object> hashMap = new HashMap<>();
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        Hashtable<String, Object> hashTable = new Hashtable<>();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
    }
}
