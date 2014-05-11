package main.java.info.jtrac.util;

public abstract class MappingUtil {
	/**
	 * method will map a Long to a String
	 * @param lng as long
	 * @return result as String
	 */
	public static String mapLongToString(long object){
		String mapped = "";
		try{
			mapped = Long.toString(object);
		}catch(Exception nFe){
		}
		return mapped;
	}
	/**
	 * Method will map a String to a Long
	 * @param object as String 
	 * @return result as long 
	 */
	public static long mapStringToLong(String object){
		long mapped = 0;
		try{
			mapped = Long.parseLong(object);
		}catch(Exception nFe){
		}
		return mapped;
	}
	/**
	 * Method will parse a String to a Integer
	 * @param object as String
	 * @return result as Integer
	 */
	public static int parseInt(String object){
		int result = 0;
		try{
			if(null!= object){
				result = Integer.parseInt(object);
			}
		}catch(NumberFormatException nXe){
		}
		return result;
	}
	/**
	 * Method will parse a Integer to a String
	 * @param object as Integer
	 * @return result as String
	 */
	public static String parseInt(int object){
		return  Integer.toString(object);
	}
	/**
	 * Method will map a String to a Double
	 * @param value as String
	 * @return Double as result
	 */
	public static Double mapStringToDouble(String value) {
		Double result = 0.00;
		if(null != value){
			result = Double.parseDouble(value);
		}
		return result;
	}
	/**
	 * Method will map a Double to a String
	 * @param value as Double
	 * @return String as result
	 */
	public static String mapDoubleToString(Double value) {
		String result = "";
		if(null != value){
			result = Double.toString(value);
		}
		return result;
	}
}
