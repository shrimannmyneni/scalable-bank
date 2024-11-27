package com.shrimannmyneni.scalable_bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {
    @Schema(
            name="Email Recipient"
    )
    private String recipient;
    @Schema(
            name="Email Subject"
    )
    private String subject;
    @Schema(
            name="Email Body"
    )
    private String body;
    @Schema(
            name="Email Attachment"
    )
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
