# 核心组件之分布式文件系统HDFS    

* 源自Google的GFS论文，发表于2003年10月  

* HDFS是GFS的开源实现  

* 特点：扩展性&容错性&海量数据存储  

* 将文件切分成指定大小的数据块（默认128M）并以多副本的形式存储在多个机器上，块大小  
的默认值是一个最佳实践得出的结果，一般是不需要修改  
数据切分、多副本、容错等操作对用户是透明的，也就是说我们在实际操作过程中还是对于文件  
来进行操作，而不是数据块，具体底层的操作都是hadoop实现的，这样就使我们类似于单机的  
模式进行分布式的开发


# 核心组件之资源调度系统YARN  

* YARN：Yet Another Resource Negotiator  

* 负责整个集群资源的管理和调度（CPU core、内存等）  

* 特点：扩展性&容错性&多框架资源统一调度(使得可以跑不同框架的任务比如Spark等)  

# 核心组件之分布式计算框架MapReduce  

* 源自谷歌的MapReduce论文，发表于2014年12月  

* 特点：扩展性&容错性&海量数据离线处理  