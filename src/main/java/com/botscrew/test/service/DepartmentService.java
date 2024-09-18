package com.botscrew.test.service;

public interface DepartmentService {
    String showHead(String name);

    String showStatistics(String name);

    String showAverageSalary(String name);

    long getEmployeeCount(String name);

    String showGlobal(String template);
}
