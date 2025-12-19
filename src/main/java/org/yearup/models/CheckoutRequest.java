package org.yearup.models;

import java.util.List;

public class CheckoutRequest
{
    private List<Integer> selectedProductIds;

    public CheckoutRequest() {}

    public CheckoutRequest(List<Integer> selectedProductIds)
    {
        this.selectedProductIds = selectedProductIds;
    }

    public List<Integer> getSelectedProductIds()
    {
        return selectedProductIds;
    }

    public void setSelectedProductIds(List<Integer> selectedProductIds)
    {
        this.selectedProductIds = selectedProductIds;
    }
}
