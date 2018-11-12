**master/slave架构，一个master带n个slaves**  

一般是一个NameNode多个DataNodes，而NameNode还有一个处于standby状态，用于  
出现错误的处理  

* NameNode&DataNode作用  

NameNode作用：  
1. 用于响应客户端的请求  
2. 存放文件目录等元数据（文件名称、副本系数、block存放的datanode）信息  

DataNode作用：  
1. 存储用户文件对应的数据块  
2. 顶起向NameNode发送心跳信息，汇报本身及其所有的block信息，健康状况  

实际情况是可以在一台机器上同时部署NN以及DN，但是我们在生产环境中不这么做  

* 副本存放策略  
1st replica. 如果写请求方所在机器是其中一个datanode,则直接存放在本地,  
否则随机在集群中选择一个datanode.  
2nd replica. 第二个副本存放于不同第一个副本的所在的机架.  
3rd replica. 第三个副本存放于第二个副本所在的机架,但是属于不同的节点.  