package org.codetab.uknit.itest.ex;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DealsServiceAdapter {

    private static final Logger LOG =
            Logger.getLogger(DealsServiceAdapter.class.getName());

    private final DealsServiceClient dealsService;

    public DealsServiceAdapter(final DealsServiceClient dealsService) {
        this.dealsService = dealsService;
    }

    public List<DisplayDeal> safeGetDeals(final String userId) {
        try {
            final GetDealsRequest request = new GetDealsRequest();
            request.setUserId(userId);
            return convertToDisplayDeals(
                    dealsService.getDeals(request).getActiveDeals());
        } catch (final ServiceException | NetworkException e) {
            LOG.log(Level.WARNING, "Exception querying deals service.", e);
            return Collections.emptyList();
        }
    }

    private List<DisplayDeal> convertToDisplayDeals(final List<Deal> deals) {
        // Omitted: code to convert deals into display deals.
        return Collections.emptyList();
    }
}

interface DisplayDeal {

}

interface Deal {
    List<Deal> getActiveDeals();
}

class GetDealsRequest {
    public void setUserId(final String userId) {
    }
}

interface DealsServiceClient {
    Deal getDeals(GetDealsRequest request)
            throws ServiceException, NetworkException;
}

class NetworkException extends Exception {
    private static final long serialVersionUID = 1L;
}

class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;
}
