package com.shrimannmyneni.scalable_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {
    private String recipient;
    private String subject;
    private String body;
    private String attachment;

//    public String getRecipient() {
//        return recipient;
//    }
//
//    // Getter for subject
//    public String getSubject() {
//        return subject;
//    }
//
//    // Getter for body
//    public String getBody() {
//        return body;
//    }
//
//    // Getter for attachment
//    public String getAttachment() {
//        return attachment;
//    }
}
