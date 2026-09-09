package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.model.dto.SaleCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.SaleResponse;

public interface ISaleService  {

    SaleResponse createSale(SaleCreateRequest request);




}
