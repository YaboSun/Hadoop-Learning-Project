可以通过输入对应的来获取帮助  
hdfs dfs 或者hadoop fs  

**常用命令**：  
hadoop fs -ls /  查看hdfs根目录的文件信息  
hadoop fs -put path/to/your/file path/of/your/hdfs 将指定的文件上传到  
hdfs指定文件路径下  
hadoop fs -copyFromLocal 用法同put，也是将指定文件上传到hdfs  
hadoop fs -copyToLocal path/of/your/hdfs path/to/your/file 将HDFS  
下文件拷贝到本地  
hadoop fs -rm -r path/of/your/hdfs 将指定hdfs路径删除  
hadoop fs -cat(text) path/of/your/hdfs 显示hdfs文件路径下的文件内容  
hadoop fs -mkdir path/to/your/hdfs 在hdfs指定路径创建文件夹  
hadoop fs -mkdir -p path/to/your/hdfs递归创建文件夹  
hadoop fs -ls -R path/to/your/hdfs 递归显示hdfs文件夹  

**注意**  

经过上传文件并查看占用的block，发现不管你上传的文件大小是多大，默认块大小都是128  
M，比如说一个文件大小为257M，那么占用的块会为3块，而且有对应的块地址以及时间戳等  

如果后面继续上传文件，那么这个文件不会使用上一个文件没有使用的块，会重新开辟一个块