package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.InvoiceDetail;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class InvoiceDetailServiceImplTest {

    @InjectMocks
    private InvoiceDetailServiceImpl invoiceDetailService;

    @Mock
    private InvoiceDetailRepository invoiceDetailRepository;

    private Product product;
    private Invoice invoice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Initialize Product and Invoice with necessary values
        product = new Product(1L, "Product A", 50.0f, 100, null, null); // ProductCategory and Stock can be null for simplicity
        invoice = new Invoice(1L, 10.0f, 200.0f, new Date(), new Date(), false, new HashSet<>(), null);
    }

    @Test
    public void testRetrieveAllInvoiceDetails() {
        // Given
        InvoiceDetail invoiceDetail1 = new InvoiceDetail(1L, 2, 100.0f, product, invoice);
        InvoiceDetail invoiceDetail2 = new InvoiceDetail(2L, 3, 200.0f, product, invoice);
        List<InvoiceDetail> mockInvoiceDetails = Arrays.asList(invoiceDetail1, invoiceDetail2);

        when(invoiceDetailRepository.findAll()).thenReturn(mockInvoiceDetails);

        // When
        List<InvoiceDetail> result = invoiceDetailService.retrieveAllInvoiceDetails();

        // Then
        assertEquals(2, result.size());
        assertEquals(100.0f, result.get(0).getPrice());
        assertEquals(3, result.get(1).getQuantity());
    }

    @Test
    public void testAddInvoiceDetail() {
        // Given
        InvoiceDetail newInvoiceDetail = new InvoiceDetail(null, 2, 100.0f, product, invoice);

        when(invoiceDetailRepository.save(newInvoiceDetail)).thenReturn(new InvoiceDetail(1L, 2, 100.0f, product, invoice));

        // When
        InvoiceDetail savedInvoiceDetail = invoiceDetailService.addInvoiceDetail(newInvoiceDetail);

        // Then
        assertEquals(1L, savedInvoiceDetail.getIdInvoiceDetail());
        assertEquals(100.0f, savedInvoiceDetail.getPrice());
        assertEquals(2, savedInvoiceDetail.getQuantity());
    }

//    @Test
//    public void testUpdateInvoiceDetail() {
//        // Given
//        InvoiceDetail existingInvoiceDetail = new InvoiceDetail(1L, 2, 100.0f, product, invoice);
//        InvoiceDetail updatedInvoiceDetail = new InvoiceDetail(1L, 3, 150.0f, product, invoice);
//
//        when(invoiceDetailRepository.findById(1L)).thenReturn(Optional.of(existingInvoiceDetail));
//        when(invoiceDetailRepository.save(updatedInvoiceDetail)).thenReturn(updatedInvoiceDetail);
//
//        // When
//        InvoiceDetail result = invoiceDetailService.updateInvoiceDetail(1L, updatedInvoiceDetail);
//
//        // Then
//        assertEquals(1L, result.getIdInvoiceDetail());
//        assertEquals(3, result.getQuantity());
//        assertEquals(150.0f, result.getPrice());
//    }

    @Test
    public void testDeleteInvoiceDetail() {
        // Given
        InvoiceDetail invoiceDetail = new InvoiceDetail(1L, 2, 100.0f, product, invoice);

        when(invoiceDetailRepository.findById(1L)).thenReturn(Optional.of(invoiceDetail));

        // When
        invoiceDetailService.deleteInvoiceDetail(1L);

        // Then
        verify(invoiceDetailRepository, times(1)).delete(invoiceDetail);
    }

    @Test
    public void testRetrieveInvoiceDetail() {
        // Given
        InvoiceDetail invoiceDetail = new InvoiceDetail(1L, 2, 100.0f, product, invoice);

        when(invoiceDetailRepository.findById(1L)).thenReturn(Optional.of(invoiceDetail));

        // When
        InvoiceDetail result = invoiceDetailService.retrieveInvoiceDetail(1L);

        // Then
        assertEquals(1L, result.getIdInvoiceDetail());
        assertEquals(100.0f, result.getPrice());
        assertEquals(2, result.getQuantity());
    }

    @Test
    public void testRetrieveInvoiceDetail_NotFound() {
        // Given
        when(invoiceDetailRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            invoiceDetailService.retrieveInvoiceDetail(1L);
        });
    }
}
