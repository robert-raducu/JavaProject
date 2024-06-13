package com.java_project.JavaProject.api.controller;
//Business logic

import com.java_project.JavaProject.api.dto.expenseDto.AddExpenseDto;
import com.java_project.JavaProject.api.dto.expenseDto.UpdateExpenseDto;
import com.java_project.JavaProject.domain.category.CategoryRepository;
import com.java_project.JavaProject.domain.expense.Expense;
import com.java_project.JavaProject.domain.expense.ExpenseRepository;
import com.java_project.JavaProject.domain.user.User;
import com.java_project.JavaProject.domain.user.UserRepository;
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

    final UserRepository userRepository;

    public ExpenseController(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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
                .orElseThrow(()->new BadRequestException("The selected category does not exist"));
        User userTemp = userRepository.findById(addDto.getUserId())
                .orElseThrow(()->new BadRequestException("The selected user does not exist"));

        if (addDto.getAmount() > userTemp.getRemainingSalary()) {
            throw new BadRequestException("Expense amount exceeds remaining salary.");
        }

        //RemainingSalary logic
        double remainingSalary = userTemp.getRemainingSalary();
        remainingSalary -= addDto.getAmount();
        userTemp.setRemainingSalary(remainingSalary);
        userRepository.save(userTemp);

        Expense newExpense = new Expense();
        newExpense.setAmount(addDto.getAmount());
        newExpense.setDescription(addDto.getDescription());
        newExpense.setDate(addDto.getDate());
        newExpense.setCategoryId(addDto.getCategoryId());
        newExpense.setUserId(addDto.getUserId());

        return expenseRepository.save(newExpense);
    }


    @PatchMapping("/updateExpense/{id}")
    Expense updateExpense(
            @PathVariable Integer id,
            @RequestBody UpdateExpenseDto updateDto){

        Expense updatedExpense = expenseRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("No expense" +
                        "available for this id: " + id));

        User userTemp = userRepository.findById(updatedExpense.getUserId())
                .orElseThrow(()->new BadRequestException("The selected user does not exist"));


        double remainingSalary = userTemp.getRemainingSalary();
        remainingSalary += updatedExpense.getAmount();


        if (updateDto.getAmount() > remainingSalary) {
            throw new BadRequestException("New expense amount exceeds remaining salary.");
        }

        // Update remaining salary
        remainingSalary -= updateDto.getAmount();
        userTemp.setRemainingSalary(remainingSalary);
        userRepository.save(userTemp);

        updatedExpense.setAmount(updateDto.getAmount());
        updatedExpense.setDescription(updateDto.getDescription());
        updatedExpense.setDate(updateDto.getDate());
        updatedExpense.setCategoryId(updateDto.getCategoryId());
        updatedExpense.setUserId(updateDto.getUserId());


        return expenseRepository.save(updatedExpense);
    }


    @DeleteMapping("/deleteExpense/{id}")
    ResponseEntity<String> deleteExpense(@PathVariable Integer id){
        Expense deletedExpense = expenseRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("No expense" +
                "available for this id: " + id));

        User userTemp = userRepository.findById(deletedExpense.getUserId())
                .orElseThrow(() -> new BadRequestException("The user associated with this expense does not exist"));

        double remainingSalary = userTemp.getRemainingSalary();
        remainingSalary += deletedExpense.getAmount();
        userTemp.setRemainingSalary(remainingSalary);


        userRepository.save(userTemp);

        expenseRepository.delete(deletedExpense);
        return ResponseEntity.ok("Your expense was successfully deleted!");
    }


    //get all expenses from a category
    @GetMapping("/category/{categoryId}")
    List<Expense> gellAllExpensesByCategoryId(
            @PathVariable Integer categoryId
    ) {
        categoryRepository.findById(categoryId)
                .orElseThrow(()->new BadRequestException("The selected category does not exist"));

        return expenseRepository.findAllByCategoryId(categoryId);
    }
}
