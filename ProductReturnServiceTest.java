package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.BadProductReturnCountException;
import ru.shop.model.Order;
import ru.shop.model.ProductReturn;
import ru.shop.repository.ProductReturnRepository;


import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductReturnServiceTest {

    private ProductReturnService productReturnService;
    private ProductReturnRepository repository;
    private Order order;


    @Test
    public void shouldGetProductReturn() {
        // given
        UUID Id = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        ProductReturn mockedProductReturn = new ProductReturn(Id, orderId, LocalDate.now(), 10);
        when(repository.findById(Id)).thenReturn(Optional.of(mockedProductReturn));

        // when
        ProductReturn productReturn = productReturnService.getById(Id);

        // then
        assertThat(productReturn).isEqualTo(mockedProductReturn);
    }

    @Test
    public void shouldThrowWhenProductReturnNotFound() {
        // then

        assertThatThrownBy(() -> productReturnService.getById(UUID.randomUUID())).isInstanceOf(BadProductReturnCountException.class);
    }
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ProductReturnRepository.class);
        productReturnService = new ProductReturnService(repository);
        order = new Order(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),1,1);
    }

    @Test
    void testAddProductReturn_InvalidCount() {
        long count = 11;
        Assertions.assertThrows(BadProductReturnCountException.class, () -> {
            productReturnService.add(order, count);
        });

        verify(repository, Mockito.never()).save(Mockito.any(ProductReturn.class));
    }
    @Test
    public void testSaveProductReturn() {
        // Given
        ProductReturn productReturn = new ProductReturn(/* Add necessary parameters */);

        // When
        productReturnService.save(productReturn);

        // Then
        verify(repository).save(productReturn);
    }
}
