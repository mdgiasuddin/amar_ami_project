package programs;

public class Student {

    private String name;

    private String schoolName;

    private int schoolRoleNo;

    private int roleNo;

    private int regNo;

    private double marks;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setSchoolRoleNo(int schoolRoleNo) {
        this.schoolRoleNo = schoolRoleNo;
    }

    public void setRoleNo(int roleNo) {
        this.roleNo = roleNo;
    }

    public void setRegNo(int regNo) {
        this.regNo = regNo;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

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
}
