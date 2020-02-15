package com.example.demo.redis;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Setter
@Getter
public class TransactionData implements Serializable {
    private String transactionId;
    private Status status;
}
