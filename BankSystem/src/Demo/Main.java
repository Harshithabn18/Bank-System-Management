package Demo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankService service = new BankService();
        Scanner sc = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\n===== Bank Menu =====");
                System.out.println("1. Register");
                System.out.println("2. Create Account");
                System.out.println("3. Deposit");
                System.out.println("4. Withdraw");
                System.out.println("5. Check Balance");
                System.out.println("6. Exit");

                int choice = sc.nextInt();
                sc.nextLine();  

                switch (choice) {
                    case 1:
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Password: ");
                        String pass = sc.nextLine();
                        service.registerUser(name, email, pass);
                        break;

                    case 2:
                        System.out.print("Email: ");
                        email = sc.nextLine();
                        service.createAccount(email);
                        break;

                    case 3:
                        System.out.print("Email: ");
                        email = sc.nextLine();
                        System.out.print("Amount to Deposit: ");
                        double deposit = sc.nextDouble();
                        service.deposit(email, deposit);
                        break;

                    case 4:
                        System.out.print("Email: ");
                        email = sc.nextLine();
                        System.out.print("Amount to Withdraw: ");
                        double withdraw = sc.nextDouble();
                        service.withdraw(email, withdraw);
                        break;

                    case 5:
                        System.out.print("Email: ");
                        email = sc.nextLine();
                        service.checkBalance(email);
                        break;

                    case 6:
                        System.out.println("Exiting. Thank you!");
                        return;

                    default:
                        System.out.println("Invalid option.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
