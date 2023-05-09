package bloomFilter;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class Main {
    public static void main(String[] args) {
//        try {
//            String bloomFilterName = "MyBloomFilter"; //固定名称
//            List<String> dataList = new ArrayList<>();
//            for (int i = 0; i < 10000; i++) {
//                dataList.add(String.valueOf(i));
//            }
//            BloomFilterRedis  filterRedis = new BloomFilterRedis(bloomFilterName, dataList);
//            boolean trueFlag = filterRedis.mightContain("MyBloomFilter","678");
//            boolean falseFlag = filterRedis.mightContain("MyBloomFilter","12345678");
//            System.out.println(trueFlag);
//            System.out.println(falseFlag);
////            String str = "123456";
////            System.out.println(str.hashCode());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Resource resource = new FileSystemResourceLoader().getResource("F:\\workSpace\\LearnJava\\src\\main\\resources\\spring-data.xml");
        Resource resource = new ClassPathResource("spring-data.xml");
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(resource);
        org.springframework.data.redis.connection.jedis.JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) xmlBeanFactory.getBean("jedisConnectionFactory");
        System.out.println(jedisConnectionFactory.getHostName());
    }
}
