import java.util.Scanner;

public class minAtm {

	public static void main(String[] args) {
		ManageAcc[] accounts = new ManageAcc[10] ;


		Scanner sc = new Scanner(System.in);
		
		int input = 0;
		String[] list1 = {"1ADD ACOUNT", "2ACCOUNT", "3Exit"};
		String[] list2 = {"1)Balance enquiry", "2)Withdraw", "3)Deposite","4)Forget pin"};


		while (input != 3) {
			System.out.println("\t\t\tBANKING");

			for (int i = 0; i < 3; i++) {
				System.out.println(list1[i]);
			}
			input = sc.nextInt();

			switch (input) {

			////////Add account
			case 1:
				for (int i = 0; i < accounts.length; i++) {
					if (accounts[i] == null) {
						accounts[i] = new ManageAcc();
						accounts[i].Add_acc(accounts, i);
						break;
					}

				}
				break;
///////Accounts
			case 2:

				for (int i = 0; i < accounts.length; i++) {
////Checking accounts exist or not

					if (accounts[i] == null) { /// if not
						System.out.println();
					}

					else { // if exist

						System.out.println("Enter the pin ");
						int pin = sc.nextInt();


						if (pin == accounts[i].pin) {
							for (int j = 0; j < 4; j++) {
								System.out.println(list2[j]);
							}
							int ch = sc.nextInt();

							switch (ch) {

							case 1:

								System.out.println(accounts[i].bal);///BLANCE

								break;
							case 2:
								accounts[i].Withdraw(accounts, i);///Withdraw 
								break;
							case 3:
								accounts[i]. Deposite(accounts, i);//Deposit 
								break;
								case 4:
								accounts[i]. changePIn(accounts, i);//Deposit 
								break;

							default:
                                 	System.out.println("Invalid key");
								break;
							}
						}


						else {
							System.out.println("Wrong pin!!");
						}
					}
				}

				break;
			case 3:
				System.exit(0);
				break;
			default:

				break;


			}

		}
	}
}

/////////////////////////
class ManageAcc {
	
	
	Scanner sc = new Scanner(System.in);
	String name;
	int Acc_n, pin;
	int money;
	double bal;
	
	
	//////To add Account
	void Add_acc(ManageAcc[] accounts, int index) {
		System.out.println("Enter your name");
		accounts[index].name = sc.nextLine();
		System.out.println("Set account no.");
		accounts[index].Acc_n = sc.nextInt();

		// Declare enteredPin outside the loop
		int enteredPin;
		// Check if the entered PIN already exists
		boolean pinExists;
		do {
			pinExists = false; // Assume the PIN doesn't exist initially
			System.out.println("Enter your PIN");
			enteredPin = sc.nextInt(); // Move the declaration inside the loop
			for (int i = 0; i < accounts.length; i++) {
				if (accounts[i] != null && enteredPin == accounts[i].pin) {
					System.out.println("SUCCESSFUL!!");
					pinExists = true;
					break;
				}
			}
		} while (pinExists); // Loop until a unique PIN is entered

		accounts[index].pin = enteredPin; // Set the unique PIN

		System.out.println("Enter your balance");
		accounts[index].bal = sc.nextDouble();
		System.out.println("Set your PIN successfully");
	}
	
	///////WITHDRAW 
	void Withdraw(ManageAcc[] accounts, int index) {

		System.out.println("Enter Amount");
		money = sc.nextInt();
		
			
		if(money>accounts[index].bal ) ///Exception Handling 
		{
			System.out.println("Not enough money");
		}
		
		else{
		accounts[index].bal = bal - money;
		System.out.println("Transaction successful");
		}
	}
	
	///////Deposit 
	void Deposite(ManageAcc[] accounts, int index) {

		System.out.println("Enter Amount");
		money = sc.nextInt();
		
		if(money<1 )///Exception Handling 
		{
			System.out.println("invalid transaction");
		}
		else{
		accounts[index].bal = bal + money;
		System.out.println("Transaction successful");
		}
	}

	void changePIn(ManageAcc[] accounts, int index)
	{
		System.out.print("\nEnter the account number >> ");
		int Acc=sc.nextInt();
		if(Acc!=accounts[index].Acc_n)
		{
			System.out.println("Invalid details");
			
		}
		else{
			System.out.print("\n Enter new pin >> ");
			pin=sc.nextInt();
			accounts[index].pin=pin;
		}
	}

}