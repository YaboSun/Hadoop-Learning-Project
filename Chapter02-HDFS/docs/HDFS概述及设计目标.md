如何自己设计一个分布式文件系统？  

* 负载均衡  

无法保证带来的问题：  
1. 如果单个节点负载过大，那么在进行数据处理的时候，很难进行并行处理，可能会带来网络  
瓶颈，很难进行大数据的处理  
2. 节点利用率低  

# 分布式文件系统HDFS  

* 什么是HDFS  
Hadoop实现了一个分布式文件系统，简称HDFS（Hadoop Distributed File System）  

* Hdfs设计目标  
1. 非常巨大分布式文件系统  
2. 运行在普通廉价的硬件上  
3. 易扩展、为用户提供性能不错的文件存储服务  
