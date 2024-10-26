package com.future.dubbo.consumer.filter;

import com.alibaba.fastjson2.JSON;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Activate(group = {"consumer"}, order = -1)
public class InvokeBusinessFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(InvokeBusinessFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.nanoTime();
        String serviceName = invoker.getInterface().getName();
        String methodName = invocation.getMethodName();
        Object[] args = invocation.getArguments();
        logger.info("#######=====> Invoke service:{}, method:{}, args:{}", serviceName, methodName, JSON.toJSONString(args));

        Result result;
        try {
            //调用下一个过滤器或服务
            result = invoker.invoke(invocation);
            long elapsedTime = System.nanoTime() - startTime;
            logger.info("#######=====> Service ==> {}, method ==> {}, result ==> {}, elapsedTime ==> {} ns.", serviceName, methodName, result.getValue(), elapsedTime);
            return result;
        } catch (RpcException e) {
            logger.error("Exception during RPC call: {}", e.getMessage(), e);
            throw e;
        }
    }
}
