package com.java_project.JavaProject.api;
//Business logic

import com.java_project.JavaProject.api.dto.AddExpenseDto;
import com.java_project.JavaProject.api.dto.UpdateExpenseDto;
import com.java_project.JavaProject.domain.expense.Expense;
import com.java_project.JavaProject.domain.expense.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//request-urile care vin pe calea adaugata, vor ajunge in zona aceasta de cod
//prima parte a denumirii endpoint ului
@RequestMapping("/api/expense")
public class ExpenseController {

    //@Autowired
    final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/expenses")
    public List<Expense> getallExpenses(){
        return expenseRepository.findAll();
    }

    //returneaza un singur expense
    @GetMapping("/expenses/{id}")
    Expense getAllExpenses(@PathVariable Integer id){
        return expenseRepository.findById(id).get();
    }


    @PostMapping("/addExpense")
    Expense addExpense(@RequestBody AddExpenseDto addDto){

        Expense newExpense = new Expense();
        newExpense.setAmount(addDto.getAmount());
        newExpense.setDescription(addDto.getDescription());
        newExpense.setDate(addDto.getDate());

        return expenseRepository.save(newExpense);
    }

    @PostMapping("/updateExpense/{id}")
    Expense updateExpense(
            @PathVariable Integer id,
            @RequestBody UpdateExpenseDto updateDto){

        Expense updatedExpense = expenseRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Nu " +
                        "este urmatoarea achizitie aferenta id-ului: " + id));


        updatedExpense.setAmount(updateDto.getAmount());
        updatedExpense.setDescription(updateDto.getDescription());
        updatedExpense.setDate(updateDto.getDate());

        return expenseRepository.save(updatedExpense);
    }


    @GetMapping
    public String expenseString(){
        return "Expense example";
    }

}
