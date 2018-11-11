# 狭义Hadoop  

传统意义上的Hadoop也就是Hadoop的三大核心组件，包括HDFS、YARN以及MapReduce  

# 广义Hadoop  

广义上的Hadoop指的是Hadoop生态系统中的所有组件，其中包括：  

* HDFS  

HDFS可以说是Hadoop生态系统最底层的组件，用于数据的存储  

* MapReduce  

数据存储完成以后就需要进行相关的数据计算，这个时候就会用到MapReduce进行数据的离线  
处理，现在版本的MapReduce是跑在YARN之上的  

存在的问题：  

1. 编程门槛较高，学习曲线陡峭，不方便使用开发,不过针对这个不足，Hadoop提供了Pig  
、Hive等相关组件来简化开发

2. 处理模型速度因为其Map以及Reduce操作都是在磁盘中完成，这就会带来很多的磁盘IO的  
问题  
  

如何调优：  

* Pig  
用做离线处理  

* Hive  

* Flume  

* Sqoop  

* HBase  

每一个组件都是用于处理一个特定域