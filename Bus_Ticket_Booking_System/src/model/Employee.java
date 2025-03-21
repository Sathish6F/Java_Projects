package model;

public class Employee {
    private int travels_id;
    private int emp_id;
    private String emp_name;
    private String mobile;

    public Employee(int travels_id, int emp_id, String emp_name, String mobile) {
        this.travels_id = travels_id;
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.mobile = mobile;
    }

    public Employee(int emp_id,String emp_name,String mobile){
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.mobile = mobile;
    }

    public Employee(String emp_name,String mobile){
        this.emp_name = emp_name;
        this.mobile = mobile;
    }
    public int getTravels_id() {
        return travels_id;
    }

    public void setTravels_id(int travels_id) {
        this.travels_id = travels_id;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
