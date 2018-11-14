package hdfs.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

/**
 * Created by YaboSun in 18-11-12
 *
 * Hadoop HDFS JavaApi 操作
 */
public class HDFSApp {

    // 以string类型定义hdfs
    public static final String HDFS_PATH = "hdfs://leader:8020";

    private Path path = new Path("/hdfsapi/test/1.txt");
    // 声明一个FileSystem
    FileSystem fileSystem = null;

    // 用于Hadoop的设置
    Configuration configuration = null;

    /**
     * 测试创建目录功能
     */
    @Test
    public void mkdir() throws Exception {
        fileSystem.mkdirs(new Path("/hdfsapi/test"));
    }

    /**
     * 文件系统创建文件
     * @throws IOException 当要创建的文件存在时，删除当前文件并创建新的文件
     */
    @Test
    public void create() {
       // fileSystem.createNewFile(new Path("/hdfsapi/test/test.txt"));
        try {
            if (fileSystem.exists(path)) {
                fileSystem.delete(path, true);
                System.out.println("file deleted");
            } else {
                FSDataOutputStream fsDataOutputStream = fileSystem.create(path);
                fsDataOutputStream.write("hello hadoop".getBytes());
                fsDataOutputStream.flush();
                fsDataOutputStream.close();
                System.out.println("file created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看HDFS文件内容
     * @exception IOException 当所要查看的文件不存在时抛出异常
     */
    @Test
    public void cat() {
        try {
            if (fileSystem.exists(path)) {
                /*FSDataInputStream fsDataInputStream = fileSystem.open(path);
                byte[] bytes = new byte[12];
                fsDataInputStream.read(bytes);
                System.out.println(bytes);*/

                FSDataInputStream in = fileSystem.open(path);
                IOUtils.copyBytes(in, System.out, 12);
                in.close();
            }
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("file not exist");
        }
    }

    /**
     * 重命名指定路径文件为目标文件名
     * @throws IOException
     */
    @Test
    public void rename() throws IOException {
        fileSystem.rename(path, new Path("/hdfsapi/test/rename1.txt"));
    }

    /**
     * 将本地文件上传到hdfs
     */
    @Test
    public void copyFromLocal() {
        Path localPath = new Path("/usr/local/hadoop-2.6.0-cdh5.7.0/README.txt");
        Path hdfsPath = new Path("/hdfsapi/test/");
        try {
            fileSystem.copyFromLocalFile(localPath, hdfsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将本hdfs文件下载到本地
     */
    @Test
    public void copyToLocal() {
        Path localPath = new Path("/home/hadoop");
        Path hdfsPath = new Path("/hdfsapi/test/README.txt");
        try {
            fileSystem.copyToLocalFile(hdfsPath, localPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 准备开发环境
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        System.out.println("HDFSApp setUp");
        // 配置环境
        configuration = new Configuration();
        // 根据URI以及configuration获取文件系统
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration);
    }

    /**
     * 释放资源
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        configuration = null;
        fileSystem = null;
        System.out.println("HDFSApp tearDown");
    }
}