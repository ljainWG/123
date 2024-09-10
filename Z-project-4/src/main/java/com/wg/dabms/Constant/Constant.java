package com.wg.dabms.Constant;

public class Constant {
	
	public static final String launchingMenu = """
			1. LogIn
			2. Create new Account
			3. Exit
			""";
	public final static int launchingMenuSize = 3;
	
	public final static String patientMenu = """
			1. Book Appointment
			2. update appointment
			3. view appointment
			4 .view prescription
			5. delete account
			6. logout
			7. exit
			""";
	public final static int patientMenuSize = 7;
	public final static String doctorMenu = """
			1. View Appointments
			2. Cancel Appointment
			3. create prescription
			4. view prescription
			5. update prescription
			6. update profile
			7. view profile
			8. Logout
			9. exit
			""";
	public final static int doctorMenuSize = 9;
	public final static String adminMenu = """
			1. book appointment
			2. update appointment
			3. view appointment
			4. delete appointment
			5. view prescription
			6. update prescription
			7. delete prescription
			8. create other users
			9. delete other users
			10. update other users
			11. view my profile
			12. logout
			13. exit
			""";
	public final static int adminMenuSize = 13;
}
