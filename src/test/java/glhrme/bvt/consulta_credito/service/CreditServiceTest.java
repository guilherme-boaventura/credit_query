package glhrme.bvt.consulta_credito.service;

import glhrme.bvt.consulta_credito.model.Credit;
import glhrme.bvt.consulta_credito.repository.CreditRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for CreditService")
class CreditServiceTest {

    @Mock
    private CreditRepository creditRepository;

    @InjectMocks
    private CreditService creditService;

    @Test
    @DisplayName("Must return the credit list if there is an valid invoice")
    void mustReturnCreditListIfValidInvoice() {
        String numeroNfse = "1122334";



        creditRepository.findByInvoiceNumber(numeroNfse);

        List<Credit> resultado = creditService.findByInvoiceNumber(numeroNfse);

        assertNotNull(resultado);
//        assertEquals(creditosEsperados, resultado);
        verify(creditRepository, times(1)).findByInvoiceNumber(numeroNfse);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando NFSe válida não possui créditos")
    void deveRetornarListaVaziaQuandoNenhumCreditoEncontrado() {
        String numeroNfse = "231321321321312";

        when(creditRepository.findByInvoiceNumber(numeroNfse)).thenReturn(new ArrayList<>());

        List<Credit> resultado = creditService.findByInvoiceNumber(numeroNfse);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(creditRepository, times(1))
                .findByInvoiceNumber(numeroNfse);
    }

    @Test
    @DisplayName("Deve lançar exceção quando número da NFSe for nulo, vazio ou apenas espaços")
    void deveLancarExcecaoQuandoNumeroNfseInvalido() {
        List<String> numerosNfseInvalidos = Arrays.asList(null, "", "   ");

        for (String numeroNfse : numerosNfseInvalidos) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                                              () -> creditService.findByInvoiceNumber(numeroNfse));
            assertEquals("Um número de nota fiscal eletrônica deve ser informado para consulta.", exception.getMessage());
        }

        verify(creditRepository, never()).findByInvoiceNumber(any());
    }
}