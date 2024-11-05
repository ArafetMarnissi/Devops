package tn.esprit.devops_project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.StockServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {
    @Mock
    StockRepository stockRepository;
    @InjectMocks
    StockServiceImpl stockService;
    @Test
    public void testAddStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setTitle("Test Stock");

        when(stockRepository.save(stock)).thenReturn(stock);

        Stock createdStock = stockService.addStock(stock);

        assertNotNull(createdStock);
        assertEquals(stock.getTitle(), createdStock.getTitle());
        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    public void testRetrieveStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setTitle("Test Stock");

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Stock retrievedStock = stockService.retrieveStock(1L);

        assertNotNull(retrievedStock);
        assertEquals(stock.getIdStock(), retrievedStock.getIdStock());
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveStockNotFound() {
        when(stockRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            stockService.retrieveStock(1L);
        });

        assertEquals("Stock not found", exception.getMessage());
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveAllStock() {
        Stock stock1 = new Stock();
        stock1.setIdStock(1L);
        stock1.setTitle("Test Stock 1");

        Stock stock2 = new Stock();
        stock2.setIdStock(2L);
        stock2.setTitle("Test Stock 2");

        when(stockRepository.findAll()).thenReturn(Arrays.asList(stock1, stock2));

        List<Stock> stocks = stockService.retrieveAllStock();

        assertNotNull(stocks);
        assertEquals(2, stocks.size());
        verify(stockRepository, times(1)).findAll();
    }
}


