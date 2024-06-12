package com.java_project.JavaProject.api.controller;
//Business logic

import com.java_project.JavaProject.api.dto.expenseDto.AddExpenseDto;
import com.java_project.JavaProject.api.dto.expenseDto.UpdateExpenseDto;
import com.java_project.JavaProject.domain.category.CategoryRepository;
import com.java_project.JavaProject.domain.expense.Expense;
import com.java_project.JavaProject.domain.expense.ExpenseRepository;
import com.java_project.JavaProject.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//request-urile care vin pe calea adaugata, vor ajunge in zona aceasta de cod
//prima parte a denumirii endpoint ului
@RequestMapping("/api/expense")
public class ExpenseController {

    //@Autowired
    final ExpenseRepository expenseRepository;
    final CategoryRepository categoryRepository;

    public ExpenseController(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String expenseString(){
        return "Expense example";
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

        categoryRepository.findById(addDto.getCategoryId())
                .orElseThrow(()->new BadRequestException("Nu " +
                        "exista categoria selectata"));

        Expense newExpense = new Expense();
        newExpense.setAmount(addDto.getAmount());
        newExpense.setDescription(addDto.getDescription());
        newExpense.setDate(addDto.getDate());
        newExpense.setCategoryId(addDto.getCategoryId());

        return expenseRepository.save(newExpense);
    }


    @PatchMapping("/updateExpense/{id}")
    Expense updateExpense(
            @PathVariable Integer id,
            @RequestBody UpdateExpenseDto updateDto){

        Expense updatedExpense = expenseRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("No expense" +
                        "available for this id: " + id));


        updatedExpense.setAmount(updateDto.getAmount());
        updatedExpense.setDescription(updateDto.getDescription());
        updatedExpense.setDate(updateDto.getDate());
        updatedExpense.setCategoryId(updateDto.getCategoryId());

        return expenseRepository.save(updatedExpense);
    }


    @DeleteMapping("/deleteExpense/{id}")
    ResponseEntity<String> deleteExpense(@PathVariable Integer id){
        Expense deletedExpense = expenseRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("No expense" +
                "available for this id: " + id));

        expenseRepository.delete(deletedExpense);
        return ResponseEntity.ok("Your expense was successfully deleted!");
    }


    @GetMapping("/category/{categoryId}")
    List<Expense> gellAllExpensesByCategoryId(
            @PathVariable Integer categoryId
    ) {
        categoryRepository.findById(categoryId)
                .orElseThrow(()->new BadRequestException("Nu " +
                        "exista categoria selectata"));

        return expenseRepository.findAllByCategoryId(categoryId);
    }
}
