## 扩展新租户步骤（随时扩展新租户非常简单）

1. 实现`MessageHandler`接口创建`TenantXHandler.java`
2. 在`TenantHandlerMapping.java`中增加`handlerMap.put("tenantX", tenantXHandler)`
3. Producer端发送消息，Key设为"tenantX"即可自动路由消费。
4. 注意topic分区的数量需要和租户数量一致，Listener的线程数需要和topic分区数一致。
