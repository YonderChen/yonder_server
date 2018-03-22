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
	
	public static int ones(byte x) {
		x -= ((x >> 1) & 0x55);
		x  = (byte)(((x >> 2) & 0x33) + (x & 0x33));
		return (byte)(((x >> 4) + x) & 0x0f);
	}
	
	public static int ones(short x) {
		x -= ((x >> 1) & 0x5555);
		x  = (short)(((x >> 2) & 0x3333) + (x & 0x3333));
		x  = (short)(((x >> 4) + x) & 0x0f0f);
		x += (x >> 8);
		return x &= 0x001f;
	}
	
	public static long ones(long x) {
		x -= ((x >> 1) & 0x5555555555555555L);
		x  = (((x >> 2) & 0x3333333333333333L) + (x & 0x3333333333333333L));
		x  = (((x >> 4) + x) & 0x0f0f0f0f0f0f0f0fL);
		x  = (((x >> 8) + x) & 0x00ff00ff00ff00ffL);
		x += (x >> 16);
		x += (x >> 32);
		return x &= 0x007f;
	}
	
	public static int ccc(int n) {
        n = (n & 0x55555555) + ((n >> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
        n = (n & 0x0f0f0f0f) + ((n >> 4) & 0x0f0f0f0f);
        n = (n & 0x00ff00ff) + ((n >> 8) & 0x00ff00ff);
        n = (n & 0x0000ffff) + ((n >> 16) & 0x0000ffff);
        return n;
	}

	public static void main(String[] args) {
		long initN = Long.MAX_VALUE - 156151215;
		long maxN = Long.MAX_VALUE;
		long n = initN;
		long maxWileCount = 1;
		long a = System.currentTimeMillis();
		long count = 0;
		long whileCount = 0;
		while (true) {
			count += ones(n);
			if (n == maxN) {
				whileCount++;
				if (whileCount >= maxWileCount) {
					break;
				}
			}
			n++;
		}
		long b = System.currentTimeMillis();
		System.out.println("ones:" + (b - a));
		System.out.println(count);
		count = 0;
		a = System.currentTimeMillis();
		n = initN;
		whileCount = 0;
		while (true) {
			count += uuu(n);
			if (n == maxN) {
				whileCount++;
				if (whileCount >= maxWileCount) {
					break;
				}
			}
			n++;
		}
		b = System.currentTimeMillis();
		System.out.println("ccc:" + (b - a));
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
	
    public static int ttt(int k) {
    	int count=0;
    	if (k == 0) {
			return count;
		}
		while(k != 0){  
	        k=k&(k-1);  
	        count++;  
		}
		return count;
    }

    public static int ttt(byte k) {
    	int count=0;
    	if (k == 0) {
			return count;
		}
		while(k != 0){  
	        k=(byte) (k&(k-1));  
	        count++;  
		}  
		return count;
    }

    public static int ttt(short k) {
    	int count=0;
    	if (k == 0) {
			return count;
		}
		while(k != 0){  
	        k=(short) (k&(k-1));  
	        count++;  
		}  
		return count;
    }

    public static int ttt(long k) {
    	int count=0;
    	if (k == 0) {
			return count;
		}
		while(k != 0){  
	        k=k&(k-1);  
	        count++;  
		}  
		return count;
    }

    public static int uuu(byte k) {
    	int count=0;
    	if (k == 0) {
			return count;
		}
    	if (k < 0) {
			k = (byte) (k & 0x7f);
			count++;
		}
    	if (k > 0) {
    		while(k != 0){
    			count += k&1;
    	        k=(byte) (k>>1);
    		}  
		}
		return count;
    }

    public static int uuu(short k) {
    	int count=0;
    	if (k == 0) {
			return count;
		}
    	if (k < 0) {
			k = (short) (k & 0x7fff);
			count++;
		}
    	if (k > 0) {
    		while(k != 0){
    			count += k&1;
    	        k=(short) (k>>1);
    		}  
		}
		return count;
    }

    public static int uuu(int k) {
    	int count=0;
    	if (k == 0) {
			return count;
		}
    	if (k < 0) {
			k = k & 0x7fffffff;
			count++;
		}
    	if (k > 0) {
    		while(k != 0){
    			count += k&1;
    	        k=k>>1;
    		}  
		}
		return count;
    }

    public static int uuu(long k) {
    	int count=0;
    	if (k == 0) {
			return count;
		}
    	if (k < 0) {
			k = k & 0x7fffffffffffffffL;
			count++;
		}
    	if (k > 0) {
    		while(k != 0){
    			count += k&1;
    	        k=k>>1;
    		}  
		}
		return count;
    }

}
