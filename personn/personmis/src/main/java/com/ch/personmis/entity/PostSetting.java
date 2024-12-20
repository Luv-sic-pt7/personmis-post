package com.ch.personmis.entity;

public class PostSetting {

    private String postName;  // 岗位名称
    private String employeeNames;  // 岗位下员工的姓名列表（以逗号分隔）
    private int employeeCount;  // 岗位员工数量

    // Getter and Setter
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getEmployeeNames() {
        return employeeNames;
    }

    public void setEmployeeNames(String employeeNames) {
        this.employeeNames = employeeNames;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    @Override
    public String toString() {
        return "PostSetting{" +
                "postName='" + postName + '\'' +
                ", employeeNames='" + employeeNames + '\'' +
                ", employeeCount=" + employeeCount +
                '}';
    }
}