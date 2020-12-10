package pj;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class TriText implements WritableComparable<TriText> {
	public String w0;
	public String w1;
	public String w2;
	public TriText() {}
	
	public TriText(String s1, String s2,String s3) {
		w0 = s1;
		w1 = s2;
		w2 = s3;
	}
	
	@Override
	public void readFields(DataInput in)throws IOException{
		this.w0 = in.readUTF();
		this.w1 = in.readUTF();
		this.w2 = in.readUTF();
	}
	
	@Override
	public void write(DataOutput out)throws IOException{
		out.writeUTF(w0);
		out.writeUTF(w1);
		out.writeUTF(w2);
	}
	
	@Override
	public int compareTo(TriText another) {
		int a = this.w0.compareTo(another.w0);
		if(a!=0) return a;
		int b = this.w1.compareTo(another.w1);
		if(b!=0) return b;
		return this.w2.compareTo(another.w2);
	}
	
	@Override
	public int hashCode() {
		return this.w0.hashCode() + this.w1.hashCode() + this.w1.hashCode();
	}
	
	@Override
		public boolean equals(Object obj) {
		if(!(obj instanceof TriText)) return false;
		TriText t = (TriText) obj;
		return t.w0.equals(this.w0) && t.w1.equalsIgnoreCase(this.w1) && t.w2.equals(this.w2);
	}
	
	@Override
	public String toString() {
		return w0+" "+w1+" "+w2;
	}
}
