package com.py.tools;

public class BitUtil {

	//统计32位类型中含1的位数
	public static int ones(int x) {
		x -= ((x >> 1) & 0x55555555);
		x  = (((x >> 2) & 0x33333333) + (x & 0x33333333));
		x  = (((x >> 4) + x) & 0x0f0f0f0f);
		x += (x >> 8);
		x += (x >> 16);
		return x &= 0x003f;
	}
	
	public static int BitCount5(int n) 
	{
	    int tmp = n - ((n >>1) &033333333333) - ((n >>2) &011111111111);
	    return ((tmp + (tmp >>3)) &030707070707) %63;
	}

	public static void main(String[] args) {
//		int n = Integer.MIN_VALUE;
//		while (true) {
//			if (ones(n) != ccc(n)) {
//				System.out.println("error n:" + n);
//			}
//			if (n == Integer.MAX_VALUE) {
//				break;
//			}
//			n++;
//		}
//		System.out.println("success");
		long count = 0;
		long a1 = System.currentTimeMillis();
		int n1 = 0;
		while (true) {
			count += ones(n1);
			if (n1 == 100000000) {
				break;
			}
			n1++;
		}
		long b1 = System.currentTimeMillis();
		System.out.println("ones:" + (b1 - a1));
		System.out.println(count);
		count = 0;
		long a2 = System.currentTimeMillis();
		int n2 = 0;
		while (true) {
			count += ccc(n2);
			if (n2 == 100000000) {
					break;
			}
			n2++;
		}
		long b2 = System.currentTimeMillis();
		System.out.println("ccc:" + (b2 - a2));
		System.out.println(count);
		count = 0;
		long a3 = System.currentTimeMillis();
		int n3 = 0;
		while (true) {
			count += ttt(n3);
			if (n3 == 100000000) {
				break;
			}
			n3++;
		}
		long b3 = System.currentTimeMillis();
		System.out.println("ttt:" + (b3 - a3));
		System.out.println(count);
	}
	
	public static int ccc(byte n) {
        n = (byte) ((n & 0x55) + ((n >> 1) & 0x55));
        n = (byte) ((n & 0x33) + ((n >> 2) & 0x33));
        n = (byte) ((n & 0x0f) + ((n >> 4) & 0x0f));
        return n;
	}
	
	public static int ccc(short n) {
        n = (short) ((n & 0x5555) + ((n >> 1) & 0x5555));
        n = (short) ((n & 0x3333) + ((n >> 2) & 0x3333));
        n = (short) ((n & 0x0f0f) + ((n >> 4) & 0x0f0f));
        n = (short) ((n & 0x00ff) + ((n >> 8) & 0x00ff));
        return n;
	}
	
	public static int ccc(long n) {
        n = (n & 0x5555555555555555L) + ((n >> 1) & 0x5555555555555555L);
        n = (n & 0x3333333333333333L) + ((n >> 2) & 0x3333333333333333L);
        n = (n & 0x0f0f0f0f0f0f0f0fL) + ((n >> 4) & 0x0f0f0f0f0f0f0f0fL);
        n = (n & 0x00ff00ff00ff00ffL) + ((n >> 8) & 0x00ff00ff00ff00ffL);
        n = (n & 0x0000ffff0000ffffL) + ((n >> 16) & 0x0000ffff0000ffffL);
        n = (n & 0x00000000ffffffffL) + ((n >> 32) & 0x00000000ffffffffL);
        return (int)n;
	}
	
	public static int ccc(int n) {
        n = (n & 0x55555555) + ((n >> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
        n = (n & 0x0f0f0f0f) + ((n >> 4) & 0x0f0f0f0f);
        n = (n & 0x00ff00ff) + ((n >> 8) & 0x00ff00ff);
        n = (n & 0x0000ffff) + ((n >> 16) & 0x0000ffff);
        return n;
	}
	
    public static int ttt(int k) {
    	int count=0;
		while(k > 0){  
	        k=k&(k-1);  
	        count++;  
		}  
		return count;
    }

    public static int ttt(byte k) {
    	int count=0;
		while(k > 0){  
	        k=(byte) (k&(k-(byte)1));  
	        count++;  
		}  
		return count;
    }

    public static int ttt(short k) {
    	int count=0;
		while(k > 0){  
	        k=(short) (k&(k-(short)1));  
	        count++;  
		}  
		return count;
    }

    public static int ttt(long k) {
    	int count=0;
		while(k > 0){  
	        k=k&(k-1);  
	        count++;  
		}  
		return count;
    }

}
