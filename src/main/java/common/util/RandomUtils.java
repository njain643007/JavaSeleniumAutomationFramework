package common.util;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import com.github.javafaker.Faker;

public class RandomUtils {

	public RandomUtils() {

	}

	private static Faker faker = new Faker(new Locale("EN", "INDIA"));

	private static final SecureRandom random = new SecureRandom();

	public static String generateRandomString(int length) {

		String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(text.charAt(random.nextInt(text.length())));
		return sb.toString();

	}

	public static String generateRandomNumericString(int length) {
		String textnumber = "123456789";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(textnumber.charAt(random.nextInt(textnumber.length())));
		return sb.toString();

	}

	public static int generatRandomPositiveNegitiveValue(int max, int min) {
		int ii = -min + (int) (Math.random() * ((max - (-min)) + 1));
		return ii;
	}

	public static Object getRandomElementFromList(List<Object> l) {
		Random rand = new Random();
		Object randomElement = l.get(rand.nextInt(l.size()));
		return randomElement;
	}

	public static String getRandomAddress() {
		String address = faker.address().fullAddress();
		return address;
	}

	public static String getRandomCountry() {
		String country = faker.address().country();
		return country;
	}

	public static String getRandomCity() {
		String city = faker.address().city();
		return city;
	}

	
	public static String getRandomFirstName() {
		String first_name = faker.name().firstName();
		while(first_name.length()<3) {
			first_name = faker.name().firstName();
		}
		first_name = first_name.replaceAll("'", "");
		return first_name;
	}

	public static String getRandomLastName() {
		String last_name = faker.name().lastName();
		while(last_name.length()<3) {
			last_name = faker.name().lastName();
		}
		last_name = last_name.replaceAll("'", "");
		return last_name;
	}

	public static String getRandomFullName() {
		String full_name = getRandomFirstName() + " " + getRandomLastName();
		if(isContainSpecialChar(full_name)){
			getRandomLastName();
		}
		full_name = full_name.replace("'", "");

		return full_name;
	}

	public static String getRandomPhoneNumber() {
		int getRandomValue = (int) (Math.random() * (9 - 6)) + 6;
		int randonNum = (int) ((Math.random() * (999999999 - 100000000)) + 100000000);
		String phoneNumber = getRandomValue + "" + randonNum;
		return phoneNumber;
	}

	public static int getRandomNumberBetween(int max, int min) {
		int getRandomValue = (int) (Math.random() * (max - min)) + min;
		return getRandomValue;
	}

	public static void main(String[] args) {
		System.out.println(isContainSpecialChar("O'Kon"));
		
	}
	
	
	public static boolean isContainSpecialChar(String str) {
		boolean isContain = false;
		String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
		for (int i=0; i < str.length() ; i++)
        {
            char ch = str.charAt(i);
            if(specialCharactersString.contains(Character.toString(ch))) {
            	isContain = true;
                break;
            }    
            else {
            	isContain = false;
            }
        }
		return isContain;
	}

	public static String getRandomEmail() {
		return faker.internet().emailAddress();
	}
	
	public static String getNewuuid() {
		return UUID.randomUUID().toString();
	}
}
