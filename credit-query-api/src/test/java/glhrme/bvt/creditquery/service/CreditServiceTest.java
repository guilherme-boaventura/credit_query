package glhrme.bvt.creditquery.service;

import glhrme.bvt.creditquery.repository.CreditRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for CreditService")
class CreditServiceTest {

    @Mock
    private CreditRepository creditRepository;

    @InjectMocks
    private CreditService creditService;

    @Test
    @DisplayName("Should throw exception when NFSe number is null, empty, or only whitespace")
    void shouldThrowExceptionWhenInvoiceNumberIsNullOrBlank() {
        List<String> invalidInvoiceNumbers = Arrays.asList(null, "", "   ");

        for (String invoiceNumber : invalidInvoiceNumbers) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> creditService.findByInvoiceNumber(invoiceNumber));
            assertEquals("An invoice number is required to query credits.", exception.getMessage());
        }

        verify(creditRepository, never()).findByInvoiceNumber(any());
    }

    @Test
    @DisplayName("Should throw exception when credit number is null, empty, or only whitespace")
    void shouldThrowExceptionWhenCreditNumberIsNullOrBlank() {
        List<String> invalidCreditNumbers = Arrays.asList(null, "", "   ");

        for (String credit : invalidCreditNumbers) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> creditService.findByCreditNumber(credit));
            assertEquals("A credit number is required to query a credit.", exception.getMessage());
        }

        verify(creditRepository, never()).findByInvoiceNumber(any());
    }
}