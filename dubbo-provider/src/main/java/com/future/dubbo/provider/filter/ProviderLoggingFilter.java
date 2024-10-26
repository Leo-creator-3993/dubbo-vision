package com.future.dubbo.provider.filter;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Activate(group = {"provider"}, order = -1)
@Component
public class ProviderLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ProviderLoggingFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        long startTime = System.nanoTime();
        String serviceName = invoker.getInterface().getName();
        String methodName = invocation.getMethodName();
        Object[] args = invocation.getArguments();

        RpcContext rpcContext = RpcContext.getContext();
        String consumerHost = rpcContext.getRemoteHost();
        String consumerApplication = rpcContext.getAttachment("dubbo.application");

        logger.info("#######=====> Provider received service:{}, method:{}, args:{} from consumerHost:{}, consumerApplication:{}",
                serviceName, methodName, args, consumerHost, consumerApplication);

        Result result;
        try {
            result = invoker.invoke(invocation);
            long elapsedTime = System.nanoTime() - startTime;
            logger.info("#######=====> Provider service ==> {}, method ==> {}, result ==> {}, elapsedTime ==> {} ns.",
                    serviceName, methodName, result.getValue(), elapsedTime);
            return result;
        } catch (RpcException e) {
            logger.error("Exception occurred in Provider during RPC call: {}", e.getMessage(), e);
            throw e;
        }
    }
}
