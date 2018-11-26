package mapreduce.app;import org.apache.hadoop.conf.Configuration;import org.apache.hadoop.fs.FileSystem;import org.apache.hadoop.fs.Path;import org.apache.hadoop.io.LongWritable;import org.apache.hadoop.io.Text;import org.apache.hadoop.mapreduce.Job;import org.apache.hadoop.mapreduce.Mapper;import org.apache.hadoop.mapreduce.Reducer;import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;import java.io.IOException;/** * 使用MapReduce开发wordcount应用程序 */public class WordCountApp {    /**     * Map:读取输入的文件     *     * 这里其实LongWritable相当于java中的Long，Text相当于String     * Mapper类似一个模板模式 TODO 模板模式？     */    public static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable> {        // 定义一个全局常量        LongWritable one = new LongWritable(1);        /**         * @param key         * @param value 接收到的每一行数据         * @param context 上下文         * @throws IOException         * @throws InterruptedException         */        @Override        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {            // 接收到的每一行数据            String line = value.toString();            // 按照指定分割符进行拆分            String[] words = line.split(" ");            // 通过上下文把map的处理结果输出            for (String word : words) {                context.write(new Text(word), one);            }        }    }    /**     * Reduce：归并操作     */    public static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {        @Override        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {            long sum = 0;            for (LongWritable value : values) {                // 求key出现的次数总和                sum += value.get();            }            // 最终统计结果的输出            context.write(key, new LongWritable(sum));        }    }    /**     * 定义Driver：封装了MapReduce作业的所有信息     */    public static void main(String[] args) throws Exception {        // 创建configuration        Configuration configuration = new Configuration();        // 创建job        Job job = Job.getInstance(configuration, "WorCount");        // 设置运行的主类jar包        job.setJarByClass(WordCountApp.class);        // 设置作业处理的输入路径        FileInputFormat.setInputPaths(job, new Path(args[0]));        // 设置map相关参数        job.setMapperClass(MyMapper.class);        job.setMapOutputKeyClass(Text.class);        job.setMapOutputValueClass(LongWritable.class);        // 设置reduce相关参数        job.setReducerClass(MyReducer.class);        job.setOutputKeyClass(Text.class);        job.setOutputValueClass(LongWritable.class);        // 设置作业处理的输出路径        // 这个步骤需要先判断文件是否存在        Path path = new Path(args[1]);        FileSystem fileSystem = FileSystem.get(configuration);        if (fileSystem.exists(path)) {            fileSystem.delete(path, true);            System.out.println("file exists but has been deleted");        }        FileOutputFormat.setOutputPath(job, path);        // 提交作业        System.exit(job.waitForCompletion(true) ? 0 : 1);    }}