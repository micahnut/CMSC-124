/*
This is a recursive-descent parser that will validate the following grammar:

<expr>          ::=     <term>+<term>   |   <term>-<term>
<term>          ::=     <factor>*<factor>   |   <factor>/<factor>
<factor>        ::=     (<expr>)  |   0 | 1 | 2 | 3


* all valid input should be terminated by $(dollar sign) */


import java.util.*;
import java.util.Scanner;
import java.lang.String;
import java.lang.*;

public class rdp {
 	
	int value;
	String input;
    String tokens;
    char next_token;
    String[] array;
    char[] chars;
    int count=0;
    int n=0;
    int start;
    int end;


    public rdp(String input){
        this.input = input;
    }

    public void checker(){
        System.out.println("<expr>  ::= <expr>+<term>   |   <expr>-<term>   |   <term>");
        System.out.println("<term>  ::= <term>*<factor> |   <term>/<factor> |   <factor>");
        System.out.println("<factor>::= (<expr>)    |<digit>");
        System.out.println("<digit> ::= 0|1|2|3");

        //1-2*3

        //<expr>-<term>
        //(1-2)*(3+1)
        //2/3

        System.out.println(" ");   
        chars = input.toCharArray();

        //System.out.println(Arrays.toString(array));

        n = input.length();
        start = 0;
        end = n-1;

        get_next();
        expr();

        if(next_token == '$'){
            System.out.println("Valid Input");
        }
        else
            System.out.println("Invalid Input");


     }

        public void get_next(){
          //System.out.println("in");

            next_token = input.charAt(start);
            //tokens = input.substring(start+1);

            if(start!=n)
               start++;
            
        //System.out.println("start: "+ start);
        System.out.println("next_token: " + next_token);
        //System.out.println("tokens:" + tokens);

        }

        public void expr(){
            //System.out.println("in1");
            term();
            //System.out.println(next_token + "_" + tokens);

            while(next_token == '+' || next_token == '-'){
                //System.out.println("in1.1");
                get_next();
                term();
            }


        }


     public void term(){

            //System.out.println("in2");
            //System.out.println(next_token);
            //System.out.println(next_token + "_" + tokens);
            factor();
            //System.out.println(next_token);
            //System.out.println(next_token + "_" + tokens);

             while(next_token == '*' || next_token == '/'){
                //System.out.println("in2.1");
                get_next();
                factor();
             }
        }

          public void factor(){

            //System.out.println("in3");

            if(next_token == '0' || next_token == '1' || next_token == '2' || next_token == '3'){
                //System.out.println("in4");
                get_next();
            
            }

            else if(next_token == '('){
                get_next();
                expr();
                if(next_token == ')'){
                    get_next();
                }
                else{
                    System.out.println("Invalid Input");
                    System.exit(0);
                }
            }

            else{
                System.out.println("Invalid Input");
                System.exit(0);
            }

         }

	   public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a string: ");
            String input = sc.nextLine();
            rdp test = new rdp(input);
            test.checker();
        }

}