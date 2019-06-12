
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.lang.String;
import java.lang.*;
import java.lang.Math;


public class ExpressionEvaluation {
 	
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


    public ExpressionEvaluation (String copyInput, String input){
        this.copyInput = copyInput;
        this.input = input;
    }

    public void checker(){
        System.out.println("<expr>   ::= <expr>+<term>   |   <expr>-<term>   |   <term>");
        System.out.println("<term>   ::= <term>*<factor> |   <term>/<factor> |   <term>^<factor> | <term>%<factor>| <factor>");
        System.out.println("<factor> ::= (<expr>)        |   <num>           |   -<num>");
        System.out.println("<num>    ::= <digits>");
        System.out.println("<digits> ::= 0|1|2|3|4|5|6|7|8|9");

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
        	String postfix = InfixtoPostfix2(input);
            //System.out.println("postfix: " + postfix);
            System.out.println("Infix evaluation: "+ computePostfix(postfix));
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

             while(next_token == '*' || next_token == '/' || next_token == '^' || next_token == '%' ){
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
            num();

         }
         public void num(){
            digits();

            //digits();


         }
         public void digits(){

            while(Character.isDigit(next_token)){
                //System.out.println("in4");
                get_next();
            
            }
            
         }


         public static boolean isOperator(char c) {
	        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%';
	    }

		public static double eval(char op, double val2, double val1){
			switch(op){
				case '+':
					return val1+val2;
				case '-':
					return val1-val2;
				case '*':
					return val1*val2;
				case '/':
					 if (val2 == 0)
               		 	throw new
               		 	UnsupportedOperationException("Cannot divide by zero"); 
               		 
               		 return val1/val2;
               	case '^':
               		return Math.pow(val1,val2);

			}
			return 0;
		}

		public static boolean hasPrecedence(char operator1, char operator2){
			if(operator2 == '(' || operator2 == ')')
				return false;
			if((operator1 == '*' || operator1 == '/' || operator1 == '%') && (operator2 =='+' || operator2 == '-'))
				return false;
			else
				return true;
		}

		public static double evaluateInfix(String exp){
			Stack<Double> stack = new Stack<Double>();
			Stack<Character> operators = new Stack<Character>();
			char c;

			for(int i=0; i<exp.length(); i++){
				//System.out.println("in1");
				c = exp.charAt(i);

				//System.out.println("c:" + c);

				if(c == ' ')
	    			continue;

	    		else if(Character.isDigit(c)){
	    			//System.out.println("in2");
	    			double n=0;
	    			//System.out.println("length:" + exp.length());
	    			while(i<exp.length() && Character.isDigit(c)){
	    			//	System.out.println("in3");
	    				n = (n*10) + (double)(c-'0');
	    				i++;  
	    				//System.out.println("i:"+ i);
	    				if(i==exp.length()){
	    					break;
	    				}
                    	c = exp.charAt(i); 
                    	//System.out.println("c(insideloop):" + c);
	    			}
	    			i--;
	    			c = exp.charAt(i);
	    			//System.out.println("c:" + c);
	    			//System.out.println("n:" + n);

	    			stack.push(n);
	    		}

	    		else if (c == '(') {
	    			//System.out.println("in4");
	                operators.push(c);
	            }
	            else if(c == ')'){
	            	//System.out.println("in5");
	            	while(operators.peek() != '(')
	            		stack.push(eval(operators.pop(), stack.pop(), stack.pop()));
	            	operators.pop();
	            	/*
	            		if (stack.isEmpty()){
		                    System.out.println("Wrong input");
		                    //return null;
		                    System.exit(0);
	                	}*/

	            }
	            else if(isOperator(c)){
	            	//System.out.println("in6");
	            		while(!operators.empty() && hasPrecedence(c, operators.peek())){
	            			stack.push(eval(operators.pop(), stack.pop(), stack.pop()));
	            		}

	            		operators.push(c);
	            	}

			}
				while(!operators.empty()){
				//	System.out.println("in7");
	            	stack.push(eval(operators.pop(), stack.pop(), stack.pop()));
	            }

	            return stack.pop();

		}

		    private static int operatorPrecedence(String operator) {
				if (operator.equals("u-") || operator.equals("u+")) {
					return 2;
				} else if (operator.equals("*") || operator.equals("/")
						|| operator.equals("%")) {
					return 1;
				} else if (operator.equals("-") || operator.equals("+")) {
					return 0;
				} else if(operator.equals("^")){
					return 3;
				}
					else {
					//throw new ExpressionFormatException();
					System.out.println("error");
					return -1;
				}
			}
		

	    public static String InfixtoPostfix2(String infix) {
	    	//no checking of sunod2 na letters. and operators and only letters input
		try {
			String postfix = "";
			boolean unary = true; // is the current operator unary?
			Stack<String> stack = new Stack<String>();
			StringTokenizer st = new StringTokenizer(infix, "()+-/%*^ ", true);
			while (st.hasMoreTokens()) {
				String token = st.nextToken().trim();
				if (token.equals("")) { // skip any empty token
				} else if (token.equals("(")) {
					stack.push(token);
				} else if (token.equals(")")) {
					String op;
					while (!(op = stack.pop()).equals("(")) {
						postfix += " " + op;
						System.out.println("op:"+op);
					}
				

				} else if (token.equals("*")
						|| // an operator
						token.equals("+") || token.equals("-")
						|| token.equals("%") || token.equals("/")
						|| token.equals("^")){
					if (unary) {
						token = "u" + token;
						// a unary op always goes on 
						// the stack without popping any other op
						stack.push(token);
					} else {
						int p = operatorPrecedence(token);
						while (!stack.isEmpty() && !stack.peek().equals("(")
								&& operatorPrecedence(stack.peek()) >= p) {
							String op = stack.pop();
							postfix += " " + op;
						}
						stack.push(token);
					}
					unary = true; // if an operator is after this one, it
					// has to be unary
				} else { // an operand
					Integer.parseInt(token); // just to check that
					// it is a number
					// If not a number, an exception is
					// thrown*/
					postfix += " " + token;
					unary = false; // any operator after an operand is binary
				}
			}
			while (!stack.isEmpty()) {

				String op = stack.pop();
				if(op.equals("(")){
					System.out.println("Wrong expression");
					System.exit(0);
				}
				postfix += " " + op;
			}
			return postfix;
		} catch (EmptyStackException ese) {
			//throw new ExpressionFormatException();
			System.out.println("Wrong expression");
			String error = "Wrong expression";
			return error;
		} catch (NumberFormatException nfe) {
			System.out.println("Wrong expression");
			String error = "Wrong expression";
			return error;
			//throw new ExpressionFormatException();
		}
	}

	public static int computePostfix(String postfix) {

		try {
			Stack<Integer> stack = new Stack<Integer>();
			StringTokenizer st = new StringTokenizer(postfix);
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (token.equals("*")
						|| // an operator
						token.equals("+") || token.equals("-")
						|| token.equals("%") || token.equals("/")
						|| token.equals("u+") || token.equals("u-")) {
					applyOperator(token, stack);
				} else { // an operand
					stack.push(new Integer(token));
				}
			}
			int result = ((Integer) stack.pop()).intValue();
			if (!stack.isEmpty()) { // the stack should be empty
				//throw new ExpressionFormatException();
				System.out.println("error");
				return 0;
			}
			return result;
		} catch (EmptyStackException ese) {
			//throw new ExpressionFormatException();
			System.out.println("error");
			return 0;
		} catch (NumberFormatException nfe) {
			//throw new ExpressionFormatException();
			System.out.println("error");
			return 0;
		}
	}

