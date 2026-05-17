import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	HashMap<Integer, Account> data = new HashMap<Integer, Account>();

	public void getLogin() throws IOException {
    while (true) {
        try {
            System.out.print("\nEnter your customer number: ");
            int customerNumber = menuInput.nextInt();

            System.out.print("Enter your PIN number: ");
            int pinNumber = menuInput.nextInt();

            Account acc = data.get(customerNumber);

            if (acc != null && acc.getPinNumber() == pinNumber) {
                getAccountType(acc);
                return;
            }

            System.out.println("\nWrong Customer Number or Pin Number");

        } catch (InputMismatchException e) {
            System.out.println("\nInvalid Character(s). Only Numbers.");
            menuInput.next();
        }
    }
}

	public void getAccountType(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSelect the account you want to access: ");
				System.out.println(" Type 1 - Checking Account");
				System.out.println(" Type 2 - Savings Account");
				System.out.println(" Type 3 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
				case 1:
					getChecking(acc);
					break;
				case 2:
					getSaving(acc);
					break;
				case 3:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void getChecking(Account acc) {
    	boolean end = false;
    	while (!end) {
        	try {
            System.out.println("\nChecking Account: ");
            System.out.println(" Type 1 - View Balance");
            System.out.println(" Type 2 - Withdraw Funds");
            System.out.println(" Type 3 - Deposit Funds");
            System.out.println(" Type 4 - Transfer Funds");
            System.out.println(" Type 5 - Exit");
            System.out.print("\nChoice: ");

            int selection = menuInput.nextInt();
            String msg; // declare once for all cases

            switch (selection) {
            case 1:
                System.out.println("\nChecking Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
                break;

            case 2: {
                double beforeW = acc.getCheckingBalance();
                acc.getCheckingWithdrawInput();
                double afterW = acc.getCheckingBalance();

                msg = "WITHDRAW CHECKING | Customer " + acc.getCustomerNumber() +
                      " | Before: " + moneyFormat.format(beforeW) +
                      " | After: " + moneyFormat.format(afterW);
                logTransaction(msg);
                acc.addTransaction(msg);
                break;
            }

            case 3: {
                double beforeD = acc.getCheckingBalance();
                acc.getCheckingDepositInput();
                double afterD = acc.getCheckingBalance();

                msg = "DEPOSIT CHECKING | Customer " + acc.getCustomerNumber() +
                      " | Before: " + moneyFormat.format(beforeD) +
                      " | After: " + moneyFormat.format(afterD);
                logTransaction(msg);
                acc.addTransaction(msg);
                break;
            }

            case 4: {
                double beforeChecking = acc.getCheckingBalance();
                double beforeSaving = acc.getSavingBalance();

                acc.getTransferInput("Checking");

                double afterChecking = acc.getCheckingBalance();
                double afterSaving = acc.getSavingBalance();

                msg = "TRANSFER CHECKING→SAVINGS | Customer " + acc.getCustomerNumber() +
                      " | Checking: " + moneyFormat.format(beforeChecking) + " → " + moneyFormat.format(afterChecking) +
                      " | Savings: " + moneyFormat.format(beforeSaving) + " → " + moneyFormat.format(afterSaving);
                logTransaction(msg);
                acc.addTransaction(msg);
                break;
            }

            case 5:
                end = true;
                break;

            default:
                System.out.println("\nInvalid Choice.");
            }
        } catch (InputMismatchException e) {
            System.out.println("\nInvalid Choice.");
            menuInput.next();
        }
    }
}

	public void getSaving(Account acc) {
    boolean end = false;
    while (!end) {
        try {
            System.out.println("\nSavings Account: ");
            System.out.println(" Type 1 - View Balance");
            System.out.println(" Type 2 - Withdraw Funds");
            System.out.println(" Type 3 - Deposit Funds");
            System.out.println(" Type 4 - Transfer Funds");
            System.out.println(" Type 5 - Exit");
            System.out.print("Choice: ");

            int selection = menuInput.nextInt();
            String msg; // declare once for all cases

            switch (selection) {

            case 1:
                System.out.println("\nSavings Account Balance: " +
                                   moneyFormat.format(acc.getSavingBalance()));
                break;

            case 2: { // WITHDRAW SAVINGS
                double beforeW = acc.getSavingBalance();
                acc.getsavingWithdrawInput();
                double afterW = acc.getSavingBalance();

                msg = "WITHDRAW SAVINGS | Customer " + acc.getCustomerNumber() +
                      " | Before: " + moneyFormat.format(beforeW) +
                      " | After: " + moneyFormat.format(afterW);

                logTransaction(msg);
                acc.addTransaction(msg);
                break;
            }

            case 3: { // DEPOSIT SAVINGS
                double beforeD = acc.getSavingBalance();
                acc.getSavingDepositInput();
                double afterD = acc.getSavingBalance();

                msg = "DEPOSIT SAVINGS | Customer " + acc.getCustomerNumber() +
                      " | Before: " + moneyFormat.format(beforeD) +
                      " | After: " + moneyFormat.format(afterD);

                logTransaction(msg);
                acc.addTransaction(msg);
                break;
            }

            case 4: { // TRANSFER SAVINGS → CHECKING
                double beforeChecking = acc.getCheckingBalance();
                double beforeSaving = acc.getSavingBalance();

                acc.getTransferInput("Savings");

                double afterChecking = acc.getCheckingBalance();
                double afterSaving = acc.getSavingBalance();

                msg = "TRANSFER SAVINGS→CHECKING | Customer " + acc.getCustomerNumber() +
                      " | Checking: " + moneyFormat.format(beforeChecking) +
                      " → " + moneyFormat.format(afterChecking) +
                      " | Savings: " + moneyFormat.format(beforeSaving) +
                      " → " + moneyFormat.format(afterSaving);

                logTransaction(msg);
                acc.addTransaction(msg);
                break;
            }

                    default:
                System.out.println("\nInvalid Choice.");
            }

        } catch (InputMismatchException e) {
            System.out.println("\nInvalid Choice.");
            menuInput.next();
        }
    }
}

	public void createAccount() throws IOException {
		int cst_no = 0;
		
    while (true) {
        try {
            System.out.print("\nEnter your customer number: ");
            cst_no = menuInput.nextInt();

            if (!data.containsKey(cst_no)) {
                break; // number is available
            }

            System.out.println("\nThis customer number is already registered");

        } catch (InputMismatchException e) {
            System.out.println("\nInvalid Choice.");
            menuInput.next();
        }
    }

		System.out.println("\nEnter PIN to be registered");
		int pin = menuInput.nextInt();
		data.put(cst_no, new Account(cst_no, pin));
		System.out.println("\nYour new account has been successfuly registered!");
		System.out.println("\nRedirecting to login....");
		getLogin();
	}


	public void mainMenu() throws IOException {
		loadAccounts();
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\n Type 1 - Login");
				System.out.println(" Type 2 - Create Account");
				System.out.println(" Type 3 - Show All Customer Balances");
				System.out.print("\nChoice: ");
				int choice = menuInput.nextInt();
				switch (choice) {
				case 1:
					getLogin();
					end = true;
					break;
				case 2:
					createAccount();
					end = true;
					break;
				case 3:
    				showAllCustomerBalances();
    				break;

				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		saveAccounts();
		menuInput.close();
		System.out.println("\nThank You for using this ATM.\n");
		System.exit(0);
	}

	@SuppressWarnings("unchecked")
	public void loadAccounts() {
    try (java.io.ObjectInputStream ois =
            new java.io.ObjectInputStream(new java.io.FileInputStream("accounts.dat"))) {

        data = (HashMap<Integer, Account>) ois.readObject();
        System.out.println("Accounts loaded successfully.");

    } catch (Exception e) {
        System.out.println("No saved accounts found. Starting fresh.");
    }
}

	public void saveAccounts() {
    try (java.io.ObjectOutputStream oos =
            new java.io.ObjectOutputStream(new java.io.FileOutputStream("accounts.dat"))) {

        oos.writeObject(data);
        System.out.println("Accounts saved successfully.");

    } catch (Exception e) {
        System.out.println("Error saving accounts: " + e.getMessage());
    }
}

	public void logTransaction(String message) {
    	try (java.io.FileWriter fw = new java.io.FileWriter("transactions.log", 	true);
         	java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
         	java.io.PrintWriter out = new java.io.PrintWriter(bw)) {

        	out.println(message);

    	} catch (Exception e) {
        	System.out.println("Error writing to log file: " + e.getMessage());
    	}
	}

	public void showAllCustomerBalances() {
    System.out.println("\n=== All Customer Balances ===");

    for (Map.Entry<Integer, Account> entry : data.entrySet()) {
        Account acc = entry.getValue();

        System.out.println("\nCustomer Number: " + acc.getCustomerNumber());
        System.out.println(" Checking Balance: " + moneyFormat.format(acc.getCheckingBalance()));
        System.out.println(" Savings Balance:  " + moneyFormat.format(acc.getSavingBalance()));
    }

    System.out.println("\n==============================\n");
	}


}
