package glhrme.bvt.consulta_credito.controller;

import glhrme.bvt.consulta_credito.model.Credit;
import glhrme.bvt.consulta_credito.service.CreditService;
import glhrme.bvt.consulta_credito.utils.errorhandling.ErrorResponse;
import glhrme.bvt.consulta_credito.utils.kafka.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
@Tag(name = "Credits", description = "Endpoints for querying credits")
public class CreditController {

    @Autowired
    private KafkaProducerService kafkaProducer;

    @Autowired
    private CreditService creditService;

    @Operation(
            summary = "Find credits by invoice number",
            description = "Returns a list of credits associated with the provided invoice number. " +
                    "Returns an empty list if no credits are found."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Query executed successfully. Returns a list of credits (may be empty)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Credit.class)
                    )
            )
    })
    @GetMapping("/{invoiceNumber}")
    public List<Credit> findByInvoiceNumber(
            @Parameter(description = "Invoice number", required = true, example = "1122334")
            @PathVariable("invoiceNumber")  String invoiceNumber) {
        List<Credit> credits = creditService.findByInvoiceNumber(invoiceNumber);

        kafkaProducer.sendMessageInvoiceNumberQuery(invoiceNumber, credits);
        return credits;
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Credit found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Credit.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Credit not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/credito/{creditNumber}")
    public Credit findByCreditNumber(
            @Parameter(description = "Credit number", required = true, example = "123456")
            @PathVariable("creditNumber") String creditNumber) {
        Credit credit = null;

        try {
            credit = creditService.findByCreditNumber(creditNumber);
        } finally {
            kafkaProducer.sendMessageCreditNumberQuery(creditNumber, credit);
        }

        return credit;
    }
}