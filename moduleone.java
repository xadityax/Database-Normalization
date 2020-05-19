import java.util.*;

class Module{
	static String ans = "";
	static String hell = "";
	static String [] used = new String[100000];
	static int wow = 0;
	static String depends [][] = new String[100000][2];
	static int depindex = 0;
	static int bcnfindex = 0;
	static String combs [] = new String[100000];
	static int combindex=0;
	static String combs2 [] = new String[100000];
	static int combindex2=0;
	static String candidates [] = new String [100000];
	static String twonf[][] = new String [100000][2];
	static String finalbcnf[][] = new String [100000][2];
	static int bcindex = 0;
	static int second=0;
	static int third=0;
	static int bc=0;
	static int twonfindex=0;
	static int candindex=0;
	static String every="";
	public static void clearvisits(String visits[]){
		for(int xo=0;xo<100000;xo++){
				visits[xo]="";
		}
	}
	public static void print(String printer){
		for(int i=0;i<printer.length();i++){
			System.out.print(printer.charAt(i));
			if(i!=printer.length()-1)
				System.out.print(",");
		}
		System.out.println("");
	}
	public static void emptyit(){
		for(int i=0;i<combindex;i++){
			combs[i]="";
		}
		combindex=0;
		return;
	}
	public static void emptyit2(){
		for(int i=0;i<combindex2;i++){
			combs2[i]="";
		}
		combindex2=0;
		return;
	}
	public static String sortplease(String x){
		char tempArray[] = x.toCharArray();
		Arrays.sort(tempArray);
		String send="";
		for(int i=0;i<x.length();i++){
			send+=tempArray[i]+"";
		}
		return send;
	}
	public static boolean checkequals(String one, String two){
		char tempArray[] = two.toCharArray(); 
        char temp2[] = one.toCharArray();
        Arrays.sort(tempArray); 
        Arrays.sort(temp2); 
        if(two.length()==one.length()){
        	for(int i=0;i<two.length();i++){
        		if(tempArray[i]!=temp2[i]){
        			return false;
        		}
        	}
        	return true;
        }
        else 
        	return false;
	}
	public static void main(String[] args){	
		String s,r,dumi,dumj,dummy;
		int flag=0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Attributes.");
		s = sc.nextLine();
		System.out.println("Enter number of FDs.");
		Scanner rc = new Scanner(System.in);
		r = rc.nextLine();
		int n = Integer.parseInt(r);
		int count = 0;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the FDs");
		ArrayList<String> tokens = new ArrayList<>();
		while (scanner.hasNextLine() && count!=n) {
			count++;
			Scanner lineScanner = new Scanner(scanner.nextLine());
			while (lineScanner.hasNext()) {
				tokens.add(lineScanner.next());
			}
			lineScanner.close();
		}
		String[][] pro = new String[100000][2];
		String[][] pro2 = new String[100000][2];
		int pro2size=0;
		for(int i=0;i<100;i++){
			for(int j=0;j<2;j++){
				pro[i][j]="Z";
			}
		}
		scanner.close();
		int k=0;
		for(int i=0;i<tokens.size();i++){
			pro[k][0]=tokens.get(i);
			i++;
			pro[k][1]=tokens.get(i);
			k++;
		}
		for(int i=0;i<100;i++){
				String temp = pro[i][1];
				if(temp.length()>=2){
					for(int j=0;j<temp.length()-1;j++){
						pro[n][0]=pro[i][0];
						pro[n][1]=temp.charAt(j)+"";
						n++;
					}
					pro[i][1]=temp.charAt(temp.length()-1)+"";
				}
		}
		String [] closures = new String [2];
		String [] visits = new String [100000];
		clearvisits(visits);
		int l=0;
		//remove extraneous
		for(int i=0;i<n;i++){
			String test = pro[i][0];
			flag=0;
			clearvisits(visits);
			findclosure(test,n,pro,r,visits);
			closures[0]=sortplease(ans);
			ans="";
			clearvisits(visits);
			if(test.length()>1){
				for(int j=0;j<test.length();j++){
					flag=0;
					String toBeUsed = "";
					char ch1 = test.charAt(j);
					for(k=0;k<test.length();k++){
						if(test.charAt(k)!=ch1)
							toBeUsed+=test.charAt(k);
					}
					findclosure(toBeUsed,n,pro,r,visits);
					clearvisits(visits);
					closures[1]=sortplease(ans);
					for(int kx=0;kx<closures[0].length();kx++){
						int ind = closures[1].indexOf(closures[0].charAt(kx));
						if(ind==-1){
							flag=1;
						}
					}
					if(checkequals(closures[0],closures[1]) || flag==0)
						pro[i][0]=toBeUsed;
					ans="";
				}
			}
		}
		//redundant removal
		for(int i=0;i<n;i++){
			clearvisits(visits);
			String find = pro[i][0]+","+pro[i][1];
			for(int j=0;j<n;j++){
					if(!(pro[j][0].equals(pro[i][0]) && pro[j][1].equals(pro[i][1]))){
						pro2[pro2size][0]=pro[j][0];
						pro2[pro2size][1]=pro[j][1];
						pro2size++;
					}
			}
			findclosure2(pro[i][0],pro2size,pro2,r,visits,find);
			for(int ky=0;ky<ans.length();ky++){
				if(ans.charAt(ky)==pro[i][1].charAt(0)){
					wow=1;
				}
			}
			pro2size=0;
			if(wow==1){
				for(int j=i;j<n-1;j++){
					pro[j][0]=pro[j+1][0];
					pro[j][1]=pro[j+1][1];
				}
				i--;
				n--;
			}
			ans="";
			wow=0;
		}
		// remove copies
		String temped[] = new String[n];
		String bcnf [][] = new String [1000000][2];
		int tempedindex=0;
		for(int i=0;i<n;i++){
			flag=0;
			String lite = pro[i][0]+","+pro[i][1];
			for(int j=0;j<n;j++){
				if(lite.equals(temped[j])){
					flag=1;
				}
			}
			if(flag==1){
				for(int j=i;j<n-1;j++){
					pro[j][0]=pro[j+1][0];
					pro[j][1]=pro[j+1][1];
				}
				i--;
				n--;
			}
			else{
				temped[tempedindex]=lite;
				tempedindex++;
			}
		}
		//Minimal cover done
		System.out.println("");
		System.out.println("Minimal Cover: ");
		for(int i=0;i<n;i++){
			System.out.println(pro[i][0]+" -> "+pro[i][1]);
		}
		System.out.println("");
		System.out.println("Original Relation : ");
		print(s);
		System.out.println("");
		System.out.println("Superkeys : ");
		System.out.println("");
		String full = s;
		initiate(s);
		every = sortplease(s);
		String superkeys [] = new String [combindex];
		int supindex = 0;
		//send all combinations of our original relation and check if they satisfy superkey property.
		for(int i=0;i<combindex;i++){
			String checkkey = combs[i];
			clearvisits(visits);
			ans="";
			findclosure2(checkkey,n,pro,"XXXXXXX",visits,"LOLLLLLLLL");
			ans=sortplease(ans);
			if(ans.equals(sortplease(full))){
				superkeys[supindex]=checkkey;
				supindex++;
				print(checkkey);
			}
		}
		emptyit();
		System.out.println("");
		System.out.println("Now finding candidate keys, Please wait....");
		String all = "";
		String nonkeys = "";
		//Candidate keys STORED IN CANDIDATES WITH CANDINDEX
		for(int i=0;i<supindex;i++){
			flag=0;
			String check = superkeys[i];
			for(int j = check.length()-1;j>=0;j--){
				String temp = check;
				temp = charRemoveAt(check,j);
				clearvisits(visits);
				ans="";
				findclosure2(temp,n,pro,"XXX",visits,"LOLLLLLL");
				ans=sortplease(ans);
				if(ans.equals(full)){
					check=charRemoveAt(check,j);
				}
			}
			for(k=0;k<candindex;k++){
				if(check.equals(candidates[k])){
					flag=1;
				}
			}
			if(flag==0){
				candidates[candindex]=check;
				candindex++;
			}
		}
		System.out.println("Candidate Keys : ");
		for(int i=0;i<candindex;i++){
			all+=candidates[i];
			print(candidates[i]);
		}
		String add = candidates[0];
		System.out.println("");
		System.out.println("");
		//NONKEY ATTRIBUTES STORED IN NONKEYS
		for(int i=0;i<full.length();i++){
			flag=0;
			char ch = full.charAt(i);
			for(int j=0;j<all.length();j++){
				if(ch==all.charAt(j)){
					flag=1;
				}
			}
			if(flag==0)
				nonkeys += ch+"";
		}
		check2NF(pro,n,s,visits,nonkeys);
		clearvisits(visits);
		if(second==1)
			to2NF(pro,n,s,visits,nonkeys);
		else
			System.out.println("Already in 2NF");
		check3NF(pro,n,s,visits,nonkeys,all);
		clearvisits(visits);
		if(third==1){
			to3NF(pro,n,s,visits,bcnf);
			System.out.println("*****Starting BCNF*****");
			System.out.println("");
			// REMOVE DUPS
			tempedindex=0;
			int x=bcnfindex;
			String temped2[] = new String[100000];
			for(int i=0;i<x;i++){
				flag=0;
				String lite = bcnf[i][0]+bcnf[i][1];
				for(int j=0;j<tempedindex;j++){
					if(lite.equals(temped2[j])){
						flag=1;
					}
				}
				if(flag==1){
					for(int j=i;j<x-1;j++){
						bcnf[j][0]=bcnf[j+1][0];
						bcnf[j][1]=bcnf[j+1][1];
					}
					i--;
					x--;
				}
				else{
					temped2[tempedindex]=lite;
					tempedindex++;
				}
			}
			bcnfindex=x;
			int pehle=x;
			for(int i=0;i<x;i++){
				String ch = bcnf[i][0];
				String ch2 = bcnf[i][1];
				toBCNF(pro,ch,ch2,n,visits,bcnf);
			}
			x=bcnfindex;
			tempedindex=0;
			String temped3[] = new String[100000];
			for(int i=0;i<x;i++){
				flag=0;
				String lite = bcnf[i][0]+bcnf[i][1];
				for(int j=0;j<tempedindex;j++){
					if(lite.equals(temped3[j])){
						flag=1;
					}
				}
				if(flag==1){
					for(int j=i;j<x-1;j++){
						bcnf[j][0]=bcnf[j+1][0];
						bcnf[j][1]=bcnf[j+1][1];
					}
					i--;
					x--;
				}
				else{
					temped3[tempedindex]=lite;
					tempedindex++;
				}
			}
			String allchars = "";
			for(int i=pehle;i<x;i++){
				int ind=0;
				s = bcnf[i][0]+bcnf[i][1];
				for(int j=0;j<s.length();j++){
					char ch = s.charAt(j);
					ind = allchars.indexOf(ch);
					if(ind==-1)
						break;
				}
				emptyit();
				if(s.length()>=2 && ind==-1){
				allchars+=s;
				System.out.println("Candidate Key : ");
				print(bcnf[i][0]);
				emptyit();
				String tempo = sortplease(bcnf[i][0]+bcnf[i][1]);
				String answer = "";
				int ind2=0;
				for(int p=0;p<tempo.length();p++){
					ind2 = answer.indexOf(tempo.charAt(p));
					if(ind2==-1)
						answer+=tempo.charAt(p)+"";
				}
				System.out.print("Relation : ");
				print(answer);
				System.out.println("");
				}
			}
			String last = "";
			for(int i=0;i<full.length();i++){
				char ch = full.charAt(i);
				int ind = allchars.indexOf(ch);
				if(ind==-1){
					last+=ch+"";
				}
			}
			if(last!=""){
			System.out.println("Candidate Key : ");
			print(sortplease(add));
			String tempo = add+last;
			String answer = "";
			int ind=0;
			for(int i=0;i<tempo.length();i++){
				ind = answer.indexOf(tempo.charAt(i));
				if(ind==-1)
					answer+=tempo.charAt(i)+"";
			}
			System.out.print("Relation : ");
			print(sortplease(answer));
			System.out.println("");
			}
			System.out.println("*****End of BCNF*****");
			System.out.println("");
			return;
		}
		else
			System.out.println("Already in 3NF");
		//
		checkBCNF(pro,n,s,visits,nonkeys,all);
		clearvisits(visits);
		if(bc==1){
			doBCNF(pro,n,s,visits,bcnf);
		}
		else{
			System.out.println("Already in BCNF");
			return;
		}
		String allchars = "";
		System.out.println("*****Starting BCNF*****");
		System.out.println("");
		// REMOVE DUPS
		tempedindex=0;
		int x=bcnfindex;
		String temped2[] = new String[x];
		for(int i=0;i<x;i++){
			flag=0;
			String lite = bcnf[i][0]+bcnf[i][1];
			for(int j=0;j<tempedindex;j++){
				if(lite.equals(temped2[j])){
					flag=1;
				}
			}
			if(flag==1){
				for(int j=i;j<x-1;j++){
					bcnf[j][0]=bcnf[j+1][0];
					bcnf[j][1]=bcnf[j+1][1];
				}
				i--;
				x--;
			}
			else{
				temped2[tempedindex]=lite;
				tempedindex++;
			}
		}
		bcnfindex=x;
			// Recall on individual relations inside bcnf
		int pehle=x;
		for(int i=0;i<x;i++){
				String ch = bcnf[i][0];
				String ch2 = bcnf[i][1];
				toBCNF(pro,ch,ch2,n,visits,bcnf);
		}
			x=bcnfindex;
		x=bcnfindex;
			tempedindex=0;
			String temped3[] = new String[x];
			for(int i=0;i<x;i++){
			flag=0;
			String lite = bcnf[i][0]+bcnf[i][1];
			for(int j=0;j<tempedindex;j++){
				if(lite.equals(temped3[j])){
					flag=1;
				}
			}
			if(flag==1){
				for(int j=i;j<x-1;j++){
					bcnf[j][0]=bcnf[j+1][0];
					bcnf[j][1]=bcnf[j+1][1];
				}
				i--;
				x--;
			}
			else{
				temped3[tempedindex]=lite;
				tempedindex++;
			}
			}
		for(int i=pehle;i<x;i++){
			int ind=0;
			s = bcnf[i][0]+bcnf[i][1];
			for(int j=0;j<s.length();j++){
					char ch = s.charAt(j);
					ind = allchars.indexOf(ch);
					if(ind==-1)
						break;
				}
			if(s.length()>=2 && ind==-1){
			allchars+=s;
			emptyit();
			System.out.println("Candidate Key : ");
			print(bcnf[i][0]);
			emptyit();
			String tempo = sortplease(bcnf[i][0]+bcnf[i][1]);
			String answer = "";
			int ind2=0;
			for(int p=0;p<tempo.length();p++){
				ind2 = answer.indexOf(tempo.charAt(p));
				if(ind2==-1)
					answer+=tempo.charAt(p)+"";
			}
			System.out.print("Relation : ");
			print(answer);
			System.out.println("");
			}
		}
		String last = "";
		for(int i=0;i<full.length();i++){
			char ch = full.charAt(i);
			int ind = allchars.indexOf(ch);
			if(ind==-1){
				last+=ch+"";
			}
		}
		if(last!=""){
		System.out.println("Candidate Key : ");
		print(sortplease(add));
		String tempo = add+last;
		String answer = "";
		int ind=0;
		for(int i=0;i<tempo.length();i++){
			ind = answer.indexOf(tempo.charAt(i));
			if(ind==-1)
				answer+=tempo.charAt(i)+"";
		}
		System.out.print("Relation : ");
		print(sortplease(answer));
		System.out.println("");
		}
		System.out.println("*****End of BCNF*****");
		System.out.println("");
		return;
	}
	public static void doBCNF(String pro[][], int n, String s, String visits[],String bcnf[][]){
		int no=n;
		String temp [][] = new String[n][2];
		for(int i=0;i<n;i++){
			temp[i][1]="";
		}
		int tempindex=0;
		for(int i=0;i<n;i++){	
			String query = pro[i][0];
			temp[tempindex][0]=pro[i][0];
			for(int j=0;j<n;j++){
				if(pro[i][0].equals(pro[j][0])){
					temp[tempindex][1]+=pro[j][1];
				}
			}
			tempindex++;
		}
		// REMOVE DUPLICATES
		String temped[] = new String[n];
		int tempedindex=0;
		n=tempindex;
		for(int i=0;i<n;i++){
			int flag=0;
			String lite = temp[i][0]+","+temp[i][1];
			for(int j=0;j<n;j++){
				if(lite.equals(temped[j])){
					flag=1;
				}
			}
			if(flag==1){
				for(int j=i;j<n-1;j++){
					temp[j][0]=temp[j+1][0];
					temp[j][1]=temp[j+1][1];
				}
				i--;
				n--;
			}
			else{
				temped[tempedindex]=lite;
				tempedindex++;
			}
		}
		//call for bcnf for individual relations
		int ind =0;
		for(int i=0;i<n;i++){
			String ch = temp[i][0];
			String ch2 = temp[i][1];
			toBCNF(pro,ch,ch2,no,visits,bcnf);
		}
	}
	public static void checkBCNF(String pro[][],int n,String s, String visits[], String nons, String allkeys){
		emptyit();
		initiate(s);
		int flag=0;
		clearvisits(visits);
		depindex=0;
		findclosure3(s,n,pro,"--",visits,"XXXX");
		clearvisits(visits);
		for(int xz=1;xz<depindex;xz++){
			String b = depends[xz][1];
			String a = depends[xz][0];
			if(!(a.equals("")) && !(b.equals(""))){
				ans="";
				findclosure2(a,n,pro,"LLL",visits,"XXX");
				ans=sortplease(ans);
				//check superkey
				if(!(ans.equals(s))){
							clearvisits(visits);
							depindex=0;
							emptyit();
							bc=1;
							return;
						}
			}
		}
		emptyit();
		depindex=0;
		clearvisits(visits);
		return;
	}
	public static void check3NF(String pro[][], int n, String s, String visits[], String nons, String allkeys){
		initiate(s);
		int flag=0;
		for(int xo=0;xo<100000;xo++){
			visits[xo]="";
		}
		depindex=0;
		findclosure3(s,n,pro,"--",visits,"XXXX");
		for(int xo=0;xo<100000;xo++){
			visits[xo]="";
		}
		for(int xz=1;xz<depindex;xz++){
			String b = depends[xz][1];
			String a = depends[xz][0];
			if(!(a.equals("")) && !(b.equals(""))){
			ans="";
			findclosure2(a,n,pro,"LLL",visits,"XXX");
			ans=sortplease(ans);
			//check superkey
			if(!(ans.equals(s)) && b.length()==1){
				// check if b is key attribute
				for(int u=0;u<b.length();u++){
					char check = b.charAt(u);
					int ind = allkeys.indexOf(check);
					if(ind==-1){
						for(int xo=0;xo<100000;xo++){
							visits[xo]="";
						}
						depindex=0;
						emptyit();
						third=1;
						return;
					}
				}
			}
			}
		}
		emptyit();
		depindex=0;
		for(int xo=0;xo<100000;xo++){
			visits[xo]="";
		}
		return;
	}
	public static void to2NF(String pro[][],int n,String s,String visits[], String nons){
		initiate(s);
		int flag=0;
		String all = "";
		clearvisits(visits);
		depindex=0;
		findclosure3(s,n,pro,"lOOOOO",visits,"XXXX");
		for(int i=0;i<nons.length();i++){
			flag=0;
			String search = nons.charAt(i)+"";
			for(int xz=1;xz<depindex;xz++){
				flag=0;
				if(search.equals(sortplease(depends[xz][1])) && !(depends[xz][0].equals(""))){
					String u = depends[xz][0];
					u=sortplease(u);
					// depends on nonkey attribute
					for(int j=0;j<nons.length();j++){
						int ind = u.indexOf(nons.charAt(j));
						if(ind!=-1){
							twonf[twonfindex][0]=depends[xz][0];
							twonf[twonfindex][1]=depends[xz][1];
							twonfindex++;
							flag=1;
							break;
						}
					}
					// depends on key attribute but not on full key
					if(flag!=1){
						int karma=0;
						for(int z =0;z<candindex;z++){
							if(u.equals(candidates[z])){
								karma=1;
							}
						}
						if(karma!=1){
							twonf[twonfindex][0]=depends[xz][0];
							twonf[twonfindex][1]=depends[xz][1];
							twonfindex++;
							flag=1;
							break;
						}
					}
					if(flag==1){
						break;
					}
				}
			}
		}
		emptyit();
		System.out.println("*****Starting 2NF***** ");
		System.out.println("");
		for(int i=0;i<twonfindex;i++){
			all+=twonf[i][0]+twonf[i][1];
			System.out.println("Candidate Key : ");
			if(!twonf[i][0].equals(""))
				print(twonf[i][0]);
			else
				print(twonf[i][1]);
			System.out.print("Relation : ");
			print(twonf[i][0]+twonf[i][1]);
			System.out.println("");
		}
		String remaining = "";
		for(int i=0;i<s.length();i++){
			int ind = all.indexOf(s.charAt(i));
			if(ind==-1){
				remaining+=s.charAt(i);
			}
		}
		if(!(remaining.equals(""))){
			System.out.println("Candidate KEY : ");
			initiate(remaining);
			for(int i=0;i<combindex;i++){
				flag=0;
				for(int j=0;j<candindex;j++){
					if(candidates[j].equals(combs[i])){
						print(candidates[j]);
						flag=1;
						break;
					}
				}
				if(flag==1)
					break;
			}
			if(flag==0){
				print(remaining);
			}
			System.out.print("Relation : ");
			print(remaining);
			emptyit();
		}
		System.out.println("");
		System.out.println("*****End of 2NF*****");
		System.out.println("");
		return;
	}
	public static void to3NF(String pro[][], int n, String s, String visits[],String bcnf[][]){
		int no=n;
		String temp [][] = new String[n][2];
		for(int i=0;i<n;i++){
			temp[i][1]="";
		}
		int tempindex=0;
		for(int i=0;i<n;i++){	
			String query = pro[i][0];
			temp[tempindex][0]=pro[i][0];
			for(int j=0;j<n;j++){
				if(pro[i][0].equals(pro[j][0])){
					temp[tempindex][1]+=pro[j][1];
				}
			}
			tempindex++;
		}
		// REMOVE DUPLICATES
		String temped[] = new String[n];
		int tempedindex=0;
		n=tempindex;
		for(int i=0;i<n;i++){
			int flag=0;
			String lite = temp[i][0]+","+temp[i][1];
			for(int j=0;j<n;j++){
				if(lite.equals(temped[j])){
					flag=1;
				}
			}
			if(flag==1){
				for(int j=i;j<n-1;j++){
					temp[j][0]=temp[j+1][0];
					temp[j][1]=temp[j+1][1];
				}
				i--;
				n--;
			}
			else{
				temped[tempedindex]=lite;
				tempedindex++;
			}
		}
		System.out.println("*****Starting 3NF*****");
		System.out.println("");
		for(int i=0;i<n;i++){
			s = temp[i][0]+temp[i][1];
			initiate(s);
			System.out.println("Candidate Key : ");
			print(temp[i][0]);
			System.out.print("Relation : ");
			print(sortplease(temp[i][0]+temp[i][1]));
			System.out.println("");
			emptyit();
		}
		//call for bcnf for individual relations
		int ind =0;
		for(int i=0;i<n;i++){
			String ch = temp[i][0];
			String ch2 = temp[i][1];
			toBCNF(pro,ch,ch2,no,visits,bcnf);
		}
		System.out.println("*****End of 3NF*****");
		System.out.println("");
	}
	public static void toBCNF(String pro[][],String a, String b, int n,String visits[], String bcnf[][]){
		String temp [] = new String[n];
		int tempindex = 0;
		int count=0;
		for(int i=0;i<n;i++){
			temp[i]="";
		}
		initiate(a+b);
		String full=sortplease(a+b);
		String superkeys [] = new String [100000];
		int supindex = 0;
		for(int i=0;i<combindex;i++){
			int flag=0;
			String checkkey = combs[i];
			clearvisits(visits);
			ans="";
			findclosure2(checkkey,n,pro,"XXXXXXX",visits,"LOLLLLLLLL");
			ans=sortplease(ans);
			for(int j=0;j<full.length();j++){
				flag=0;
					for(int k=0;k<ans.length();k++){
						if(full.charAt(j)==ans.charAt(k))
							flag=1;
					}
					if(flag!=1)
						break;
			}
			if(flag==1){
				superkeys[supindex]=checkkey;
				supindex++;
			}
		}
		emptyit();
		clearvisits(visits);
		ans="";
		int flag;
		findclosure3(full,n,pro,"LOOOL",visits,"XX");
		for(int i=1;i<depindex;i++){
			flag=0;
			String xd = sortplease(depends[i][0]);
			for(int j=0;j<supindex;j++){
				if(xd.equals(sortplease(superkeys[j]))){
					flag=1;
				}
			}
			if(flag!=1 && depends[i][1].length()==1 && !(xd.equals(sortplease(depends[i][1])))){
				//split
				String alpha = xd;
				String beta = depends[i][1];
				int ind = full.indexOf(beta.charAt(0));
				String final1 = full;
				if(ind!=-1){
				if(full.length()!=1)
					final1 = charRemoveAt(full,ind);
				bcnf[bcnfindex][0] = final1;
				bcnf[bcnfindex][1] = "";
				bcnfindex++;
				bcnf[bcnfindex][0] = alpha;
				bcnf[bcnfindex][1] = beta;
				bcnfindex++;
				count = 1;
				}
			}
		}
		emptyit();
		return;
	}
	public static void findclosure2(String test, int n, String pro[][], String r,String visits[],String find){
		hell=test;
		initiate2(test);
		for(int i=0;i<n;i++){
			for(int j=0;j<combindex2;j++){
				if(checkequals(combs2[j],pro[i][0])){
					ans+=pro[i][1];
					hell+=pro[i][1];
				}
			}
		}	
		for(int j=0;j<combindex2;j++){
				closure2(combs2[j],pro,n,(int)r.charAt(0),visits,0,find,"XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				if(wow==1)
					break;
				clearvisits(visits);
		}
		emptyit2();
		ans=sortplease(ans);
		if(!(ans.equals(every))){
		String temp3="";
		int visited[]= new int[200];
		for(int i=0;i<200;i++)
			visited[i]=0;
		for(int i=0;i<ans.length();i++){
			if(visited[(int)ans.charAt(i)]==0){
				temp3+=ans.charAt(i);
				visited[(int)ans.charAt(i)]=1;
			}
		}
		ans=temp3;
		clearvisits(visits);
		hell=ans;
		initiate2(ans);
		for(int i=0;i<n;i++){
			for(int j=0;j<combindex2;j++){
				if(checkequals(combs2[j],pro[i][0])){
					ans+=pro[i][1];
					hell+=pro[i][1];
				}
			}
		}	
		for(int j=0;j<combindex2;j++){
				closure2(combs2[j],pro,n,(int)r.charAt(0),visits,0,find,"XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				for(int xo=0;xo<100000;xo++){
					visits[xo]="";
				}
				if(wow==1)
					break;
				clearvisits(visits);
		}
		clearvisits(visits);
		}
		// remove duplicates
		String temp3="";
		int visited[]= new int[200];
		for(int i=0;i<200;i++)
			visited[i]=0;
		for(int i=0;i<ans.length();i++){
			if(visited[(int)ans.charAt(i)]==0){
				temp3+=ans.charAt(i);
				visited[(int)ans.charAt(i)]=1;
			}
		}
		ans=temp3;
		emptyit2();
	}
	public static void findclosure3(String test, int n, String pro[][], String r,String visits[],String find){
		hell=test;
		initiate2(test);
		for(int i=0;i<n;i++){
			for(int j=0;j<combindex2;j++){
				if(checkequals(combs2[j],pro[i][0])){
					ans+=pro[i][1];
					hell+=pro[i][1];
				}
			}
		}		
		for(int j=0;j<combindex2;j++){
				closure3("",combs2[j],pro,n,(int)r.charAt(0),visits,0,find,"");
				for(int xo=0;xo<100000;xo++){
					visits[xo]="";
				}
		}
		emptyit2();
		ans=sortplease(ans);
		String temp3="";
		int visited[]= new int[200];
		for(int i=0;i<200;i++)
			visited[i]=0;
		for(int i=0;i<ans.length();i++){
			if(visited[(int)ans.charAt(i)]==0){
				temp3+=ans.charAt(i);
				visited[(int)ans.charAt(i)]=1;
			}
		}
		ans=temp3;
		clearvisits(visits);
		hell=ans;
		initiate2(ans);
		for(int i=0;i<n;i++){
			for(int j=0;j<combindex2;j++){
				if(checkequals(combs2[j],pro[i][0])){
					ans+=pro[i][1];
					hell+=pro[i][1];
				}
			}
		}	
		for(int j=0;j<combindex2;j++){
				closure3("",combs2[j],pro,n,(int)r.charAt(0),visits,0,find,"");
				clearvisits(visits);
		}
		clearvisits(visits);
		// remove duplicates
		temp3="";
		for(int i=0;i<200;i++)
			visited[i]=0;
		for(int i=0;i<ans.length();i++){
			if(visited[(int)ans.charAt(i)]==0){
				temp3+=ans.charAt(i);
				visited[(int)ans.charAt(i)]=1;
			}
		}
		ans=temp3;
		emptyit2();
	}
	public static void findclosure(String test, int n, String pro[][], String r,String visits[]){
		hell=test;
		initiate2(test);
		for(int i=0;i<n;i++){
			for(int j=0;j<combindex2;j++){
				if(checkequals(combs2[j],pro[i][0])){
					ans+=pro[i][1];
					hell+=pro[i][1];
				}
			}
		}	
		for(int j=0;j<combindex2;j++){
				wow=0;
				closure2(combs2[j],pro,n,(int)r.charAt(0),visits,0,"XXXXXXXX","LLLL");
				clearvisits(visits);
				wow=0;
		}
		emptyit2();
		ans=sortplease(ans);
		String temp3="";
		int visited[]= new int[200];
		for(int i=0;i<200;i++)
			visited[i]=0;
		for(int i=0;i<ans.length();i++){
			if(visited[(int)ans.charAt(i)]==0){
				temp3+=ans.charAt(i);
				visited[(int)ans.charAt(i)]=1;
			}
		}
		ans=temp3;
		clearvisits(visits);
		hell=ans;
		initiate2(ans);
		for(int i=0;i<n;i++){
			for(int j=0;j<combindex2;j++){
				if(checkequals(combs2[j],pro[i][0])){
					ans+=pro[i][1];
					hell+=pro[i][1];
				}
			}
		}	
		for(int j=0;j<combindex2;j++){
				closure2(combs2[j],pro,n,(int)r.charAt(0),visits,0,"llllllllllll","XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				clearvisits(visits);
				
		}
		clearvisits(visits);
		// remove duplicates
		temp3="";
		for(int i=0;i<200;i++)
			visited[i]=0;
		for(int i=0;i<ans.length();i++){
			if(visited[(int)ans.charAt(i)]==0){
				temp3+=ans.charAt(i);
				visited[(int)ans.charAt(i)]=1;
			}
		}
		ans=temp3;
		ans=sortplease(ans);
		emptyit2();	
	}
	public static void closure2(String attribute, String pro[][],int n,int u, String visited[], int index, String find, String query){
		if(wow==1)
			return;
		if(query.equals(find)){
			wow=1;
			return;
		}
		for(int i=0;i<index;i++){
			if(checkequals(visited[i],query))
				return;
		}
		index++;
		visited[index]=query;
		int flag=0;
		ans+=attribute;
		for(int i=0;i<attribute.length();i++){
			for(int j=0;j<hell.length();j++){
				if(attribute.charAt(i)==hell.charAt(j)){
					flag=1;
				}
			}
			if(flag==0){
				hell+=attribute.charAt(i);
			}
			flag=0;
		}
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
				closure2(sortplease(pro[i][1]),pro,n,u,visited,index,find,sortplease(pro[i][0])+","+sortplease(pro[i][1]));
			}
		}
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
				String y = pro[i][1];
				String x = pro[i][0];
				// x - > y
				for(int k=0;k<hell.length();k++){
					String letter = hell.charAt(k)+"";
					if(!(checkequals(x,letter) || checkequals(y,letter))){
						closure2(sortplease(y+letter),pro,n,u,visited,index,find,sortplease(x+letter)+","+sortplease(y+letter));
					}
				}
			}
		}
		//IR3 Jalaj
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
               String y=pro[i][1];
			   for(int j=0;j<n;j++){
				if(i!=j&& checkequals(pro[j][0],y)){
                       if(!(checkequals(pro[j][1],pro[i][0]))){
                           closure2(sortplease(pro[j][1]),pro,n,u,visited,index,find,sortplease(pro[i][0])+","+sortplease(pro[j][1]));
                       	}
			    	}
				}
			}
		}
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
				String a = pro[i][1];
				for(int j=0;j<n;j++){
					if(i!=j){
						if(checkequals(attribute,pro[j][0])){
							String b = pro[j][1];
							closure2(sortplease(a+b),pro,n,u,visited,index,find,sortplease(pro[i][0])+","+a+b);
						}
					}
				}
			}
		}
		return;
	}
	public static void closure(String attribute, String pro[][],int n,int u, String visited[], int index){
		// initialize empty string (globally before main method) and keep adding attributes which can be derived from given attribute using IRs.
		for(int i=0;i<index;i++){
			if(checkequals(visited[i],attribute))
				return;
		}
		index++;
		visited[index]=attribute;
		int flag=0;
		ans+=attribute;
		for(int i=0;i<attribute.length();i++){
			for(int j=0;j<hell.length();j++){
				if(attribute.charAt(i)==hell.charAt(j)){
					flag=1;
				}
			}
			if(flag==0){
				hell+=attribute.charAt(i);
			}
			flag=0;
		}
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
				closure(sortplease(pro[i][1]),pro,n,u,visited,index);
			}
		}
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
				String y = pro[i][1];
				String x = pro[i][0];
				// x - > y
				for(int k=0;k<hell.length();k++){
					String letter = hell.charAt(k)+"";
					if(!(checkequals(x,letter) || checkequals(y,letter))){
						closure(sortplease(y+letter),pro,n,u,visited,index);
					}
				}
			}
		}
		//IR3 Jalaj
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
               String y=pro[i][1];
			   for(int j=0;j<n;j++){
				if(i!=j&& checkequals(pro[j][0],y)){
                       if(!(checkequals(pro[j][1],pro[i][0]))){
                           closure(sortplease(pro[j][1]),pro,n,u,visited,index);
                       	}
			    	}
				}
			}
		}
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
				String a = pro[i][1];
				for(int j=0;j<n;j++){
					if(i!=j){
						if(checkequals(attribute,pro[j][0])){
							String b = pro[j][1];
							closure(sortplease(a+b),pro,n,u,visited,index);
						}
					}
				}
			}
		}
		return;
	}

	public static void closure3(String before, String attribute, String pro[][],int n,int u, String visited[], int index, String find, String query){
		// initialize empty string (globally before main method) and keep adding attributes which can be derived from given attribute using IRs.
		for(int i=0;i<index;i++){
			if(checkequals(visited[i],attribute))
				return;
		}
		index++;
		visited[index]=attribute;
		depends[depindex][1]=attribute;
		depends[depindex][0]=before;
		depindex++;
		int flag=0;
		ans+=attribute;
		for(int i=0;i<attribute.length();i++){
			for(int j=0;j<hell.length();j++){
				if(attribute.charAt(i)==hell.charAt(j)){
					flag=1;
				}
			}
			if(flag==0){
				hell+=attribute.charAt(i);
			}
			flag=0;
		}
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
				closure3(pro[i][0],pro[i][1],pro,n,u,visited,index,find,sortplease(pro[i][0])+","+sortplease(pro[i][1]));
			}
		}
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
				String y = pro[i][1];
				String x = pro[i][0];
				// x - > y
				for(int k=0;k<hell.length();k++){
					String letter = hell.charAt(k)+"";
					if(!(checkequals(x,letter) || checkequals(y,letter))){
						closure3(sortplease(x+letter),sortplease(y+letter),pro,n,u,visited,index,find,sortplease(x+letter)+","+sortplease(y+letter));
					}
				}
			}
		}
		//IR3 Jalaj
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
               String y=pro[i][1];
			   for(int j=0;j<n;j++){
				if(i!=j&& checkequals(pro[j][0],y)){
                       if(!(checkequals(pro[j][1],pro[i][0]))){
                           closure3(pro[i][0],pro[j][1],pro,n,u,visited,index,find,sortplease(pro[i][0])+","+sortplease(pro[j][1]));
                       	}
			    	}
				}
			}
		}
		for(int i=0;i<n;i++){
			if(checkequals(attribute,pro[i][0])){
				String a = pro[i][1];
				for(int j=0;j<n;j++){
					if(i!=j){
						if(checkequals(attribute,pro[j][0])){
							String b = pro[j][1];
							closure3(pro[i][0],sortplease(a+b),pro,n,u,visited,index,find,sortplease(pro[i][0])+","+a+b);
						}
					}
				}
			}
		}
		return;
	}
	public static void check2NF(String pro[][],int n,String s,String visits[], String nons){
		initiate(s);
		int flag=0;
		String all = "";
		clearvisits(visits);
		depindex=0;
		findclosure3(s,n,pro,"lOOOOO",visits,"XXXX");
		for(int i=0;i<nons.length();i++){
			flag=0;
			String search = nons.charAt(i)+"";
			for(int xz=1;xz<depindex;xz++){
				flag=0;
				if(search.equals(sortplease(depends[xz][1])) && !(depends[xz][0].equals(""))){
					String u = depends[xz][0];
					u=sortplease(u);
					// depends on nonkey attribute
					for(int j=0;j<nons.length();j++){
						int ind = u.indexOf(nons.charAt(j));
						if(ind!=-1){
							emptyit();
							depindex=0;
							second=1;
							twonfindex=0;
							return;
						}
					}
					// depends on key attribute but not on full key
					if(flag!=1){
						int karma=0;
						for(int z =0;z<candindex;z++){
							if(u.equals(candidates[z])){
								karma=1;
							}
						}
						if(karma!=1){
							emptyit();
							depindex=0;
							second=1;
							twonfindex=0;
							return;
						}
					}
					if(flag==1){
						break;
					}
				}
			}
		}
		emptyit();
		depindex=0;
		twonfindex=0;
		clearvisits(visits);
		return;
	}
	static void initiate(String s)
 	{
    	combo("", s);
  	}
  	static void combo(String prefix, String s)
    {	
    	int N = s.length();
    	combs[combindex]=prefix;
    	combindex++;
    	for (int i = 0 ; i < N ; i++)
      		combo(prefix + s.charAt(i), s.substring(i+1));
  	}
  	static void initiate2(String s)
 	{
    	combo2("", s);
  	}
  	static void combo2(String prefix, String s)
    {	
    	int N = s.length();
    	combs2[combindex2]=prefix;
    	combindex2++;
    	for (int i = 0 ; i < N ; i++)
      		combo2(prefix + s.charAt(i), s.substring(i+1));
  	}
  	public static String charRemoveAt(String str, int p) {  
        return str.substring(0, p) + str.substring(p + 1);  
    }  
} 