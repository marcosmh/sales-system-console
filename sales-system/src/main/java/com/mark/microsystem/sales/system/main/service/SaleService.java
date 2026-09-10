package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.exception.ResourceNotFoundException;
import com.mark.microsystem.sales.system.main.model.dto.*;
import com.mark.microsystem.sales.system.main.model.entity.Product;
import com.mark.microsystem.sales.system.main.model.entity.Sale;
import com.mark.microsystem.sales.system.main.model.entity.SaleDetail;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import com.mark.microsystem.sales.system.main.repository.ProductRepository;
import com.mark.microsystem.sales.system.main.repository.SaleRepository;
import com.mark.microsystem.sales.system.main.repository.UserPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleService implements ISaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final UserPersonRepository userRepository;

    @Override
    public SaleResponse createSale(SaleCreateRequest request) {
        UserPerson user = userRepository.findById(request.userId())
                        .orElseThrow( () ->
                                new ResourceNotFoundException("User not found."));

        Sale sale = Sale.builder()
                .user(user)
                .date(LocalDateTime.now())
                .details(new ArrayList<>())
                .build();

        for(SaleDetailCreateRequest detailRequest : request.details()) {

            Product product = productRepository
                    .findById(detailRequest.productId())
                    .orElseThrow( ()->
                            new ResourceNotFoundException("Product not found: " + detailRequest.productId()));

            validateStock(product, detailRequest.quantity());

            // update stock
            product.setStock( product.getStock() - detailRequest.quantity() );

            // create detail
            SaleDetail detail = SaleDetail.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(detailRequest.quantity())
                    .build();

            // calculate total
            detail.setSubtotal(detail.subTotal());

            sale.getDetails().add(detail);

        }

        sale.setTotal(sale.total());

        Sale savedSale = saleRepository.save(sale);

        return toSaleResponse(savedSale);
    }

    private void validateStock(Product product, Integer quantity) {
        if(product.getStock() == null) {
            throw  new IllegalStateException("Product stock is not configured: " + product.getName());
        }

        if(product.getStock() < quantity) {
            throw new IllegalStateException("Insufficient stock for product: "
            + product.getName() + ". Available: " + product.getStock() + ", requested: " + quantity);
        }
    }

    private SaleResponse toSaleResponse(Sale sale) {
        UserSummaryResponse userResponse = new UserSummaryResponse(
                sale.getUser().getId(),
                sale.getUser().getName(),
                sale.getUser().getUsername()
        );

        List<SaleDetailResponse> details = sale.getDetails()
                .stream()
                .map(this::toSaleDetailResponse)
                .toList();

        return new SaleResponse(
                sale.getId(),
                userResponse,
                sale.getDate(),
                sale.getTotal(),
                details,
                sale.getCreatedAt(),
                sale.getUpdatedAt()
        );

    }

    private SaleDetailResponse toSaleDetailResponse(SaleDetail saleDetail) {
        Product product = saleDetail.getProduct();
        ProductSummaryResponse productoResponse = new ProductSummaryResponse(
                product.getId(),
                product.getName(),
                product.getPrice() );

        return new SaleDetailResponse(
                saleDetail.getId(),
                productoResponse,
                saleDetail.getQuantity(),
                saleDetail.getSubtotal(),
                saleDetail.getCreatedAt(),
                saleDetail.getUpdatedAt() );

    }


}
