package basic.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * java 中的注解是用来打标签的 不做任何操作
 * 具体注解的功能实现都由其他代码完成
 */
@MyTypeAnnotation(name = "自创的类型注解")
public class Worker {

    @MyFiledAnnotation(value = "自创的字段注解-name")
    String name;

    @MyFiledAnnotation(value = "自创的字段注解-age")
    String age;

    public Worker() {
    }

    @MyMethodAnnotation
    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        Class cls = null;
        try {
            cls = Class.forName("basic.annotation.Worker");
            Method[] method = cls.getMethods();
            /**判断Worker类上是否有TypeAnnotation注解*/
            boolean flag = cls.isAnnotationPresent(MyTypeAnnotation.class);
            /**获取Worker类上是TypeAnnotation注解值*/
            if (flag) {
                MyTypeAnnotation typeAnno = (MyTypeAnnotation) cls.getAnnotation(MyTypeAnnotation.class);
                System.out.println("@TypeAnnotation值:" + typeAnno.name());
            }

            /**方法上注解*/
            List<Method> list = new ArrayList<Method>();
            for (int i = 0; i < method.length; i++) {
                list.add(method[i]);
            }

            for (Method m : list) {
                MyMethodAnnotation methodAnno = m.getAnnotation(MyMethodAnnotation.class);
                if (methodAnno == null)
                    continue;
                System.out.println( "方法名称:" + m.getName());
                System.out.println("方法上注解name = " + methodAnno.name());
                System.out.println("方法上注解url = " + methodAnno.url());
            }
            /**属性上注解*/
            List<Field> fieldList = new ArrayList<Field>();
            for (Field f : cls.getDeclaredFields()) {// 访问所有字段
                MyFiledAnnotation filedAno = f.getAnnotation(MyFiledAnnotation.class);
                System.out.println( "属性名称:" + f.getName());
                System.out.println("属性注解值FiledAnnotation = " + filedAno.value());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }




    }
}
