### 笔记

1. Lombok
   1. @Setter和@Getter,@NoArgsConstructor和@AllArgsConstructor：无参/全参构造器
   2. @Data重写了@Hashcode和@Equal、@ToString等
   3. @Slf4j为lomback日志框架，log.warn
2. maven环境隔离
   1. dev和prod，profiles
   2. mvn clean package -Dmaven.test.skip=true -p dev 打包并跳过测试，环境隔离并选dev环境
3. tomcat集群
   1. 好处：提高服务的性能，并发能力，以及高可用性；提供项目架构的横向扩展能力
   2. 实现原理：通过nginx负载均衡进行请求转发
   3. 带来的问题：session登录信息存储及读取的问题；服务器定时任务并发的问题
   4. 单机部署多应用
      * 修改/etc/profile增加tomcat环境变量，添加多配置
      * 修改第二台的catalina.sh 在OSxxx下加入export CATALINA_HOME=环境变量
      * 打开conf下的server.xml，改3个端口
      * 分别启动，再部署webapps的ROOT项目首页
4. nginx负载均衡配置、常用策略、场景及特点
   1. 轮询(默认)，权重，ip hash， url hash，fair
   2. upstream 域名{ server 域名:8080 weight; server 域名:9080 weight;}
   3. 单机可修改host文件映射本地对应域名
   4. 编辑conf/nginx.conf配置文件，在http里upstream负载均衡server配置域名，在http节点下增加include  vhost/*.conf
5. redis进阶
   1. redis-cli -p 端口 -h ip -a 密码
   2. 系统级配置
      * info：查看redis所有配置
      * select db空间：切换空间，save：持久化
      * flushdb：清除当前空间key；flushall：清除所有key
   3. 命令
      1. ttl key：查看过期时间，expire：设置过期时间
      2. rename：key重命名并覆盖 nx判断重复
6. 单点登录
    1. redis+cookie+tomcat集群
    2. 把登录的session转为cookie自定义持久化并用redis保存，集群环境下通过redis校对并获取。
7. redis分布式
    1. 一致性hash算法：Consistent hashing
    2. redis集群采用分片的ShardedJedis -> RedisShardedPool
    3. 集群与分布式的区别
        * 集群是一种物理形态，分布式是一种工作方式
    * 通过ShardedJedis持久化cookie实现spring-session单点登录的效果
8. spring-session
9. spring schedule -> 作业调度，如定时任务
    1. cron表达式
    2. 配置
        * 
