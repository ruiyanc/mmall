### 笔记

1. Lombok
   1. @Setter和@Getter,@NoArgsConstructor和@AllArgsConstructor：无参/全参构造器
   2. @Data重写了@Hashcode和@Equal、@ToString等
   3. @Slf4j为lomback日志框架，log.warn
2. maven环境隔离
   1. dev和prod，profiles
3. tomcat集群
   1. 好处：提高服务的性能，并发能力，以及高可用性；提供项目架构的横向扩展能力
   2. 实现原理：通过nginx负载均衡进行请求转发
   3. 带来的问题：session登录信息存储及读取的问题；服务器定时任务并发的问题