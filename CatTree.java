import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

//import COMP250_A3_W2020.CatInfo; 


public class CatTree implements Iterable<CatInfo>{
   
	public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    
    class CatNode {
        
        CatInfo data;
        CatNode senior; //left
        CatNode same; //
        CatNode junior; //right
        private int numM = 0;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        public CatNode addCat(CatNode c) {
        	
        	if(this.data.monthHired == c.data.monthHired ) { //same as original root
        		
        		if(this.data.furThickness < c.data.furThickness){ //switch should work(new CatNode bigger than root)
	        		
		        		if(this.same == null) { 
		        			
		        			CatInfo t = this.data;
		        			
		        			CatNode tt = new CatNode(t);
		        			
		        			this.data = c.data;
		        			this.same = tt;
		        			
		   		
			        		return this;
		        			
		        		}
		        		
		        		else { //effective switch
		        			
		        			CatInfo te = this.data;
		        			CatNode oldThis = new CatNode(te);
		        			oldThis.same = this.same;
		        			
		        			this.data = c.data;
		        			this.same = oldThis;
		    
			        		
			        		return this;
			        		
		        		}
		        		
		        		
        			
        		}
        		
        		else {
        			CatNode head = this;
        			
        			
        			
        			while(head.same!=null && head.same.data.furThickness > c.data.furThickness) {  //O(n)--> goes through every node in same
        				head = head.same;
        			}
        			
        			
        			CatNode temp2 = head.same;
        			head.same = c;
        			head.same.same = temp2;
        		}
        		
        	
        		
        		
        		
        	}
        	
        	if(this.junior!=null && this.data.monthHired < c.data.monthHired) { //junior to root
        		
        		this.junior.addCat(c);
        		
        		
        	}
        	
        	if(this.junior==null && this.data.monthHired < c.data.monthHired) { //junior to root
        		
        		this.junior=c;
        		
        		return this;
        	}
        	
        	if(this.senior!=null && this.data.monthHired > c.data.monthHired) { //senior to root
        		
        		this.senior.addCat(c);
        		
        		
        	}
        	
        	if(this.senior==null && this.data.monthHired > c.data.monthHired) { //senior to root
        		
        		this.senior=c;
        		
        		return this;
        		
        	}
        	
        	
        	return this;
        }
        
        
        
