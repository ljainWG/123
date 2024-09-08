package App;

import com.wg.dabms.Constant.Constant;
import com.wg.dabms.a.controller.UserController;
import com.wg.dabms.input.choice.ChoiceInputHandler;

public class Main {
	public static void main(String args[]) {
		
		while(true) {
			System.out.println(Constant.launchingMenu);
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