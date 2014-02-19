import java.util.Arrays;

public class SymbolTable {
	int intArray[] = new int[512];
	char charArray[] = new char[4096];
	int intLastSlot1 = -1;
	int intFreeSlot2 = 0;
	int hash = 0;
	
	public SymbolTable(){
		Arrays.fill(charArray,'~');
		Arrays.fill(intArray, -1);		
	}
	public int Method1(char c){
	
		int prime[] = { 7507, 7517, 7523, 7529, 7537, 7541, 7547, 7549, 7559,
				7561, 7573, 7577, 7583, 7589, 7591, 7603, 7607, 7621, 7639,
				7643, 7649, 7669, 7673, 7681, 7687, 7691, 7699, 7703, 7717,
				7723, 7727, 7741, 7753, 7757, 7759, 7789, 7793 };

		charArray[intFreeSlot2] = c;
		intFreeSlot2 ++;
		int p = intFreeSlot2-intLastSlot1-2;
		int hashcode = c * prime[p % prime.length];
		hash += hashcode;
		return hashcode;
		
	}
	
	public int Method2(){
		
		int pos = hash;
		pos = pos % 512;
		hash = 0;
		
		if(intArray[pos]== -1){
			intArray[pos] = intLastSlot1 + 1;
			intLastSlot1 = intFreeSlot2;
			intFreeSlot2 += 1;
		}

		else if(intArray[pos] != -1){
			int size = intFreeSlot2 - intLastSlot1 - 1;
			boolean flag = true;
			for(int x = 0; x<=size;x++){
				if(charArray[intArray[pos] + x] != charArray[intLastSlot1 + x]){
					flag = false;
				}
			}
			if(flag==true){
				intFreeSlot2=intLastSlot1+1;
			}
			else{
				intArray[CollisionHandling(pos)] = intLastSlot1 + 1;
				intLastSlot1 = intFreeSlot2;
				intFreeSlot2 += 1;
			}
			
		}
		
		return pos;
	}
	
	public int CollisionHandling(int pos){
		int x = 0;
		
		
		
		while(intArray[pos + x] != -1){
			x++;
		}
		return pos + x;
	}
}
