import java.util.*;


class circShifter {
	
		private List <String> stringList;
		private List <offIndexPair> indexList;
		private List<String> nolist;
		
	public circShifter (List<String> givenlist, List<String> nolist)
	{
		this.stringList=givenlist;
		this.nolist= nolist;
		this.indexList= new ArrayList<offIndexPair> ();
		//let the nolist items become lower case
		for(int i=0;i<nolist.size();i++)
		{
			nolist.set(i, nolist.get(i).toLowerCase());
		}
	}
	
	public void doShift () {
		if(stringList.size()==0)
		{
			System.out.println("The inputs are empty");
			System.exit(1);
		}
		
		for(int i=0;i<stringList.size();i++)
		{
			
			process(stringList.get(i), i);
		}
	}
	
	private void process (String target,int index)
	{
		if(target.indexOf(" ")==-1 && nolist.contains(target))
			return;
		//else if(target.indexOf(" ")==-1 &&!nolist.contains(target))
			//indexList.add(new offIndexPair(0,index));
		else
		{
			int offcounter=0;
			StringTokenizer tk= new StringTokenizer (target);
			String cur=null;
			
			while(tk.hasMoreTokens())
			{
				cur= tk.nextToken();
				if(!nolist.contains(cur.toLowerCase()))
				{
					indexList.add(new offIndexPair(offcounter,index));
					//make sure the tokens are in correct format: Cap for the first letter, lower for the rest
			
					stringList.set(index,stringList.get(index).substring(0,offcounter)+cur.substring(0,1).toUpperCase()+cur.substring(1).toLowerCase()+stringList.get(index).substring(offcounter+cur.length()));
				}
				else// change the ignored item to lower case
				{
					stringList.set(index,stringList.get(index).substring(0,offcounter)+cur.toLowerCase()+stringList.get(index).substring(offcounter+cur.length()));
				}
				offcounter+=cur.length()+1;//jump to next tokens's position
			}
		}
	}
	
	public String print()
	{
		//System.out.println((char)(27)+"[1m Results are: ");
		StringBuilder str= new StringBuilder();
		
		for(int i=0;i<indexList.size();i++)
		{
			String current=stringList.get(indexList.get(i).getindex());
			int curOff= indexList.get(i).getoffset();
			str.append(current.substring(curOff)+" "+ current.substring(0,curOff)+"\n");
		}
		return str.toString();
	}
	
	public void alphabeter (){
		Collections.sort(indexList, new IndexComparator ());
	}
	
	private class IndexComparator implements Comparator <offIndexPair>{
		public int compare (offIndexPair p1, offIndexPair p2)
		{
			String current1= stringList.get(p1.getindex());
			String current2= stringList.get(p2.getindex());
			int off1= p1.getoffset();
			int off2= p2.getoffset();
			
			String s1=current1.substring(off1)+" "+ current1.substring(0,off1);
			String s2=current2.substring(off2)+" "+ current2.substring(0,off2);
			if(Character.isLetter(s1.charAt(0)) && Character.isLetter(s2.charAt(0)) )
			return (s1).compareTo(s2);
			else if(Character.isLetter(s1.charAt(0)) && !Character.isLetter(s2.charAt(0)))
				return -1;
			else if(!Character.isLetter(s1.charAt(0)) && Character.isLetter(s2.charAt(0)))
				return 1;
			else 
				return s1.compareTo(s2);
			//return (current1.substring(off1)+" "+ current1.substring(0,off1)).compareTo(current2.substring(off2)+" "+ current2.substring(0,off2));
			
		}
	}
}