        public CatNode removeCat(CatInfo c) {
            // ADD YOUR CODE HERE
        	
        	
        	if(this.data.monthHired == c.monthHired ) {// Wanted Node in this.same line
        	
        		if(this.data.equals(c) && this.same!=null) { //Case 1(Want to remove original root that has A SAME)
        			
        			this.data = this.same.data;
        			this.same = this.same.same; //might cause null-pointer exception
        			
        			return this;
        		}
        		
        		
        		
        		else if(this.data.equals(c) && this.same==null) { //Case 2(remove root that doesn't have a same)
        			
        			//CODE HERE
        			if(this.senior == null & this.junior == null) { //og root doesn't have a junior,senior,or same
        				
        				
        				//this.data = null;
        				return null;
        				
        			}
        			
        			
        			
        			else if(this.senior == null) {
        				this.data = this.junior.data;
        				this.same = this.junior.same;
        				this.senior = this.junior.senior;
        				this.junior = this.junior.junior;
        				
        				
        				
        				
        			}
        			
        			else {
        				
        				CatNode temp = this.junior;
        				
        				this.data = this.senior.data;
        				this.same = this.senior.same;
        				this.junior = this.senior.junior;
        				this.senior = this.senior.senior;
        				
        				if(temp != null) {
        				this.addCat(temp);
        				}		
        				
        				
        			}
        			
        			return this;
        		}
        		
        		else { //Case 3(remove node contained in original root same
        			
        			
        			if(this.same!=null && this.same.data.equals(c) != true) { 
        				
        				this.same.removeCat(c); //traverses down
        			}
        			
        			
        			this.same = this.same.same; //might cause null-pointer exception
        			
        			
        			
        		}
        	
        	
        	}
        	
        	else if(this.junior!=null && this.junior.data.equals(c) && this.junior.junior == null && this.junior.senior == null && this.junior.same==null ) {
    			this.junior = null;
    			return this;
    		}
    		
    		else if(this.senior!=null && this.senior.data.equals(c) && this.senior.junior == null && this.senior.senior == null && this.senior.same==null ) {
    			this.senior = null;
    			return this;
    		}
        	
        	else if(this.data.monthHired > c.monthHired) { //senior to root
        		
        		if(this.senior!= null)
        		this.senior.removeCat(c);
        		
        	}
        	
			else if(this.data.monthHired < c.monthHired) { //junior to root
			        		
			  	if(this.junior!= null)
				this.junior.removeCat(c);
			        		
			}
        	
        	
        	
            return this; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        
        public int mostSenior() {
            // ADD YOUR CODE HERE
        	
        	if(this.senior == null) {
        		return this.data.monthHired;
        	}
        	
        	else {
        		
        		return this.senior.mostSenior();
        	}
        	
        	
        }
        
        public int fluffiest() {
            // ADD YOUR CODE HERE
        	int max = this.data.furThickness;
        	
        	if(this.senior==null && this.junior==null) {
        		return this.data.furThickness;
        	}
        	
        	else {
        		
        		CatNode head = this;
        		
        		while(head.senior!=null) {
        			head = head.senior;
        			
        			if(head.data.furThickness>max) {
        				max = head.data.furThickness;
        			}
        		}
        		
        		head = this;
        		
        		while(head.junior!=null) {
        			head = head.junior;
        			
        			if(head.data.furThickness>max) {
        				max = head.data.furThickness;
        			}
        		}
        		
        	}
        		
            return max; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        private void dinorder(CatNode c,int monthMin, int monthMax) { //for my own testing   	
        	
        	if(c.data.monthHired>=monthMin && c.data.monthHired<=monthMax) {
        		numM++;
        	}
        		
        	
        	if (c.senior != null) {
        		
        		dinorder(c.senior,monthMin,monthMax);       		
        	}
       		
//        	System.out.println(c.data);
        	
        	
        	
        	if(c.same !=null) {
        		//System.out.print("same:");
        	        		
        		dinorder(c.same,monthMin,monthMax);
        		
        		
        	}    	
        	
        	if (c.junior != null) {
        		
        		
        		dinorder(c.junior,monthMin,monthMax);
        		
        		
        	}
        	
        	
        	
        }
        
        public int hhiredFromMonths(int monthMin, int monthMax) {
        	
        	int numM = 0 ;
        	
        	if(monthMin>monthMax) {
        		return 0;
        	}
        	
        	
        	
        	dinorder(this,monthMin,monthMax);
        	
        	return numM;
        }
        
        
        
        public int hiredFromMonths(int monthMin, int monthMax) {
        	
        	Iterator<CatInfo> itr = CatTree.this.iterator();
        	CatInfo element=itr.next();
        	int i = 0;
        	
        	if(monthMin>monthMax) {
        		return 0;
        	}
        	
        	
        	while(itr.hasNext()) {
        		if(element.monthHired>=monthMin && element.monthHired<=monthMax ) {
        			i++;
        		}
        		element = itr.next();
        	}
        	if(element.monthHired>=monthMin && element.monthHired<=monthMax ) {
    			i++;
    		}
        	
        	
        	
        	return i;
        }
        
        
        
        
        public CatInfo fluffiestFromMonth(int month) {
            // ADD YOUR CODE HERE
        	
        	if(this.data.monthHired == month) {
        		return this.data;
        	}
        	
        	else if(this.senior!=null && this.data.monthHired > month) {
        		return this.senior.fluffiestFromMonth(month);
        	}
        	
        	else if(this.junior!=null && this.data.monthHired < month) {
        		return this.junior.fluffiestFromMonth(month);
        	}
            
        	return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        public int[] costPlanning(int nbMonths) {
            // ADD YOUR CODE HERE
        	
        	Iterator<CatInfo> itr = CatTree.this.iterator();
        	CatInfo element=itr.next() ;//= CatTree.this.root.data;
        	
        	int[] cost = new int[nbMonths]; //int 0 is always 243
        	
        	for(int i = 0; i<cost.length ;i++) {
        		
        		
        		
        		while(itr.hasNext()!=false) {
        			
        			
        			
        			if(element.nextGroomingAppointment == (243 + i)) {
        				cost[i]= cost[i] + element.expectedGroomingCost;
        			}
        			
        			element = itr.next();
        			
        			
        		}
        		
        		if(element.nextGroomingAppointment == (243 + i)) {
    				cost[i]= cost[i] + element.expectedGroomingCost;
    			}
        		
        		itr = CatTree.this.iterator();
        		element=itr.next() ;
        		
        	}
               	
            return cost; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
    }
    
    private class CatTreeIterator implements Iterator<CatInfo> { //from most senior to most junior, less fluffy to most fluffy
        // HERE YOU CAN ADD THE FIELDS YOU NEED
    	
    	CatNode head = CatTree.this.root; 
    	ArrayList<CatInfo> ar = new ArrayList< >();
    	Iterator<CatInfo> itr = ar.iterator();
    	
    	
        public CatTreeIterator() {
            //YOUR CODE GOES HERE
        
      
       
        	iinorder(head);
       
        
	       // System.out.println(ar);
	        itr = ar.iterator();
        
//        System.out.println(itr.next());
//        System.out.println(itr.next());
        	
        }
        
        private void iinorder(CatNode c) { //own private method
        	
        	if (c.senior != null) {
        		iinorder(c.senior);
        	}        	
        	
        	
        	
        	if(c.same !=null) {
        		iinorder(c.same);
        	}
        	
        	ar.add(c.data);
        	//System.out.println("Using iterator= " + c.data.name);
        	
        	if (c.junior != null) {
        		iinorder(c.junior);
        	}
        	
        	
        	
        }
        
        public CatInfo next(){
            //YOUR CODE GOES HERE
        	
        	
          	
        	if(itr.hasNext()) {	
        		
        		return (CatInfo)itr.next();
        	}
        	
        	
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        public boolean hasNext() {
            //YOUR CODE GOES HERE
        
        	
        	
            return itr.hasNext(); // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
    }
    
	
    
    
}

	

