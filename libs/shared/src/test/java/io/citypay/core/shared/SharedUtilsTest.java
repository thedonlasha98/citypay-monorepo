package io.citypay.core.shared;

import io.citypay.core.shared.util.SharedUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SharedUtilsTest {
    @Test
    void formatAmount() {
        assertEquals("$1.23", SharedUtils.formatAmount(123));
    }
}
