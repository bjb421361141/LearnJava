package collection;



import collection.obj.Person;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * Set 元素存在不插入
 * 常用 Set 类
 * HashSet(哈希表 聚合一个HashMap对象 使用 LinkedHashMap 来保存元素)
 * TreeSet(二叉树  聚合一个 实现 NavigableMap 的对象 默认使用 TreeMap)
 * LinkHashSet(继承于HashSet 底层使用 LinkedHashMap 来保存所有元素)
 *
 *  使用的版本 java version "1.8.0_112"
 */
public class MySet {
    static HashSet<Person> hashSet = new HashSet<>();
    static TreeSet<Person> treeSet = new TreeSet<>();
    static LinkedHashSet<Person> linkedHashSet = new LinkedHashSet<>();


    public static void main(String[] args) {
        Person person = new Person("12345");
        Person person1 = new Person("67890");
        System.out.println(hashSet.add(person)+".hashcode："+person.hashCode());
        System.out.println(hashSet.add(person1)+".hashcode："+person1.hashCode());
        for (Person p :hashSet) {
            System.out.println(p);
        }
    }

}

