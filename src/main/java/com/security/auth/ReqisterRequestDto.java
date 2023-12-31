package com.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReqisterRequestDto {
    String  firstname;
    String  lastname;
    String  schoolRole;
    String email;
    String password;
}
