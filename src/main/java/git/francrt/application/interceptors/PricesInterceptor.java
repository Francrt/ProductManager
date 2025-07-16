package git.francrt.application.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PricesInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(PricesInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String productId = request.getParameter("productId");
        String brandId = request.getParameter("brandId");
        String appDate = request.getParameter("appDate");

        if (!validateAtLeastOnePresent(productId, brandId, appDate, response)) return false;
        if (!validateProductIdWithoutAppDate(productId, brandId, appDate, response)) return false;
        if (!validateBrandIdWithoutAppDate(productId, brandId, appDate, response)) return false;

        return true;
    }

    private boolean validateAtLeastOnePresent(String productId, String brandId, String appDate, HttpServletResponse response) throws Exception {
        if (productId == null && brandId == null && appDate == null) {
            log.error("Al menos uno de los parámetros productId, brandId o appDate debe estar presente.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You must at least fill one of the following fields: productId, brandId o appDate.");
            return false;
        }
        return true;
    }

    private boolean validateProductIdWithoutAppDate(String productId, String brandId, String appDate, HttpServletResponse response) throws Exception {
        if (productId != null && appDate == null && brandId != null) {
            log.error("Si productId está presente y appDate no, brandId no debe estar presente.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You cannot send brandId and productId if appDate is not populated.");
            return false;
        }
        return true;
    }

    private boolean validateBrandIdWithoutAppDate(String productId, String brandId, String appDate, HttpServletResponse response) throws Exception {
        if (brandId != null && appDate == null && productId != null) {
            log.error("Si brandId está presente y appDate no, productId no debe estar presente.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You cannot send productId and brandId if appDate is not populated.");
            return false;
        }
        return true;
    }
}