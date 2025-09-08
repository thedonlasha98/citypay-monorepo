package io.citypay.core.shared;

import static org.junit.jupiter.api.Assertions.*;

import io.citypay.core.shared.util.SharedUtils;
import org.junit.jupiter.api.Test;

class SharedUtilsTest {
  @Test
  void formatAmount() {
    assertEquals("$1.23", SharedUtils.formatAmount(123));
  }
}
