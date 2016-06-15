package com.yonder.groovy.test

class Main {

	public static void main(String[] args) {
		def number1 = 100
		def number2 = 100
		def eagerGString = "${ def test = {num1, num2 -> return num1 * num2}; test(number1, number2); }"
		def lazyGString = { num1, num2 -> 
			return num1 + num2
		}
		
		println eagerGString
		println lazyGString.call(number1, number2)
		
//		assert eagerGString == "1"
//		assert lazyGString.call(number, 2) == 13
		
		number1 = 10
		number2 = 10
		def val = number2
		println eagerGString
		println lazyGString.call(number1, number2);
		
		eagerGString = "${ def test = {num1, num2 -> return num1 * num2}; test(number1, number2); }"
		lazyGString = { num1, num2 ->
			return num1 + num2
		}
		println eagerGString
		println lazyGString.call(number1, number2);
//		assert eagerGString == "1"
//		assert lazyGString.call(number, 1) == 11
			
		def scanner = new Scanner(System.in)
		eagerGString = scanner.nextLine()
		println eagerGString.call()
		println eagerGString
	}
	
	def static reverse(string) {
		if(string.length() == 0) {
			return string;
		} else {
			return reverse(string.substring(1, string.length())) + string.substring(0, 1);
		}
	}
}
