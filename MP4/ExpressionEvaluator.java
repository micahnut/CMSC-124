import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.lang.String;
import java.lang.*;

public class ExpressionEvaluator {
 	
	int value;
	String input;
    String copyInput;
    String tokens;
    char next_token;
    String[] array;
    char[] chars;
    int count=0;
    int n=0;
    int start;
    int end;
    boolean valid = false;


    public ExpressionEvaluator(String copyInput, String input){
        this.copyInput = copyInput;
        this.input = input;
    }

    public void checker(){
        System.out.println("<expr>   ::= <expr>+<term>   |   <expr>-<term>   |   <term>");
        System.out.println("<term>   ::= <term>*<factor> |   <term>/<factor> |   <term>^<factor> | <term>%<factor>  | <factor>");
        System.out.println("<factor> ::= (<expr>)        |   <letter>        |   -<letter>");
        System.out.println("<letter> ::= a|z|A|Z");

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
            valid = true;
        }
        else
            System.out.println("Invalid Input");

        if(valid){
            String postfix = convertToPostfix(input);
            System.out.println("Postfix: "+ postfix);
            System.out.println("Prefix: "+ convertToPrefix(postfix));
        }
        

     }

        public void get_next(){
          //System.out.println("in");

            next_token = copyInput.charAt(start);
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

             while(next_token == '*' || next_token == '/' || next_token == '^' || next_token == '%'){
                //System.out.println("in2.1");
                get_next();
                factor();
             }
        }

          public void factor(){

            //System.out.println("in3");

            if(next_token == '-'){
                get_next();
                //digit();
            }

            if((next_token >= 'a' && next_token <= 'z') || (next_token >= 'A' && next_token <= 'Z')){
                //System.out.println("in4");
                letter();
            
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

         public void letter(){

            if((next_token >= 'a' && next_token <= 'z') || (next_token >= 'A' && next_token <= 'Z')){
                //System.out.println("in4");
                get_next();
            
            }
            else{
                  System.out.println("Invalid Input");
                  System.exit(0);
            }
         }

         private static boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%'|| c == '(' || c == ')';
        }

        private static int getPrecedence(char ch) {
            switch (ch) {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
            case '%':
                return 2;

            case '^':
                return 3;
            }
            return -1;
        }

        // A utility function to check if the given character is operand
        private static boolean isOperand(char ch) {
            return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
        }

        public static String convertToPostfix(String infix) {
            Stack<Character> stack = new Stack<Character>();
            StringBuffer postfix = new StringBuffer(infix.length());
            char c, d;
            boolean checker = false;
            //boolean unary = true; //is the current operator unary?

            for (int i = 0; i < infix.length(); i++) {
                c = infix.charAt(i);
            

                if (isOperand(c)) {
                    postfix.append(c);
                } else if (c == '(') {
                    stack.push(c);
                }
                // If the scanned character is an ‘)’, pop and output from the stack
                // until an ‘(‘ is encountered.
                else if (c == ')') {
                   // System.out.println("In1");
                    while (stack.peek() != '(') {
                     //   System.out.println("In2");
                        postfix.append(stack.pop());

                        if (stack.isEmpty()){
                            System.out.println("Wrong input");
                            return null;
                            //System.exit(0);
                        }
                        
          
                    }
                    if(!stack.isEmpty()){
                       //     System.out.println("In3");
                            stack.pop();
                    }

                    /*
                    if (stack.isEmpty() && stack.peek() != '('){
                        System.out.println("Wrong input");
                        //return null;
                        System.exit(0);
                    }*/
                    
                }
               /* else if (isOperator(c)) // operator encountered
                {
                    if (!stack.isEmpty() && (getPrecedence(c) <= getPrecedence(stack.peek()))){
                        postfix.append(stack.pop());
                    }
                    stack.push(c);
                }*/
                else{
                    String temp = "";
                    if(isOperator(c)){ //<=
                        /*if(unary){
                            char x;
                            //x = "u" + c;
                            x= 'u';
                            stack.push(x);
                        }*/
                        //else{
                        while(!stack.isEmpty() && (getPrecedence(c) <= getPrecedence(stack.peek()))){
                        postfix.append(stack.pop());
                        }
                        stack.push(c);
                        //}
                        //unary = true;
                    }
                    else{
                        //unary = false;
                        System.out.println("Wrong Expression");
                        System.exit(0);
                    }
                }
            }

            while (!stack.isEmpty()) {
                if(stack.peek() =='('){
                    System.out.println("Wrong input");
                    System.exit(0);
                }
                postfix.append(stack.pop());
            }
            return postfix.toString();
        }

           public static String convertToPrefix(String postfix){
            

            Stack<String> stack = new Stack<String>();
            StringBuffer prefix = new StringBuffer(postfix.length());
            char c;

            for(int i=0; i<postfix.length(); i++){
                c = postfix.charAt(i);

                if (isOperator(c)){
                    String op1 = stack.peek();
                    stack.pop();
                    String op2 = stack.peek();
                    stack.pop();

                    String temp = c + op2 + op1;

                    stack.push(temp);

                }
                else{
                    stack.push(c + "");
                }
            }

            return stack.peek();
        }

        public static String prefixToInfix(String prefix){
                Stack<String> s= new Stack<String>(); 

                  // length of expression 
                int length = prefix.length();

                // reading from right to left 
                      for (int i = length - 1; i >= 0; i--) { 
                      
                            // check if symbol is operator 
                            if (isOperator(prefix.charAt(i))) { 
                          
                              // pop two operands from stack 
                              String op1 = s.peek();   s.pop(); 
                              String op2 = s.peek();   s.pop(); 
                          
                              // concat the operands and operator 
                              String temp = "(" + op1 + prefix.charAt(i) + op2 + ")"; 
                          
                              // Push string temp back to stack 
                              s.push(temp); 
                            } 
                          
                            // if symbol is an operand 
                            else { 
                          
                              // push the operand to the stack 
                              s.push(prefix.charAt(i) + ""); 
                             } 
                      } 
              
              // Stack now contains the Infix expression 
              return s.peek(); 
            } 

            public static String prefixToPost(String prefix){
                Stack<String> s= new Stack<String>(); 
                int checker = 0;
      
                // length of expression 
                int length = prefix.length(); 
                System.out.println(prefix.charAt(0));
                /*if(!isOperator(prefix.charAt(0))){
                    System.out.println("Wrong input");
                    System.exit(0);
                }*/
                // reading from right to left 
                for (int i = length - 1; i >= 0; i--)  
                {   
                    
                    // check if symbol is operator 
                    if (isOperator(prefix.charAt(i)))  
                    { 
                        
                        // pop two operands from stack 
                        String op1 = s.peek(); s.pop(); 
                        String op2 = s.peek(); s.pop(); 
              
                        // concat the operands and operator 
                        String temp = op1 + op2 + prefix.charAt(i); 
              
                        // Push String temp back to stack 
                        s.push(temp); 
                    } 
              
                    // if symbol is an operand 
                    else
                    { 
                        // push the operand to the stack 
                        s.push(prefix.charAt(i)+""); 
                    } 
                } 
      
            // stack contains only the Postfix expression 
            return s.peek(); 
        }


	   public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            boolean quit = false;
            String input; //line input by the user
            String copyInput; 
            int choice;

            do {
                try {
                    System.out.println("\n");
                    System.out.println("Menu \n");
                    System.out.println("1. Infix input\n");
                    System.out.println("2. Prefix input \n");
                    System.out.println("3. Postfix input \n");
                    choice = sc.nextInt();
           

                    System.out.println("\n");
                    System.out.print(">"); // prompt
                    input = in.readLine();
                    if (input.equals("q")) {
                        quit = true;
                    } else {
                        switch(choice){
                        case 1:
                            copyInput = input+"$";
                            copyInput = copyInput.replaceAll(" ","");
                            ExpressionEvaluator test = new ExpressionEvaluator(copyInput,input);
                            test.checker();
                        break;
                        case 2:
                            try{
                                String letters = input.replaceAll("[^a-zA-Z]+", "");
                                System.out.println("letters: "+ letters);
                                System.out.println("length: "+ letters.length());

                                String infix = prefixToInfix(input);
                                String lettersinf = infix.replaceAll("[^a-zA-Z]+", "");
                                System.out.println("lettersinf: "+ lettersinf);
                                System.out.println("length: "+ lettersinf.length());

                                if(letters.length() == lettersinf.length()){
                                    System.out.println("Infix : " + infix); 
                                }
                                else{
                                    System.out.println("Infix: Wrong Expression");
                                }

                                //System.out.println("Infix : " + prefixToInfix(input)); 
                                
                                String postfix = prefixToPost(input);
                                String letterspost = postfix.replaceAll("[^a-zA-Z]+", "");
                                System.out.println("letterspost: "+ letterspost);
                                System.out.println("length: "+ letterspost.length());

                                if(letters.length() == letterspost.length())
                                     System.out.println("Postfix : " + postfix);
                                else{
                                    System.out.println("Postfix: Wrong Expression");
                                } 
                            }
                            catch(Exception e){
                                System.out.println("Wrong Expression");
                            }
                         break;
                         case 3:
                            try{
                                String letters = input.replaceAll("[^a-zA-Z]+", "");
                                System.out.println("letters: "+ letters);
                                System.out.println("length: "+ letters.length());
                                
                                String prefix = convertToPrefix(input);
                                String letterspre = prefix.replaceAll("[^a-zA-Z]+", "");
                                System.out.println("letterspre: "+ letterspre);
                                System.out.println("length: "+ letterspre.length());

                                if(letters.length() == letterspre.length())
                                    System.out.println("Prefix: " + prefix);
                                else{
                                    System.out.println("Prefix: Wrong Expression");
                                }

                                String infix = prefixToInfix(prefix);
                               
                                String lettersinf = infix.replaceAll("[^a-zA-Z]+", "");
                                System.out.println("lettersinf: "+ lettersinf);
                                System.out.println("length: "+ lettersinf.length());

                                if(letters.length() == lettersinf.length())
                                    System.out.println("Infix: " + infix);
                                else{
                                     System.out.println("Infix: Wrong Expression");
                                }

                                
                            }
                            catch(Exception e){
                                System.out.println("Wrong Expression");
                            }
                          break;
                         default:
                            System.out.println("Choose from the menu");  
                            continue; 
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Invalid expression");
                }
            } while (!quit);
        /*
            System.out.print("Enter a string: ");
            String input = sc.nextLine();
            String copyInput; 
            copyInput = input+"$";
            // System.out.println("copyInput:"+ copyInput);
            ExpressionEvaluator test = new ExpressionEvaluator(copyInput,input);
            test.checker();*/
        }

}