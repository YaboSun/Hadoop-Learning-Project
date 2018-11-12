# 相关组件   

在HDFS文件读写的过程中主要涉及三大组件：Client、NameNode、DataNode  

## NameNode  

NameNode主要是管理文件系统的命名空间，维护着**文件系统**以及**整棵树内所有的文件和目录**  
这些信息主要以俩个文件的形式永久保存在本地磁盘：**命名空间镜像文件和编辑日志文件**，NameNode也记录着每个文件  
各个块所在的数据节点信息，但是这些信息不是永久保存的，会在系统启动时根据数据节点信息重建   

没有NameNode文件系统将无法工作，如果NameNode如果挂掉，那么整个文件系统将无法使用，因为无法根据DataNode来进  
行文件系统的重建，为此，Hadoop提供俩种机制：  

* 第一种  
文件备份，将组成文件系统元数据持久状态的文件进行备份，(TODO 这部分还是不太完全理解，即使有了持久状态的文件，如  
何来恢复，后面查看大概明白，就是三个步骤：1.将命名空间的映像导入内存;2.重演编辑日志;3.接收足够多的来自DataNode  
的数据块报告并退出安全模式)。一般的配置是将持久状态写入本地磁盘的同时，写入一个远程挂在的网络文件系统（NFS） 
* 第二种   
运行一个辅助NameNode，处于standby状态，这个辅助的NameNode主要作用是定期合并编辑日志与命名空间镜像，防止编辑  
日志过大，但是有一个问题就是，这个NameNode保存的状态总是滞后于主节点，所以如果全部失效的时候，会出现部分数据丢  
失。这种情况下，一般将存储在NFS的NameNode元数据复制到辅助NameNode并作为新的主NameNode来运行  

## DataNode  

DataNode是文件系统的工作节点，主要是按块存储并检索数据块，并定期向NameNode发送心跳，报告块信息等，受NameNode  
调度

## Client  

客户端代表用户通过NameNode以及DataNode来与文件系统进行交互，因此，用户在编程时无需知道NameNode以及DataNode  
相关信息也可以实现其功能

# HDFS的高可用性（HA）  

**需要重点掌握**  

针对NameNode可能出现的问题，在Hadoop2.x中增加了对HDFS的高可用支持，具体就是：配置一对active-standby的NameNode  
当active NameNode失效，standby NameNode就会接管其任务并开始服务来自客户端的请求，中间不会出现任何的间断，详细  
实现：  
* NameNode之间通过**高可用共享存储**实现编辑日志的共享  
* DataNode同时向俩个NameNode发送数据块处理报告  
* 客户端需要使用特定的机制来处理NameNode的失效问题  
* 辅助NameNode的作用被standby NameNode包含，standby NameNode为active NameNode的命名空间设置周期性检查点  

# HDFS读写流程  

## HDFS写流程  
1. Client将数据按照指定大小进行切分（split）  
2. 向NameNode发送请求  
3. NameNode通过元数据文件查询DataNode信息，选择最近的DataNode（如果是3副本就是3个DataNodes）进行存储  
4. 按照指定的副本数来进行数据块的复制，将数据块同时写入三个DataNode（以PipeLine方式）  
5. 写完后将信息传递给NameNode，NameNode会做相应的记录更新元数据信息  
6. 重复相同的操作写剩下的块  
7. 关闭文件流   

## HDFS读流程  
1. 客户端发起请求，向NameNode查询指定路径的文件  
2. NameNode通过元数据信息查询文件信息，包括所在节点，块等并返回给Client  
3. Client通过NameNode提供的信息与指定的DataNode进行交互，将需要的块下载，完成数据读取  

**容错处理**
当在读写过程中，有一个DataNode挂掉了，HDFS怎么处理的呢？这就涉及到容错机制  
  
