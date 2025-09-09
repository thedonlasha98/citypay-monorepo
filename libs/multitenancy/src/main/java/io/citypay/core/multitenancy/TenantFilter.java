package io.citypay.core.multitenancy;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter implements Filter {
  private final MultitenancyProperties props;

  public TenantFilter(MultitenancyProperties props) {
    this.props = props;
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    try {
      HttpServletRequest http = (HttpServletRequest) req;
      String tenantId = http.getHeader(props.getHeader());
      if (tenantId != null && !tenantId.isBlank()) {
        TenantContext.setTenantId(tenantId.trim());
      }
      chain.doFilter(req, res);
    } finally {
      TenantContext.clear();
    }
  }
}
