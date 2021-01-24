import java.util.Iterator;

//import COMP250_A3_W2020.CatInfo;

public class tester {

	public static void main(String[] args) {
		
		CatInfo a = new CatInfo("a", 87, 55, 0, 0);
		
		//junior
		CatInfo b = new CatInfo("b", 89, 56, 0, 0);
		CatInfo c = new CatInfo("c", 89, 55, 0, 0);
		CatInfo cc = new CatInfo("cc", 89, 54, 0, 0);
		CatInfo c2 = new CatInfo("c2", 89, 45, 0, 0);
		CatInfo d = new CatInfo("d" , 99, 55, 0, 0);
		CatInfo dd = new CatInfo("dd" , 99, 53, 0, 0);
		
		
		
		CatTree cT = new CatTree(a);
		cT.addCat(b);
		cT.addCat(c);
		cT.addCat(c2);
		cT.addCat(cc);
		cT.addCat(d);
		cT.addCat(dd);
		//cT.traverse();
		
		Iterator<CatInfo> itr = cT.iterator();
    	CatInfo element=itr.next();
    	int i = 0;
    	//System.out.println(element);
    	
//    	while(itr.hasNext() && element.monthHired!=89) {
//    		System.out.println(element.monthHired);
//    		element = itr.next();
//    	}
//    	//System.out.println(element.monthHired);
//    	
//    	while(itr.hasNext() && element.monthHired>99!=true) {
//    		System.out.println(element.monthHired);
//    		i++;
//    		element = itr.next();
//    	}
//    	i++;
//    	System.out.println(i);
    	
    	while(itr.hasNext()) {
    		if(element.monthHired>=89 && element.monthHired<=99 ) {
    			i++;
    		}
    		element = itr.next();
    	}
    	if(element.monthHired>=89 && element.monthHired<=99 ) {
			i++;
		}
    	System.out.println(i);
		

	}

}
