package com.rabigol.wowmoneywebapp.сontrollers;

import com.rabigol.wowmoneywebapp.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.rabigol.wowmoneywebapp.service.user.UserServiceImpl.getUserId;
import static com.rabigol.wowmoneywebapp.service.user.UserServiceImpl.getUsername;
import static com.rabigol.wowmoneywebapp.utils.Helper.valueCheck;

@Controller
@Slf4j
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private OperationRepository repository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private OperationCategoryRepository operationCategoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String listOperations(Model model) {
        // add list of operations
        model.addAttribute("operations", repository.findAllDescTimeStamp(getUserId()));

        model.addAttribute("getCurrencyRUB", repository.getCurrencyRub());
        model.addAttribute("getCurrencyUSD", repository.getCurrencyUsd());
        model.addAttribute("getCurrencyEUR", repository.getCurrencyEur());

        model.addAttribute("username", getUsername());

        model.addAttribute("totalBalance", getTotalBalance(repository.getCurrencyRub(), repository.getCurrencyUsd(), repository.getCurrencyEur()));

        //Wallet balance
        model.addAttribute("walletBalanceRub", getSum(getUserId(), repository.getCurrencyRub(), "Wallet"));
        model.addAttribute("walletBalanceUsd", getSum(getUserId(), repository.getCurrencyUsd(), "Wallet"));
        model.addAttribute("walletBalanceEur", getSum(getUserId(), repository.getCurrencyEur(), "Wallet"));

        //Bank card balance
        model.addAttribute("bankCardBalanceRub", getSum(getUserId(), repository.getCurrencyRub(), "Bank card"));
        model.addAttribute("bankCardBalanceUsd", getSum(getUserId(), repository.getCurrencyUsd(), "Bank card"));
        model.addAttribute("bankCardBalanceEur", getSum(getUserId(), repository.getCurrencyEur(), "Bank card"));

        //Bank account balance
        model.addAttribute("bankAccountBalanceRub", getSum(getUserId(), repository.getCurrencyRub(), "Bank account"));
        model.addAttribute("bankAccountBalanceUsd", getSum(getUserId(), repository.getCurrencyUsd(), "Bank account"));
        model.addAttribute("bankAccountBalanceEur", getSum(getUserId(), repository.getCurrencyEur(), "Bank account"));

        return "operations/list";
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public String statistics(Model model) {
        // All spents and all incomes
        model.addAttribute("outcomeSum", -1 * getStatSum(getUserId(), "Outcome"));
        model.addAttribute("incomeSum", getStatSum(getUserId(), "Income"));
        model.addAttribute("currency", repository.getCurrencyRub());

        int incomeRatio = (int) (100* getStatSum(getUserId(), "Income") / (getStatSum(getUserId(), "Income") - getStatSum(getUserId(), "Outcome")));
        int outcomeRatio = (int) (-100* getStatSum(getUserId(), "Outcome") / (getStatSum(getUserId(), "Income") - getStatSum(getUserId(), "Outcome")));
        model.addAttribute("incomeRatio", incomeRatio);
        model.addAttribute("outcomeRatio", outcomeRatio);

        return "operations/statistics";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable long id) {
        repository.delete(id);
        return new ModelAndView("redirect:/operations");
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProject(Model model) {
        model.addAttribute("operationTypes", operationTypeRepository.getOperationTypes());
        model.addAttribute("operationCategories", operationCategoryRepository.getOperationCategory());
        model.addAttribute("accounts", accountRepository.getAccounts());
        model.addAttribute("currencies", currencyRepository.getCurrencies());
        return "operations/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam("operationType") String operationType,
                               @RequestParam("operationCategory") String operationCategory,
                               @RequestParam("account") String account,
                               @RequestParam("value") Double value,
                               @RequestParam("currency") String currency,
                               @RequestParam("description") String description) {

        value = value * 100;
        if (operationType.equals("Income") || operationType.equals("Transfer")) {
            if (value < 0) value = -1 * value;
        } else if (operationType.equals("Outcome")) {
            if (value > 0) value = -1 * value;
        }
        Operation operation = Operation.builder()
                .operationType(operationType)
                .operationCategory(operationCategory)
                .account(account)
                .value(value.longValue())
                .currency(currency)
                .description(description)
                .build();
        //TODO: set method for setting actual owner ID
        operation.setOwnerId(getUserId());
        //TODO: make currenttimestamp without this long line!
        operation.setTimestamp(System.currentTimeMillis() / 1000);
        repository.save(operation);
        return new ModelAndView("redirect:/operations");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("operation_id") long id,
                               @RequestParam("operationType") String operationType,
                               @RequestParam("operationCategory") String operationCategory,
                               @RequestParam("account") String account,
                               @RequestParam("value") String value,
                               @RequestParam("currency") String currency,
                               @RequestParam("description") String description) {
        Double aDouble = Double.parseDouble(value) * 100;
        Operation operation = repository.findOne(id);
        operation.setOperationType(operationType);
        operation.setOperationCategory(operationCategory);
        operation.setAccount(account);
        operation.setValue(valueCheck(aDouble.longValue(), operationType));
        operation.setCurrency(currency);
        operation.setDescription(description);
        repository.save(operation);
        return new ModelAndView("redirect:/operations");
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id,
                       Model model) {
        Operation operation = repository.findOne(id);
        //Attributes for default values
        model.addAttribute("currentOperationType", operation.getOperationType());
        model.addAttribute("currentOperationCategory", operation.getOperationCategory());
        model.addAttribute("currentOperationAccount", operation.getAccount());
        model.addAttribute("currentOperationValue", operation.getValue());
        model.addAttribute("currentOperationCurrency", operation.getCurrency());
        model.addAttribute("currentOperationDescription", operation.getDescription());

        model.addAttribute("operation", operation);
        model.addAttribute("operationValueEdit", operation.getDoubleValue());
        model.addAttribute("operationTypesEdit", operationTypeRepository.getOperationTypes());
        model.addAttribute("operationCategoriesEdit", operationCategoryRepository.getOperationCategory());
        model.addAttribute("accountsEdit", accountRepository.getAccounts());
        model.addAttribute("currenciesEdit", currencyRepository.getCurrencies());
        return "operations/edit";
    }

    // Method that returns balance in rouble (can be upgraded to "in maincurrency")
    private Double getTotalBalance(String rub, String usd, String eur) {
        Double totalBalance = (getSumByCurrency(getUserId(), rub)
                + getSumByCurrency(getUserId(), usd) * repository.getExhangeRate(usd) / 100
                + getSumByCurrency(getUserId(), eur) * repository.getExhangeRate(eur) / 100);
        return totalBalance;
    }

    private Double getSum(Long userId, String currency, String account) {
        Double value = repository.getSum(userId, currency, account);
        if (value == null) {
            value = 0d;
        }
        return value / 100;
    }

    private Double getSumByCurrency(Long userId, String currency) {
        Double value = repository.getSum(userId, currency);
        if (value == null) {
            value = 0d;
        }
        return value / 100;
    }

    private Double getStatSum(Long userId, String operationType) {
        Double result = 0.0;
        result = repository.getStatSum(userId, operationType, "RUB")
                + repository.getStatSum(userId, operationType, "USD") * repository.getExhangeRate("USD") / 100
                + repository.getStatSum(userId, operationType, "EUR") * repository.getExhangeRate("EUR") / 100;
        return result / 100;
    }
}
