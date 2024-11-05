package tn.esprit.devops_project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testAddProduct() {
        // Arrange
        Long stockId = 1L;
        Stock stock = new Stock(stockId, "stock1", new HashSet<>());
        Product product = new Product(null, "product1", 14.2f, 50, ProductCategory.BOOKS, null);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setIdProduct(1L); // Simulate auto-generated ID
            return savedProduct;
        });

        // Act
        Product result = productService.addProduct(product, stockId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdProduct());
        assertEquals("product1", result.getTitle());
        assertEquals(stock, result.getStock());
        verify(stockRepository, times(1)).findById(stockId);
        verify(productRepository, times(1)).save(product);
    }


    @Test
    public void testRetrieveProduct() {
        Long productId = 1L;
        Product product = new Product(productId, "product1", 14.2f, 50, ProductCategory.BOOKS, null);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.retrieveProduct(productId);

        assertNotNull(result);
        assertEquals(productId, result.getIdProduct());
        verify(productRepository, times(1)).findById(productId);
    }


    @Test
    public void testRetrieveAllProduct() {
        Product product1 = new Product(1L, "product1", 14.2f, 50, ProductCategory.BOOKS, null);
        Product product2 = new Product(2L, "product2", 20.0f, 30, ProductCategory.ELECTRONICS, null);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<Product> result = productService.retreiveAllProduct();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }





    @Test
    public void testRetrieveProductByCategory() {
        // Arrange
        ProductCategory category = ProductCategory.BOOKS;
        Product product1 = new Product(1L, "product1", 14.2f, 50, category, null);
        Product product2 = new Product(2L, "product2", 20.0f, 30, category, null);

        when(productRepository.findByCategory(category)).thenReturn(List.of(product1, product2));

        // Act
        List<Product> result = productService.retrieveProductByCategory(category);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(category, result.get(0).getCategory());
        assertEquals(category, result.get(1).getCategory());
        verify(productRepository, times(1)).findByCategory(category);
    }


    @Test
    public void testRetrieveProductStock() {
        // Arrange
        Long stockId = 1L;
        Stock stock = new Stock(stockId, "stock1", new HashSet<>());
        Product product1 = new Product(1L, "product1", 14.2f, 50, ProductCategory.BOOKS, stock);
        Product product2 = new Product(2L, "product2", 20.0f, 30, ProductCategory.ELECTRONICS, stock);

        when(productRepository.findByStockIdStock(stockId)).thenReturn(List.of(product1, product2));

        // Act
        List<Product> result = productService.retreiveProductStock(stockId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(stockId, result.get(0).getStock().getIdStock());
        verify(productRepository, times(1)).findByStockIdStock(stockId);
    }


    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }



}
