# 环境  
IDEA2018 + Maven3.3.9  

# 具体开发  

1. 新建项目  
新建一个maven空项目
2. 配置pom文件  
```java
    <properties>
        <junit.version>4.12</junit.version>
        <hadoop.version>2.6.0-cdh5.7.0</hadoop.version>
    </properties>

    <repositories>
        <repository>
            <id>cloudera</id>
            <url>https://repository.cloudera.com/artifactory/cloudera-repos/</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-client</artifactId>
            <version>${hadoop.version}</version>
        </dependency>
    </dependencies>
```
其中hadoop是必须需要的，这里不清楚为什么采用hadoop-client，还有就是使用junit来进行单元测试，另外添加  
repository.cloudera