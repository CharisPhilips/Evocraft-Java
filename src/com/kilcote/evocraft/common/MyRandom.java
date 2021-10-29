package com.kilcote.evocraft.common;

import java.util.Random;

public class MyRandom extends Random {

	private static final long serialVersionUID = 973822238617821724L;
	
	public int nextInt(int min, int max) {
		if (max == min) {
			return min;
		}
		return this.nextInt(max - min) + min;
	}

}
