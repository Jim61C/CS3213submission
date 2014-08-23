
class offIndexPair {

	private int offset;
	private int index;
	
	public offIndexPair (int of, int in)
	{
		this.offset=of;
		this.index=in;
	}
	
	public int getoffset (){
		return this.offset;
	}
	public void setoffset (int off){
		this.offset=off;
	}
	public int getindex (){
		return this.index;
	}
	public void setindex (int in){
		this.index= in;
	}
	
}
