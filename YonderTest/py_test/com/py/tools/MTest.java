package com.py.tools;

public class MTest {


	public static int ones(int x) {
		x -= ((x >> 1) & 0x55555555);
		x  = (((x >> 2) & 0x33333333) + (x & 0x33333333));
		x  = (((x >> 4) + x) & 0x0f0f0f0f);
		x += (x >> 8);
		x += (x >> 16);
		return x &= 0x003f;
	}
	
	public static int cccc(int n) {
        n = (n & 0x55555555) + ((n >> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
        n = (n & 0x0f0f0f0f) + ((n >> 4) & 0x0f0f0f0f);
        n = (n & 0x00ff00ff) + ((n >> 8) & 0x00ff00ff);
        n = (n & 0x0000ffff) + ((n >> 16) & 0x0000ffff);
        return n;
	}
	
	public static int BitCount5(int n) 
	{
	    int tmp = n - ((n >>1) &033333333333) - ((n >>2) &011111111111);
	    return ((tmp + (tmp >>3)) &030707070707) %63;
	}
	
	public static void main(String[] args) {
		System.out.println(ones(1000));
		System.out.println(cccc(1000));
	}
}
