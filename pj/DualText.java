package pj;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class DualText implements WritableComparable<DualText> {
	public String w0;
	public String w1;
	public DualText() {}
	
	public DualText(String s1, String s2) {
		w0 = s1;
		w1 = s2;
	}
	
	@Override
	public void readFields(DataInput in)throws IOException{
		this.w0 = in.readUTF();
		this.w1 = in.readUTF();
	}
	
	@Override
	public void write(DataOutput out)throws IOException{
		out.writeUTF(w0);
		out.writeUTF(w1);
	}
	
	@Override
	public int compareTo(DualText another) {
		int a = this.w0.compareTo(another.w0);
		if(a!=0)return a;
		return this.w1.compareTo(another.w1);
	}
	
	@Override
	public int hashCode() {
		return this.w0.hashCode() + this.w1.hashCode();
	}
	
	@Override
		public boolean equals(Object obj) {
		if(!(obj instanceof DualText)) return false;
		DualText t = (DualText) obj;
		return t.w0.equals(this.w0) && t.w1.equalsIgnoreCase(this.w1);
	}
	
	@Override
	public String toString() {
		return w0+" "+w1;
	}
}
