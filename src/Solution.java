/**
 * Created by Victor Wardi - @vwardi on 19/02/2019
 **/

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class TransactionException extends Exception {

    private String errorMessage;
    private String errorCode;

    public TransactionException(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

}

class DigitalWallet {

    private String walletId;
    private String userName;
    private String userAccessCode;
    private int walletBalance;

    public DigitalWallet(String walletId, String userName) {
        this.walletId = walletId;
        this.userName = userName;
    }

    public DigitalWallet(String walletId, String userName, String userAccessCode) {
        this.walletId = walletId;
        this.userName = userName;
        this.userAccessCode = userAccessCode;
    }

    public String getWalletId() {
        return walletId;
    }

    public String getUsername() {
        return userName;
    }

    public String getUserAccessCode() {
        return userAccessCode;
    }

    public int getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(int walletBalance) {
        this.walletBalance = this.walletBalance + walletBalance;
    }
}

class DigitalWalletTransaction {


    public void addMoney(DigitalWallet digitalWallet, int amount) throws TransactionException {

        checkUserAccess(digitalWallet);

        checkAmount(amount);

        digitalWallet.setWalletBalance(amount);

    }


    public void payMoney(DigitalWallet digitalWallet, int amount) throws TransactionException {

        checkUserAccess(digitalWallet);

        checkAmount(amount);

        checkBalance(amount, digitalWallet.getWalletBalance());

        digitalWallet.setWalletBalance(-amount);

    }

    private void checkUserAccess(DigitalWallet digitalWallet) throws TransactionException {
        if (digitalWallet.getUserAccessCode() == null || digitalWallet.getUserAccessCode().isEmpty()) {
            throw new TransactionException("User not authorized", "USER_NOT_AUTHORIZED");
        }
    }

    private void checkAmount(int amount) throws TransactionException {
        if (amount <= 0) {
            throw new TransactionException("Amount should be greater then zero", "INVALID_AMOUNT");
        }
    }

    private void checkBalance(int payment, int balance) throws TransactionException {
        if (payment > balance) {
            throw new TransactionException("Insufficient balance", "INSUFFICIENT_BALANCE");
        }
    }

}

/** input values
 *
 * 2
 * 1 Julia bff834a2c117a76d9ceb782f98e428686ca3c4ea
 * 2 Samantha
 * 10
 * 1 pay 50
 * 1 add 100
 * 1 add 0
 * 1 pay 30
 * 2 add 500
 * 1 add -5
 * 1 add 1000
 * 1 pay -20
 * 1 pay 100
 * 1 add 720
 *
 */

public class Solution {

    private static final Scanner INPUT_READER = new Scanner(System.in);
    private static final DigitalWalletTransaction DIGITAL_WALLET_TRANSACTION = new DigitalWalletTransaction();

    private static final Map<String, DigitalWallet> DIGITAL_WALLETS = new HashMap<>();

    public static void main(String[] args) {

        int numberOfWallets = Integer.parseInt(INPUT_READER.nextLine());
        while (numberOfWallets-- > 0) {
            String[] wallet = INPUT_READER.nextLine().split(" ");
            DigitalWallet digitalWallet;

            if (wallet.length == 2) {
                digitalWallet = new DigitalWallet(wallet[0], wallet[1]);
            } else {
                digitalWallet = new DigitalWallet(wallet[0], wallet[1], wallet[2]);
            }

            DIGITAL_WALLETS.put(wallet[0], digitalWallet);
        }

        int numberOfTransactions = Integer.parseInt(INPUT_READER.nextLine());
        while (numberOfTransactions-- > 0) {

            String line = INPUT_READER.nextLine();
            String[] transaction = line.split(" ");
            DigitalWallet digitalWallet = DIGITAL_WALLETS.get(transaction[0]);

            if (transaction[1].equals("add")) {
                try {
                    DIGITAL_WALLET_TRANSACTION.addMoney(digitalWallet, Integer.parseInt(transaction[2]));
                    System.out.println("Wallet successfully credited.");
                } catch (TransactionException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getMessage() + ".");
                }
            } else {
                try {
                    DIGITAL_WALLET_TRANSACTION.payMoney(digitalWallet, Integer.parseInt(transaction[2]));
                    System.out.println("Wallet successfully debited.");
                } catch (TransactionException ex) {
                    System.out.println(ex.getErrorCode() + ": " + ex.getMessage() + ".");
                }
            }
        }

        System.out.println();

        DIGITAL_WALLETS.keySet()
                .stream()
                .sorted()
                .map((digitalWalletId) -> DIGITAL_WALLETS.get(digitalWalletId))
                .forEachOrdered((digitalWallet) -> {
                    System.out.println(digitalWallet.getWalletId()
                            + " " + digitalWallet.getUsername()
                            + " " + digitalWallet.getWalletBalance());
                });
    }
}