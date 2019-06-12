/*
This is a recursive-descent parser that will validate the following grammar:

<expr>          ::=     +<num>  |   -<num>   |  <num>
<num>           ::=     <digits>.<digits> | <digits>
<digits>        ::=    0* | 1* | 2* | 3* | 4* | 5* | 6* | 7* | 8* | 9*


* all valid input should be terminated by $(dollar sign)*/

import java.util.*;
import java.util.Scanner;
import java.lang.String;
import java.lang.*;


public class recursivedescentparser{

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




    public recursivedescentparser(String input){
        this.input = input;
    }

    public void checker(){

    	System.out.println("<expr>  ::= +<num>   |   -<num>   |   <num>");
        System.out.println("<num>  ::= <digits> |   <digits>.<digits>");
        System.out.println("<digits> ::= 0|1|2|3|4|5|6|7|8|9");
        System.out.println("Terminate every input string with ‘$’");

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
        try{
            next_token = input.charAt(start);
            //tokens = input.substring(start+1);

            if(start!=n)
               start++;
            
        //System.out.println("start: "+ start);
        System.out.println("next_token: " + next_token);
        //System.out.println("tokens:" + tokens);
         
         }catch(StringIndexOutOfBoundsException g){
                System.out.println("Invalid Input");
                System.exit(0);
            }


        }

        public void expr(){
            //System.out.println("in1");
            //System.out.println(next_token + "_" + tokens);

            if(next_token == '+' || next_token == '-'){
                //System.out.println("in1.1");
                get_next();
            }
            num();


        }

        public void num(){
        	digits();

        	if(next_token == '.'){
                get_next();
                if(next_token == '$'){
                    System.out.println("Invalid Input");
                    System.exit(0);
                }
        	}
        	digits();
        }

        public void digits(){

        	while(next_token == '0' || next_token == '1' || next_token == '2' || next_token == '3' || next_token == '4' || next_token == '5' || next_token == '6' || next_token == '7' || next_token == '8' || next_token == '9'){
                //System.out.println("in4");
                get_next();
            
            }
        }

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter a string: ");
            String input = sc.nextLine();
            recursivedescentparser test = new recursivedescentparser(input);
            test.checker();
        }




}