package com.botscrew.test.runner;

import com.botscrew.test.entity.Department;
import com.botscrew.test.entity.Lector;
import com.botscrew.test.repository.DepartmentRepository;
import com.botscrew.test.service.DepartmentService;
import com.botscrew.test.util.Degree;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MainRunner implements CommandLineRunner {
    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) {
        populate();

        System.out.println("PLEASE, INPUT A COMMAND");

        var scanner = new Scanner(System.in);

        while (true) {
            var in = scanner.nextLine();

            try {
                if (in.matches("Who is head of department .+")) {
                    System.out.println(departmentService.showHead(in.split(" ")[5]));
                } else if (in.matches("Show .+ statistics")) {
                    System.out.println(departmentService.showStatistics(in.split(" ")[1]));
                } else if (in.matches("Show the average salary of the department .+")) {
                    System.out.println(departmentService.showAverageSalary(in.split(" ")[7]));
                } else if (in.matches("Show count of employee for .+")) {
                    System.out.println(departmentService.getEmployeeCount(in.split(" ")[5]));
                } else if (in.matches("Show search by .+")) {
                    System.out.println(departmentService.showGlobal(in.split(" ")[3]));
                } else if (in.equals("q")) {
                    break;
                } else {
                    System.out.println("An incorrect command");
                }
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println(noSuchElementException.getMessage());
            }
        }
    }

    @Transactional
    private void populate() {
        var head = new Lector();

        head.setDegree(Degree.PROFESSOR);
        head.setFirstName("John");
        head.setLastName("Doe");
        head.setSalary(3000f);

        var lector1 = new Lector();

        lector1.setDegree(Degree.ASSOCIATE_PROFESSOR);
        lector1.setFirstName("Jane");
        lector1.setLastName("Doe");
        lector1.setSalary(1500f);

        var lector2 = new Lector();

        lector2.setDegree(Degree.ASSISTANT);
        lector2.setFirstName("Not");
        lector2.setLastName("Sure");
        lector2.setSalary(750f);

        var department = new Department();

        department.setDepartmentName("IT");
        department.setHead(head);
        department.addLector(lector1);
        department.addLector(lector2);

        departmentRepository.save(department);
    }
}
