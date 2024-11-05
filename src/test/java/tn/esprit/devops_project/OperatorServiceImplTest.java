package tn.esprit.devops_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.services.OperatorServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperatorServiceImplTest {
    @Mock
    OperatorRepository operatorRepository;
    @InjectMocks
    OperatorServiceImpl operatorService;
    @Test
    void testRetrieveAllOperators() {
        // Arrange
        Operator operator1 = new Operator();
        Operator operator2 = new Operator();
        when(operatorRepository.findAll()).thenReturn(Arrays.asList(operator1, operator2));

        // Act
        List<Operator> operators = operatorService.retrieveAllOperators();

        // Assert
        assertNotNull(operators);
        assertEquals(2, operators.size());
        verify(operatorRepository, times(1)).findAll();
    }

    @Test
    void testAddOperator() {
        // Arrange
        Operator operator = new Operator();
        operator.setFname("John");
        when(operatorRepository.save(any(Operator.class))).thenReturn(operator);

        // Act
        Operator result = operatorService.addOperator(operator);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFname());
        verify(operatorRepository, times(1)).save(operator);
    }

    @Test
    void testDeleteOperator() {
        // Arrange
        Long operatorId = 1L;
        doNothing().when(operatorRepository).deleteById(operatorId);

        // Act
        operatorService.deleteOperator(operatorId);

        // Assert
        verify(operatorRepository, times(1)).deleteById(operatorId);
    }

    @Test
    void testUpdateOperator() {
        // Arrange
        Operator operator = new Operator();
        operator.setFname("John");
        when(operatorRepository.save(any(Operator.class))).thenReturn(operator);

        // Act
        Operator result = operatorService.updateOperator(operator);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFname());
        verify(operatorRepository, times(1)).save(operator);
    }

    @Test
    void testRetrieveOperator() {
        // Arrange
        Long operatorId = 1L;
        Operator operator = new Operator();
        operator.setIdOperateur(operatorId);
        when(operatorRepository.findById(operatorId)).thenReturn(Optional.of(operator));

        // Act
        Operator result = operatorService.retrieveOperator(operatorId);

        // Assert
        assertNotNull(result);
        assertEquals(operatorId, result.getIdOperateur());
        verify(operatorRepository, times(1)).findById(operatorId);
    }

    @Test
    void testRetrieveOperatorNotFound() {
        // Arrange
        Long operatorId = 1L;
        when(operatorRepository.findById(operatorId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            operatorService.retrieveOperator(operatorId);
        });

        assertEquals("Operator not found", exception.getMessage());
        verify(operatorRepository, times(1)).findById(operatorId);
    }
}
