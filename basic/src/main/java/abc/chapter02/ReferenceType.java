package abc.chapter02;

import org.junit.Test;

/**
 * 引用类型：
 *      强引用（默认）
 *          *只要强引用存在，垃圾回收器将永远不会回收被引用的对象，哪怕内存不足时，JVM也会直接抛出OutOfMemoryError，不会去回收
 *      软引用（SoftReference）
 *          *软引用是用来描述一些非必需但仍有用的对象。在内存足够的时候，软引用对象不会被回收，只有在内存不足时，系统则会回收软引用对象
 *      弱引用(WeakReference)
 *          *弱引用的引用强度比软引用要更弱一些，无论内存是否足够，只要 JVM 开始进行垃圾回收，那些被弱引用关联的对象都会被回收
 *      虚引用(PhantomReference)
 *          *虚引用是最弱的一种引用关系，如果一个对象仅持有虚引用，那么它就和没有任何引用一样，它随时可能会被回收
 */
public class ReferenceType {

    @Test
    public void testOOM(){

    }
}