		private static void applyOperator(String operator, Stack<Integer> s) {
		int op1 = s.pop();
		if (operator.equals("u-")) {
			s.push(-op1);
		} else if (operator.equals("u+")) {
			s.push(op1);
		} else { // binary operator
			int op2 = s.pop();
			int result=0;
			if (operator.equals("+")) {
				result = op2 + op1;
			} else if (operator.equals("-")) {
				result = op2 - op1;
			} else if (operator.equals("/")) {
				result = op2 / op1;
			} else if (operator.equals("%")) {
				result = op2 % op1;
			} else if (operator.equals("*")) {
				result = op2 * op1;
			} else {
				//throw new IllegalArgumentException();
				System.out.println("error");

			}
			s.push(result);
		}
	}

	    public static double evaluatePostfix(String exp){
	    	Stack<Double> stack = new Stack<Double>();

	    	char c;

	    	for(int i=0; i<exp.length(); i++){
	    		c = exp.charAt(i);

	    		if(Character.isDigit(c)){
	    			double n=0;

	    			while(Character.isDigit(c)){
	    				n = n*10 + (double)(c-'0'); 
                    	i++; 
                    	c = exp.charAt(i); 
	    			}
	    			i--;

	    			stack.push(n);
	    		}

	    		else if(c == ' ')
	    			continue;

	    		else{
	    			double val1 = stack.pop(); 
                	double val2 = stack.pop(); 
                  
                switch(c) 
                { 
                    case '+': 
                    stack.push(val2+val1); 
                    break; 
                      
                    case '-': 
                    stack.push(val2- val1); 
                    break; 
                      
                    case '/': 
                    stack.push(val2/val1); 
                    break; 
                      
                    case '*': 
                    stack.push(val2*val1); 
                    break; 

                    case '^':
                    stack.push(Math.pow(val2,val1));
                    break;

                    case '%':
                    stack.push(val2 % val1);
                    break;
            	} 

	    		}
	    	}
	    	return stack.pop();
	    }


