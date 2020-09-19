package programs;

import java.util.*;

public class Demo {


    public boolean checkCondition(Map<String, String> map, String expression) {
        String [] splitStrings = expression.split("[()|&]+");

        System.out.println(Arrays.toString(splitStrings));
        List<Boolean> boolList = new ArrayList<>();
        for (String string : splitStrings) {
            String [] ss = string.split("==");
            if (ss.length == 2)
            {
                boolean val = map.get(ss[0].trim()) == null ? ss[1] == null : map.get(ss[0].trim()).equals(ss[1].trim());
                boolList.add(val);
            }

        }

        System.out.println(boolList);

        Stack<Object> stack = new Stack<>();
        int boolTaken = 0;

        for (int i=0; i<expression.length(); i++) {
            if (i+1 < expression.length()) {
                if (expression.substring(i, i+2).equals("&&"))
                    stack.push("AND");
                else if (expression.substring(i, i+2).equals("||"))
                    stack.push("OR");
                else if (expression.substring(i, i+2).equals("==")) {
                    stack.push(boolList.get(boolTaken));
                    boolTaken++;
                }
            }
            System.out.println(stack);
            if (expression.charAt(i) == '(')
                stack.push("(");
            else if (expression.charAt(i) == ')') {
                boolean lastBool = (boolean) stack.pop();
                while (!stack.empty() && !stack.peek().equals("(")) {
                    String op = (String) stack.pop();
                    boolean prevBool = (boolean) stack.pop();
                    if (op.equals("OR"))
                        lastBool = lastBool || prevBool;
                    else if (op.equals("AND"))
                        lastBool = lastBool && prevBool;

                }
                stack.pop();
                stack.push(lastBool);
            }
        }
        boolean result = (boolean) stack.pop();
        while (!stack.empty()) {
            String op = (String) stack.pop();
            boolean lastBool = (boolean) stack.pop();
            if (op.equals("OR"))
                result = result || lastBool;
            else if (op.equals("AND"))
                result = result && lastBool;
        }

        return result;

    }


    public Map<String, String> stringToMap(String expression) {
        Map<String, String> map = new HashMap<>();
        String [] stringList = expression.split(",");

        for (String str : stringList) {
            String [] ss = str.split("=");

            if (ss.length == 2 && ss[0].trim().length() > 0) {
                map.put(ss[0].trim(), ss[1].trim());
            }
        }

        return map;

     }

     public void testFunction() {
        Map<String, String> map = new HashMap<>();
        map.put("abc", "abc");

        if (map.get("abcd") != null && map.get("abcd").equals("abc")) {
            System.out.println("Hello");
        }
     }

    public static void main(String[] args) {

        StudentProcessing studentProcessing = new StudentProcessing();

        List<Student> studentList = studentProcessing.inputStudent();

        for (Student student : studentList)
            System.out.println(student.getName() + " " + student.getSchoolName());

        List<Student> sortedStudentList = studentProcessing.getSortStudentList(studentList);

        studentProcessing.createRollAndRegNo(sortedStudentList);

        studentProcessing.printFinalStudentList(studentList);
    }
}
