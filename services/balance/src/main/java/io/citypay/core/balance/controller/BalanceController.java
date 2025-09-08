package io.citypay.core.balance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
public class BalanceController {

  @RequestMapping()
  public String getBalance() {
    return "Hello World!";
  }
}
