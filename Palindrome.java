public class Palindrome {
    public static void main(String[] args) {
        int num = 141;
        int originalNum = num;
        int rev = 0;
        while (num != 0) {
            int digit = num % 10;
            num /= 10;
            rev = rev * 10 + digit;
        }
        System.out.println(rev == originalNum);
    }
}
