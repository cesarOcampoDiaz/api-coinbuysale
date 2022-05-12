package com.nttdata.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="buy_sale")
public class BuySale {
    @Id
    private String id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    private Double purchaseAmount;
    private Double saleAmount;

}
