package io.citypay.core.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.citypay.core.shared.util.SharedUtils;

class SharedUtilsTest {
  @Test
  void formatAmount() {
    assertEquals("$1.23", SharedUtils.formatAmount(123));
  }
}
