参考：
http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.6.0-cdh5.7.0/hadoop-project-dist/hadoop-common/SingleCluster.html
  
1. 安装java环境  

2. 安装ssh，设置免密登陆  

3. 下载解压hadoop安装包  

4. 配置hadoop-env.sh（hadoop_home/etc/hadoop）  

* hadoop-env.sh配置  
主要是将JAVA_HOME配置即可  

* core-site.xml配置  
配置默认的HDFS地址  
```java
    <property>  
        <name>fs.defaultFS</name>  
        <value>hdfs://slave02:9000</value>  
    </property>  
```

配置hadoop临时文件目录，因为这个临时目录默认是在系统的tmp路径下，如果重启会丢失
```java
    <property>  
        <name>hadoop.tmp.dir</name>  
        <value>/usr/local/tmp</value>  
    </property>  
```
    
* hdfs-site.xml配置  
```java
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
```
    
* slaves配置  
将对应的节点主机名写入  

5. 启动hdfs  
* 格式化文件系统（仅第一次，不能重复执行）：执行/bin/hdfs namenode -format  
* 启动NameNode以及DataNode进程sbin/start-dfs.sh  
* 要关闭的话也是对应的sbin/stop-dfs.sh   

6. 查看是否启动  
通过jps查看进程
DataNode、NameNode、SecondaryNameNode  
或者通过网络端口http：//localhost：50070