package programs;

public class Student {

    private String name;

    private String schoolName;

    private String className;

    private int schoolRoleNo;

    private int roleNo;

    private int regNo;

    private double marks;

    private String verificationNo;

    public Student(String name, String schoolName, String className, int schoolRoleNo) {
        this.name = name;
        this.schoolName = schoolName;
        this.className = className;
        this.schoolRoleNo = schoolRoleNo;
    }

    public Student(String name, String schoolName, String className, int schoolRoleNo, int roleNo, int regNo, double marks, String verificationNo) {
        this.name = name;
        this.schoolName = schoolName;
        this.schoolRoleNo = schoolRoleNo;
        this.className = className;
        this.roleNo = roleNo;
        this.regNo = regNo;
        this.marks = marks;
        this.verificationNo = verificationNo;
    }

    public void setRoleNo(int roleNo) {
        this.roleNo = roleNo;
    }

    public void setRegNo(int regNo) {
        this.regNo = regNo;
    }

    public void setVerificationNo(String verificationNo) { this.verificationNo = verificationNo; }

    public String getName() {
        return name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getClassName() { return className; }

    public int getSchoolRoleNo() {
        return schoolRoleNo;
    }

    public int getRoleNo() {
        return roleNo;
    }

    public int getRegNo() {
        return regNo;
    }

    public double getMarks() {
        return marks;
    }

    public String getVerificationNo() {
        return verificationNo;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", className='" + className + '\'' +
                ", schoolRoleNo=" + schoolRoleNo +
                ", roleNo=" + roleNo +
                ", regNo=" + regNo +
                ", marks=" + marks +
                ", verificationNo='" + verificationNo + '\'' +
                '}';
    }
}
