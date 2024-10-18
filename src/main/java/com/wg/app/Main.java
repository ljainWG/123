package com.wg.app;

import com.wg.dabms.constant.Constant;
import com.wg.dabms.controller.UserController;
import com.wg.dabms.input.choice.ChoiceInputHandler;

public class Main {
	public static void main(String args[]) {
		
		while(true) {
			int choice = ChoiceInputHandler.getIntChoice(Constant.launchingMenu, 1, Constant.launchingMenuSize);
			switch (choice) {
			case 1 : UserController.LogIn();
				break;
			case 2 : UserController.registerUser(null);
				break;
			case 3 : System.exit(0);
				break;
			}
		}
	}
}