	    public static void evaluatePrefix(String prefix){
	    //	String[] prefixStr = "+ 7 * 4  ".split(" ");
        String[] prefixStr = prefix.split(" ");
        Deque<Double> stack = new ArrayDeque<>();

        try{
        for(int i=prefixStr.length-1;i>-1;i--){
            String s = prefixStr[i];
            if(s.equals("")){
                continue;
            }
            if(s.equals("+")){
                stack.push(stack.poll()+stack.poll());
            }else if(s.equals("*")){
                stack.push(stack.poll() * stack.poll());
            }else if(s.equals("/")){
                stack.push(stack.poll() / stack.poll());
            }else if(s.equals("-")){
                stack.push(stack.poll() - stack.poll());
            }else if(s.equals("%")){
                stack.push(stack.poll() % stack.poll());
            }else if(s.equals("^")){
                stack.push(Math.pow(stack.poll(),stack.poll())); //Math.pow(val1,val2)
            }else{
                stack.push(Double.parseDouble(s));
            }

        }
       	 Double result = stack.poll();
       	 Double afterresult = stack.poll();
       	 //System.out.println(afterresult);
       	if(result!=null && afterresult==null){ // the stack should be empty
			System.out.println("Prefix evaluation: " + result);
		}
		else
			System.out.println("Wrong Expression");

        }catch(Exception e){
            System.out.println("Wrong Expression");
        }
	   }





         	public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            boolean quit = false;
            String input; //line input by the user
            String copyInput; 
            int choice;
            //String prefix;
			//String postfix;

            do{
	            try{
	            	copyInput = "";

	           		System.out.println("\n");
					System.out.println("Menu \n");
					System.out.println("1. Infix input\n");
					System.out.println("2. Prefix input \n");
					System.out.println("3. Postfix input \n");
					choice = sc.nextInt();
					
					System.out.println("\n");
					System.out.print(">"); // prompt
		            input = in.readLine();

		            if(input.equals("q")){
		            	quit = true;
		            }
		            else{

					switch(choice){
						case 1:
			   				copyInput = input+"$";
			   				copyInput = copyInput.replaceAll(" ","");
		                    ExpressionEvaluation test = new ExpressionEvaluation(copyInput,input);
		                    test.checker();


						break;
						case 2:
			
			   				evaluatePrefix(input);

						break;
						case 3:
							//postfix input

							try{
							//postfix = "+ 9 * 2 6";
							System.out.println("Postfix evaluation: "+ evaluatePostfix(input));
						}catch(Exception e){
							System.out.println("Wrong Expression");
						}
						break;
						default:
							System.out.println("Choose from the menu");  
                            continue; 
					}
				}

		}catch(Exception e){
             System.out.println("Invalid expression");
         }
     }while(!quit);

	    }

}