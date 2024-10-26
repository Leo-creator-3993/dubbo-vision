package com.future.dubbo.consumer;

import com.future.dubbo.provider.model.User;
import com.future.dubbo.provider.service.IUserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerSpringApplication  implements CommandLineRunner {

    @DubboReference
    private IUserService iUserService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerSpringApplication.class, args);

        //dubbo泛化调用
        new ConsumerSpringApplication().dubboGenericInvoke();
    }

    //dubbo泛化调用
    private void dubboGenericInvoke() {
        //获取泛化配置
        ReferenceConfig<GenericService> reference = getGenericServiceReferenceConfig();

        //获取泛化服务
        GenericService genericService = reference.get();

        //无参数泛化调用
        Object result = genericService.$invoke("getUser", null, null);

        //无参泛化调用结果输出
        System.out.println("######==========######泛化调用结果: " + result);

        //有参泛化调用
        Object result1 = genericService.$invoke("getUserBy", new String[]{"java.lang.String","int"}, new Object[]{"MM", 33});
        System.out.println("######==========######泛化调用结果: " + result1);
    }

    //获取泛化服务配置
    private  ReferenceConfig<GenericService> getGenericServiceReferenceConfig() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-consumer");
        application.setQosEnable(false);

        //配置注册中心
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");

        //创建泛化服务引用
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface("com.future.dubbo.provider.service.IUserService");
        reference.setGeneric("true");
        return reference;
    }

    @Override
    public void run(String... args) throws Exception {
        RpcContext.getContext().setAttachment("dubbo.application", "#####Void#####");
        User user0 = iUserService.getUser();
        System.out.println("######==========######常规调用结果: " + user0);

        RpcContext.getContext().setAttachment("dubbo.application", "#####ArgsArgs#####");
        User user1 = iUserService.getUserBy("MOS", 98);
        System.out.println("######==========######常规调用结果: " + user1);
    }
}
