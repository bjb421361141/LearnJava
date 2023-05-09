package collection;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 常用list类
 * ArrayList：数组实现
 * Vector(线程同步)：数组实现
 * linkedlist：链表实现 Node 内部类
 */
public class MyList {

    /**
     * List集合下的 队列、双向队列和栈的使用
     * LinkList没有对队列的大小进行控制
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("使用linklist 来实现队列");
        Queue<String> queue = new LinkedList<>();
        queue.offer("张三");//队列尾部添加元素
        queue.offer("李四");
        queue.offer("王五");
        while (queue.peek() != null) {//返回头部信息，不改变队列
            System.out.println(queue.poll());//返回头部元素，改变队列
        }

        System.out.println("双向队列：不论那端都可以进行插入");
        Deque<String> deque = new LinkedList<>();
        deque.add("白某"); // 与offer不同 超出容量返回异常？
        deque.offer("张三");//队列尾部添加元素
        deque.addFirst("李四");
        deque.addLast("王五");
        while (deque.peekLast() != null) {
            System.out.println(deque.pollLast());
        }

        System.out.println("使用Deque 来实现栈的功能（java Stack官方已经建议淘汰不用）");
        Deque<String> myStack = new ArrayDeque<>();
        myStack.offer("白某");
        myStack.offer("张三");
        myStack.offer("李四");
        myStack.offer("王五");
        while (myStack.peekLast() != null) {
            System.out.println(myStack.pollLast());
        }
    }
}
