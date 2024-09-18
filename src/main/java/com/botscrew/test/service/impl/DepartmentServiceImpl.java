package com.botscrew.test.service.impl;

import com.botscrew.test.entity.Lector;
import com.botscrew.test.repository.DepartmentRepository;
import com.botscrew.test.repository.LectorRepository;
import com.botscrew.test.service.DepartmentService;
import com.botscrew.test.util.Degree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.StringJoiner;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final LectorRepository lectorRepository;

    @Override
    public String showHead(String name) {
        var head = departmentRepository.findByDepartmentName(name).orElseThrow().getHead();

        return "Head of " + name + " department is " + head.getFirstName() + " " + head.getLastName();
    }

    @Override
    public String showStatistics(String name) {
        var department = departmentRepository.findByDepartmentName(name).orElseThrow();
        var lectorSet = department.getLectorSet();
        var assistantCount = lectorSet.stream().filter(lector -> lector.getDegree() == Degree.ASSISTANT).count();
        var associateProfessorCount = lectorSet.stream().filter(lector -> lector.getDegree() == Degree.ASSOCIATE_PROFESSOR).count();
        var professorCount = lectorSet.stream().filter(lector -> lector.getDegree() == Degree.PROFESSOR).count();

        switch (department.getHead().getDegree()) {
            case ASSISTANT -> assistantCount++;
            case ASSOCIATE_PROFESSOR -> associateProfessorCount++;
            case PROFESSOR -> professorCount++;
        }

        return "assistants - " + assistantCount + "\nassociate professors - " + associateProfessorCount + "\nprofessors - "
               + professorCount;
    }

    @Override
    public String showAverageSalary(String name) {
        var department = departmentRepository.findByDepartmentName(name).orElseThrow();
        var sum = department.getHead().getSalary();
        var lectorSet = department.getLectorSet();

        for (var lector : lectorSet) {
            sum += lector.getSalary();
        }

        return "The average salary of " + name + " is " + sum / (lectorSet.size() + 1);
    }

    @Override
    public long getEmployeeCount(String name) {
        return departmentRepository.findByDepartmentName(name).orElseThrow().getLectorSet().size() + 1;
    }

    @Override
    public String showGlobal(String template) {
        var result = new StringJoiner(", ");

        for (var department : departmentRepository.findAll()) {
            var name = department.getDepartmentName();

            if (name.contains(template)) {
                result.add(name);
            }

            joinLector(department.getHead(), template, result);
        }

        lectorRepository.findAll().forEach(lector -> joinLector(lector, template, result));

        return result.toString();
    }

    private void joinLector(Lector lector, String template, StringJoiner result) {
        var degree = lector.getDegree().getValue();

        if (degree.contains(template)) {
            result.add(degree);
        }

        var firstName = lector.getFirstName();
        var lastName = lector.getLastName();
        var fullName = firstName + " " + lastName;

        if ((firstName.contains(template) || lastName.contains(template)) && !result.toString().contains(fullName)) {
            result.add(fullName);
        }
    }
}
