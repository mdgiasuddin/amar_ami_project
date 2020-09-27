package programs;

public class Student {

    private String name;

    private String schoolName;

    private int schoolRoleNo;

    private int roleNo;

    private int regNo;

    private double marks;

    private String verificationNo;

    public Student(String name, String schoolName, int schoolRoleNo) {
        this.name = name;
        this.schoolName = schoolName;
        this.schoolRoleNo = schoolRoleNo;
    }

    public Student(String name, String schoolName, int roleNo, int regNo, double marks) {
        this.name = name;
        this.schoolName = schoolName;
        this.roleNo = roleNo;
        this.regNo = regNo;
        this.marks = marks;
    }

    public Student(String name, int roleNo, int regNo, String verificationNo) {
        this.name = name;
        this.roleNo = roleNo;
        this.regNo = regNo;
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
}
