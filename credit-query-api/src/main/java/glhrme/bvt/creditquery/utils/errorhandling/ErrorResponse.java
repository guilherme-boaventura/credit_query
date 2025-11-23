package glhrme.bvt.creditquery.utils.errorhandling;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response structure")
public record ErrorResponse(

        @Schema(description = "Error type", example = "Record not found.")
        String error,

        @Schema(description = "Detailed error message", example = "There is no credit with the given number.")
        String message,

        @Schema(description = "HTTP status code", example = "404")
        int status
) {}