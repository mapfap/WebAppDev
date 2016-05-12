package sarun;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.Writable;
import java.io.DataInput;
import java.io.DataOutput;

public class Message implements Writable {
    public int value;
    public boolean isFromTo;

    public Message() {

    }
    
    public Message(int value, boolean isFromTo) {
      this.value = value;
      this.isFromTo = isFromTo;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        value = in.readInt();
        isFromTo = in.readBoolean();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(value);
        out.writeBoolean(isFromTo);
    }

    @Override
    public String toString() {
        return "" + value;// + ((isFromTo) ? "^" : ":");
    }
  }
