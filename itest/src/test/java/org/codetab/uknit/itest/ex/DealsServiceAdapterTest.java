package org.codetab.uknit.itest.ex;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class DealsServiceAdapterTest {
    @InjectMocks
    private DealsServiceAdapter dealsServiceAdapter;

    @Mock
    private DealsServiceClient dealsService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSafeGetDealsTry() throws Exception {
        String userId = "Foo";

        Deal deal = Mockito.mock(Deal.class);
        List<Deal> list = new ArrayList<>();
        list.add(deal);

        when(dealsService.getDeals(any(GetDealsRequest.class)))
                .thenReturn(deal);
        when(deal.getActiveDeals()).thenReturn(list);

        List<DisplayDeal> actual = dealsServiceAdapter.safeGetDeals(userId);

    }

    // @Test
    // public void testSafeGetDealsTryCatchServiceException() throws Exception {
    // String userId = "Foo";
    // GetDealsRequest request = new GetDealsRequest();
    // Deal deal = Mockito.mock(Deal.class);
    // List<Deal> list = new ArrayList<>();
    //
    // when(dealsService.getDeals(request)).thenReturn(deal);
    // when(deal.getActiveDeals()).thenReturn(list);
    //
    // List<DisplayDeal> actual = dealsServiceAdapter.safeGetDeals(userId);
    // fail("unable to assert, STEPIN");
    // }
    //
    // @Test
    // public void testSafeGetDealsTryCatchNetworkException() throws Exception {
    // String userId = "Foo";
    // GetDealsRequest request = new GetDealsRequest();
    // Deal deal = Mockito.mock(Deal.class);
    // List<Deal> list = new ArrayList<>();
    //
    // when(dealsService.getDeals(request)).thenReturn(deal);
    // when(deal.getActiveDeals()).thenReturn(list);
    //
    // List<DisplayDeal> actual = dealsServiceAdapter.safeGetDeals(userId);
    // fail("unable to assert, STEPIN");
    // }
}